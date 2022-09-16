package otherClass;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public interface TitleBarController {
    void dragged(MouseEvent event);
    void pressed(MouseEvent event);
    void minimize(ActionEvent actionEvent);
}
