package handlers;

import models.client.CircleField;
import models.client_server.Connection;

/**
 * Klasa obsługuje kliknięcia w pola na planszy.
 */
public class Handle {
    /**
     * Metoda określająca działanie wykonywane po naciścnięiu pionka gracza.
     * @param cf pionek gracza
     */
    public static void playerHandle(CircleField cf) {
        if (Connection.isitMyTurn()) Connection.sendChosenPawn(cf.getField());
    }

    /**
     * Metoda określająca działanie wykonywane po naciścnięiu jednego z możliwych pól.
     * @param cf pole
     */
    public static void possibleFieldHandle(CircleField cf) {
        Connection.sendChosenField(cf.getField());
    }
}
