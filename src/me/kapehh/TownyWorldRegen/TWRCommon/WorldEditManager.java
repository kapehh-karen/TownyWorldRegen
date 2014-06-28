package me.kapehh.TownyWorldRegen.TWRCommon;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.blocks.BaseBlock;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Created by Karen on 28.06.2014.
 */
public class WorldEditManager {

    public static LocalWorld getLocalWorld(String worldName) {
        for (LocalWorld localWorld : WorldEdit.getInstance().getServer().getWorlds()) {
            if (localWorld.getName().equalsIgnoreCase(worldName)) {
                return localWorld;
            }
        }
        return null;
    }

    public static BaseBlock getBlock(LocalWorld world, Vector pt) {
        return world.getBlock(pt);
    }

    public static void setBlock(LocalWorld worldWE, World worldBukkit, Vector pt, BaseBlock baseBlock) {
        Block block = worldBukkit.getBlockAt((int) pt.getX(), (int) pt.getY(), (int) pt.getZ());
        block.setTypeId(baseBlock.getId());
        block.setData((byte) baseBlock.getData());
        worldWE.copyToWorld(pt,baseBlock);
    }

}
