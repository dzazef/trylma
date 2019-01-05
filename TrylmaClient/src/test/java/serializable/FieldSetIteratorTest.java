package serializable;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class FieldSetIteratorTest {
    private Iterator iterator;
    @Before
    public void init() {
        FieldsSet fieldsSet = new FieldsSet();
        fieldsSet.addField(new Field());
        fieldsSet.addField(new Field());
        fieldsSet.addField(new Field());
        iterator = fieldsSet.createIterator();
    }

    @Test
    public void hasNext() {
        assertTrue(iterator.hasNext());
    }

    @Test
    public void next() {
        assertNotNull(iterator.next());
    }

    @Test
    public void remove() {
    }
}