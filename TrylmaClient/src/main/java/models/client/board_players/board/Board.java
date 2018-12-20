package models.client.board_players.board;

import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import models.client.CircleField;
import models.client.FieldGenerator;
import handlers.Handle;
import javafx.scene.paint.Color;
import models.client.board_players.players.Player;
import serializable.Field;
import serializable.FieldsSet;
import views.BoardView;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static java.lang.Math.abs;

/**
 * Klasa obsługująca planszę.
 */
@SuppressWarnings("Duplicates")
public class Board {
    private static double radius, wGap, hGap;
    private static int ch;
    private static List<CircleField> circleFields = new ArrayList<>();
    private static List<Player> playerList = new ArrayList<>();
    private static List<CircleField> possibleFields = new ArrayList<>();
    private static List<FillTransition> fillTransitions = new ArrayList<>();

    /**
     * Ustawia parametry planszy.
     * @param radius1 patrz {@link views.BoardView#initialize(int, int, double, double)}
     * @param ch1 patrz {@link views.BoardView#initialize(int, int, double, double)}
     * @param wGap1 patrz {@link views.BoardView#initialize(int, int, double, double)}
     * @param hGap1 patrz {@link views.BoardView#initialize(int, int, double, double)}
     */
    public static void set(double radius1, int ch1, double wGap1, double hGap1) {
        radius=radius1;
        ch=ch1;
        wGap=wGap1;
        hGap=hGap1;
    }

    /**
     * Funkcja generuje pola dla poszczególnych trójkątów, z których się składa plansza.
     */
    public static void generateFields() {
        circleFields.addAll(FieldGenerator.generateFields(false,false, 3*ch+1, -(2* ch), -ch, -ch, ch, radius, wGap, hGap, Color.GRAY));
        circleFields.addAll(FieldGenerator.generateFields(false, true, ch, -1, -(ch+1), ch, ch, radius, wGap, hGap, Color.GRAY));
        circleFields.addAll(FieldGenerator.generateFields(false,true, ch, -1, ch, -(ch+1), ch, radius, wGap, hGap, Color.GRAY));
        circleFields.addAll(FieldGenerator.generateFields(false,true, ch, 2*ch, ch, ch, ch, radius, wGap, hGap, Color.GRAY));
    }
    public static List<CircleField> getCircleFields() {
        return circleFields;
    }
    public static List<Player> getPlayerList() {
        return playerList;
    }
    public static void addHandlersToCircles() {
        for(CircleField circleField : circleFields) {
            circleField.setOnMouseClicked(e -> Handle.boardHandle(circleField, radius, ch, wGap, hGap));
        }
    }

    /**
     * Funkcja dodaje nowego gracza.
     * @param isThisMe parametr określa czy dany gracz jest obsługiwany przez aktualnie uruchomionego klienta (czy jest innym klientem.
     */
    public static void addNewPlayer(boolean isThisMe) {
        int playerID = playerList.size()+1;
        Player player = new Player(playerID);
        player.generateFields(isThisMe, radius, ch, wGap, hGap);
        playerList.add(player);
        BoardView.draw(player.getCircleFields());
    }

    /**
     * Funkcja wykonuje przesunięcie dla danej ścieżki. Zostaje odtworzona animacja.
     * @param playerID id gracza do którego należy pionek.
     * @param FieldsSet ścieżka po której porusza się pionek.
     */
    public static void makeMove(int playerID, FieldsSet FieldsSet) {

        Iterator iterator = FieldsSet.createIterator();
        Field sourceField = (Field) iterator.next();
        Field temp = sourceField;
        System.out.println("MOVEINFO: source: "+sourceField.getX()+" "+sourceField.getY()+" "+sourceField.getZ()+" ");
        CircleField sourceCircle = null;
        Player player = null;

        Path path;
        PathTransition pathTransition;
        double centerX = 0;
        double centerY = 0;

        for (Player p : playerList) {
            if (p.getID()==playerID) player=p;
        }
        if (player!=null) {
            System.out.println("MOVEINFO: player: "+player.getID());
            for (CircleField cf : player.getCircleFields()) {
                if (cf.compare(sourceField)) sourceCircle = cf;
            }
            if (sourceCircle!=null) {
                int duration = 0;
                path = new Path();
                pathTransition = new PathTransition();
                path.getElements().add(new MoveTo(sourceCircle.getCenterX(), sourceCircle.getCenterY()));
                while(iterator.hasNext()) {
                    temp = (Field) iterator.next();
                    System.out.println("MOVEINFO: moveto: "+temp.getX()+" "+temp.getY()+" "+temp.getZ());
                    centerX = (temp.getY()-((float)temp.getX()/2-(1.5*ch)))*(wGap+2*radius)+wGap+radius;
                    centerY = abs(hGap)+radius+(temp.getX()+2*ch)*(2*radius-hGap);
                    path.getElements().add(new LineTo(centerX, centerY));
                    duration += 300;
                }
                sourceCircle.setField(temp);
                pathTransition.setDuration(Duration.millis(duration));
                pathTransition.setPath(path);
                pathTransition.setNode(sourceCircle);
                pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                CircleField finalSourceCircle = sourceCircle;
                double finalCenterX = centerX;
                double finalCenterY = centerY;
                pathTransition.setOnFinished(e -> {
                    finalSourceCircle.setTranslateX(0);
                    finalSourceCircle.setTranslateY(0);
                    finalSourceCircle.setXY(finalCenterX, finalCenterY);
                });
                pathTransition.play();
            }
            else {
                System.err.println("ERROR: Pawn not found. Check coordinates.");
            }
        }
        else {
            System.err.println("ERROR: Player not found. Check playerID.");
        }
    }

    public static void showPossibleFields(FieldsSet FieldsSet) {
        removePossibleFields();
        possibleFields.clear();
        CircleField circleField;
        FillTransition ft;
        double centerX, centerY;
        Iterator iterator = FieldsSet.createIterator();
        while (iterator.hasNext()) {
            Field field = (Field) iterator.next();
            centerX = (field.getY()-((float)field.getX()/2-(1.5*ch)))*(wGap+2*radius)+wGap+radius;
            centerY = abs(hGap)+radius+(field.getX()+2*ch)*(2*radius-hGap);

            circleField = new CircleField(field, centerX, centerY, radius, Color.BLACK);
            circleField.setScaleX(1.03);
            circleField.setScaleY(1.03);

            ft = new FillTransition(Duration.millis(800), circleField, Color.web("#3e3e3e") ,Color.GRAY);
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            fillTransitions.add(ft);

            possibleFields.add(circleField);
            CircleField finalCircleField = circleField;
            circleField.setOnMouseClicked(e -> Handle.possibleFieldHandle(finalCircleField));
        }
        BoardView.draw(possibleFields);
        for (FillTransition fillTransition : fillTransitions) {
            fillTransition.play();
        }
    }

    public static void removePossibleFields() {
        for (FillTransition fillTransition : fillTransitions) {
            fillTransition.stop();
        }
        BoardView.undraw(possibleFields);
        fillTransitions.clear();
        possibleFields.clear();
    }
}
