package gamemanager;

import gamemanager.board.Field;

import java.util.ArrayList;

public class Path {
    public Field start;
    public Field end;
    public ArrayList<Field> path;

    public void addStepToPath(Field step)
    {
        this.path.add(step);
        this.end=step;

    }
}
