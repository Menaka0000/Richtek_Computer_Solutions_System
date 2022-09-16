package model;

import java.util.List;

public class OrderUpdater {
    private String orderId;
    private double costOfOrder;
    private double discountOfOrder;
    private List<ItemDetails> updatedItems;


    public OrderUpdater(String orderId, double costOfOrder, double discountOfOrder, List<ItemDetails> updatedItems) {
        this.setOrderId(orderId);
        this.setCostOfOrder(costOfOrder);
        this.setDiscountOfOrder(discountOfOrder);
        this.setUpdatedItems(updatedItems);
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getCostOfOrder() {
        return costOfOrder;
    }

    public void setCostOfOrder(double costOfOrder) {
        this.costOfOrder = costOfOrder;
    }

    public double getDiscountOfOrder() {
        return discountOfOrder;
    }

    public void setDiscountOfOrder(double discountOfOrder) {
        this.discountOfOrder = discountOfOrder;
    }

    public List<ItemDetails> getUpdatedItems() {
        return updatedItems;
    }

    public void setUpdatedItems(List<ItemDetails> updatedItems) {
        this.updatedItems = updatedItems;
    }
    @Override
    public String toString() {
        return "OrderUpdater{" +
                "orderId='" + getOrderId() + '\'' +
                ", costOfOrder=" + getCostOfOrder() +
                ", discountOfOrder=" + getDiscountOfOrder() +
                ", updatedItems=" + getUpdatedItems() +
                '}';
    }
}
