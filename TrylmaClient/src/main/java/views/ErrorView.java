package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorView {
    private Stage errorStage = new Stage();
    public void initialize() throws IOException {
        final Parent errorParent = FXMLLoader.load(getClass().getResource("../errorStructure.fxml"));
        errorStage.setScene(new Scene(errorParent));
        errorStage.setResizable(false);
        errorStage.setTitle("Błąd");
    }
    public void show() {
        errorStage.show();
    }
    public void hide() {
        errorStage.hide();
    }
}
