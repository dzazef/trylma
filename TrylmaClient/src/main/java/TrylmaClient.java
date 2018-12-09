import javafx.application.Application;
import javafx.stage.Stage;
import models.client_server.Connection;
import views.MenuView;


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
        if(connection.isConnectionsuccess()) {
            connection.write("To ja klient");
        }

        launch(args);

    }

    public void start(final Stage stage) {
        MenuView menuView = new MenuView();
        try {
            menuView.initialize(stage);
            menuView.show(stage);

        } catch (IOException e) {
            System.out.println("Failed to initialize views.MenuView");
        }


    }

}
