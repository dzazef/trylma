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

    public CircleField() {
        System.out.println("run empty constructor for circlefield");
    }

    public CircleField(Field field1, double centerX, double centerY, double radius, Color color){
        super(centerX, centerY, radius, color);
        this.field=field1;
    }

    public Field getField() {
        return field;
    }

    public boolean compare(Field field1) {
        return (field.getX()==field1.getX() && field.getY()==field1.getY() && field.getZ()==field1.getZ());
    }

    public void setXY(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }
}
