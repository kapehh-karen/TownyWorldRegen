package me.kapehh.TownyWorldRegen.TWRSet;

import com.sk89q.worldedit.blocks.BaseBlock;
import me.kapehh.TownyWorldRegen.TWRCommon.PosVector;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Karen on 27.06.2014.
 */
public class SetterBlockManager {

    // Мир
    private World world;

    // Координаты региона
    private PosVector pos1;
    private PosVector pos2;

    // Строка шаблона
    private String patternString;

    public SetterBlockManager(String worldName, int x1, int y1, int z1, int x2, int y2, int z2, String patternString) throws Exception {
        this(Bukkit.getWorld(worldName), x1, y1, z1, x2, y2, z2, patternString);
    }

    public SetterBlockManager(World world, int x1, int y1, int z1, int x2, int y2, int z2, String patternString) throws Exception {
        this(
                world,
                new PosVector(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2)),
                new PosVector(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2)),
                patternString
        );
    }

    public SetterBlockManager(String worldName, PosVector pos1, PosVector pos2, String patternString) throws Exception {
        this(Bukkit.getWorld(worldName), pos1, pos2, patternString);
    }

    public SetterBlockManager(World world, PosVector pos1, PosVector pos2, String patternString) throws Exception {
        if (world == null) {
            throw new Exception("World not found");
        }
        this.world = world;

        this.pos1 = pos1;
        this.pos2 = pos2;
        if (pos1.getX() > pos2.getX() || pos1.getY() > pos2.getY() || pos1.getZ() > pos2.getZ()) {
            this.pos1 = new PosVector(
                Math.min(pos1.getX(), pos2.getX()),
                Math.min(pos1.getY(), pos2.getY()),
                Math.min(pos1.getZ(), pos2.getZ())
            );
            this.pos2 = new PosVector(
                Math.max(pos1.getX(), pos2.getX()),
                Math.max(pos1.getY(), pos2.getY()),
                Math.max(pos1.getZ(), pos2.getZ())
            );
        }

        this.patternString = patternString;
    }

    public int run() throws Exception {
        List<RandomBaseBlock> listChanceOfBlock = new ParseBlockPattern().parse(patternString);
        Random random = new Random();
        int affected = 0;

        int min_x = Math.min(pos1.getX(), pos2.getX());
        int min_y = Math.min(pos1.getY(), pos2.getY());
        int min_z = Math.min(pos1.getZ(), pos2.getZ());
        int max_x = Math.max(pos1.getX(), pos2.getX());
        int max_y = Math.max(pos1.getY(), pos2.getY());
        int max_z = Math.max(pos1.getX(), pos2.getX());

        for (int x = min_x; x <= max_x; x++) {
            for (int z = min_z; z <= max_z; z++) {
                for (int y = min_y; y <= max_y; y++) {
                    Block block = world.getBlockAt(x, y, z);
                    double chance = random.nextDouble() * 100;

                    for (RandomBaseBlock item : listChanceOfBlock) {
                        if (item.getChanceFrom() <= chance && chance < item.getChanceTo()) {
                            BaseBlock blockItem = item.getBaseBlock();

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
