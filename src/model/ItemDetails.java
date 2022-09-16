package model;

public class ItemDetails {
    private String itemId;
    private double unitPrice;
    private int qtyOfCustomer;
    int identifier;

    public ItemDetails(String itemId, double unitPrice, int qtyOfCustomer, int identifier) {
        this.itemId = itemId;
        this.unitPrice = unitPrice;
        this.qtyOfCustomer = qtyOfCustomer;
        this.identifier = identifier;
    }

    public ItemDetails() {
    }

    public ItemDetails(String itemId, double unitPrice, int qtyOfCustomer) {
        this.setItemId(itemId);
        this.setUnitPrice(unitPrice);
        this.setQtyOfCustomer(qtyOfCustomer);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOfCustomer() {
        return qtyOfCustomer;
    }

    public void setQtyOfCustomer(int qtyOfCustomer) {
        this.qtyOfCustomer = qtyOfCustomer;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "ItemDetails{" +
                "itemId='" + itemId + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOfCustomer=" + qtyOfCustomer +
                '}';
    }
}
