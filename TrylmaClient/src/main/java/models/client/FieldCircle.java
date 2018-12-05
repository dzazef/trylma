package models.client;

import models.client_server.Field;

/**
 * Klasy łączy graficzne koło z polem na planszy.
 */
public class FieldCircle {
    private Field field;
    private CircleParam circle;
    public FieldCircle(Field f, CircleParam c) {
        this.circle=c;
        this.field=f;
    }
    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public CircleParam getCircle() {
        return circle;
    }

    public void setCircle(CircleParam circle) {
        this.circle = circle;
    }
}
