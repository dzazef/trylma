package views;

import handlers.Handle;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import models.client.CircleField;
import models.client.FieldGenerator;
import models.client.players.Player;
import models.client.players.PlayerFactory;
import serializable.Field;
import serializable.FieldsSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.abs;

public class BoardViewManager extends BoardView {
    private static List<CircleField> circleFields;
    private static List<Player> playerList;
    private static List<CircleField> possibleFields;
    private static List<FillTransition> fillTransitions;
    public static void initializeFields() {
        circleFields = new ArrayList<>();
        playerList = new ArrayList<>();
        possibleFields = new ArrayList<>();
        fillTransitions = new ArrayList<>();
        circleFields.addAll(FieldGenerator.generateFields(false,false, 3*ch+1, -(2* ch), -ch, -ch, ch, radius, wGap, hGap, Color.GRAY));
        circleFields.addAll(FieldGenerator.generateFields(false, true, ch, -1, -(ch+1), ch, ch, radius, wGap, hGap, Color.GRAY));
        circleFields.addAll(FieldGenerator.generateFields(false,true, ch, -1, ch, -(ch+1), ch, radius, wGap, hGap, Color.GRAY));
        circleFields.addAll(FieldGenerator.generateFields(false,true, ch, 2*ch, ch, ch, ch, radius, wGap, hGap, Color.GRAY));
    }

    /**
     * Funkcja dodaje nowego gracza.
     * @param isThisMe true, jeśli gracz jest aktualnym klientem
     */
    public static void addNewPlayer(boolean isThisMe) {
        Player player = PlayerFactory.getPlayer(playerList.size()+1, isThisMe, radius, ch, wGap, hGap);
        if (player==null) System.err.println("Wrong user id. Couldn't create user.");
        else {
            playerList.add(player);
            BoardViewManager.draw(player.getCircleFields());
        }
    }

    /**
     * Metoda ustawia kolor gracza.
     * @param color kolor gracza
     */
    public static void setMyColor(Paint color) {
        myColor.setFill(color);
    }


    /**
     * Metoda ustawiająca kolor indykatora informującego o możliwości ruchu.
     * @param b true, jeśli jest możliwy ruch, w przeciwnym wypadku false.
     */
    public static void setMyTurn(boolean b) {
        if (b) myTurn.setFill(Color.GOLD);
        else myTurn.setFill(Color.SILVER);
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
            if (p.getID()==playerID) player =p;
        }
        if (player !=null) {
            System.out.println("MOVEINFO: player: "+ player.getID());
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

    /**
     * Funkcja pokazuje możliwe pola za pomocą animacji.
     * @param FieldsSet lista możliwych pól
     */
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

            ft = new FillTransition(Duration.millis(800), circleField, Color.web("#3e3e3e"), Color.GRAY);
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            fillTransitions.add(ft);

            possibleFields.add(circleField);
            CircleField finalCircleField = circleField;
            circleField.setOnMouseClicked(e -> Handle.possibleFieldHandle(finalCircleField));
        }
        BoardViewManager.draw(possibleFields);
        for (FillTransition fillTransition : fillTransitions) {
            fillTransition.play();
        }
    }

    /**
     * Metoda usuwa możliwe pola z planszy.
     */
    public static void removePossibleFields() {
        for (FillTransition fillTransition : fillTransitions) {
            fillTransition.stop();
        }
        BoardViewManager.undraw(possibleFields);
        fillTransitions.clear();
        possibleFields.clear();
    }

    /**
     * Metoda resetuje planszę.
     */
    public static void reset() {
        if (isBoardInitialized()) {
            undraw(circleFields);
            for (Player p : playerList) {
                undraw(p.getCircleFields());
            }
            circleFields.clear();
            playerList.clear();
            possibleFields.clear();
            fillTransitions.clear();
            setMyColor(Color.GRAY);
            setMyTurn(false);
        }
    }

    /**
     * Funkcja zwraca listę pól planszy.
     * @return lista pól planszy.
     */
    public static List<CircleField> getCircleFields() {
        return circleFields;
    }

    /**
     * Funkcja zwraca listę graczy.
     * @return lista graczy.
     */
    public static List<Player> getPlayerList() {
        return playerList;
    }
}
