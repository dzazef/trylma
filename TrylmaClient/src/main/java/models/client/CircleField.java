package models.client;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import models.client_server.Field;

import java.io.Serializable;

/**
 * Klasa rozszerza klasę Circle dodając do każdego koła obiekt klasy Field z nim powiązany.
 */
public class CircleField extends Circle implements Serializable {
    public void setField(Field field) {
        this.field = field;
    }

    private Field field;

    public CircleField with(Field field1, double centerX, double centerY, double radius, Color color){
        setCenterX(centerX);
        setCenterY(centerY);
        setRadius(radius);
        setFill(color);
        this.field=field1;
        return this;
    }

    public Field getField() {
        return field;
    }
}
