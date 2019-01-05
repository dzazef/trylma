package models.client.players;

import javafx.scene.paint.*;
import models.client.FieldGenerator;
import views.BoardViewManager;

public class Player6 extends Player{
    Player6(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        this.setID(6);
        this.generateFields(isThisMe, radius, ch, wGap, hGap);
        this.addPlayerHandlers();
    }
    @Override
    public void generateFields(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        Paint color = new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#ffc0f9")), new Stop(1, Color.web("#a100f9")));
        if (isThisMe) BoardViewManager.setMyColor(color);
        this.setCircleFieldList(FieldGenerator.generateFields(true, false, ch, 1, ch+1, -ch, ch, radius, wGap, hGap, color));
    }
}
