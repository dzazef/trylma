package views;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.client.CircleField;
import models.client_server.Connection;

import java.util.List;

import static java.lang.Math.abs;

/**
 * Klasa obsługująca widok okna Board.
 */
public abstract class BoardView {
    //board parameters
    static int ch;
    static double hGap, wGap, radius;
    //window elements
    private static Stage boardStage;
    private static Group group;
    static Circle myColor, myTurn;
    private static boolean gameInitialized = false;


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
        ch =ch1;
        wGap=wGap1;
        hGap=hGap1;

        radius=(windowWidth1 - wGap *(3* ch +2))/(2*(3* ch + 1));
        double windowHeight = 50+(2 * (4 * ch + 1)) * radius + 2 * abs(hGap) - 4 * ch * hGap;

        Button skip = new Button("P");
        skip.setStyle("-fx-background-radius: 10em; "+"-fx-min-width: 16px; "+
                "-fx-min-height: 16px; "+"-fx-max-width: 16px; "+"-fx-max-height: 16px; "+
                "-fx-background-color: #c9c9c9;" + "-fx-background-insets: 0px; "+"-fx-padding: 0px;");
        skip.setMaxHeight(10);
        skip.setOnAction(e -> Connection.sendSkip());

        Text yourMove = new Text("Twój kolor: ");
        yourMove.setFill(Color.WHITESMOKE);
        yourMove.setStyle("-fx-font-weight: bold");

        myColor = new Circle(8, Color.GRAY);

        myTurn = new Circle(8, Color.SILVER);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(8, 5, 8, 0.27*windowWidth1));
        gridPane.getColumnConstraints().addAll(new ColumnConstraints(100), new ColumnConstraints(90), new ColumnConstraints(100), new ColumnConstraints(100));
        gridPane.setStyle("-fx-background-color: #575757");
        gridPane.add(skip, 0, 0);
        gridPane.add(yourMove, 1, 0);
        gridPane.add(myColor, 2, 0);
        gridPane.add(myTurn, 3, 0);

        Scene scene = new Scene(borderPane, windowWidth1, windowHeight);
        boardStage.setScene(scene);
        borderPane.setCenter(group);
        borderPane.setBottom(gridPane);
//        boardStage.setResizable(false);
        boardStage.setTitle("Trylma");
        boardStage.initModality(Modality.APPLICATION_MODAL);
        gameInitialized = true;
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
     * Metoda umieszcza pola na planszy.
     * @param circleFields lista pól do umieszczenia na planszy.
     */
    public static void draw(List<CircleField> circleFields) {
        group.getChildren().addAll(circleFields);
    }

    /**
     * Metoda usuwa pola z planszy.
     * @param circleFields lista pól do usunięcia z planszy
     */
    static void undraw(List<CircleField> circleFields) {
        group.getChildren().removeAll(circleFields);
    }

    /**
     * Funkcja sprawdza czy plansza została utworzona
     * @return true jeśli plansza została utowrzona, w przeciwnym wypadku false
     */
    static boolean isBoardInitialized() {
        return gameInitialized;
    }
}
