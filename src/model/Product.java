package model;

public class Product {

    private String productId;
    private String name;
    private String brand;
    private String series;
    private String modelNum;
    private String type;
    private String moreDetail;
    private String availability;
    private int qtyOnHand;
    private String prize;
    private String imageAddress;

    public Product() {
    }
    public Product(String productId, String name, String brand, String series, String modelNum, String type, String moreDetail, String availability, int qtyOnHand, String prize, String imageAddress) {
        this.setProductId(productId);
        this.setName(name);
        this.setBrand(brand);
        this.setSeries(series);
        this.setModelNum(modelNum);
        this.setType(type);
        this.setMoreDetail(moreDetail);
        this.setAvailability(availability);
        this.setQtyOnHand(qtyOnHand);
        this.setPrize(prize);
        this.setImageAddress(imageAddress);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getModelNum() {
        return modelNum;
    }

    public void setModelNum(String modelNum) {
        this.modelNum = modelNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoreDetail() {
        return moreDetail;
    }

    public void setMoreDetail(String moreDetail) {
        this.moreDetail = moreDetail;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", brad='" + brand + '\'' +
                ", series='" + series + '\'' +
                ", modelNum='" + modelNum + '\'' +
                ", type='" + type + '\'' +
                ", moreDetail='" + moreDetail + '\'' +
                ", availability='" + availability + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", prize='" + prize + '\'' +
                ", imageAddress='" + imageAddress + '\'' +
                '}';
    }
}
