package me.kapehh.TownyWorldRegen;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.patterns.Pattern;
import com.sk89q.worldedit.patterns.SingleBlockPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Karen on 13.06.2014.
 */
public class TownyWorldRegenCore {
    public static final int MAX_Y = 256;

    public static int setWorldRegion(String worldName, int x1, int y1, int z1, int x2, int y2, int z2, String patternString) {
        Map<Double, BaseBlock> listChanceOfBlock = new HashMap<Double, BaseBlock>();
        BaseBlock blockItem;
        Double blockChance;
        String[] p;

        for (String blockString : patternString.split(",")) {
            blockItem = new BaseBlock(0);
            blockChance = (double) 100;

            if (blockString.matches("[0-9]+(\\.[0-9]*)?%.*")) {
                p = blockString.split("%");

                if (p.length == 2) {
                    blockChance = Double.parseDouble(p[0]);
                    blockString = p[1];
                }
            }

            if (blockString.contains(":")) {
                p = blockString.split(":");

                if (p.length == 2) {
                    blockItem.setIdAndData(
                        Integer.parseInt(p[0]),
                        Integer.parseInt(p[1])
                    );
                }
            } else {
                blockItem.setId(Integer.parseInt(blockString));
            }

            listChanceOfBlock.put(blockChance, blockItem);
        }

        return 0;
    }

    public static LocalWorld getLocalWorld(String worldName) {
        for (LocalWorld localWorld : WorldEdit.getInstance().getServer().getWorlds()) {
            if (localWorld.getName().equalsIgnoreCase(worldName)) {
                return localWorld;
            }
        }
        return null;
    }

    public static void regenWorldRegion(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        // TODO: При выгруженных чанках регенит сразу чанками
        LocalWorld selectWorld =  getLocalWorld(worldName);

        if (selectWorld == null) {
            return;
        }

        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(selectWorld, MAX_Y);
        CuboidRegion cuboidRegion = new CuboidRegion(selectWorld, new Vector(x1, y1, z1), new Vector(x2, y2, z2));
        selectWorld.regenerate(cuboidRegion, editSession);
    }

    public static boolean locationCheckDistance(int x, int z, int xCenter, int zCenter, int radius) {
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
