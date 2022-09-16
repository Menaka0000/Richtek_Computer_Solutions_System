package controller;

import controller.scrollpane.ItemController;
import controller.scrollpane.MenuButtonController;
import controller.scrollpane.QuotationItemController;
import controller.scrollpane.QuotationUpdatingItemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.CartItem;
import model.ItemDetails;
import model.OrderUpdater;
import otherClass.FxmlLoader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdatingCartController extends CartController {
    public GridPane gridContainer2;
    public static  String orderId;

    @Override
    public void initialize(){
        txtDiscountPer.setVisible(false);
        txtDiscountPer.setText("0.0");
        MainOrderController.btnPlaceOrderCopy=btnPlaceOrder;
        MainOrderController.lblCountCopy=lblCount;
        MainOrderController.btnUpdateCopy=btnUpdate;
        QuotationItemController.itemCount=lblCount;
        QuotationUpdatingItemController.gridContainer2=gridContainer2;
        QuotationItemController.scrollPaneForQuotation=scrollPaneForQuotation;
        QuotationItemController.lblGrandTotal=lblGrandTotal;
        int column=0;
        int row=1;
        total=0;
        try {
            for (CartItem item: ItemController.items
            ) {
                total+=(Integer.parseInt(item.getPrice())*item.getQtyOfCustomer());
                FXMLLoader fxmlLoader=new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/scrollpane/QuotationUpdatingItem.fxml"));
                HBox QItemBox = fxmlLoader.load();
                QuotationItemController itemController=fxmlLoader.getController();
                itemController.setData(item);
                gridContainer2.add(QItemBox, column, row++);
                GridPane.setMargin(QItemBox,new Insets(5));
            }
            lblGrandTotal.setText(total+".0");
        } catch (IOException e) {
            e.printStackTrace();
        }

        cmbCustomer.getItems().addAll(loadCustomerIds());
        cmbCustomer.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setCustomerName(newValue);
                });
        calculateDiscount();
    }

    public void updateOnAction(ActionEvent actionEvent) {
        List<ItemDetails> updatedItems=new ArrayList<>();

        for (CartItem temp:ItemController.items){
           updatedItems.add( new ItemDetails(temp.getItemId(),Double.parseDouble(temp.getPrice()),temp.getQtyOfCustomer(),0));
        }
        try {
            if (new MainOrderController().updateOrder(new OrderUpdater(orderId,CartController.total,Double.parseDouble(lblDiscount.getText()),updatedItems))){
                new Alert(Alert.AlertType.CONFIRMATION,"Order Updated Successfully").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try again").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void newCartOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        DashBoardForUserController.Switch=0;
        MainOrderController.cartStatus.setVisible(false);
        MenuButtonController.grdContainerCopy.getChildren().clear();
        ItemController.items.clear();
        ItemController.lblCart.setVisible(false);
        ItemController.x=0;
        new MenuButtonController().loadItems("SELECT * FROM `item` WHERE Type =?","LAPTOP");

    }

    @Override
    public void calculateDiscount() {
        super.calculateDiscount();
    }

    @Override
    public void CancelOnAction(ActionEvent actionEvent) {
        try {
            new FxmlLoader().loadChildFxml("../view/Order.fxml",actionEvent,CartController.contextCopy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
