package gamemanager.board;

public class Field {
    int x,y,z;
    public enum State{
        TAKEN,FREE
    }
    State state;
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return  this.y;
    }
    public int getZ()
    {
        return this.z;
    }
    public void setXYZ(int x,int y,int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void changeState()
    {
        if(state.equals(State.FREE))
        {
            state = State.TAKEN;
        }
        else
        {
            state = State.FREE;
        }
    }
    public State getState()
    {
        return state;
    }
    public String getId()
    {
        return x+","+y+","+z;
    }
    public Field(int x,int y,int z,boolean taken)
    {
        this.x=x;
        this.y = y;
        this.z = z;
        if(taken = true)
        {
            this.state = State.TAKEN;
        }
        else
        {
            this.state = State.FREE;
        }
    }
}
