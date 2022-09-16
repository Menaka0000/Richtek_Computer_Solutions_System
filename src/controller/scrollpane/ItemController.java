package controller.scrollpane;

import com.jfoenix.controls.JFXTextArea;
import controller.MoreDetailsController;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.CartItem;
import model.Product;
import otherClass.FxmlLoader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemController {
    public ImageView laptopImage;
    public Label lblPrice;
    public Label lblhover;
    public JFXTextArea txtNameWithSpec;
    public Label lblAvailability;
    public Label lblType;
    public Label lblItemId;
    public static Label lblCart;
    public static List<CartItem> items= new ArrayList<>();
    public static int x=0;

    public void setItemData(Product product){
       Image image = new Image(getClass().getResourceAsStream(product.getImageAddress()));
       laptopImage.setImage(image);
       txtNameWithSpec.setText(product.getBrand()+" "+ product.getName());
       lblPrice.setText("Rs: "+ product.getPrize());
       lblAvailability.setText(product.getAvailability());
       lblItemId.setText(product.getProductId());
       lblType.setText("-"+product.getType()+"-");
       centerImage();
    }

    public void centerImage() {
        Image img = laptopImage.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = laptopImage.getFitWidth()/ img.getWidth() ;
            double ratioY = laptopImage.getFitHeight()/ img.getHeight() ;

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            laptopImage.setX((laptopImage.getFitWidth() - w) / 2);
            laptopImage.setY((laptopImage.getFitHeight() - h) / 2);

        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            for (CartItem item:items) {
            if(item.getItemId().equals(lblItemId.getText())){
                if(checkAvailability(item)){
                    Alert alert  = new Alert(Alert.AlertType.CONFIRMATION, "This item already in the cart!\nDo you want to add another one?");
                    ButtonType yesButtonType = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(cancelButtonType, yesButtonType );
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == yesButtonType) {
                        item.setQtyOfCustomer(item.getQtyOfCustomer()+1);
                        lblCart.setVisible(true);
                        lblCart.setText(String.valueOf(++x));
                    }
                }else{
                    new Alert(Alert.AlertType.WARNING,"Sorry, this item is running OUT OF STOCK").show();
                }
            return;
            }
        }

            PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `item` WHERE `ItemId`=?");
            pst.setObject(1,lblItemId.getText());
            ResultSet rst = pst.executeQuery();
            rst.next();
            if(rst.getInt(9)>0){
                items.add(new CartItem(lblItemId.getText(),rst.getString(2),rst.getInt(9),1,rst.getString(10),rst.getString(11)));
                lblCart.setVisible(true);
                lblCart.setText(String.valueOf(++x));
            }else {
                new Alert(Alert.AlertType.WARNING,"Sorry, this item is running OUT OF STOCK").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean checkAvailability(CartItem item){
        if(item.getQtyOnStock()>item.getQtyOfCustomer() || item.getQtyOfCustomer()<item.getPrevQtyOfCus() || item.getQtyOfCustomer()< item.getMaxQtyForUpdating()){return true;}
        return false;
    }

    public void moreDetailOnAction(ActionEvent actionEvent) throws IOException {
        new FxmlLoader().newStage("../view/MoreDetails.fxml",actionEvent);
         MoreDetailsController.load(txtNameWithSpec,lblItemId);
    }
}
