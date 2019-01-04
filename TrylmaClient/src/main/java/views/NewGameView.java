package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Widok dla okna NewGame.
 */
public class NewGameView {
    private static Stage newGameStage = new Stage();

    /**
     * Metoda ustawiająca parametry okna NewGame
     * @throws IOException wyjątek, gdy nie udało się załadować fxmla.
     */
    public static void initialize() throws IOException {
        final Parent newGameParent = FXMLLoader.load(NewGameView.class.getResource("../newGameStructure.fxml"));
        newGameStage.setScene(new Scene(newGameParent));
//        newGameStage.setResizable(false);
        newGameStage.setTitle("Nowa gra");
    }

    /**
     * Metoda pokazująca okno.
     */
    public static void show() {
        newGameStage.show();
    }

    /**
     * Metoda ukrywająca okno.
     */
    public static void hide() {
        newGameStage.hide();
    }
}