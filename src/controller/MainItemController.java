package controller;


import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainItemController {


    public boolean saveItem(Product product) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `item` VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        pst.setObject(1,product.getProductId());
        pst.setObject(2,product.getName());
        pst.setObject(3,product.getBrand());
        pst.setObject(4,product.getSeries());
        pst.setObject(5,product.getModelNum());
        pst.setObject(6,product.getType());
        pst.setObject(7,product.getMoreDetail());
        pst.setObject(8,product.getAvailability());
        pst.setObject(9,product.getQtyOnHand());
        pst.setObject(10,product.getPrize());
        pst.setObject(11,product.getImageAddress());
        return pst.executeUpdate()>0;
    }

    public ObservableList<Product> loadProductData() throws SQLException, ClassNotFoundException {
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `item` ").executeQuery();
        while(rst.next()){
            productsList.add(new Product(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),
                    rst.getString(6),rst.getString(7),rst.getString(8),rst.getInt(9),rst.getString(10),rst.getString(11)));
        }return productsList;
    }
    public String getProductId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT `ItemId` FROM `item` ORDER BY `ItemId` DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "I-00"+tempId;
            }else if(tempId<=99){
                return "I-0"+tempId;
            }else{
                return "I-"+tempId;
            }
        }else{
            return "I-001";
        }
    }

    public String moreDetails(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `item` WHERE `ItemId`=?");
        pst.setObject(1,id);
        ResultSet rst = pst.executeQuery();
        rst.next();
        String details=rst.getString(7);
        return details;
    }

    public boolean UpdateItem(Product product) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("UPDATE  `item` SET `Name`=?,`Brand`=?,`Series`=?,`ModelId`=?,`Type`=?,`MoreDetail`=?,`Availability`=?,`QtyOnHand`=?,`Price`=?,`ImageAddress`=? WHERE `ItemId`=?");
        pst.setObject(1,product.getName());
        pst.setObject(2,product.getBrand());
        pst.setObject(3,product.getSeries());
        pst.setObject(4,product.getModelNum());
        pst.setObject(5,product.getType());
        pst.setObject(6,product.getMoreDetail());
        pst.setObject(7,product.getAvailability());
        pst.setObject(8,product.getQtyOnHand());
        pst.setObject(9,product.getPrize());
        pst.setObject(10,product.getImageAddress());
        pst.setObject(11,product.getProductId());
        return pst.executeUpdate()>0;
    }

    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `item` WHERE `ItemId`=?");
        pst.setObject(1,id);
        if(pst.executeUpdate()>0){
            return true;
        }return false;
    }
}
