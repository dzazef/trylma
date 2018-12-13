package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewGameView {
    private Stage newGameStage = new Stage();
    public void initialize() throws IOException {
        final Parent newGameParent = FXMLLoader.load(getClass().getResource("../newGameStructure.fxml"));
        newGameStage.setScene(new Scene(newGameParent));
        newGameStage.setResizable(false);
        newGameStage.setTitle("Nowa gra");
    }
    public void show() {
        newGameStage.show();
    }
    public void hide() {
        newGameStage.hide();
    }
}
