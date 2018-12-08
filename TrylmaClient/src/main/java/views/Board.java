package views;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Widok planszy.
 */
public class Board {
    private Stage boardStage;
    private Group group;
    private Scene scene;
    private int windowWidth = 0, windowHeight = 0;
    public Board() {
        this.boardStage=new Stage();
        this.group=new Group();
    }
    public void initialize() {
        scene=new Scene(group, windowWidth, windowHeight);
        boardStage.setScene(scene);
        boardStage.setResizable(false);
        boardStage.setTitle("Trylma");
    }
    @SuppressWarnings("SameParameterValue")
    public void setDimensions(int w, int h) {
        this.windowHeight=h;
        this.windowWidth=w;
    }
    public void show() {
        boardStage.show();
    }
    public void hide() {
        boardStage.hide();
    }

    /**
     * Funkcja generująca pola dla planszy.
     * @param checkers Parametr okreslający ilość pionków w bazie gracza. Domyślna wartość to 4.
     *                 Całkowita ilość pionków określona jest wzorem ilosc=(1+2+..+checkers).
     */
    public void initializeFields(int checkers) {

    }
}
