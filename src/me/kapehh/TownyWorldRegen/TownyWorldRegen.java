package me.kapehh.TownyWorldRegen;

import me.kapehh.main.pluginmanager.checker.PluginChecker;
import me.kapehh.main.pluginmanager.config.PluginConfig;
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
        if (getServer().getPluginManager().getPlugin("PluginManager") == null) {
            getLogger().info("PluginManager not found!!!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;

        PluginChecker pluginChecker = new PluginChecker(this);
        if (!pluginChecker.check("WorldEdit")) {
            return;
        }

        TownyWorldRegenExecutor townyWorldRegenExecutor = new TownyWorldRegenExecutor();
        getCommand("townyworldregen").setExecutor(townyWorldRegenExecutor);

        pluginConfig = new PluginConfig(this); // Initialize
        pluginConfig.addEventClasses(townyWorldRegenExecutor)
                    .setup()
                    .loadData();
    }

    @Override
    public void onDisable() {
        if (instance == null) {
            return;
        }

        /*if (pluginConfig != null) {
            pluginConfig.saveData();
        }*/

        instance = null;
        pluginConfig = null;
    }

    /*public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TownyWorldRegen twr = new TownyWorldRegen();
        twr.onEnable();
        twr.getPluginConfig().RaiseEvent(PluginConfig.EventPluginConfig.EventType.LOAD);
    }*/
}
