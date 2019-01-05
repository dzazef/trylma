package serializable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class which objects are that flexible, that they can be used in various different occasions. They are playing a role of container, that stores Fields. FieldsSet is used while sending
 * to client possible move destinations, and while sending to client path, that pawn should follow. All in all, it is a flexible container object for storing fields.
 */
public class FieldsSet implements Serializable {
    private static final long serialVersionUID = -5232299769650634853L;
    private Field start;
    private Field end;
    private ArrayList<Field> path;

    /**
     * Function that adds new Field to set, simultaneously setting start of the set and end of the set.
     * @param step
     */
    public void addField(Field step)
    {
        this.path.add(step);
        this.start = path.get(0);
        this.end=step;
    }

    /**
     * Function returns FieldsSet end.
     * @return Last Field in set.
     */
    public Field getEnd() {
        return end;
    }

    /**
     * Function returns FieldsSet start.
     * @return First Field in set.
     */
    public Field getStart() {
        return start;
    }

    /**
     * Standard constructor.
     */
    public FieldsSet()
    {
        this.path = new ArrayList<>();
    }

    /**
     * Constructor that creates new FieldsSet with initialized list of Fields. Used to generate longer and longer jump paths.
     * @param initializingListOfSteps List of steps that initializes new FieldsSet.
     */
    public FieldsSet(ArrayList<Field> initializingListOfSteps)
    {
        this.path = new ArrayList<>();
        this.path.addAll(initializingListOfSteps);

    }

    /**
     * Function that returns list of Fields in FieldsSet.
     * @return list of Fields.
     */
    public ArrayList<Field> getPath()
    {
        return path;
    }

    /**
     * Function that removes last element from set. Used mainly in Client application.
     */
    public void removeLast() {
        this.path.remove(this.path.size()-1);
    }

    /**
     * Function that creates iterator for FieldsSet. Part of Iterator design pattern.
     * @return
     */
    public Iterator createIterator() {
        return new MovePathIterator(path);
    }
}
