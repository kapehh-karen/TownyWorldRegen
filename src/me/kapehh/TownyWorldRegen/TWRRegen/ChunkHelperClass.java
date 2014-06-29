package me.kapehh.TownyWorldRegen.TWRRegen;

/**
 * Created by Karen on 27.06.2014.
 */
public class ChunkHelperClass {
    public static final int CHUNK_MAX_Y = 256;
    public static final int CHUNK_MAX_XZ = 16;

    public static boolean locationCheckDistance(int x, int z, int xCenter, int zCenter, int radius) {
        return (radius >= Math.sqrt(Math.pow(Math.abs(x - xCenter), 2) + Math.pow(Math.abs(z - zCenter), 2)));
    }

    // Получаем координаты чанка, относительно мировых координат
    public static int locationToChunk(int worldCoordinate) {
        // -1 through -16 are chunk -1,
        // 0 through 15 are chunk 0,
        // 16 through 32 are chunk 1...

        /*if (worldCoordinate < 0)
            return (worldCoordinate + 1) / CHUNK_MAX_XZ - 1;
        else
            return worldCoordinate / CHUNK_MAX_XZ;*/

        return worldCoordinate >> 4;
    }

    // Получаем координаты в чанке, относительно мировых координат
    public static int locationInChunk(int worldCoordinate) {
        if (worldCoordinate < 0)
            return (CHUNK_MAX_XZ - 1) + ((worldCoordinate + 1) % CHUNK_MAX_XZ);
        else
            return worldCoordinate % CHUNK_MAX_XZ;
    }

    // Получаем координаты чанка в мировых координатах
    public static int locationFromChunk(int chunkCoord) {
        //return chunkCoord * CHUNK_MAX_XZ;
        return chunkCoord << 4;
    }
}
