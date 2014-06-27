package me.kapehh.TownyWorldRegen;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;

/**
 * Created by Karen on 13.06.2014.
 */
public class TownyWorldRegenCore {
    // TODO: Удалить класс

    public static void regenWorldRegion(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        // TODO: При выгруженных чанках регенит сразу чанками
        LocalWorld selectWorld = TownyWorldRegen.getLocalWorld(worldName);

        if (selectWorld == null) {
            return;
        }

        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(selectWorld, 256);
        CuboidRegion cuboidRegion = new CuboidRegion(selectWorld, new Vector(x1, y1, z1), new Vector(x2, y2, z2));
        selectWorld.regenerate(cuboidRegion, editSession);
    }

}
