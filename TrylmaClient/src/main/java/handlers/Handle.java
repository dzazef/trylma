package handlers;

import models.client.CircleField;
import models.client_server.Field;

import static java.lang.Math.abs;

/**
 * Klasa obsługuje kliknięcia w pola na planszy.
 */
public class Handle {
    private static CircleField circleField;
    public static void boardHandle(CircleField cf, double radius, int ch, double wGap, double hGap) {
        if (Handle.circleField!=null) {
            moveTo(circleField, cf.getField(), radius, ch, wGap, hGap);
        }
    }
    public static void playerHandle(CircleField circleField1, double radius, int ch, double wGap, double hGap) {
        Handle.circleField = circleField1;
    }

    @SuppressWarnings("Duplicates")
    public static void moveTo(CircleField from, Field field, double r, int ch, double wGap, double hGap) {
        from.setField(field);
        int x = field.getX();
        int y = field.getY();
        double centerX = (y-((float)x/2-(1.5*ch)))*(wGap+2*r)+wGap+r;
        double centerY = abs(hGap)+r+(x+2*ch)*(2*r-hGap);
        from.setCenterX(centerX);
        from.setCenterY(centerY);
    }
}
