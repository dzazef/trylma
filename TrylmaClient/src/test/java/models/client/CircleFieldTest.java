package models.client;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import serializable.Field;
import static org.junit.Assert.*;


public class CircleFieldTest {
    private CircleField dummy;
    @Before
    public void init() {
        dummy = new CircleField(new Field(1, 1, 1, true), 1, 1, 1, Color.GRAY);
    }

    @Test
    public void setField() {
        Field field = new Field(1, 1, 1, false);
        dummy.setField(field);
        assertEquals(dummy.getField(), field);
    }

    @Test
    public void getField() {
        assertNotNull(dummy.getField());
    }

    @Test
    public void compare() {
        assertTrue(dummy.compare(new Field(1,1,1,true)));
    }

    @Test
    public void setXY() {
        dummy.setXY(10, 10);
        assertEquals(10, dummy.getCenterX(), 0.1);
        assertEquals(10, dummy.getCenterY(), 0.1);
    }
}