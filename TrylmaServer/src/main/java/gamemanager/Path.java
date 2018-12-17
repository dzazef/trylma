package gamemanager;

import serializable.Field;

import java.io.Serializable;
import java.util.ArrayList;

public class Path implements Serializable {
    public Field start;
    public Field end;
    public ArrayList<Field> path;

    public Path()
    {
        this.path = new ArrayList<Field>();
    }
    public Path(ArrayList<Field> initializingListOfSteps)
    {
        this.path = initializingListOfSteps;
    }
    public void addStepToPath(Field step)
    {
        this.path.add(step);
        this.end=step;

    }
    public ArrayList<Field> getPath()
    {
        return path;
    }
}
