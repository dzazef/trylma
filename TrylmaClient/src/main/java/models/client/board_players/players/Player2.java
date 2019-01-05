package models.client.board_players.players;

import javafx.scene.paint.*;
import models.client.FieldGenerator;

public class Player2 extends Player{
    Player2(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        this.setID(2);
        this.generateFields(isThisMe, radius, ch, wGap, hGap);
        this.addPlayerHandlers();
    }
    @Override
    public void generateFields(boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        Paint color = new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#dce3fb")), new Stop(1, Color.web("#0007f9")));
        this.setCircleFieldList(FieldGenerator.generateFields(true, true, ch, 2*ch, ch, ch, ch, radius, wGap, hGap, color));
    }
}
