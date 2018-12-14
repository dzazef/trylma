package handlers;

import models.client.CircleField;
import models.client_server.Connection;

/**
 * Klasa obsługuje kliknięcia w pola na planszy.
 */
public class Handle {
    public static void boardHandle(CircleField cf, double radius, int ch, double wGap, double hGap) {
        //TODO: delete later
    }
    public static void playerHandle(CircleField cf, double radius, int ch, double wGap, double hGap) {
        if (Connection.isitMyTurn()) Connection.sendChosenPawn(cf.getField());
    }
    public static void possibleFieldHandle(CircleField cf) {
        Connection.sendChosenField(cf.getField());
    }
    public static void skipHandle() {
        Connection.sendSkip();
    }
}
