package models.client.board_players;

import javafx.scene.paint.Color;
import models.client.CircleField;
import models.client_server.Field;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Klasa pozwalająca na generowanie pól dla planszy i graczy.
 */
public class FieldGenerator {
    /**
     * Funkcja gennerująca trójkąt pól powiązane z kołami. Pozwala na tworzenie nowych planszy oraz graczy.
     * @param d3 parametr określa czy trójkąt powinien być przeskalowany (pionki trochę większe niż pola).
     * @param ud parametr określa czy trójkąt jest odwrócony.
     * @param lines parametr okresla z ilu linii składa się trójkąt.
     * @param x współrzędna x punktu startowego.
     * @param y współrzędna y punktu startowego.
     * @param z współrzędna z punktu startowego.
     * @param ch parametr określa ilość pionków gracza. Więcej w {@link views.BoardView#initialize(int, int, double, double)}.
     * @param radius parametr określa promień narysowanego pionka.
     * @param WGap parametr określa odległość pomiędzy pionkami w poziomie.
     * @param HGap parametr określa odległość pomiędzy pionkami w pionie, gdzie wartość 0 oznacza że pola są ułożone w równe linie.
     * @param color parametr określa kolor narysowanego pionka.
     * @return zwraca listę obiektów FieldCircle, łączących narysowane koła z polami.
     */
    public static List<CircleField> generateFields(boolean d3, boolean ud, int lines, int x, int y, int z,
                                                   int ch, double radius,
                                                   double WGap, double HGap, Color color) {
        List<CircleField> circleFields = new ArrayList<>();
        double currentCenterX = (y-((float)x/2-(1.5*ch)))*(WGap+2*radius)+WGap+radius;
        double currentCenterY = abs(HGap)+radius+(x+2*ch)*(2*radius-HGap);
        int xlevel=x; int ylevel=y; int zlevel=z; double currentCenterXLevel=currentCenterX;

        for (int i = 0; i < lines; i++) {
            x = xlevel; y = ylevel; z = zlevel; currentCenterX=currentCenterXLevel;
            for (int j = 0; j <= i; j++) {
                final Field field = (new Field()).with(x, y, z);
                CircleField circleField = new CircleField().with(field, currentCenterX, currentCenterY, radius, color);
                if (d3) {
                    circleField.setScaleX(1.03);
                    circleField.setScaleY(1.03);
                }
                circleFields.add(circleField);
                y++;
                z--;
                currentCenterX+=WGap+2*radius;
            }
            currentCenterXLevel-=(WGap/2+radius);
            if (ud) {
                currentCenterY-=2*radius-HGap;
                xlevel--;
                ylevel--;
            } else {
                currentCenterY+=2*radius-HGap;
                xlevel++;
                zlevel++;
            }
        }

        return circleFields;
    }
}
