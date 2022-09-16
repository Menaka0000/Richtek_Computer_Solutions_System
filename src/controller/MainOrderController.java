package controller;

import com.jfoenix.controls.JFXButton;
import controller.scrollpane.ItemController;
import controller.scrollpane.MenuButtonController;
import controller.scrollpane.QuotationItemController;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CartItem;
import model.ItemDetails;
import model.Order;
import model.OrderUpdater;
import model.tm.OrderDetailTM;
import model.tm.OrderTM;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static controller.scrollpane.QuotationItemController.lblGrandTotal;

public class MainOrderController{
    public static Label lblCountCopy;
    public static Label cartStatus;
    public static JFXButton btnPlaceOrderCopy;
    public static JFXButton btnUpdateCopy;
    public static Label lblNetTotalCopy;

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT OrderId FROM `order` ORDER BY OrderId DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "O-00"+tempId;
            }else if(tempId<=99){
                return "O-0"+tempId;
            }else{
                return "O-"+tempId;
            }
        }else{
            return "O-001";
        }
    }

    public boolean placeOrder(Order order){
        Connection con=null;
        try {
            con=DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm=con.prepareStatement("INSERT INTO `order` VALUES (?,?,?,?,?,?)");
            stm.setObject(1,order.getOrderId());
            stm.setObject(2,order.getCustomerId());
            stm.setObject(3,order.getOrderDate());
            stm.setObject(4,order.getOrderTime());
            stm.setObject(5,order.getCost());
            stm.setObject(6,order.getDiscount());
            if(stm.executeUpdate()>0 && saveOrderDetail(order.getOrderId(),order.getItems()) && updateItemTable(order.getItems())){
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    private  boolean saveOrderDetail(String orderId, ArrayList<ItemDetails> items) throws SQLException, ClassNotFoundException {
        for (ItemDetails temp :items
        ) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().
                    prepareStatement("INSERT INTO `order_detail` VALUES (?,?,?,?)");
            stm.setObject(1,temp.getItemId());
            stm.setObject(2,orderId);
            stm.setObject(3,temp.getQtyOfCustomer());
            stm.setObject(4,temp.getUnitPrice());
            if( stm.executeUpdate() > 0){}else{return false;}
        }
        return true;
    }

    private boolean updateItemTable(ArrayList<ItemDetails> items) throws SQLException, ClassNotFoundException, IOException {
        List<CartItem> removableIndexes=new ArrayList();
        System.out.println(items+"test");
        for (ItemDetails temp:items
        ) {
            PreparedStatement stm1 = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `item` WHERE ItemId=?");
            stm1.setObject(1,temp.getItemId());
            ResultSet rst = stm1.executeQuery();
            rst.next();
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `item` SET QtyOnHand=? WHERE ItemId=?");
            stm.setObject(1,(Integer.parseInt(rst.getString(9))-temp.getQtyOfCustomer()));
            stm.setObject(2,temp.getItemId());
            if (stm.executeUpdate()>0){
                int x=0;
                int y=1;
                for(CartItem temp2:ItemController.items){

                    if(temp2.getItemId().equals(temp.getItemId())){
                        temp2.setQtyOnStock(Integer.parseInt(rst.getString(9))-temp.getQtyOfCustomer());
                        temp2.setQtyOfCustomer(1);

                        if(Integer.parseInt(rst.getString(9))-temp.getQtyOfCustomer()<=0){
                            PreparedStatement stm2 = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `item` SET Availability=? WHERE ItemId=?");
                            stm2.setObject(1,"Out Of Stock");
                            stm2.setObject(2,temp.getItemId());
                            stm2.executeUpdate();
                            removableIndexes.add(temp2);
                        }
                    }
                }

            }else {return false;}

            List<CartItem> toRemove = new ArrayList<>();
            for(CartItem cartItem:removableIndexes){
                toRemove.add(cartItem);
            }
            ItemController.items.removeAll(toRemove);

            QuotationItemController.gridContainer1.getChildren().clear();
            lblCountCopy.setText(String.valueOf(ItemController.items.size()));
            ItemController.x=ItemController.items.size();
            if(ItemController.x==0){ItemController.lblCart.setVisible(false);
            }else {
                ItemController.lblCart.setText(String.valueOf(ItemController.x));
            }
            int column=0;
            int row=1;
            CartController.total=0;
            for (CartItem item: ItemController.items
            ) {
                CartController.total+=(Integer.parseInt(item.getPrice())*item.getQtyOfCustomer());
                FXMLLoader fxmlLoader=new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/scrollpane/QuotationItem.fxml"));
                HBox QItemBox = fxmlLoader.load();
                QuotationItemController itemController=fxmlLoader.getController();
                itemController.setData(item);
                QuotationItemController.gridContainer1.add(QItemBox,column,row++);
                GridPane.setMargin(QItemBox,new Insets(5));
            }
            lblGrandTotal.setText(""+CartController.total);
            lblNetTotalCopy.setText("0.0");

        }return true;
    }

    public ObservableList<OrderTM> loadOrders() throws SQLException, ClassNotFoundException {
        ObservableList<OrderTM> orders= FXCollections.observableArrayList();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `order`").executeQuery();
        while (rst.next()){
            PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `order_detail` WHERE `OrderId`=?");
            pst.setObject(1,rst.getString(1));
            ResultSet rst1 = pst.executeQuery();
            ObservableList<OrderDetailTM>items=FXCollections.observableArrayList();
            while (rst1.next()){
                PreparedStatement pst2 = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `item` WHERE `ItemId`=?");
                pst2.setObject(1,rst1.getString(1));
                ResultSet rst2 = pst2.executeQuery();
                rst2.next();
                items.add(new OrderDetailTM(rst1.getString(1),rst1.getDouble(4),rst1.getInt(3),rst2.getString(3),rst2.getString(2)));
            }
            Button btn=new Button("UPDATE");
            OrderTM orderTM=new OrderTM(rst.getString(1),rst.getString(2),rst.getString(3),
                    rst.getString(4),rst.getDouble(5), rst.getDouble(6),items,btn);
            orders.add(orderTM);

            btn.setOnAction((e)->{
                UpdatingCartController.orderId=orderTM.getOrderId();
                int itemCount=0;
                ItemController.items.clear();
                cartStatus.setVisible(true);
                DashBoardForUserController.Switch=1;
                for (OrderDetailTM temp:orderTM.getItems()){
                    try {
                        PreparedStatement pst3  = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `item` WHERE `ItemId`=?");
                        pst3.setObject(1,temp.getItemId());
                        ResultSet rst3 = pst3.executeQuery();
                        rst3.next();
                        ItemController.items.add(new CartItem(temp.getItemId(),temp.getName(),rst3.getInt(9),temp.getQtyOfCustomer(),rst3.getString(10),rst3.getInt(9)+temp.getQtyOfCustomer(),temp.getQtyOfCustomer(),rst3.getString(11)));
                        itemCount+= temp.getQtyOfCustomer();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
                try {
                    MenuButtonController.grdContainerCopy.getChildren().clear();
                    FXMLLoader fxmlLoader=new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../view/UpdatingCart.fxml"));
                    int column=0;
                    int row=1;
                    VBox settingsBox  = fxmlLoader.load();
                    MenuButtonController.grdContainerCopy.add(settingsBox,column++,row);
                    GridPane.setMargin(settingsBox,new Insets(10));
                    ItemController.lblCart.setVisible(true);
                    ItemController.x=itemCount;
                    ItemController.lblCart.setText(String.valueOf(itemCount));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }
        return orders;
    }

    public  boolean updateOrder(OrderUpdater orderUpdater) throws SQLException, ClassNotFoundException {
        Connection connection=DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement pst = connection.prepareStatement("UPDATE `order` SET  `Cost`=?,`Discount`=? WHERE `OrderId`=?");
        pst.setObject(1,orderUpdater.getCostOfOrder());
        pst.setObject(2,orderUpdater.getDiscountOfOrder());
        pst.setObject(3,orderUpdater.getOrderId());
        if (pst.executeUpdate()>0 && updateOrderDetailTable(orderUpdater)){
            connection.commit();
            return true;
        }
        connection.setAutoCommit(true);
        return false;
    }

    public boolean updateOrderDetailTable(OrderUpdater orderUpdater) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  `order_detail` WHERE `OrderId`=? ");
        System.out.println(orderUpdater.getUpdatedItems());
        pst.setObject(1,orderUpdater.getOrderId());
        ResultSet rst = pst.executeQuery();
        while (rst.next()){
            for (ItemDetails temp:orderUpdater.getUpdatedItems()){
                if (rst.getString(1).equals(temp.getItemId())){
                    temp.setIdentifier(1);
                    int difference=rst.getInt(3)- temp.getQtyOfCustomer();

                    PreparedStatement pst1 = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  `item` WHERE `ItemId`=? ");
                    pst1.setObject(1,temp.getItemId());
                    ResultSet rst1 = pst1.executeQuery();
                    rst1.next();

                    PreparedStatement pst2 = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `item` SET `QtyOnHand`=?,`Availability`=? WHERE `ItemId`=? ");
                    System.out.println((rst1.getInt(9)+difference)<0);
                    if (rst1.getInt(9)+difference<0){
                        pst2.setObject(1,0);
                    }else {
                        pst2.setObject(1,(rst1.getInt(9)+difference));
                    }
                    if(rst1.getInt(9)+difference<=0){
                        pst2.setObject(2,"Out Of Stock");
                    }else {
                        pst2.setObject(2,"In Stock");
                    }
                    pst2.setObject(3,temp.getItemId());
                    if( pst2.executeUpdate() == 0){return false;}

                    PreparedStatement pst3 = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `order_detail` SET `Qty`=? WHERE `ItemCode`=? ");
                    pst3.setObject(1,temp.getQtyOfCustomer());
                    pst3.setObject(2,temp.getItemId());
                    if (pst3.executeUpdate()==0){return false;}
                }
            }
        }
        for (ItemDetails temp:orderUpdater.getUpdatedItems()){
            if (temp.getIdentifier()==0){
                PreparedStatement stm = DbConnection.getInstance().getConnection().
                        prepareStatement("INSERT INTO `order_detail` VALUES (?,?,?,?)");
                stm.setObject(1,temp.getItemId());
                stm.setObject(2,orderUpdater.getOrderId());
                stm.setObject(3,temp.getQtyOfCustomer());
                stm.setObject(4,temp.getUnitPrice());
                if( stm.executeUpdate() == 0){return false;}

                PreparedStatement pst1 = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  `item` WHERE `ItemId`=? ");
                pst1.setObject(1,temp.getItemId());
                ResultSet rst3 = pst1.executeQuery();
                rst3.next();
                PreparedStatement stm2 = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `item` SET Availability=?,`QtyOnHand`=? WHERE ItemId=?");
                stm2.setObject(2,(Integer.parseInt(rst3.getString(9))-temp.getQtyOfCustomer()));
                if(Integer.parseInt(rst3.getString(9))-temp.getQtyOfCustomer()<=0){
                    stm2.setObject(1,"Out Of Stock");
                }else{
                    stm2.setObject(1,"In Stock");
                }
                stm2.setObject(3,temp.getItemId());
                if( stm2.executeUpdate() == 0){return false;}
            }

        }
        return true;
    }
}
