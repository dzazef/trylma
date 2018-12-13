package controllers;

import javafx.fxml.FXML;
import views.BoardView;

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

    }
    @FXML
    public void handleNewGame() {
        BoardView.initialize(800, 4, 1, 0);
        BoardView.show();
        BoardView.initializeFields();
    }
}
