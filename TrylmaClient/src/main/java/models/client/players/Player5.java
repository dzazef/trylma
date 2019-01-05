package models.client.players;

import javafx.scene.paint.*;
import models.client.FieldGenerator;
import views.BoardViewManager;

public class Player5 extends Player{
    Player5(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        this.setID(5);
        this.generateFields(isThisMe, radius, ch, wGap, hGap);
        this.addPlayerHandlers();
    }
    @Override
    public void generateFields(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        Paint color = new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#ffffc6")), new Stop(1, Color.web("#ffcb00")));
        if (isThisMe) BoardViewManager.setMyColor(color);
        this.setCircleFieldList(FieldGenerator.generateFields(true, true, ch, -1, -(ch+1), ch, ch, radius, wGap, hGap, color));
    }
}
