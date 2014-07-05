package me.kapehh.TownyWorldRegen.TWRRegen;

import com.palmergames.bukkit.towny.object.TownyUniverse;
import me.kapehh.TownyWorldRegen.TWRCommon.PosVector;
import me.kapehh.TownyWorldRegen.TWRCommon.RandomBaseBlock;
import me.kapehh.TownyWorldRegen.TWRCommon.TownyBlockItem;
import me.kapehh.TownyWorldRegen.TownyWorldRegen;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 04.07.2014.
 */
public class TownyChunkRegenManager extends ChunkRegenManager {

    private List<TownyBlockItem> listOfBlockReplace = null;

    public TownyChunkRegenManager(World world, PosVector pos1, PosVector pos2, List<TownyBlockItem> listOfBlockReplace) throws Exception {
        super(world, pos1, pos2);
        this.listOfBlockReplace = listOfBlockReplace;
    }

    public TownyChunkRegenManager(String worldName, PosVector pos1, PosVector pos2, List<TownyBlockItem> listOfBlockReplace) throws Exception {
        super(worldName, pos1, pos2);
        this.listOfBlockReplace = listOfBlockReplace;
    }

    public TownyChunkRegenManager(World world, int x1, int y1, int z1, int x2, int y2, int z2, List<TownyBlockItem> listOfBlockReplace) throws Exception {
        super(world, x1, y1, z1, x2, y2, z2);
        this.listOfBlockReplace = listOfBlockReplace;
    }

    public TownyChunkRegenManager(String worldName, int x1, int y1, int z1, int x2, int y2, int z2, List<TownyBlockItem> listOfBlockReplace) throws Exception {
        super(worldName, x1, y1, z1, x2, y2, z2);
        this.listOfBlockReplace = listOfBlockReplace;
    }

    @Override
    public TownyChunkRegenManager run() throws Exception {
        for (Chunk chunk : super.getListOfChunks()) {
            boolean isWilderness = TownyUniverse.isWilderness(chunk.getBlock(0, 0, 0));
            QueueChunkRegen queueChunkRegen;
            if (isWilderness) {
                queueChunkRegen = new QueueChunkRegen(chunk, super.getPosRegen1(), super.getPosRegen2());
            } else {
                queueChunkRegen = new TownyQueueChunkRegen(chunk, super.getPosRegen1(), super.getPosRegen2(), listOfBlockReplace);
            }
            queueChunkRegen.regen();
        }
        return this;
    }
}
