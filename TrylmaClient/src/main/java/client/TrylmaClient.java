package client;

import javafx.application.Application;
import javafx.stage.Stage;
import views.MenuView;

import java.io.IOException;

/**
 * Główna klasa programu.
 */
public class TrylmaClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metoda tworząca okno powitalne i rozpoczynająca program.
     * @param stage główna scena
     */
    public void start(Stage stage) {
        MenuView.menuStage = stage;
        try {
            MenuView.initialize();
        } catch (IOException e) {
            System.out.println("Failed to initialize views.MenuView");
        }


    }

}
