package controller;

import com.jfoenix.controls.JFXButton;
import controller.scrollpane.ItemController;
import controller.scrollpane.MenuButtonController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Button;
import otherClass.FxmlLoader;
import otherClass.TitleBarController;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardForUserController implements TitleBarController {
    public  ScrollPane scrlPane1;
    public GridPane grdContainer;
    public Pane pneMove;
    public JFXButton btnMinimize;
    public JFXButton btnExit;
    public  ScrollPane scrollPane2;
    public GridPane grdContainer1;
    public Label lblCart;
    public Label lblCartStatus;
    public Pane contextForFilters;
    private Stage stage;
    private double x, y;
    int column=0;
    int row=1;
    public static int Switch=0;

    public void initialize(){
        MenuButtonController.contextForFilters=contextForFilters;
        lblCartStatus.setVisible(false);
        ItemController.lblCart=lblCart;
        lblCart.setVisible(false);
        MenuButtonController.scrollPaneCopy=scrlPane1;        //to catch this scrollPane1 for clear its content
        MenuButtonController.grdContainerCopy=grdContainer;
        MainOrderController.cartStatus=lblCartStatus;
        try {
            for (Button button:addButton()) {
                FXMLLoader fxmlLoader=new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/scrollpane/MenuButton.fxml"));
                VBox buttonBox =fxmlLoader.load();
                MenuButtonController menuButtonController=fxmlLoader.getController();
                menuButtonController.setButton(button);
                if (column==1){
                    column=0;
                    ++row;
                }
                grdContainer1.add(buttonBox,column++,row);
                GridPane.setMargin(buttonBox,new Insets(5));
            }
            grdContainer.getChildren().clear();
            new MenuButtonController().loadItems("SELECT * FROM `item` WHERE Type =?","LAPTOP");

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<Button> addButton(){
        List<Button>buttons=new ArrayList<>();
        buttons.add(new Button("/assets/Button_laptop.png","LAPTOP"));
        buttons.add(new Button("/assets/processor.png","PROCESSOR"));
        buttons.add(new Button("/assets/Motherboard.png","MOTHERBOARD"));
        buttons.add(new Button("/assets/Ram.png","MEMORY (RAM)"));
        buttons.add(new Button("/assets/gpu.png","GRAPHIC CARDS"));
        buttons.add(new Button("/assets/ps.png","POWER SUPPLY"));
        buttons.add(new Button("/assets/cooling.png","COOLERS"));
        buttons.add(new Button("/assets/storage.png","STORAGE"));
        buttons.add(new Button("/assets/casing.png","CASINGS"));
        buttons.add(new Button("/assets/monitor.png","MONITOR"));
        buttons.add(new Button("/assets/key.png","KEYBOARD"));
        buttons.add(new Button("/assets/mouse.png","GAMING MOUSE"));
        buttons.add(new Button("/assets/Button_laptop.png","Laptop"));
        buttons.add(new Button("/assets/Button_laptop.png","Laptop"));
        buttons.add(new Button("/assets/Button_laptop.png","Laptop"));
        buttons.add(new Button("/assets/Button_laptop.png","Laptop"));
        buttons.add(new Button("/assets/Button_laptop.png","Laptop"));

        return buttons;
    }

    public void btnSettingsOnAction(){
        loadOnScrollPane("../view/scrollpane/Settings.fxml");
    }

    @Override
    public void dragged(MouseEvent event) {
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @Override
    public void pressed(MouseEvent event) {
        stage = (Stage) pneMove.getScene().getWindow();
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @Override
    public void minimize(ActionEvent actionEvent) {
        Stage stage=(Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public void exitOnAction(ActionEvent actionEvent) {new FxmlLoader().exit(actionEvent);}


    public void cartOnAction(){
        if (Switch==0){
            if(ItemController.items.isEmpty()){
                new Alert(Alert.AlertType.WARNING,"Your cart is empty...\nAdd something first, before jump up to the cart").show();
            }else {
                lblCartStatus.setVisible(false);
                loadOnScrollPane("../view/Cart.fxml");
            }
        }else{
            lblCartStatus.setVisible(true);
            loadOnScrollPane("../view/UpdatingCart.fxml");
        }
    }

    private void loadOnScrollPane(String path){
        grdContainer.getChildren().clear();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path));
        VBox settingsBox = null;
        try {
            settingsBox = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        grdContainer.add(settingsBox,column++,row);
        GridPane.setMargin(settingsBox,new Insets(10));
        column=0;
        row=1;
    }

    public void homeOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        grdContainer.getChildren().clear();
        new MenuButtonController().loadItems("SELECT * FROM `item` WHERE Type =?","LAPTOP");

    }
}
