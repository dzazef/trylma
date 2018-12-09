import javafx.application.Application;
import javafx.stage.Stage;
import models.client_server.Connection;
import views.Menu;


import java.io.IOException;

public class TrylmaClient extends Application {

    public static void main(String[] args) {
        Connection connection = new Connection();
        if(connection.establishConnection())
        {
            System.out.println("connection established");
        }
        else
        {
            System.out.println("connection failure");
        }

            connection.write("To ja klient");

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
