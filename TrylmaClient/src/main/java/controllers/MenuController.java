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
        BoardView.initialize(500, 4, 1, 0);
        BoardView.show();
        BoardView.initializeFields();
    }
}
