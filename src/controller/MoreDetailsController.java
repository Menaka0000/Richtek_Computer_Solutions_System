package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import otherClass.TitleBarController;

import java.sql.SQLException;

public class MoreDetailsController implements TitleBarController {
    public Label lblItemName;
    public JFXTextArea txtDetails;
    static Label lblItemNameCopy;
    static JFXTextArea txtDetailsCopy;
    public JFXButton btnMinimize;
    public JFXButton btnExit;
    public Pane pneMove;
    private Stage stage;
    private double x, y;

    public void initialize(){
        lblItemNameCopy=lblItemName;
        txtDetailsCopy=txtDetails;
    }

    public static void load(JFXTextArea heading,Label lblItemName1){
        try {
            lblItemNameCopy.setText(heading.getText());
            txtDetailsCopy.setText(new MainItemController().moreDetails(lblItemName1.getText()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
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

    public void exitOnAction(ActionEvent actionEvent) {
        Stage stage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
