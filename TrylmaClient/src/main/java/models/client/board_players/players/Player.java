package models.client.board_players.players;

import models.client.CircleField;
import models.client.FieldGenerator;
import handlers.Handle;
import javafx.scene.paint.Color;
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
    public void generateFields(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        int x = 0; int y = 0; int z = 0; boolean ud = false; Color color = Color.GRAY;
        assert ID<=6;
        switch(ID) {
            case 1: {
                x = -2*ch; y = -ch; z = -ch; ud = false; color = Color.SALMON; break;
            } case 4: {
                x = -1; y = ch; z = -(ch+1); ud = true; color = Color.GREEN; break;
            } case 6: {
                x = 1; y = ch+1; z = -ch; ud = false; color = Color.RED; break;
            } case 2: {
                x = 2*ch; y = ch; z = ch; ud = true; color = Color.BLUE; break;
            } case 3: {
                x = 1; y = -ch; z = ch+1; ud = false; color = Color.VIOLET; break;
            } case 5: {
                x = -1; y = -(ch+1); z = ch; ud = true; color = Color.ORANGE; break;
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
            circleField.setOnMousePressed(e -> Handle.playerHandle(circleField, radius, ch, wGap, hGap));
        }
    }
}
