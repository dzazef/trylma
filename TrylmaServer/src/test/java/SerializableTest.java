import org.junit.Test;
import serializable.Field;
import serializable.FieldsSet;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SerializableTest {
    @Test
    public void testField()
    {
        Field field = new Field(12,32,11,false);

        assertEquals(field.getX(),12);
        assertEquals(field.getY(),32);
        assertEquals(field.getZ(),11);
        assertEquals(field.getState(),Field.State.FREE);
        assertEquals(field.getId(),"12,32,11");

        field.changeState();

        assertEquals(field.getState(),Field.State.TAKEN);

        Field field2 = new Field();

        assertNotNull(field2);

    }

    @Test
    public void testFieldSet()
    {
        FieldsSet dummySet = new FieldsSet();

        dummySet.addField(new Field(12,34,22,false));
        dummySet.addField(new Field(1,1,1,true));

        assertEquals(dummySet.getEnd().getId(),"1,1,1");
        assertEquals(dummySet.getStart().getId(),"12,34,22");
        assertEquals(dummySet.getPath().size(),2);

        dummySet.removeLast();
        assertEquals(dummySet.getPath().size(),1);
        assertNotNull(dummySet.createIterator());

        ArrayList<Field> dummyarray = new ArrayList<>();
        dummyarray.add(new Field(33,33,33,false));

        FieldsSet dummySet2 = new FieldsSet(dummyarray);

        assertEquals(dummySet2.getPath().size(), 1);

    }
}
