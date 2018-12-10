package models.client.board_players.board;

import models.client.CircleField;
import models.client.board_players.FieldGenerator;
import handlers.Handle;
import javafx.scene.paint.Color;
import models.client.board_players.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa obsługująca planszę.
 */
public class Board {
    private static double radius, wGap, hGap;
    private static int ch;
    private static List<CircleField> circleFields = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();

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
    public static List<Player> getPlayers() {
        return players;
    }
    public static void addHandlersToCircles() {
        for(CircleField circleField : circleFields) {
            circleField.setOnMouseClicked(e -> Handle.boardHandle(circleField, radius, ch, wGap, hGap));
        }
    }
    public static Player getPlayerByID(int id) {
        for (Player player : players) {
            if (player.getID()==id) return player;
        }
        return null;
    }

    /**
     * Funkcja dodaje nowego gracza.
     * @param isThisMe parametr określa czy dany gracz jest obsługiwany przez aktualnie uruchomionego klienta (czy jest innym klientem.
     */
    public static void addNewPlayer(boolean isThisMe) {
        int playerID = players.size()+1;
        Player player = new Player(playerID);
        player.generateFields(isThisMe, radius, ch, wGap, hGap);
        players.add(player);
    }
}
