package board_players.board;

import board_players.FieldGenerator;
import handlers.Handle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import models.client.FieldCircle;
import models.client_server.Field;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static List<FieldCircle> fieldCircles = new ArrayList<>();
    public static void generateFields(double radius, int ch, double wGap, double hGap) {
        fieldCircles.addAll(FieldGenerator.generateFields(false,false, 3*ch+1, -(2* ch), -ch, -ch, ch, radius, wGap, hGap, Color.GRAY));
        fieldCircles.addAll(FieldGenerator.generateFields(false, true, ch, -1, -(ch+1), ch, ch, radius, wGap, hGap, Color.GRAY));
        fieldCircles.addAll(FieldGenerator.generateFields(false,true, ch, -1, ch, -(ch+1), ch, radius, wGap, hGap, Color.GRAY));
        fieldCircles.addAll(FieldGenerator.generateFields(false,true, ch, 2*ch, ch, ch, ch, radius, wGap, hGap, Color.GRAY));
    }
    public static List<FieldCircle> getFieldCircles() {
        return fieldCircles;
    }
    public static void addHandlersToCircles(double radius, int ch, double wGap, double hGap) {
        for(FieldCircle fieldCircle : fieldCircles) {
            fieldCircle.getCircle().setOnMouseClicked(e -> Handle.boardHandle(fieldCircle, radius, ch, wGap, hGap));
        }
    }
    @Deprecated
    public static Circle findByField(Field field) {
        for (FieldCircle fieldCircle : fieldCircles) {
            if (fieldCircle.getX()==field.getX() && fieldCircle.getY()==field.getY() && fieldCircle.getZ()==field.getZ()) {
                System.out.println(field.getX()+" "+field.getY()+" "+field.getZ());
                return fieldCircle.getCircle();
            }
        }
        System.out.println("null");
        return new Circle();
    }
}
