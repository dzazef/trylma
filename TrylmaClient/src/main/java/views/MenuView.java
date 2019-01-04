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
    public static Stage menuStage;
    /**
     * Funkcja ustawiająca parametry okna.
     */
    public static void initialize() throws IOException {
        final Parent menuParent = FXMLLoader.load(MenuView.class.getResource("../menuStructure.fxml"));
        menuStage.setScene(new Scene(menuParent));
//        menuStage.setResizable(false);
        menuStage.setTitle("Trylma");
        menuStage.show();
    }

    /**
     * Funkcja pokazująca okno Menu.
     */
    public static void show() {
        menuStage.show();
    }

    /**
     * Funkcja ukrywająca okno Menu.
     */
    public void hide() {
        menuStage.hide();
    }
}
