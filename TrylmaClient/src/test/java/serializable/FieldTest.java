package serializable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FieldTest {
    private Field field;
    @Before
    public void init() {
        field = new Field(1, 2, 3, true);
    }

    @Test
    public void getX() {
        assertEquals(1, field.getX());
    }

    @Test
    public void getY() {
        assertEquals(2, field.getY());

    }

    @Test
    public void getZ() {
        assertEquals(3, field.getZ());

    }

    @Test
    public void changeState() {
        field.changeState();
        assertEquals(Field.State.FREE, field.getState());
    }

    @Test
    public void getState() {
        assertNotNull(field.getState());
    }

    @Test
    public void getId() {
        assertEquals("1,2,3", field.getId());
    }
}