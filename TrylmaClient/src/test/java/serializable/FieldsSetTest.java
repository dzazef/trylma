package serializable;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FieldsSetTest {
    private FieldsSet fieldsSet = new FieldsSet();

    @Test
    public void addField() {
        fieldsSet.addField(new Field());
        assertTrue(fieldsSet.getPath().size()>0);
    }

    @Test
    public void getPath() {
        fieldsSet.addField(new Field(1, 1, 1, true));
        List<Field> fieldList = fieldsSet.getPath();
        assertEquals(1, fieldList.get(0).getX());
        assertEquals(1, fieldList.get(0).getY());
        assertEquals(1, fieldList.get(0).getZ());
        assertEquals(Field.State.TAKEN, fieldList.get(0).getState());
    }

    @Test
    public void removeLast() {
        fieldsSet.addField(new Field());
        fieldsSet.addField(new Field());
        assertEquals(2, fieldsSet.getPath().size());
        fieldsSet.removeLast();
        assertEquals(1, fieldsSet.getPath().size());
    }

    @Test
    public void createIterator() {
        fieldsSet.addField(new Field());
        Iterator iterator = fieldsSet.createIterator();
        assertTrue(iterator.hasNext());
    }
}