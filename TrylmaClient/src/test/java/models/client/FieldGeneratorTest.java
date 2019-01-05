package models.client;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FieldGeneratorTest {
    @Test
    public void generateFields() {
        List<CircleField> circleField = FieldGenerator.generateFields(
           false, false, 4, 8, -4, -4, 4, 1, 1, 1, Color.BLACK);
        assertEquals(10, circleField.size());
        assertEquals(8, circleField.get(0).getField().getX());
        assertEquals(-4, circleField.get(0).getField().getY());
        assertEquals(-4, circleField.get(0).getField().getZ());
        assertEquals(1, (int)circleField.get(0).getScaleX());
        assertEquals(1, (int)circleField.get(0).getScaleY());
    }
}