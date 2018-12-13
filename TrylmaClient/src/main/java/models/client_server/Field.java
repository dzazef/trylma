package models.client_server;

import java.io.Serializable;



/**
 * Klasa określa strukturę pojedynczego pola.
 * Wartości x, y i z to współrzędne danego pola.
 */
public class Field implements Serializable {
    private int x;
    private int y;
    private int z;

    public Field with(int x, int y, int z) {
        this.x=x;
        this.y=y;
        this.z=z;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
