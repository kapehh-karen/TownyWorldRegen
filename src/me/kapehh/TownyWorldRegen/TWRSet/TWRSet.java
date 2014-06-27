package me.kapehh.TownyWorldRegen.TWRSet;

import com.sk89q.worldedit.blocks.BaseBlock;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Karen on 27.06.2014.
 */
public class TWRSet {

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
}
