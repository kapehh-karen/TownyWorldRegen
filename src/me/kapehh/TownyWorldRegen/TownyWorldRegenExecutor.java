package me.kapehh.TownyWorldRegen;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Karen on 12.06.2014.
 */
public class TownyWorldRegenExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*if (args.length < 1) {
            return false;
        }*/
        //World world = Bukkit.getWorlds().get(0);
        //world.getLoadedChunks().length
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Chunk chunk =  player.getWorld().getChunkAt(player.getLocation());
            player.getWorld().regenerateChunk(chunk.getX(), chunk.getZ());
            player.sendMessage("Regenerated!");
        } else TownyWorldRegen.instance.getLogger().warning("You need be Player");
        return true;
    }
}
