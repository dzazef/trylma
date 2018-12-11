package controllers;

import javafx.fxml.FXML;
import views.BoardView;
import views.NewGameView;

import java.io.IOException;

/**
 * Kontroler dla okna Menu.
 */
public class MenuController {
    @FXML
    public void menuExit() {
        System.exit(0);
    }
    @FXML
    public void menuNewGame() {
        try {
            NewGameView newGameView = new NewGameView();
            newGameView.initialize();
            newGameView.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Unable to start new game, FXML not found");
        }
    }
    @FXML
    public void handleNewGame() {
        BoardView.initialize(800, 4, 1, 0);
        BoardView.show();
        BoardView.initializeFields();
    }
}
