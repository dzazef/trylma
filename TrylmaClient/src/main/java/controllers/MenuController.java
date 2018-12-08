package controllers;

import javafx.fxml.FXML;
import views.Board;

/**
 * Kontroler obsługujący widok Menu;
 */
public class MenuController {
    @FXML
    public void menuExit() {
        System.exit(0);
    }
    @FXML
    public void menuNewGame() {
        Board board = new Board();
        board.setDimensions(700, 700);
        board.initialize();
        board.show();
    }
}
