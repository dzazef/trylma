package views;

import models.client.CircleField;
import models.client.board_players.board.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.client.board_players.players.Player;
import models.client_server.Field;
import models.client_server.MovePath;

import java.util.List;

import static java.lang.Math.abs;

/**
 * Klasa obsługująca widok okna Board.
 */
@SuppressWarnings("Duplicates")
public class BoardView {
    //board parameters
    private static int ch;
    private static double hGap, wGap, radius;
    //window elements
    private static Stage boardStage;
    private static Group group;

    /**
     * Funkcja ustawiająca parametry okna Board.
     * Ponieważ wysokość okna zależy od trybu gry (i szerokości), parametry te są ustawiane bezpośrenio w konstruktorze tej klasy.
     * @param windowWidth1 szerokość okna.
     * @param ch1 określa z ilu pól składa się sługość bazy trójkąta gracza.
     * @param wGap1 określa lukę pomiędzy polami w poziomie.
     * @param hGap1 określa lukę pomiędzy polami w pionie.
     */
    public static void initialize(int windowWidth1, int ch1, double wGap1, double hGap1) {
        boardStage=new Stage();
        group=new Group();
        BoardView.ch =ch1;
        wGap=wGap1;
        hGap=hGap1;
        radius=(windowWidth1 - wGap *(3* BoardView.ch +2))/(2*(3* BoardView.ch + 1));
        double windowHeight = (2 * (4 * BoardView.ch + 1)) * radius + 2 * abs(hGap) - 4 * BoardView.ch * hGap;
        Scene scene = new Scene(group, windowWidth1, windowHeight);
        boardStage.setScene(scene);
        boardStage.setResizable(false);
        boardStage.setTitle("Trylma");
        boardStage.initModality(Modality.APPLICATION_MODAL);
    }

    /**
     * Funkcja pokazująca okno Board.
     */
    public static void show() {
        boardStage.show();
    }

    /**
     * Funkcja ukrywająca okno Board.
     */
    public static void hide() {
        boardStage.hide();
    }

    /**
     * Funkcja zapełnia planszę polami.
     */
    public static void initializeFields() {
        Board.set(radius, ch, wGap, hGap);
        Board.generateFields();
        draw(Board.getCircleFields());
        Board.addHandlersToCircles();
        Board.addNewPlayer(true);
        Board.addNewPlayer(true);
        Board.addNewPlayer(true);
        Board.addNewPlayer(true);
        for (Player player : Board.getPlayerList()) {
            draw(player.getCircleFields());
        }
        MovePath movePath = new MovePath();
        movePath.addField(new Field().with(-5, -3, -2));
        movePath.addField(new Field().with(-4, -2, -2));
        movePath.addField(new Field().with(-3, -2, -1));
        movePath.addField(new Field().with(-3, -3, 0));
        movePath.addField(new Field().with(-2, -3, 1));
        movePath.addField(new Field().with(-1, -3, 2));
        movePath.addField(new Field().with(0, -2, 2));
        movePath.addField(new Field().with(1, -2, 3));
        movePath.addField(new Field().with(2, -2, 4));
        movePath.addField(new Field().with(3, -1, 4));
        Board.makeMove(1, movePath);
    }

    /**
     * Funkcja umieszcza pola na planszy.
     * @param circleFields lista pól do umieszczenia na planszy.
     */
    public static void draw(List<CircleField> circleFields) {
        group.getChildren().addAll(circleFields);
    }

    public static void undraw(List<CircleField> circleFields) {
        group.getChildren().removeAll(circleFields);
    }
}
