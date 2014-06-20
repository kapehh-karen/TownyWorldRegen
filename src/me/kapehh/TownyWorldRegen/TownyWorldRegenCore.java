package me.kapehh.TownyWorldRegen;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.patterns.Pattern;
import com.sk89q.worldedit.patterns.SingleBlockPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

import java.util.*;

/**
 * Created by Karen on 13.06.2014.
 */
public class TownyWorldRegenCore {
    public static final int MAX_Y = 256;

    public static class RandomBaseBlock {
        private double chanceFrom;
        private double chanceTo;
        private BaseBlock baseBlock;

        public RandomBaseBlock(double chanceFrom, double chanceTo, BaseBlock baseBlock) {
            this.chanceFrom = chanceFrom;
            this.chanceTo = chanceTo;
            this.baseBlock = baseBlock;
        }

        public double getChanceFrom() {
            return chanceFrom;
        }

        public void setChanceFrom(double chanceFrom) {
            this.chanceFrom = chanceFrom;
        }

        public double getChanceTo() {
            return chanceTo;
        }

        public void setChanceTo(double chanceTo) {
            this.chanceTo = chanceTo;
        }

        public BaseBlock getBaseBlock() {
            return baseBlock;
        }

        public void setBaseBlock(BaseBlock baseBlock) {
            this.baseBlock = baseBlock;
        }
    }

    public static int setWorldRegion(String worldName, int x1, int y1, int z1, int x2, int y2, int z2, String patternString) throws Exception {
        List<RandomBaseBlock> listChanceOfBlock = new ArrayList<RandomBaseBlock>();
        BaseBlock blockItem;
        double blockChance;
        double blockOldChance;
        double limitOfChance;
        String[] p;

        blockChance = 0;
        limitOfChance = 0;
        for (String blockString : patternString.split(",")) {
            blockOldChance = blockChance;
            blockItem = new BaseBlock(0);

            if (blockString.matches("[0-9]+(\\.[0-9]*)?%.*")) {
                p = blockString.split("%");

                if (p.length == 2) {
                    blockChance += Double.parseDouble(p[0]);
                    blockString = p[1];
                }
            } else {
                blockChance = 100;
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

            listChanceOfBlock.add(new RandomBaseBlock(blockOldChance, blockChance, blockItem));
            limitOfChance += (blockChance - blockOldChance);
        }
        
        if (limitOfChance > 100) {
            throw new Exception("Invalid pattern string");
        }

        int affected = 0;
        Random random = new Random();
        World bukkitWorld;
        Block block;
        double chance;
        int min_x = Math.min(x1, x2);
        int min_y = Math.min(y1, y2);
        int min_z = Math.min(z1, z2);
        int max_x = Math.max(x1, x2);
        int max_y = Math.max(y1, y2);
        int max_z = Math.max(z1, z2);

        bukkitWorld = Bukkit.getWorld(worldName);
        if (bukkitWorld == null) {
            throw new Exception("World not found");
        }

        for (int x = min_x; x <= max_x; x++) {
            for (int z = min_z; z <= max_z; z++) {
                for (int y = min_y; y <= max_y; y++) {
                    block = bukkitWorld.getBlockAt(x, y, z);
                    chance = random.nextDouble() * 100;

                    for (RandomBaseBlock item : listChanceOfBlock) {
                        if (item.getChanceFrom() <= chance && chance < item.getChanceTo()) {
                            blockItem = item.getBaseBlock();

                            if (blockItem.getId() != block.getTypeId() || blockItem.getData() != block.getData()) {
                                block.setTypeIdAndData(blockItem.getId(), (byte) blockItem.getData(), false);
                                affected++;
                            }

                            break;
                        }
                    }
                }
            }
        }
        
        return affected;
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
