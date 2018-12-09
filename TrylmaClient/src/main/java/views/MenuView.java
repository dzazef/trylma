package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuView {
    public void initialize(Stage menuStage) throws IOException {
        final Parent menuParent = FXMLLoader.load(getClass().getResource("../menuStructure.fxml"));
        menuStage.setScene(new Scene(menuParent));
        menuStage.setResizable(false);
        menuStage.setTitle("Trylma");
    }

    public void show(Stage menuStage) {
        menuStage.show();
    }

    public void hide(Stage menuStage) {
        menuStage.hide();
    }
}