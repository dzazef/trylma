package models.client;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Klasa tworzy koło z odpowiednimi parametrami do przedstawienia na planszy.
 */
public class CircleParam {
    private Circle circle;
    public CircleParam(Circle c) {
        this.circle=c;
    }
    public void setCircleColor(Color color) {
        this.circle.setFill(color);
    }
    public void setCircleXY(double x, double y) {
        this.circle.setCenterX(x);
        this.circle.setCenterY(y);
    }
    public void setRadius(double radius) {
        this.circle.setRadius(radius);
    }
    public Circle getCircle() {
        return this.circle;
    }
}
