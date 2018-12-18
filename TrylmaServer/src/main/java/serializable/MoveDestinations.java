package serializable;



import java.io.Serializable;
import java.util.ArrayList;

public class MoveDestinations implements Serializable {
    public ArrayList<Field> moveEnds;

    public MoveDestinations()
    {
        moveEnds = new ArrayList<Field>();
    }
    public void addMoveDestination(Field field)
    {
        moveEnds.add(field);
    }
}
