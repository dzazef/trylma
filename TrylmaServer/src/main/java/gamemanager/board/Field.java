package gamemanager.board;

public class Field {
    int x,y,z;
    public enum state{
        TAKEN,FREE
    }

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
    public void changeState()
    {

    }
    public state getState()
    {

        return state.TAKEN;
    }
    public String getId()
    {
        return "x,y,z";
    }
}
