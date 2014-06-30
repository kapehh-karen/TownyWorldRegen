package me.kapehh.TownyWorldRegen;

import me.kapehh.TownyWorldRegen.TWRCommon.PosVector;
import me.kapehh.TownyWorldRegen.TWRRegen.ChunkRegenManager;
import me.kapehh.TownyWorldRegen.TWRSet.SetterBlockManager;
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
        if (sender instanceof Player) {
            sender.sendMessage("Console only!");
            return true;
        }

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

            try {
                new ChunkRegenManager(worldName, x1, y1, z1, x2, y2, z2).run();
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
                int affected = new SetterBlockManager(
                    worldName,
                    new PosVector(x1, y1, z1),
                    new PosVector(x2, y2, z2),
                    patternString
                ).run();
                TownyWorldRegen.getInstance().getLogger().info(affected + " block(s) have been changed.");
            } catch (Exception e) {
                TownyWorldRegen.getInstance().getLogger().warning("ERROR: " + e.getMessage());
            }

        }

        return true;
    }
}
