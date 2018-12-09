package models.client;

import javafx.scene.shape.Circle;
import models.client_server.Field;

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
    public void changeCircle(double x, double y) {
        getCircle().setCenterX(x);
        getCircle().setCenterY(y);
    }
    public void changeField(int x, int y, int z) {
        getField().setX(x);
        getField().setY(y);
        getField().setZ(z);
    }
    public double getCircleX() {
        return getCircle().getCenterX();
    }
    public double getCircleY() {
        return getCircle().getCenterY();
    }
    public int getX() {
        return getField().getX();
    }
    public int getY() {
        return getField().getY();
    }
    public int getZ() {
        return getField().getZ();
    }
}
