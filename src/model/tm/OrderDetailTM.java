package model.tm;

public class OrderDetailTM {
    private String itemId;
    private double unitPrice;
    private int qtyOfCustomer;
    private String brand;
    private String name;

    public OrderDetailTM(String itemId, double unitPrice, int qtyOfCustomer, String brand, String name) {
        this.setItemId(itemId);
        this.setUnitPrice(unitPrice);
        this.setQtyOfCustomer(qtyOfCustomer);
        this.setBrand(brand);
        this.setName(name);
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "itemId='" + itemId + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOfCustomer=" + qtyOfCustomer +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
