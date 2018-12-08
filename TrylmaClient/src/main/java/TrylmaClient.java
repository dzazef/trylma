import javafx.application.Application;
import javafx.stage.Stage;


import java.io.IOException;

public class TrylmaClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage stage) {
        Menu menu = new Menu();
        try {
            menu.initialize(stage);
            menu.show(stage);

        } catch (IOException e) {
            System.out.println("Failed to initialize views.Menu");
        }


    }

}
