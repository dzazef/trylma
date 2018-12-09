package controllers;

import javafx.fxml.FXML;
import views.BoardView;

public class MenuController {
    @FXML
    public void menuExit() {
        System.exit(0);
    }
    @FXML
    public void menuNewGame() {
        BoardView boardView = new BoardView(700, 4, 5, 5);
        boardView.initialize();
        boardView.show();
        boardView.initializeFields();
    }
}
