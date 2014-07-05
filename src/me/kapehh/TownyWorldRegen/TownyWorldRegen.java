package me.kapehh.TownyWorldRegen;

import me.kapehh.TownyWorldRegen.PluginManager.Config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Karen on 12.06.2014.
 */
public class TownyWorldRegen extends JavaPlugin {
    private static TownyWorldRegen instance = null;
    private static PluginConfig pluginConfig = null;

    public static TownyWorldRegen getInstance() {
        return instance;
    }

    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }

    @Override
    public void onEnable() {
        instance = this;
        pluginConfig = new PluginConfig(this);

        TownyWorldRegenExecutor townyWorldRegenExecutor = new TownyWorldRegenExecutor();
        getCommand("townyworldregen").setExecutor(townyWorldRegenExecutor);
        pluginConfig.addEventClasses(townyWorldRegenExecutor);
        pluginConfig.setup();
        pluginConfig.loadData();
    }

    @Override
    public void onDisable() {
        pluginConfig.saveData();

        instance = null;
        pluginConfig = null;
    }

    /*public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TownyWorldRegen twr = new TownyWorldRegen();
        twr.onEnable();
        twr.getPluginConfig().RaiseEvent(PluginConfig.EventPluginConfig.EventType.LOAD);
    }*/
}
