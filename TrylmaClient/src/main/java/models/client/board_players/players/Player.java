package models.client.board_players.players;

import handlers.Handle;
import models.client.CircleField;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    void setID(int ID) {
        this.ID = ID;
    }

    private int ID;
    private List<CircleField> circleFieldList = new ArrayList<>();

    public abstract void generateFields(boolean isThisMe, double radius, int ch, double wGap, double hGap);

    void addPlayerHandlers() {
        for (CircleField circleField : circleFieldList) {
            circleField.setOnMousePressed(e -> Handle.playerHandle(circleField));
        }
    }

    public int getID() {
        return ID;
    }

    public List<CircleField> getCircleFields() {
        return this.circleFieldList;
    }

    void setCircleFieldList(List<CircleField> circleFieldList) {
        this.circleFieldList = circleFieldList;
    }
}
