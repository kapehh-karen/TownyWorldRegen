package me.kapehh.TownyWorldRegen.TWRRegen;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import me.kapehh.TownyWorldRegen.TWRCommon.WorldEditManager;
import me.kapehh.TownyWorldRegen.TWRCommon.PosVector;
import me.kapehh.TownyWorldRegen.TownyWorldRegen;
import org.bukkit.Chunk;
import org.bukkit.World;

/**
 * Created by Karen on 27.06.2014.
 */
public class QueueChunkRegen {

    // Координаты в чанке для регена
    private PosVector chunkRegen1;
    private PosVector chunkRegen2;

    // Ссылка на чанк
    private Chunk chunk;

    public QueueChunkRegen(Chunk chunk, PosVector pos1, PosVector pos2) throws Exception {
        int chunkXmin = ChunkHelperClass.locationFromChunk(chunk.getX());
        int chunkYmin = Math.min(pos1.getY(), pos2.getY());
        int chunkZmin = ChunkHelperClass.locationFromChunk(chunk.getZ());
        int chunkXmax = chunkXmin + ChunkHelperClass.CHUNK_MAX_XZ - 1;
        int chunkYmax = Math.max(pos1.getY(), pos2.getY());
        int chunkZmax = chunkZmin + ChunkHelperClass.CHUNK_MAX_XZ - 1;

        if ((chunkXmin > pos1.getX() && chunkXmin > pos2.getX()) ||
            (chunkXmax < pos1.getX() && chunkXmax < pos2.getX()) ||
            (chunkZmin > pos1.getZ() && chunkXmin > pos2.getZ()) ||
            (chunkZmax < pos1.getZ() && chunkXmax < pos2.getZ())) {
            throw new Exception("Invalid pos");
        }

        this.chunk = chunk;
        this.chunkRegen1 = new PosVector(
                ChunkHelperClass.locationInChunk(Math.max(chunkXmin, Math.min(pos1.getX(), pos2.getX()))),
                chunkYmin,
                ChunkHelperClass.locationInChunk(Math.max(chunkZmin, Math.min(pos1.getZ(), pos2.getZ())))
        );
        this.chunkRegen2 = new PosVector(
                ChunkHelperClass.locationInChunk(Math.min(chunkXmax, Math.max(pos1.getX(), pos2.getX()))),
                chunkYmax,
                ChunkHelperClass.locationInChunk(Math.min(chunkZmax, Math.max(pos1.getZ(), pos2.getZ())))
        );
    }

    public void regen() throws Exception {
        LocalWorld localWorld = WorldEditManager.getLocalWorld(chunk.getWorld().getName());
        World worldBukkit = chunk.getWorld();
        if (localWorld == null || worldBukkit == null) {
            throw new Exception("World not found");
        }

        int chunkX = ChunkHelperClass.locationFromChunk(chunk.getX());
        int chunkZ = ChunkHelperClass.locationFromChunk(chunk.getZ());

        // Сохраняем предыдущее состояние чанка
        BaseBlock[] blockBackup = new BaseBlock[ChunkHelperClass.CHUNK_MAX_XZ * ChunkHelperClass.CHUNK_MAX_XZ * ChunkHelperClass.CHUNK_MAX_Y];
        for (int x = 0; x < ChunkHelperClass.CHUNK_MAX_XZ; x++) {
            for (int z = 0; z < ChunkHelperClass.CHUNK_MAX_XZ; z++) {
                for (int y = 0; y < ChunkHelperClass.CHUNK_MAX_Y; y++) {
                    int index = y * 16 * 16 + z * 16 + x;
                    Vector pt = new Vector(chunkX + x, y, chunkZ + z);
                    blockBackup[index] = WorldEditManager.getBlock(localWorld, pt);
                }
            }
        }

        worldBukkit.regenerateChunk(chunk.getX(), chunk.getZ());

        // Восстанавливаем то что не надо было регенить
        for (int x = 0; x < ChunkHelperClass.CHUNK_MAX_XZ; x++) {
            for (int z = 0; z < ChunkHelperClass.CHUNK_MAX_XZ; z++) {
                for (int y = 0; y < ChunkHelperClass.CHUNK_MAX_Y; y++) {
                    if (
                        x >= chunkRegen1.getX() && x <= chunkRegen2.getX() &&
                        y >= chunkRegen1.getY() && y <= chunkRegen2.getY() &&
                        z >= chunkRegen1.getZ() && z <= chunkRegen2.getZ()
                    ) continue;
                    int index = y * 16 * 16 + z * 16 + x;
                    Vector pt = new Vector(chunkX + x, y, chunkZ + z);
                    WorldEditManager.setBlock(localWorld, worldBukkit, pt, blockBackup[index]);
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("QueueChunkRegen{%s, %s, %s}", chunk.toString(), chunkRegen1.toString(), chunkRegen2.toString());
    }
}
