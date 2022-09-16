package model.tm;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import model.ItemDetails;

import java.util.ArrayList;

public class OrderTM {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String orderTime;
    private double cost;
    private double discount;
    private ObservableList<OrderDetailTM> items;
    private Button btn;

    public OrderTM() {
    }

    public OrderTM(String orderId, String customerId, String orderDate, String orderTime, double cost, double discount, ObservableList<OrderDetailTM> items, Button btn) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCost(cost);
        this.setDiscount(discount);
        this.setItems(items);
        this.setBtn(btn);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public ObservableList<OrderDetailTM> getItems() {
        return items;
    }

    public void setItems(ObservableList<OrderDetailTM> items) {
        this.items = items;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", cost=" + cost +
                ", discount=" + discount +
                ", items=" + items +
                ", btn=" + btn +
                '}';
    }
}
