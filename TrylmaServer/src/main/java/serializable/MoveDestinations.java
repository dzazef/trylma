package serializable;



import java.io.Serializable;
import java.util.ArrayList;
@Deprecated
public class MoveDestinations implements Serializable {
    private static final long serialVersionUID = 2775323433391508148L;
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
