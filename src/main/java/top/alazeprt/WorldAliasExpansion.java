package top.alazeprt;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldAliasExpansion extends PlaceholderExpansion {

    private final WorldAlias plugin;

    public WorldAliasExpansion(WorldAlias worldAlias) {
        this.plugin = worldAlias;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "worldalias";
    }

    @Override
    public @NotNull String getAuthor() {
        return "alazeprt";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("worldalias")) {
            Player player1;
            try {
                player1 = player.getPlayer();
            } catch(Exception e) {
                player1 = null;
            }
            if(player1 == null) return null;
            World world = player1.getWorld();
            if(!plugin.worldAliasMap.containsKey(world)) return world.getName();
            else return plugin.worldAliasMap.get(world).replace("&", "§");
        }
        return null;
    }
}
