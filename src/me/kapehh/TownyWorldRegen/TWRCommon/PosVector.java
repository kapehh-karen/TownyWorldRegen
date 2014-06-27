package me.kapehh.TownyWorldRegen.TWRCommon;

/**
 * Created by Karen on 27.06.2014.
 */
public class PosVector {
    private int x;
    private int y;
    private int z;

    public PosVector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("PosVector(%d, %d, %d)", x, y, z);
    }
}
