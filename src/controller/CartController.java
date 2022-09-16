package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import controller.scrollpane.ItemController;
import controller.scrollpane.MenuButtonController;
import controller.scrollpane.QuotationItemController;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.CartItem;
import model.ItemDetails;
import model.Order;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import otherClass.FxmlLoader;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CartController {

    public ScrollPane scrollPaneForQuotation;
    public GridPane gridContainer1;
    public Label lblCount;
    public Label lblGrandTotal;
    public static int total;
    public Label lblNetTotal;
    public Label lblDiscount;
    public Label lblCustomerName;
    public JFXComboBox<String> cmbCustomer;
    public static Pane contextCopy;
    public JFXButton btnPlaceOrder;
    public JFXButton btnUpdate;
    public Label lblDiscountPercentage;
    public TextField txtDiscountPer;
    private Double discount=0.0;
    LinkedHashMap<String,String> idWithName=new LinkedHashMap<>();

    public  void initialize(){
        txtDiscountPer.setVisible(false);
        txtDiscountPer.setText("0.0");
        MainOrderController.lblNetTotalCopy=lblNetTotal;
        MainOrderController.btnPlaceOrderCopy=btnPlaceOrder;
        MainOrderController.lblCountCopy=lblCount;
        MainOrderController.btnUpdateCopy=btnUpdate;
        QuotationItemController.itemCount=lblCount;
        QuotationItemController.gridContainer1=gridContainer1;
        QuotationItemController.scrollPaneForQuotation=scrollPaneForQuotation;
        QuotationItemController.lblGrandTotal=lblGrandTotal;
        QuotationItemController.lblNetTotal=lblNetTotal;
        lblCount.setText(String.valueOf(ItemController.items.size()));
        int column=0;
        int row=1;
        total=0;
        try {
            for (CartItem item: ItemController.items
                 ) {
                total+=(Integer.parseInt(item.getPrice())*item.getQtyOfCustomer());
                FXMLLoader fxmlLoader=new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/scrollpane/QuotationItem.fxml"));
                HBox QItemBox = fxmlLoader.load();
                QuotationItemController itemController=fxmlLoader.getController();
                itemController.setData(item);
                gridContainer1.add(QItemBox,column,row++);
                GridPane.setMargin(QItemBox,new Insets(5));
            }
            lblGrandTotal.setText(total+".0");
        } catch (IOException e) {
            e.printStackTrace();
        }

        cmbCustomer.getItems().addAll(loadCustomerIds());
        cmbCustomer.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setCustomerName(newValue);
                });
        lblNetTotal.setText(total+".0");
        lblDiscount.setText("0.0");

    }

    public void quotationViaMailOnAction(ActionEvent actionEvent) {
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        if (cmbCustomer.getValue()==null){new Alert(Alert.AlertType.WARNING,"Select Customer to place order...").show();return;}
        if (ItemController.items.size()==0){
            new Alert(Alert.AlertType.WARNING,"Add an item to place order").show();return;
        }
        ArrayList<ItemDetails> items=new ArrayList<>();
        ObservableList<CartItem> jasperList= FXCollections.observableArrayList();
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss a");
        for (CartItem itemTemp:ItemController.items
             ) {
            items.add(new ItemDetails(itemTemp.getItemId(),Double.parseDouble(itemTemp.getPrice()),itemTemp.getQtyOfCustomer()));
            double costPerItem=(Double.parseDouble(itemTemp.getPrice())*itemTemp.getQtyOfCustomer());
            jasperList.add(new CartItem(itemTemp.getItemId(),itemTemp.getItemName(),itemTemp.getQtyOfCustomer(),Double.parseDouble(itemTemp.getPrice()),costPerItem));
        }

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/RichtekInvoice.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `customer` WHERE `CustId`=?");
            pst.setObject(1,cmbCustomer.getValue());
            ResultSet rst = pst.executeQuery();
            rst.next();
            HashMap map=new HashMap();
            map.put("custId",rst.getString(1));
            map.put("custName",rst.getString(2));
            map.put("custAddress",rst.getString(3));
            map.put("contact",rst.getString(4));
            map.put("amount",(double)total);
            map.put("discount",Double.parseDouble(lblDiscount.getText()));
            map.put("netAmount",(total-Double.parseDouble(lblDiscount.getText())));
            map.put("orderId",new MainOrderController().getOrderId());
            map.put("date",f.format(date));
            map.put("time",sdf.format(new Date()));

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(jasperList.toArray()));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            Order order=new Order(new MainOrderController().getOrderId(),cmbCustomer.getValue(),f.format(date),
                    sdf.format(new Date()),total,Double.parseDouble(lblDiscount.getText()),items);
            if (new MainOrderController().placeOrder(order)){
                new Alert(Alert.AlertType.CONFIRMATION, order.getOrderId()+" Placed Successfully...").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }

    public void CancelOnAction(ActionEvent actionEvent) {
        ItemController.x=0;
        ItemController.lblCart.setVisible(false);
        ItemController.items.clear();
        MenuButtonController.grdContainerCopy.getChildren().clear();
    }

    public void addOnAction(ActionEvent actionEvent) {
        int column=0;
        int row=1;
        try {
            MenuButtonController.grdContainerCopy.getChildren().clear();
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/scrollpane/Settings.fxml"));
            VBox settingsBox = null;
            try {
                settingsBox = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            MenuButtonController.grdContainerCopy.add(settingsBox,column++,row);
            GridPane.setMargin(settingsBox,new Insets(10));
            new FxmlLoader().loadChildFxml("../view/NewCustomer.fxml",actionEvent,contextCopy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> loadCustomerIds(){
        List<String>ids=new ArrayList<>();
        try {
            idWithName=new MainCustomerController().loadCustomerIds();
        for(Map.Entry<String,String> entry :idWithName.entrySet()){
            ids.add(entry.getKey());
        }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }return ids;
    }

    public void setCustomerName(String newValue){
        for(Map.Entry<String,String> entry :idWithName.entrySet()){
            if(entry.getKey().equals(newValue)){
                lblCustomerName.setText(entry.getValue());
            }
        }
    }
    public  void calculateDiscount(){
        discount=Double.parseDouble(txtDiscountPer.getText())/100*total;
        lblDiscount.setText(String.valueOf(discount));
        lblNetTotal.setText("Rs "+(total-discount));
    }

    public void discountSettingsOnAction(MouseEvent mouseEvent) {
        txtDiscountPer.setVisible(true);
    }

    public void keyReleased(KeyEvent keyEvent) {
        lblDiscountPercentage.setText("- "+txtDiscountPer.getText()+"%");
        calculateDiscount();
    }
}
