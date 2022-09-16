package controller.scrollpane;

import controller.CartController;
import controller.DashBoardForUserController;
import controller.NewCustomerController;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import otherClass.FxmlLoader;

import java.io.IOException;

public class SettingsController {
    public Pane context;
    public static Label lblHeadingCopy;
    public static ComboBox<String> cmbItemTypeCopy;

    public void initialize(){
        CartController.contextCopy=context;
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent)  {
        try {
            NewCustomerController.contextCopy=context;
            new FxmlLoader().loadChildFxml("../view/NewCustomer.fxml",actionEvent,context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnLaptopAddOnAction(ActionEvent actionEvent) {
        try {
            new FxmlLoader().loadChildFxml("../view/AddNewProduct.fxml",actionEvent,context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOnAction(ActionEvent actionEvent) {
    btnLaptopAddOnAction(actionEvent);
    lblHeadingCopy.setText("Richtek Rig");
    cmbItemTypeCopy.setValue("RICHTEK RIG");
    }

    public void OrderOnAction(ActionEvent actionEvent) {
        try {
            new FxmlLoader().loadChildFxml("../view/Order.fxml",actionEvent,context);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void customerUpdateOnAction(ActionEvent actionEvent) throws IOException {
        new FxmlLoader().loadChildFxml("../view/EditCustomer.fxml",actionEvent,context);
    }

    public void productEditOnAction(ActionEvent actionEvent) throws IOException {
        new FxmlLoader().loadChildFxml("../view/StockUpdate.fxml",actionEvent,context);
    }
}
