package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import otherClass.Validator;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EditCustomerController {
    public TableView<Customer> tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public JFXTextField txtName;
    public JFXTextField txtCustomerId;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;

    public void initialize(){
        validateInit();
        colId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        try {
            loadDataToTable();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadDataToTextFields( newValue);
        });
    }

    private void loadDataToTextFields(Customer customer) {
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtContact.setText(customer.getContact());
        txtCustomerId.setText(customer.getCusId());
    }

    public void loadDataToTable() throws SQLException, ClassNotFoundException {
        tblCustomer.setItems(new MainCustomerController().loadCustomerData());

    }

    public void UpdateOnActoin(ActionEvent actionEvent) {
        Object response = Validator.validate(allValidations);
        if (response instanceof JFXTextField) {
            JFXTextField textField = (JFXTextField) response;
            new Alert(Alert.AlertType.WARNING,"Incompatible input in "+textField.getPromptText()).show();return;
        }
        try {
            if(new MainCustomerController().editCustomer(new Customer(txtCustomerId.getText(),txtName.getText(),txtAddress.getText(),txtContact.getText()))){
                new Alert(Alert.AlertType.CONFIRMATION,txtCustomerId.getText()+" Customer was Updated Successfully").show();
                loadDataToTable();
               tblCustomer.refresh();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            new Alert(Alert.AlertType.WARNING,throwables.getMessage()).show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            new MainCustomerController().deleteCustomer(txtCustomerId.getText());
            new Alert(Alert.AlertType.CONFIRMATION,txtCustomerId.getText()+" Customer was Deleted Successfully").show();
            loadDataToTable();
            tblCustomer.refresh();
        } catch (SQLException | ClassNotFoundException  throwables) {
            throwables.printStackTrace();
        }
    }

    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
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
