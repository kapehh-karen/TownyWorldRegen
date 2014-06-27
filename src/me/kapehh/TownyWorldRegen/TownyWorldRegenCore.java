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




}
