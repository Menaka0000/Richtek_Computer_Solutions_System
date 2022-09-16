package model;

public class CartItem {
    private String itemId;
    private String itemName;
    private int qtyOnStock;
    private int qtyOfCustomer;
    private String price;
    private double price1;
    private int maxQtyForUpdating;
    private int prevQtyOfCus;
    private String imageAddress;
    private double costPerItem;


    public CartItem(String itemId, String itemName, int qtyOfCustomer, double price1, double costPerItem) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.qtyOfCustomer = qtyOfCustomer;
        this.price1 = price1;
        this.costPerItem = costPerItem;
    }
    public CartItem(String itemId, String itemName, int qtyOnStock, int qtyOfCustomer, String price, int maxQtyForUpdating, int prevQtyOfCus, String imageAddress) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.qtyOnStock = qtyOnStock;
        this.qtyOfCustomer = qtyOfCustomer;
        this.price = price;
        this.maxQtyForUpdating = maxQtyForUpdating;
        this.prevQtyOfCus = prevQtyOfCus;
        this.imageAddress = imageAddress;
    }
    public CartItem(String itemId, String itemName, int qtyOnStock, int qtyOfCustomer, String price, String imageAddress) {
        this.setItemId(itemId);
        this.setItemName(itemName);
        this.setQtyOnStock(qtyOnStock);
        this.setQtyOfCustomer(qtyOfCustomer);
        this.setPrice(price);
        this.setImageAddress(imageAddress);
    }

    public void setPrevQtyOfCus(int prevQtyOfCus) {
        this.prevQtyOfCus = prevQtyOfCus;
    }

    public void setCostPerItem(double costPerItem) {
        this.costPerItem = costPerItem;
    }

    public double getCostPerItem() {
        return costPerItem;
    }
    public void setPrice1(double price1) {
        this.price1 = price1;
    }

    public double getPrice1() {
        return price1;
    }

    public int getPrevQtyOfCus() {
        return prevQtyOfCus;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQtyOnStock() {
        return qtyOnStock;
    }

    public void setQtyOnStock(int qtyOnStock) {
        this.qtyOnStock = qtyOnStock;
    }

    public int getQtyOfCustomer() {
        return qtyOfCustomer;
    }

    public void setQtyOfCustomer(int qtyOfCustomer) {
        this.qtyOfCustomer = qtyOfCustomer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
    public void setMaxQtyForUpdating(int maxQtyForUpdating) {
        this.maxQtyForUpdating = maxQtyForUpdating;
    }
    public int getMaxQtyForUpdating() {
        return maxQtyForUpdating;
    }

}
