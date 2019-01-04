package models.client;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import serializable.Field;

import java.io.Serializable;

/**
 * Klasa rozszerza klasę Circle dodając do każdego koła obiekt klasy Field z nim powiązany.
 */
public class CircleField extends Circle implements Serializable {
    public void setField(Field field) {
        this.field = field;
    }

    private Field field;

    /**
     * Konstruktor tworzący kompletny obiekt.
     * @param field1 obiekt Field
     * @param centerX współrzędna x środka koła
     * @param centerY współrzędna y środka koła
     * @param radius promień koła
     * @param color kolor koła
     */
    public CircleField(Field field1, double centerX, double centerY, double radius, Paint color){
        super(centerX, centerY, radius, color);
        this.field=field1;
    }

    /**
     * Getter dla pola Field.
     * @return pole Field.
     */
    public Field getField() {
        return field;
    }

    /**
     * Funkcja porównująca obiekty Field.
     * @param field1 obiekt do porównania
     * @return zwraca true jeśli obiekty są równe, w przeciwnym wypadku false.
     */
    public boolean compare(Field field1) {
        return (field.getX()==field1.getX() && field.getY()==field1.getY() && field.getZ()==field1.getZ());
    }

    /**
     * Funkcja ustawiająca współrzędne środka koła.
     * @param x współrzędna x środka koła
     * @param y współrzędna y środka koła
     */
    public void setXY(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }
}
