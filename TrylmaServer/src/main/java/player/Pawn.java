package player;

/**
 * Pawn class is used by players to create their pawns.
 */
public class Pawn {
    /**
     * Pawns coordinates.
     */
    private int x,y,z;


    public Pawn(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Function returns x coordinate.
     * @return x - coordinate.
     */
    int getX()
    {
        return this.x;
    }
    /**
     * Function returns y coordinate.
     * @return y - coordinate.
     */
    int getY()
    {
        return  this.y;
    }
    /**
     * Function returns z coordinate.
     * @return z - coordinate.
     */
    int getZ()
    {
        return this.z;
    }

    /**
     * Function sets pawn new coordinates.
     * @param x x - coordinate.
     * @param y y - coordinate.
     * @param z z - coordinate.
     */
    void setXYZ(int x,int y,int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Function returns id of pawn.
     * @return id in String.
     */
    public String getId()
    {
        return x+","+y+","+z;
    }
}
