package me.kapehh.TownyWorldRegen;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.WorldEdit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Karen on 12.06.2014.
 */
public class TownyWorldRegen extends JavaPlugin {
    private static TownyWorldRegen instance = null;

    public static TownyWorldRegen getInstance() {
        return instance;
    }

    // Временное решение
    public static LocalWorld getLocalWorld(String worldName) {
        for (LocalWorld localWorld : WorldEdit.getInstance().getServer().getWorlds()) {
            if (localWorld.getName().equalsIgnoreCase(worldName)) {
                return localWorld;
            }
        }
        return null;
    }

    @Override
    public void onEnable() {
        instance = this;
        getCommand("townyworldregen").setExecutor(new TownyWorldRegenExecutor());
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
