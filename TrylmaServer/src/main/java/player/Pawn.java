package player;

import gamemanager.board.Field;

public class Pawn {
    int x,y,z;


    int getX()
    {
        return this.x;
    }
    int getY()
    {
        return  this.y;
    }
    int getZ()
    {
        return this.z;
    }
    public void setXYZ(int x,int y,int z)
    {

    }

    public String getId()
    {
        return "x,y,z";
    }
}
