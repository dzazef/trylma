package serializable;

import java.io.Serializable;

/**
 * Class that defines serializable Field objects. It is used by Board class and FieldsSet class. Every field has its x,y,z - coordinates and state TAKEN/FREE, that defines every field.
 */
public class Field implements Serializable {
    static final long serialVersionUID = -1176358169417913238L;
    private int x,y,z;
    public enum State{
        TAKEN,FREE
    }
    private State state;

    /**
     * Function that sets x coordinate value.
     * @return x - coordinate.
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * Function that sets y coordinate value.
     * @return y - coordinate.
     */
    public int getY()
    {
        return  this.y;
    }
    /**
     * Function that sets z coordinate value.
     * @return z - coordinate.
     */
    public int getZ()
    {
        return this.z;
    }

    /**
     * Function that changes state of field to the opposite one.
     */
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

    /**
     * Function returns state of the field.
     * @return State of the field in Enum (TAKEN/FREE) type.
     */
    public State getState()
    {
        return state;
    }

    /**
     * Function returns id of the field.
     * @return Id of the field in String.
     */
    public String getId()
    {
        return x+","+y+","+z;
    }


    public Field(){}
    public Field(int x,int y,int z,boolean taken)
    {
        this.x=x;
        this.y = y;
        this.z = z;
        if(taken)
        {
            this.state = State.TAKEN;
        }
        else
        {
            this.state = State.FREE;
        }
    }
}
