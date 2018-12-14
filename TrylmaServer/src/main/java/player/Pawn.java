package player;


public class Pawn {
    private int x,y,z;


    public Pawn(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
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
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getId()
    {
        return x+","+y+","+z;
    }
}
