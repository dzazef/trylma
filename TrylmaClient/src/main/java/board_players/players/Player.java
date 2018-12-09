package board_players.players;

import board_players.FieldGenerator;
import handlers.Handle;
import javafx.scene.paint.Color;
import models.client.FieldCircle;

import java.util.Iterator;
import java.util.List;

public class Player {
    private int ID;
    private List<FieldCircle> fieldCircles;

    public Player(int id) {
        this.ID=id;
    }
    public Player(int id, List<FieldCircle> fieldCircles) {
        this.ID=id;
        this.fieldCircles=fieldCircles;
    }
    public Iterator createIterator() {
        return new PlayerFieldIterator(fieldCircles);
    }
    public int getID() {
        return ID;
    }
    public List<FieldCircle> getFieldCircles() {
        return this.fieldCircles;
    }
    public void generateFields(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        int x = 0; int y = 0; int z = 0; boolean ud = false; Color color = Color.GRAY;
        assert ID<=6;
        switch(ID) {
            case 1: {
                x = -2*ch; y = -ch; z = -ch; ud = false; color = Color.YELLOW; break;
            } case 2: {
                x = -1; y = ch; z = -(ch+1); ud = true; color = Color.GREEN; break;
            } case 3: {
                x = 1; y = ch+1; z = -ch; ud = false; color = Color.RED; break;
            } case 4: {
                x = 2*ch; y = ch; z = ch; ud = true; color = Color.BLUE; break;
            } case 5: {
                x = 1; y = -ch; z = ch+1; ud = false; color = Color.VIOLET; break;
            } case 6: {
                x = -1; y = -(ch+1); z = ch; ud = true; color = Color.ORANGE; break;
            }
        }
        fieldCircles = FieldGenerator.generateFields(true, ud, ch, x, y, z, ch, radius, wGap, hGap, color);
        if (isThisMe) addPlayerHandlers(fieldCircles, radius, ch, wGap, hGap);
    }
    private void addPlayerHandlers(List<FieldCircle> fieldCircles, double radius, int ch, double wGap, double hGap) {
        for (FieldCircle fieldCircle : fieldCircles) {
            fieldCircle.getCircle().setOnMousePressed(e -> Handle.playerHandle(fieldCircle, radius, ch, wGap, hGap));
        }
    }
}
