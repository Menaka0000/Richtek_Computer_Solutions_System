package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import model.Product;
import otherClass.Validator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class StockUpdateController extends AddNewProductController {
    public TableView tblItem;
    public TableColumn colItemId;
    public TableColumn colName;
    public TableColumn colBrand;
    public TableColumn colSeries;
    public TableColumn colModelId;
    public TableColumn colType;
    public TableColumn colAvailability;
    public TableColumn colQtyOnHand;
    public TableColumn colPrize;
    public ComboBox cmbItemType;
    public JFXTextField txtItemId;
    public JFXTextField txtBrand;
    public JFXTextField txtItemName;
    public JFXTextField txtModelId;
    public JFXTextField txtSeries;
    public JFXTextField txtQty;
    public JFXTextField txtPrice;
    public JFXTextArea txtMOreDetails;

    public void initialize() throws SQLException, ClassNotFoundException {
        validateInit();
        colItemId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colSeries.setCellValueFactory(new PropertyValueFactory<>("series"));
        colModelId.setCellValueFactory(new PropertyValueFactory<>("modelNum"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colPrize.setCellValueFactory(new PropertyValueFactory<>("prize"));

        String[] name = { "LAPTOP","PROCESSOR","MOTHERBOARD","MEMORY (RAM)","GRAPHIC CARDS","POWER SUPPLY","COOLERS","STORAGE","CASINGS","MONITOR","KEYBOARD","GAMING MOUSE" };
        List<String> itemName = new ArrayList(Arrays.asList(name));
        cmbItemType.getItems().addAll(itemName);
        loadDataToTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadDataToTextFields((Product) newValue);
        });
    }

    private void loadDataToTextFields(Product newValue) {
        txtItemId.setText(newValue.getProductId());
        txtItemName.setText(newValue.getName());
        txtBrand.setText(newValue.getBrand());
        txtSeries.setText(newValue.getSeries());
        txtModelId.setText(newValue.getModelNum());
        cmbItemType.setValue(newValue.getType());
        txtMOreDetails.setText(newValue.getMoreDetail());
        txtQty.setText(String.valueOf(newValue.getQtyOnHand()));
        txtPrice.setText(newValue.getPrize());
        Image image = new Image(getClass().getResourceAsStream(newValue.getImageAddress()));
        imageView.setImage(image);
        imageAddress=newValue.getImageAddress();
    }



    public void updateOnAction(ActionEvent actionEvent) {
        Object response = Validator.validate(allValidations);
        if (response instanceof JFXTextField) {
            JFXTextField textField = (JFXTextField) response;
            new Alert(Alert.AlertType.WARNING,"Incompatible input in "+textField.getPromptText()).show();return;
        }
        try {
            String availability="In Stock";
            if (Integer.parseInt(txtQty.getText())<=0){availability="Out Of Stock";}
            Product product =new Product(txtItemId.getText(),txtItemName.getText(),txtBrand.getText(),txtSeries.getText(),txtModelId.getText(),
                    (String) cmbItemType.getValue(),txtMOreDetails.getText(),availability,Integer.parseInt(txtQty.getText()),
                    txtPrice.getText(),imageAddress);
            if(new MainItemController().UpdateItem(product)){
                loadDataToTable();
                tblItem.refresh();
                new Alert(Alert.AlertType.CONFIRMATION,txtItemId.getText()+"was Updated Successfully").show();
            }
        } catch (SQLException | ClassNotFoundException | NumberFormatException throwables) {
            throwables.printStackTrace();
            new Alert(Alert.AlertType.WARNING,throwables.getMessage()).show();
        }
    }


    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            if(new MainItemController().deleteItem(txtItemId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,txtItemId.getText()+" item was deleted successfully...").show();
                loadDataToTable();
                tblItem.refresh();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void loadDataToTable()  {
        try {
            tblItem.setItems(new MainItemController().loadProductData());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern namePattern = Pattern.compile("^.{5,}$");
    Pattern brandPattern = Pattern.compile("^.{2,}$");
    Pattern modelPattern = Pattern.compile("^.{2,}$");
    Pattern seriesPattern = Pattern.compile("^.{2,}$");
    Pattern qtyPattern = Pattern.compile("^[1-9][0-9]*$");
    Pattern pricePattern = Pattern.compile("^[0-9]{4,}$");


    private void validateInit() {
        allValidations.put(txtItemName, namePattern);
        allValidations.put(txtBrand, brandPattern);
        allValidations.put(txtModelId, modelPattern);
        allValidations.put(txtSeries, seriesPattern);
        allValidations.put(txtQty, qtyPattern);
        allValidations.put(txtPrice, pricePattern);
    }

    public void textFields_Key_Released(javafx.scene.input.KeyEvent keyEvent) {
        Validator.keyController(allValidations,keyEvent);
    }
}
