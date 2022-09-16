package model;

public class Customer {
    private String cusId;
    private String name;
    private String address;
    private String contact;

    public Customer() {
    }

    public Customer(String cusId, String name, String address, String contact) {
        this.setCusId(cusId);
        this.setName(name);
        this.setAddress(address);
        this.setContact(contact);
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cusId='" + cusId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
