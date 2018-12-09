package handlers;

import models.client.FieldCircle;
import models.client_server.Field;

import static java.lang.Math.abs;

public class Handle {
    private static FieldCircle fieldCircle;
    public static void boardHandle(FieldCircle f, double radius, int ch, double wGap, double hGap) {
        if (Handle.fieldCircle!=null) {
            moveTo(fieldCircle, f.getField(), radius, ch, wGap, hGap);
        }
    }
    public static void playerHandle(FieldCircle fieldCircle, double radius, int ch, double wGap, double hGap) {
        Handle.fieldCircle = fieldCircle;
    }

    @SuppressWarnings("Duplicates")
    public static void moveTo(FieldCircle from, Field field, double r, int ch, double wGap, double hGap) {
        from.setField(field);
        int x = field.getX();
        int y = field.getY();
        double centerX = (y-((float)x/2-(1.5*ch)))*(wGap+2*r)+wGap+r;
        double centerY = abs(hGap)+r+(x+2*ch)*(2*r-hGap);
        from.changeCircle(centerX, centerY);
    }
}
