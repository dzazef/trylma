package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorController {
    public static String message;
    @FXML
    Label labelError;
    public void initialize() {
        labelError.setText(message);
    }
}
