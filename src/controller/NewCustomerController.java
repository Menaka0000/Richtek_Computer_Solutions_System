package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.Customer;
import otherClass.FxmlLoader;
import otherClass.Validator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class NewCustomerController {
    public JFXTextField txtCustomerId;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public TableView<Customer> tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public static Pane contextCopy;

    public void initialize(){
        validateInit();
        colId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        try {
            loadDataToTable();
            txtCustomerId.setText(new MainCustomerController().getCustomerId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addOnAction(ActionEvent actionEvent) throws IOException {
        Object response = Validator.validate(allValidations);
        if (response instanceof JFXTextField) {
            JFXTextField textField = (JFXTextField) response;
            new Alert(Alert.AlertType.WARNING,"Incompatible input in "+textField.getPromptText()).show();return;
        }

        try {
            if(new MainCustomerController().saveCustomer(new Customer(txtCustomerId.getText(),txtName.getText(),txtAddress.getText(),txtContact.getText()))){
                new Alert(Alert.AlertType.CONFIRMATION,txtCustomerId.getText()+" Customer was added Successfully").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            new Alert(Alert.AlertType.WARNING,throwables.getMessage()).show();
        }
        new FxmlLoader().loadChildFxml("../view/NewCustomer.fxml",actionEvent,CartController.contextCopy);
    }

    public void loadDataToTable() throws SQLException, ClassNotFoundException {
       tblCustomer.setItems(new MainCustomerController().loadCustomerData());
    }

    LinkedHashMap<JFXTextField,Pattern> allValidations = new LinkedHashMap<>();
    Pattern namePattern = Pattern.compile("^(Mr|Mrs)[.][A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("[A-z0-9/,. ]{6,50}$");
    Pattern contactPattern = Pattern.compile("[0-9]{10}$");

    private void validateInit() {
        allValidations.put(txtName, namePattern);
        allValidations.put(txtContact, contactPattern);
        allValidations.put(txtAddress, addressPattern);
    }

    public void textFields_Key_Released(javafx.scene.input.KeyEvent keyEvent) {
        Validator.keyController(allValidations,keyEvent);
    }
}
