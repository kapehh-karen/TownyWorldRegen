package me.kapehh.TownyWorldRegen;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Karen on 12.06.2014.
 */
public class TownyWorldRegenExecutor implements CommandExecutor {
    private Chunk chunk;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // townyregenrect | townyregencircle
        if (command.getName().equals("townyregenrect")) {
            Player player = (Player) sender;
            chunk = player.getWorld().getChunkAt(player.getLocation());
            //player.getWorld().regenerateChunk(chunk.getX(), chunk.getZ());
            player.sendMessage("Selected: " + chunk.toString());
        } else if (command.getName().equals("townyregencircle")) {
            /*if (sender instanceof Player) {
                Player player = (Player) sender;
                Chunk chunk = player.getWorld().getChunkAt(player.getLocation());
                boolean ret = player.getWorld().unloadChunk(chunk.getX(), chunk.getZ(), true, true);
                player.sendMessage("Unloaded=" + ret + " isLoaded=" + chunk.isLoaded());
            } else TownyWorldRegen.instance.getLogger().warning("You need be Player");*/
            Player player = (Player) sender;
            player.sendMessage("Unloaded=" + chunk.unload(true) + " isLoaded=" + chunk.isLoaded());
        }
        return true;
    }
}
