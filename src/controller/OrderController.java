package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.tm.OrderDetailTM;
import model.tm.OrderTM;;
import java.sql.SQLException;

public class OrderController {
    public TableView<OrderTM> tblOrder;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colCost;
    public TableColumn colDiscount;
    public TableColumn colUpdate;
    public TableView<OrderDetailTM> tblItemDetail;
    public TableColumn colItemId;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colItemBrand;
    public TableColumn colName;
    ObservableList<OrderTM>  orders;


    public void initialize(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("btn"));

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOfCustomer"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));

        try {
             orders = new MainOrderController().loadOrders();
            tblOrder.setItems(orders);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadOrderItems(newValue.getOrderId());
        });
    }

    public void loadOrderItems(String value){
        for (OrderTM temp:orders){
            if (value.equals(temp.getOrderId())){
                tblItemDetail.setItems(temp.getItems());
                System.out.println(temp.getItems().toString());
                return;
            }
        }


    }


}
