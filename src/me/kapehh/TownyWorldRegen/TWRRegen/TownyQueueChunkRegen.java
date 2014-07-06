package me.kapehh.TownyWorldRegen.TWRRegen;

import me.kapehh.TownyWorldRegen.TWRCommon.PosVector;
import me.kapehh.TownyWorldRegen.TWRCommon.TownyBlockItem;
import org.bukkit.Chunk;
import org.bukkit.block.Block;

import java.util.List;
import java.util.Random;

/**
 * Created by Karen on 06.07.2014.
 */
public class TownyQueueChunkRegen extends QueueChunkRegen {

    private List<TownyBlockItem> listOfBlockReplace = null;

    public TownyQueueChunkRegen(Chunk chunk, PosVector pos1, PosVector pos2, List<TownyBlockItem> listOfBlockReplace) throws Exception {
        super(chunk, pos1, pos2);
        this.listOfBlockReplace = listOfBlockReplace;
    }

    @Override
    public void regen() {
        Chunk chunk = super.getChunk();
        Random random = new Random();
        for (TownyBlockItem townyBlockItem : listOfBlockReplace) {
            townyBlockItem.setActive(random.nextInt(100) <= townyBlockItem.getChance());
        }
        for (int x = 0; x < ChunkHelperClass.CHUNK_MAX_XZ; x++) {
            for (int z = 0; z < ChunkHelperClass.CHUNK_MAX_XZ; z++) {
                for (int y = 0; y < ChunkHelperClass.CHUNK_MAX_Y; y++) {
                    for (TownyBlockItem townyBlockItem : listOfBlockReplace) {
                        if (townyBlockItem.isActive() && (random.nextDouble() * 100 < townyBlockItem.getRemove())) {
                            townyBlockItem.run(chunk.getBlock(x, y, z));
                        }
                    }
                }
            }
        }
    }
}
