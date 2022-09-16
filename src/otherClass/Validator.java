package otherClass;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public  class Validator {
    public static Object validate( LinkedHashMap<JFXTextField,Pattern> allValidations) {
        for (JFXTextField textField : allValidations.keySet()) {
            Pattern pattern = allValidations.get(textField);
            if (!pattern.matcher(textField.getText()).matches()) {
                if (!textField.getText().isEmpty()){
                    textField.setStyle("-fx-prompt-text-fill: red");
                }
                return textField;
            }
            textField.setStyle("-fx-prompt-text-fill: #aeaeae");
        }
        return true;
    }

    public static void keyController(LinkedHashMap<JFXTextField,Pattern> allValidations,javafx.scene.input.KeyEvent keyEvent){
        Object response = validate(allValidations);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField textField = (JFXTextField) response;
                textField.requestFocus();
                if (!textField.getText().isEmpty()){
                    new Alert(Alert.AlertType.WARNING,"Incompatible input in "+textField.getPromptText()+" TextField").show();
                }
            }
        }

    }
}
