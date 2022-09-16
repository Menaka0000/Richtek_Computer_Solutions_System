package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class MainCustomerController {
    public boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `customer` VALUES(?,?,?,?) ");
        pst.setObject(1,customer.getCusId());
        pst.setObject(2,customer.getName());
        pst.setObject(3,customer.getAddress());
        pst.setObject(4,customer.getContact());
        if(pst.executeUpdate()>0){
            return true;
        }
        return false;
    }

    public ObservableList<Customer> loadCustomerData() throws SQLException, ClassNotFoundException {
        ObservableList<Customer> cusList = FXCollections.observableArrayList();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `customer` ").executeQuery();
        while(rst.next()){
            cusList.add(new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)));
        }return cusList;
    }

    public LinkedHashMap<String,String> loadCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `customer`").executeQuery();
        LinkedHashMap<String,String> idWithName=new LinkedHashMap<>();
        while(rst.next()){
            idWithName.put(rst.getString(1),rst.getString(2));
        }return idWithName;
    }
    public boolean editCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `customer` SET `CustName`=?,`CustAddress`=?,`Contact`=? WHERE `CustId`=?");
        pst.setObject(1,customer.getName());
        pst.setObject(2,customer.getAddress());
        pst.setObject(3,customer.getContact());
        pst.setObject(4,customer.getCusId());
        if (pst.executeUpdate()>0){return true;}return false;
    }
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `customer` WHERE `CustId`=? ");
        pst.setObject(1,id);
        if (pst.executeUpdate()>0){ return true; }return false;
    }

    public String getCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT `CustId` FROM `customer` ORDER BY `CustId` DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "C-00"+tempId;
            }else if(tempId<=99){
                return "C-0"+tempId;
            }else{
                return "C-"+tempId;
            }
        }else{
            return "C-001";
        }
    }
}
