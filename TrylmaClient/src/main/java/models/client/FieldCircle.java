package models.client;

import javafx.scene.shape.Circle;
import models.client_server.Field;

/**
 * Klasa łącząca obiekt klasy Field z kołem na planszy
 */
public class FieldCircle {
    private Field field;
    private Circle circle;
    public FieldCircle(Field f, Circle c) {
        this.circle=c;
        this.field=f;
    }
    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
