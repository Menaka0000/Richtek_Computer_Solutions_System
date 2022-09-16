package controller.scrollpane;

import controller.CartController;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.CartItem;

public class QuotationUpdatingItemController extends QuotationItemController{
    public static GridPane gridContainer2;

    @Override
    public void plusOnAction(MouseEvent mouseEvent) {
        for (CartItem item:ItemController.items

        ) {

            if (lblItemId.getText().equals(item.getItemId())){
                if(  item.getQtyOnStock()>item.getQtyOfCustomer() || item.getQtyOfCustomer()<item.getPrevQtyOfCus() || item.getQtyOfCustomer()< item.getMaxQtyForUpdating()){
                    ItemController.lblCart.setText(String.valueOf(++ItemController.x));
                    item.setQtyOfCustomer(item.getQtyOfCustomer()+1);
                    setData(item);
                    CartController.total= CartController.total+Integer.parseInt(item.getPrice());
                    lblGrandTotal.setText("Rs  "+ CartController.total+".00/=");
                }else{ new Alert(Alert.AlertType.WARNING,"Sorry, this item is running OUT OF STOCK").show();}
                break;
            }
        }
    }

    @Override
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
                gridContainer2.getChildren().remove(ItemController.items.indexOf(item));
                break;
            }
        }
        ItemController.items.removeIf(item -> lblItemId.getText().equals(item.getItemId()));
        itemCount.setText(String.valueOf(ItemController.items.size()));
    }
}
