package serializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Klasa obsługująca pojedynczy ruch.
 */
public class FieldsSet implements Serializable {
    private static final long serialVersionUID = -5232299769650634853L;
    public Field start;
    public Field end;
    private ArrayList<Field> path ;
    public void addField(Field step)
    {
        this.path.add(step);
        this.end=step;

    }
    public FieldsSet()
    {
        this.path = new ArrayList<Field>();
    }

    public FieldsSet(ArrayList<Field> initializingListOfSteps)
    {
        this.path = initializingListOfSteps;
    }
    public ArrayList<Field> getPath()
    {
        return path;
    }

    public void removeLast() {
        this.path.remove(this.path.size()-1);
    }
    public Iterator createIterator() {
        return new FieldSetIterator(path);
    }
}
