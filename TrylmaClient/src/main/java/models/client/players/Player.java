package models.client.players;

import handlers.Handle;
import models.client.CircleField;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    /**
     * Ustawia id gracza.
     * @param ID id gracza
     */
    void setID(int ID) {
        this.ID = ID;
    }

    private int ID;
    private List<CircleField> circleFieldList = new ArrayList<>();

    /**
     * Metoda generuje pola dla gracza.
     * @param isThisMe parametr określa czy dany gracz jest aktualnym klientem.
     * @param radius {@link views.BoardView#initialize(int, int, double, double)}
     * @param ch {@link views.BoardView#initialize(int, int, double, double)}
     * @param wGap {@link views.BoardView#initialize(int, int, double, double)}
     * @param hGap {@link views.BoardView#initialize(int, int, double, double)}
     */
    public abstract void generateFields(boolean isThisMe, double radius, int ch, double wGap, double hGap);

    /**
     * Metoda dodaje handlery do pól gracza.
     */
    void addPlayerHandlers() {
        for (CircleField circleField : circleFieldList) {
            circleField.setOnMousePressed(e -> Handle.playerHandle(circleField));
        }
    }

    /**
     * Zwraca id gracza.
     * @return id gracza
     */
    public int getID() {
        return ID;
    }

    /**
     * Zwraca listę pionków danego gracza.
     * @return lista pionków danego gracza.
     */
    public List<CircleField> getCircleFields() {
        return this.circleFieldList;
    }

    /**
     * Ustawia listę pionków danego gracza.
     * @param circleFieldList Lista pionków danego gracza.
     */
    void setCircleFieldList(List<CircleField> circleFieldList) {
        this.circleFieldList = circleFieldList;
    }
}
