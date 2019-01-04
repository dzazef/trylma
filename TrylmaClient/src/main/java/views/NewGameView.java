package views;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewGameView {
    private static Stage newGameStage = new Stage();
    public static void initialize() throws IOException {
        final Parent newGameParent = FXMLLoader.load(NewGameView.class.getResource("../newGameStructure.fxml"));
        newGameStage.setScene(new Scene(newGameParent));
//        newGameStage.setResizable(false);
        newGameStage.setTitle("Nowa gra");
    }
    public static void show() {
        newGameStage.show();
    }
    public static void hide() {
        newGameStage.hide();
    }
}