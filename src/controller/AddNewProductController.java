package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.scrollpane.SettingsController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Product;
import otherClass.Validator;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class AddNewProductController  {
    public JFXTextField txtSeries;
    public JFXTextField txtQty;
    public JFXTextField txtPrice;
    public JFXTextArea txtMOreDetails;
    public JFXButton btnAdd;
    public JFXTextField txtModelId;
    public JFXTextField txtItemName;
    public JFXTextField txtBrand;
    public JFXTextField txtItemId;
    public ImageView imageView;
    public String imageAddress;
    public ComboBox<String> cmbItemType;
    public Label lblHeading;


    public void initialize() throws SQLException, ClassNotFoundException {
        SettingsController.cmbItemTypeCopy=cmbItemType;
        SettingsController.lblHeadingCopy=lblHeading;
        validateInit();
        txtItemId.setText(new MainItemController().getProductId());
        String[] name = { "RICHTEK RIG","LAPTOP","PROCESSOR","MOTHERBOARD","MEMORY (RAM)","GRAPHIC CARDS","POWER SUPPLY","COOLERS","STORAGE","CASINGS","MONITOR","KEYBOARD","GAMING MOUSE" };
        List<String> itemName = new ArrayList(Arrays.asList(name));
        cmbItemType.getItems().addAll(itemName);

    }

    public void imagFileOnAction(ActionEvent actionEvent) {
        FileChooser fc =new FileChooser();
        fc.getExtensionFilters().clear();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg"));
        File file=fc.showOpenDialog(null);
        if (file!=null){
            imageView.setImage(new Image(file.toURI().toString()));
            imageAddress="/assets/src/"+file.getName();
            System.out.println(imageAddress);
        }
    }

    public void addOnAction(ActionEvent actionEvent) {
        if (cmbItemType.getValue()==null){new Alert(Alert.AlertType.WARNING,"Select an item type").show();return;}
        Object response = Validator.validate(allValidations);
        if (response instanceof JFXTextField) {
            JFXTextField textField = (JFXTextField) response;
            new Alert(Alert.AlertType.WARNING,"Incompatible input in "+textField.getPromptText()).show();return;
        }
        try {
            Product product =new Product(txtItemId.getText(),txtItemName.getText(),txtBrand.getText(),txtSeries.getText(),txtModelId.getText(),
                    (String) cmbItemType.getValue(),txtMOreDetails.getText(),"In Stock",Integer.parseInt(txtQty.getText()),
                    txtPrice.getText(),imageAddress);
            if(new MainItemController().saveItem(product)){
                new Alert(Alert.AlertType.CONFIRMATION,txtItemId.getText()+"was added Successfully").show();
            }
        } catch (SQLException | ClassNotFoundException | NumberFormatException throwables) {
            throwables.printStackTrace();
            new Alert(Alert.AlertType.WARNING,throwables.getMessage()).show();
        }
    }

    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern namePattern = Pattern.compile("^.{5,}$");
    Pattern brandPattern = Pattern.compile("^.{2,}$");
    Pattern modelPattern = Pattern.compile("^.{2,}$");
    Pattern seriesPattern = Pattern.compile("^.{2,}$");
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,3}$"); // to add an item just for display without any qty.
    Pattern pricePattern = Pattern.compile("[0-9]{4,}$");

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
