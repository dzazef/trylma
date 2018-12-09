package board_players.players;

import models.client.FieldCircle;

import java.util.Iterator;
import java.util.List;

public class PlayerFieldIterator implements Iterator {
    private List<FieldCircle> fieldCircles;
    private int pos = 0;
    public PlayerFieldIterator(List<FieldCircle> fieldCircles1) {
        this.fieldCircles=fieldCircles1;
    }
    @Override
    public boolean hasNext() {
        return (pos<fieldCircles.size());
    }

    @Override
    public Object next() {
        return fieldCircles.get(pos++);
    }
}
