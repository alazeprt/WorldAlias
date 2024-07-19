package top.alazeprt;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class WorldAlias extends JavaPlugin {

    public final Map<World, String> worldAliasMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        File file = new File(getDataFolder(), "worlds.yml");
        if(!file.exists()) {
            saveResource("worlds.yml", false);
        }
        FileConfiguration worlds = YamlConfiguration.loadConfiguration(file);
        for(String key : worlds.getKeys(false)) {
            worldAliasMap.put(Bukkit.getWorld(key), worlds.getString(key));
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { //
            new WorldAliasExpansion(this).register();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        File file = new File(getDataFolder(), "worlds.yml");
        FileConfiguration worlds = YamlConfiguration.loadConfiguration(file);
        for(Map.Entry<World, String> entry : worldAliasMap.entrySet()) {
            worlds.set(entry.getKey().getName(), entry.getValue());
        }
        for(String key : worlds.getKeys(false)) {
            worlds.set(key, worldAliasMap.get(Bukkit.getWorld(key)));
        }
        try {
            worlds.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
