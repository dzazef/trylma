package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Widok dla okna Menu.
 */
public class MenuView {
    /**
     * Funkcja ustawiająca parametry okna.
     */
    public void initialize(Stage menuStage) throws IOException {
        final Parent menuParent = FXMLLoader.load(getClass().getResource("../menuStructure.fxml"));
        menuStage.setScene(new Scene(menuParent));
        menuStage.setResizable(false);
        menuStage.setTitle("Trylma");
    }

    /**
     * Funkcja pokazująca okno Menu.
     */
    public void show(Stage menuStage) {
        menuStage.show();
    }

    /**
     * Funkcja ukrywająca okno Menu.
     */
    public void hide(Stage menuStage) {
        menuStage.hide();
    }
}