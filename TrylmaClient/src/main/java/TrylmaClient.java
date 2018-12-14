import javafx.application.Application;
import javafx.stage.Stage;
import models.client_server.Connection;
import views.BoardView;
import views.MenuView;
import views.NewGameView;

import java.io.IOException;

public class TrylmaClient extends Application {

    public static void main(String[] args) {
        if(Connection.establishConnection()) {
            System.out.println("connection established");
        }
        else{
            System.out.println("connection failure");
        }
        launch(args);

    }

    public void start(Stage stage) {
        MenuView.menuStage = stage;
        try {
            MenuView.initialize();
        } catch (IOException e) {
            System.out.println("Failed to initialize views.MenuView");
        }


    }

}
