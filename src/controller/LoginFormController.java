package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import otherClass.FxmlLoader;
import otherClass.TitleBarController;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginFormController implements TitleBarController {
    public Pane pneMove;
    public JFXButton btnExit;
    public JFXButton btnMinimize;
    public JFXTextField txtUserName;
    public JFXButton btnSignIn;
    public JFXPasswordField txtPassWord;
    private Stage stage;
    private double x, y;


    public void signInOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().isEmpty()){new Alert(Alert.AlertType.WARNING,"Enter your User Name").show();return;}
        if (txtPassWord.getText().isEmpty()){new Alert(Alert.AlertType.WARNING,"Enter your Password").show();return;}
        try {
            PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `user` WHERE `UserName`=?");
            pst.setObject(1,txtUserName.getText());
            ResultSet rst = pst.executeQuery();
            rst.next();
            if (rst.getString(5).equals(txtPassWord.getText())){
                if (rst.getString(4).equals("ADMIN")){
                    Alert alert  = new Alert(Alert.AlertType.CONFIRMATION, "Select your WorkSpace.");
                    ButtonType userButtonType = new ButtonType("User", ButtonBar.ButtonData.YES);
                    ButtonType adminButtonType = new ButtonType("Admin", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(userButtonType, adminButtonType );
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == adminButtonType) {
                        new FxmlLoader().chooseFxml("../view/DashBoardForAdmin.fxml",actionEvent);
                    }else {
                        new FxmlLoader().chooseFxml("../view/DashBoardForUser.fxml",actionEvent);
                    }
                }else {
                    new FxmlLoader().chooseFxml("../view/DashBoardForUser.fxml",actionEvent);
                }
            }else{
               new Alert(Alert.AlertType.WARNING,"Incorrect Password").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            new Alert(Alert.AlertType.WARNING,"Incorrect UserName").show();
        }
    }

    public void exitOnAction(ActionEvent actionEvent) {
        new FxmlLoader().exit(actionEvent);
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
}
