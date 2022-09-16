package model;

public class Button {
    private String imageAddress;
    private String name;

    public Button(String imageAddress, String name) {
        this.setImageAddress(imageAddress);
        this.setName(name);
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Button{" +
                "imageAddress='" + imageAddress + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
