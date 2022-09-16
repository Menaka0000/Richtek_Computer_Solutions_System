package controller.scrollpane;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Button;
import model.Product;
import otherClass.FxmlLoader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuButtonController {
    public static Pane contextForFilters;
    public static ScrollPane scrollPaneCopy;
    public static GridPane grdContainerCopy;
    public ImageView buttonImage;
    public JFXButton btn;
    int column=0;
    int row=1;

    public void setButton(Button button){
          Image image = new Image(getClass().getResourceAsStream(button.getImageAddress()));
          buttonImage.setImage(image);
          btn.setText(button.getName());
    }

    public void leftButtonOnAction() throws SQLException, ClassNotFoundException, IOException {
        grdContainerCopy.getChildren().clear();

        switch (btn.getText()){
            case "LAPTOP":{
                loadItems("SELECT * FROM `item` WHERE Type =?","LAPTOP");
                new FxmlLoader().loadChildFxml1("../view/LaptopFilter.fxml",contextForFilters);
            }break;
            case "PROCESSOR":{
                loadItems("SELECT * FROM `item` WHERE Type =?","PROCESSOR");
                new FxmlLoader().loadChildFxml1("../view/ProcessorFilter.fxml",contextForFilters);
            }break;
            case "MOTHERBOARD":{
                loadItems("SELECT * FROM `item` WHERE Type =?","MOTHERBOARD");
            }break;
            case "MEMORY (RAM)":{
                loadItems("SELECT * FROM `item` WHERE Type =?","MEMORY (RAM)");
            }break;
            case "GRAPHIC CARDS":{
                loadItems("SELECT * FROM `item` WHERE Type =?","GRAPHIC CARDS");
            }break;
            case "POWER SUPPLY":{
                loadItems("SELECT * FROM `item` WHERE Type =?","POWER SUPPLY");
            }break;
            case "COOLERS":{
                loadItems("SELECT * FROM `item` WHERE Type =?","COOLERS");
            }break;
            case "STORAGE":{
                loadItems("SELECT * FROM `item` WHERE Type =?","STORAGE");
            }break;
            case "CASINGS":{
                loadItems("SELECT * FROM `item` WHERE Type =?","CASINGS");
            }break;
            case "MONITOR":{
                loadItems("SELECT * FROM `item` WHERE Type =?","MONITOR");
            }break;
            case "KEYBOARD":{
                loadItems("SELECT * FROM `item` WHERE Type =?","KEYBOARD");
            }break;
            case "GAMING MOUSE":{
                loadItems("SELECT * FROM `item` WHERE Type =?","GAMING MOUSE");
            }break;

        }



    }
    public void loadItems(String query,String x) throws SQLException, ClassNotFoundException, IOException {
        contextForFilters.getChildren().clear();
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(query);
        preparedStatement.setObject(1,x);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Product product =new Product(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),
                    resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getInt(9),
                    resultSet.getString(10),resultSet.getString(11));
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/scrollpane/Item.fxml"));
            VBox laptopBox =fxmlLoader.load();
            ItemController itemController=fxmlLoader.getController();
            itemController.setItemData(product);
            if (column==3){
                column=0;
                ++row;
            }
            grdContainerCopy.add(laptopBox,column++,row);
            GridPane.setMargin(laptopBox,new Insets(20));
        }
        column=0;
        row=1;
    }
}
