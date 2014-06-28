package me.kapehh.TownyWorldRegen;

import me.kapehh.TownyWorldRegen.TWRCommon.PosVector;
import me.kapehh.TownyWorldRegen.TWRRegen.QueueChunkRegen;
import me.kapehh.TownyWorldRegen.TWRSet.TWRSet;
import org.bukkit.Bukkit;
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
        // TODO: Не забыть раскрыть комментарий ниже
        /*if (sender instanceof Player) {
            sender.sendMessage("ERROR!");
            return true;
        }*/

        if (args.length == 0) {
            return false;
        }

        String method = args[0];

        if (method.equalsIgnoreCase("regen")) {

            if (args.length < 8) {
                return false;
            }

            String worldName = args[1];
            int x1 = Integer.parseInt(args[2]);
            int y1 = Integer.parseInt(args[3]);
            int z1 = Integer.parseInt(args[4]);
            int x2 = Integer.parseInt(args[5]);
            int y2 = Integer.parseInt(args[6]);
            int z2 = Integer.parseInt(args[7]);

            PosVector pos1 = new PosVector(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
            PosVector pos2 = new PosVector(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));

            // TODO: TEST
            Player player = (Player) sender;
            World world = Bukkit.getWorld(worldName);
            try {
                QueueChunkRegen queueChunkRegen = new QueueChunkRegen(world.getChunkAt(player.getLocation()), pos1, pos2);
                TownyWorldRegen.getInstance().getLogger().info(queueChunkRegen.toString());
                queueChunkRegen.regen();

                TownyWorldRegen.getInstance().getLogger().info("Region regenerated.");
            } catch (Exception e) {
                TownyWorldRegen.getInstance().getLogger().warning("ERROR: " + e.getMessage());
            }

        } else if (method.equalsIgnoreCase("set")) {

            if (args.length < 9) {
                return false;
            }

            String worldName = args[1];
            int x1 = Integer.parseInt(args[2]);
            int y1 = Integer.parseInt(args[3]);
            int z1 = Integer.parseInt(args[4]);
            int x2 = Integer.parseInt(args[5]);
            int y2 = Integer.parseInt(args[6]);
            int z2 = Integer.parseInt(args[7]);
            String patternString = args[8];

            try {
                int affected = TWRSet.setWorldRegion(worldName, new PosVector(x1, y1, z1), new PosVector(x2, y2, z2), patternString);
                TownyWorldRegen.getInstance().getLogger().info(affected + " block(s) have been changed.");
            } catch (Exception e) {
                TownyWorldRegen.getInstance().getLogger().warning("ERROR: " + e.getMessage());
            }

        }

        return true;
    }
}
