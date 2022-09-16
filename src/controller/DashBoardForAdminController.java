package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import otherClass.FxmlLoader;
import otherClass.TitleBarController;
import java.io.IOException;

public class DashBoardForAdminController implements TitleBarController {
    public Pane contextForAdmin;
    public JFXButton btnMinimize;
    public Pane pneMove1;
    private Stage stage;
    private double x, y;

    public void dragged(MouseEvent event) {
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    public void pressed(MouseEvent event) {
        stage = (Stage) pneMove1.getScene().getWindow();
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void minimize(ActionEvent actionEvent) {
        Stage stage=(Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public void exitOnAction(ActionEvent actionEvent) {
        new FxmlLoader().exit(actionEvent);
    }

    public void userSettingsOnAction(ActionEvent actionEvent) {
        try {
            new FxmlLoader().loadChildFxml("../view/UserSettings.fxml",actionEvent,contextForAdmin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchOnAction(ActionEvent actionEvent) {
        try {
            new FxmlLoader().chooseFxml1("../view/DashBoardForUser.fxml",actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
