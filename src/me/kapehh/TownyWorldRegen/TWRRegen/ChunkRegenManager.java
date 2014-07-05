package me.kapehh.TownyWorldRegen.TWRRegen;

import me.kapehh.TownyWorldRegen.TWRCommon.PosVector;
import me.kapehh.TownyWorldRegen.TownyWorldRegen;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 29.06.2014.
 */
public class ChunkRegenManager {

    // Мир
    private World world;

    // Координаты регена
    private PosVector posRegen1;
    private PosVector posRegen2;

    // Список чанков для регена
    private List<Chunk> listOfChunks = new ArrayList<Chunk>();

    public ChunkRegenManager(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) throws Exception {
        this(Bukkit.getWorld(worldName), x1, y1, z1, x2, y2, z2);
    }

    public ChunkRegenManager(World world, int x1, int y1, int z1, int x2, int y2, int z2) throws Exception {
        this(
            world,
            new PosVector(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2)),
            new PosVector(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2))
        );
    }

    public ChunkRegenManager(String worldName, PosVector pos1, PosVector pos2) throws Exception {
        this(Bukkit.getWorld(worldName), pos1, pos2);
    }

    public ChunkRegenManager(World world, PosVector pos1, PosVector pos2) throws Exception {
        if (world == null) {
            throw new Exception("World not found");
        }
        this.world = world;

        this.posRegen1 = pos1;
        this.posRegen2 = pos2;
        if (pos1.getX() > pos2.getX() || pos1.getY() > pos2.getY() || pos1.getZ() > pos2.getZ()) {
            this.posRegen1 = new PosVector(
                Math.min(pos1.getX(), pos2.getX()),
                Math.min(pos1.getY(), pos2.getY()),
                Math.min(pos1.getZ(), pos2.getZ())
            );
            this.posRegen2 = new PosVector(
                Math.max(pos1.getX(), pos2.getX()),
                Math.max(pos1.getY(), pos2.getY()),
                Math.max(pos1.getZ(), pos2.getZ())
            );
        }
    }

    public ChunkRegenManager selectChunks() {
        listOfChunks.clear();

        for (int x = posRegen1.getX(); x <= posRegen2.getX(); x++) {
            for (int z = posRegen1.getZ(); z <= posRegen2.getZ(); z++) {
                Chunk chunk = world.getChunkAt(ChunkHelperClass.locationToChunk(x), ChunkHelperClass.locationToChunk(z));
                if (!listOfChunks.contains(chunk)) {
                    listOfChunks.add(chunk);
                }
            }
        }

        return this;
    }

    public ChunkRegenManager run() throws Exception {
        for (Chunk chunk : listOfChunks) {
            QueueChunkRegen queueChunkRegen = new QueueChunkRegen(chunk, posRegen1, posRegen2);
            queueChunkRegen.regen();
        }
        return this;
    }

    public List<Chunk> getListOfChunks() {
        return listOfChunks;
    }

    public World getWorld() {
        return world;
    }

    public PosVector getPosRegen1() {
        return posRegen1;
    }

    public PosVector getPosRegen2() {
        return posRegen2;
    }
}
