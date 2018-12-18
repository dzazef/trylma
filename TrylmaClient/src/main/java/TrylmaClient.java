import javafx.application.Application;
import javafx.stage.Stage;
import views.MenuView;

import java.io.IOException;

public class TrylmaClient extends Application {

    public static void main(String[] args) {
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
