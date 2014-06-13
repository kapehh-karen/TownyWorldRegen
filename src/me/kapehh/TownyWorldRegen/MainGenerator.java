package me.kapehh.TownyWorldRegen;

/**
 * Created by Karen on 13.06.2014.
 */
public class MainGenerator {

    public static boolean locationDistance(int x, int z, int xCenter, int zCenter, int radius) {
        return (radius >= Math.sqrt(Math.pow(Math.abs(x - xCenter), 2) + Math.pow(Math.abs(z - zCenter), 2)));
    }

    public static int locationToChunk(int worldCoordinate) {
        // -1 through -16 are chunk -1,
        // 0 through 15 are chunk 0,
        // 16 through 32 are chunk 1...
        if (worldCoordinate < 0)
            return (worldCoordinate + 1)/16 - 1;
        else
            return worldCoordinate/16;
    }
}
