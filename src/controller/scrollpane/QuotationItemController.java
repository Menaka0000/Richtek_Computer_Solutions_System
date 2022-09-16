package controller.scrollpane;

import com.jfoenix.controls.JFXTextArea;
import controller.CartController;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.CartItem;

public class QuotationItemController extends CartController {
    public ImageView imageItem;
    public Label lblItemPrice;
    public Label lblTotalCost;
    public Label lblQTY;
    public Label lblItemId;
    public static Label lblGrandTotal;
    public static Label lblNetTotal;
    public JFXTextArea txtName;
    public static ScrollPane scrollPaneForQuotation;
    public static GridPane gridContainer1;
    public static Label itemCount;

    @Override
    public void initialize() {

    }

    public void setData(CartItem item){
        Image image = new Image(getClass().getResourceAsStream(item.getImageAddress()));
        imageItem.setImage(image);
        lblItemId.setText(item.getItemId());
        lblItemPrice.setText(item.getPrice()+" LKR");
        lblQTY.setText(String.valueOf(item.getQtyOfCustomer()));
        txtName.setText(item.getItemName());
        lblTotalCost.setText(Integer.parseInt(item.getPrice()) * item.getQtyOfCustomer() +" LKR");
    }

    public void plusOnAction(MouseEvent mouseEvent) {
        for (CartItem item:ItemController.items
             ) {
            if (lblItemId.getText().equals(item.getItemId())){
                if(item.getQtyOnStock()>item.getQtyOfCustomer()){
                    ItemController.lblCart.setText(String.valueOf(++ItemController.x));
                    item.setQtyOfCustomer(item.getQtyOfCustomer()+1);
                    setData(item);
                    CartController.total= CartController.total+Integer.parseInt(item.getPrice());
                    lblGrandTotal.setText(CartController.total+".0");
                    //lblNetTotal.setText(CartController.total+".0");
                    calculateDiscount();
                }else{ new Alert(Alert.AlertType.WARNING,"Sorry, this item is running OUT OF STOCK").show();}
                break;
            }
        }
    }

    public void minusOnAction(MouseEvent mouseEvent) {
        for (CartItem item:ItemController.items
        ) {
            if (lblItemId.getText().equals(item.getItemId())){
                if(1<item.getQtyOfCustomer()){
                    ItemController.lblCart.setText(String.valueOf(--ItemController.x));
                    item.setQtyOfCustomer(item.getQtyOfCustomer()-1);
                    setData(item);
                    CartController.total= CartController.total-Integer.parseInt(item.getPrice());
                    lblGrandTotal.setText(CartController.total+".0");
                    //lblNetTotal.setText(CartController.total+".0");
                    calculateDiscount();
                }else{ new Alert(Alert.AlertType.WARNING,"Please remove this item if you want").show();}
                break;
            }
        }
    }

    public void clearOnAction(MouseEvent mouseEvent) {

        for (CartItem item:ItemController.items
        ) {
            if (lblItemId.getText().equals(item.getItemId())){
                ItemController.x=ItemController.x-item.getQtyOfCustomer();
                if(ItemController.x!=0){
                    ItemController.lblCart.setText(String.valueOf(ItemController.x));
                }else{
                    ItemController.lblCart.setVisible(false);
                    MenuButtonController.grdContainerCopy.getChildren().clear();
                }
                CartController.total= CartController.total-(Integer.parseInt(item.getPrice())*item.getQtyOfCustomer());
                lblGrandTotal.setText("Rs  "+ CartController.total+".00/=");
                gridContainer1.getChildren().remove(ItemController.items.indexOf(item));
                break;
            }
        }
        ItemController.items.removeIf(item -> lblItemId.getText().equals(item.getItemId()));
        itemCount.setText(String.valueOf(ItemController.items.size()));

    }
}
