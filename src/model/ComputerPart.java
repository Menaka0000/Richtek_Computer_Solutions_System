package model;

public class ComputerPart {
    private String imageAddress;
    private String partId;
    private String name;
    private String brand;
    private String availability;
    private String type;
    private String modelNum;
    private String moreDetail;
    private int qtyOnHand;
    private String prize;

    public ComputerPart(String imageAddress, String partId, String name, String brand, String availability, String type
            , String modelNum, String moreDetail, int qtyOnHand, String prize) {
        this.setImageAddress(imageAddress);
        this.setPartId(partId);
        this.setName(name);
        this.setBrand(brand);
        this.setAvailability(availability);
        this.setType(type);
        this.setModelNum(modelNum);
        this.setMoreDetail(moreDetail);
        this.setQtyOnHand(qtyOnHand);
        this.setPrize(prize);
    }

    public ComputerPart() {
    }


    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModelNum() {
        return modelNum;
    }

    public void setModelNum(String modelNum) {
        this.modelNum = modelNum;
    }

    public String getMoreDetail() {
        return moreDetail;
    }

    public void setMoreDetail(String moreDetail) {
        this.moreDetail = moreDetail;
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

    @Override
    public String toString() {
        return "ComputerPart{" +
                "imageAddress='" + imageAddress + '\'' +
                ", partId='" + partId + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", availability='" + availability + '\'' +
                ", type='" + type + '\'' +
                ", modelNum='" + modelNum + '\'' +
                ", moreDetail='" + moreDetail + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", prize='" + prize + '\'' +
                '}';
    }
}
