package models.client.players;

import javafx.scene.paint.*;
import models.client.FieldGenerator;
import views.BoardViewManager;

public class Player3 extends Player{
    Player3(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        this.setID(3);
        this.generateFields(isThisMe, radius, ch, wGap, hGap);
        if (isThisMe) this.addPlayerHandlers();
    }
    @Override
    public void generateFields(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        Paint color = new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#d2f2e4")), new Stop(1, Color.web("#00d0f4")));
        if (isThisMe) BoardViewManager.setMyColor(color);
        this.setCircleFieldList(FieldGenerator.generateFields(true, false, ch, 1, -ch, ch+1, ch, radius, wGap, hGap, color));
    }
}
