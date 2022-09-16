package model.tm;

public class UserTM {
    private String userId;
    private String name;
    private String userName;
    private String position;
    private String password;
    private String address;
    private String contact;
    private double salary;


    public UserTM(String userId, String name, String userName, String position, String password, String address, String contact, double salary) {
        this.setUserId(userId);
        setName(name);
        this.setUserName(userName);
        this.setPosition(position);
        this.setPassword(password);
        this.setAddress(address);
        this.setContact(contact);
        this.setSalary(salary);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
