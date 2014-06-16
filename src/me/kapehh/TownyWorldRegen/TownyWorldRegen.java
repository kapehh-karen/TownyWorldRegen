package me.kapehh.TownyWorldRegen;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Karen on 12.06.2014.
 */
public class TownyWorldRegen extends JavaPlugin {
    public static TownyWorldRegen instance = null;

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
