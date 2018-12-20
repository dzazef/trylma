package models.client.board_players.players;

import models.client.CircleField;

import java.util.Iterator;
import java.util.List;

@Deprecated
public class PlayerFieldIterator implements Iterator {
    private List<CircleField> circleFieldList;
    private int pos = 0;
    public PlayerFieldIterator(List<CircleField> circleFields1) {
        this.circleFieldList=circleFields1;
    }
    @Override
    public boolean hasNext() {
        return (pos<circleFieldList.size());
    }

    @Override
    public Object next() {
        return circleFieldList.get(pos++);
    }
}
