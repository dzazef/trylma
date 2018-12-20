package views;

import handlers.Handle;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.client.CircleField;
import models.client.board_players.board.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private static Button skip;
    private static GridPane gridPane;
    private static Text yourMove;
    private static Circle circle;

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
        BorderPane borderPane = new BorderPane();
        group=new Group();
        BoardView.ch =ch1;
        wGap=wGap1;
        hGap=hGap1;

        radius=(windowWidth1 - wGap *(3* BoardView.ch +2))/(2*(3* BoardView.ch + 1));
        double windowHeight = 50+(2 * (4 * BoardView.ch + 1)) * radius + 2 * abs(hGap) - 4 * BoardView.ch * hGap;

        skip=new Button("P");
        skip.setStyle("-fx-background-radius: 10em; "+"-fx-min-width: 16px; "+
                "-fx-min-height: 16px; "+"-fx-max-width: 16px; "+"-fx-max-height: 16px; "+
                "-fx-background-color: #c9c9c9;" + "-fx-background-insets: 0px; "+"-fx-padding: 0px;");
        yourMove = new Text("Twój kolor: ");
        yourMove.setFill(Color.WHITESMOKE);
        yourMove.setStyle("-fx-font-weight: bold");
        circle = new Circle(8, Color.SALMON);


        gridPane = new GridPane();
        gridPane.setPadding(new Insets(8, 5, 8, 0.27*windowWidth1));
        skip.setMaxHeight(10);
        gridPane.getColumnConstraints().addAll(new ColumnConstraints(100), new ColumnConstraints(90), new ColumnConstraints(100));
        gridPane.setStyle("-fx-background-color: #575757");
        gridPane.add(skip, 0, 0);
        gridPane.add(yourMove, 1, 0);
        gridPane.add(circle, 2, 0);

        Scene scene = new Scene(borderPane, windowWidth1, windowHeight);
        boardStage.setScene(scene);
        borderPane.setCenter(group);
        borderPane.setBottom(gridPane);
        boardStage.setResizable(false);
        boardStage.setTitle("Trylma");
        boardStage.initModality(Modality.APPLICATION_MODAL);
        skip.setOnAction(e -> Handle.skipHandle());

        //tu testy
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

    public static void setMyColor(Paint color) {
        BoardView.circle.setFill(color);
    }
}
