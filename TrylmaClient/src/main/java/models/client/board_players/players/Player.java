package models.client.board_players.players;

import javafx.scene.paint.*;
import models.client.CircleField;
import models.client.FieldGenerator;
import handlers.Handle;
import views.BoardView;

import java.util.Iterator;
import java.util.List;

/**
 * Klasa obsługująca graczy.
 */
public class Player {
    private int ID;
    private List<CircleField> circleFieldList;

    public Player(){
        System.out.println("run empty constructor for player");
    }
    public Player(int id) {
        this.ID=id;
    }
    public Player(int id, List<CircleField> cf) {
        this.ID=id;
        this.circleFieldList=cf;
    }

    /**
     * Tworzy iterator po polach dla danego gracza.
     * @return iterator
     */
    public Iterator createIterator() {
        return new PlayerFieldIterator(circleFieldList);
    }
    public int getID() {
        return ID;
    }
    public List<CircleField> getCircleFields() {
        return this.circleFieldList;
    }

    /**
     * Funkcja generuje pole dla gracza w zależności od jego ID.
     * @param isThisMe parametr określa czy dany gracz jest obsługiwany przez aktualnie uruchomionego klienta (czy jest innym klientem.
     * @param radius promień koła.
     * @param ch patrz {@link views.BoardView#initialize(int, int, double, double)}
     * @param wGap patrz {@link views.BoardView#initialize(int, int, double, double)}
     * @param hGap patrz {@link views.BoardView#initialize(int, int, double, double)}
     */
    @SuppressWarnings("Duplicates")
    public void generateFields(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        RadialGradient radialGradient;
        int x = 0; int y = 0; int z = 0; boolean ud = false; Paint color = Color.GRAY;
        assert ID<=6;
        switch(ID) {
            case 1: {
                radialGradient =
                        new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("#ffb4a5")), new Stop(1, Color.web("#ab0000")));
                x = -2*ch; y = -ch; z = -ch; ud = false; color = radialGradient; break;
            } case 4: {
                radialGradient =
                        new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("#d3fbd2")), new Stop(1, Color.web("#328900")));
                x = -1; y = ch; z = -(ch+1); ud = true; color = radialGradient; break;
            } case 6: {
                radialGradient =
                        new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("#ffc0f9")), new Stop(1, Color.web("#a100f9")));
                x = 1; y = ch+1; z = -ch; ud = false; color = radialGradient; break;
            } case 2: {
                radialGradient =
                        new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("#dce3fb")), new Stop(1, Color.web("#0007f9")));
                x = 2*ch; y = ch; z = ch; ud = true; color = radialGradient; break;
            } case 3: {
                radialGradient =
                        new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("#d2f2e4")), new Stop(1, Color.web("#00d0f4")));
                x = 1; y = -ch; z = ch+1; ud = false; color = radialGradient; break;
            } case 5: {
                radialGradient =
                        new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("#ffffc6")), new Stop(1, Color.web("#ffcb00")));
                x = -1; y = -(ch+1); z = ch; ud = true; color = radialGradient; break;
            }
        }
        circleFieldList = FieldGenerator.generateFields(true, ud, ch, x, y, z, ch, radius, wGap, hGap, color);
        if (isThisMe) {
            addPlayerHandlers(circleFieldList, radius, ch, wGap, hGap);
            BoardView.setMyColor(color);
        }
    }
    private void addPlayerHandlers(List<CircleField> circleFields, double radius, int ch, double wGap, double hGap) {
        for (CircleField circleField : circleFields) {
            circleField.setOnMousePressed(e -> Handle.playerHandle(circleField));
        }
    }
}
