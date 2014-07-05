package me.kapehh.TownyWorldRegen.PluginManager.Config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 30.06.2014.
 */
public class PluginConfig {

    // Системные переменные
    public static final String fileSep = System.getProperty("file.separator");
    public static final String lineSep = System.getProperty("line.separator");

    // Списки методов, которые надо будет вызывать
    private List<Object> listOfClass = new ArrayList<Object>();

    // Внутренние переменные
    private FileConfiguration cfg;
    private JavaPlugin plugin;

    public PluginConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public PluginConfig addEventClasses(Object... classes) {
        listOfClass.clear();
        for (Object c : classes) {
            listOfClass.add(c);
        }
        return this;
    }

    public void RaiseEvent(EventType eventPluginConfig) throws InvocationTargetException, IllegalAccessException {
        for (Object c : listOfClass) {
            Method[] methods = c.getClass().getMethods();
            for(Method method : methods){
                if(method.isAnnotationPresent(EventPluginConfig.class)
                   && method.getParameterTypes().length == 0
                   && method.getAnnotation(EventPluginConfig.class).value().equals(eventPluginConfig)) {
                    method.invoke(c);
                }
            }
        }
    }

    public FileConfiguration getConfig() {
        return cfg;
    }

    public void setup() {
        plugin.saveDefaultConfig();
        cfg = plugin.getConfig();
        cfg.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public void loadData() {
        plugin.reloadConfig();
        cfg = plugin.getConfig();
        try {
            RaiseEvent(EventType.LOAD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            RaiseEvent(EventType.SAVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        plugin.saveConfig();
    }


    // Сделать конструктор типа PluginConfig(String configName) для кода ниже

    /*private static LinkedHashMap<String, YamlConfiguration> lstCfg = new LinkedHashMap<String, YamlConfiguration>();

    public static YamlConfiguration addCustomConfig(String name) {
        return lstCfg.put(
            name,
            YamlConfiguration.loadConfiguration(
                new File(CFPlugin.getPlugin().getDataFolder().getPath() + CFConfig.fileSep + name + ".yml")
            )
        );
    }

    public static YamlConfiguration getCustomConfig(String name) {
        return lstCfg.get(name);
    }*/
}
