package me.kapehh.TownyWorldRegen;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Karen on 12.06.2014.
 */
public class TownyWorldRegenExecutor implements CommandExecutor {
    public static class LolBlock {
        private int typeId;
        private byte data;

        public LolBlock(Block block) {
            this.typeId = block.getTypeId();
            this.data = block.getData();
        }

        public LolBlock(int typeId, byte data) {
            this.typeId = typeId;
            this.data = data;
        }

        public int getTypeId() {
            return typeId;
        }

        public byte getData() {
            return data;
        }
    }

    Chunk chunk;
    private LolBlock[] chunkBlocks = new LolBlock[16 * 16 * 256];

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        String method = args[0];
        if (method.equalsIgnoreCase("select")) {

            int x, z;
            String worldName;
            worldName = args[1];
            x = Integer.parseInt(args[2]);
            z = Integer.parseInt(args[3]);
            chunk = Bukkit.getWorld(worldName).getChunkAt(x, z);

            sender.sendMessage("Selected: " + x + "," + z);

        } else if (method.equalsIgnoreCase("regen")) {

            int index;

            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 256; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        index = y * 16 * 16 + z * 16 + x;
                        chunkBlocks[index] = new LolBlock(chunk.getBlock(x, y, z));
                    }
                }
            }

            sender.sendMessage("Regenerated!");

        } else if (method.equalsIgnoreCase("undo")) {

            Block block;
            int index;

            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 256; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        index = y * 16 * 16 + z * 16 + x;
                        block = chunk.getBlock(x, y, z);
                        block.setTypeId(chunkBlocks[index].getTypeId());
                        block.setData(chunkBlocks[index].getData());
                    }
                }
            }

            sender.sendMessage("Undo!");

        } else {
            return false;
        }

        return true;
    }
}
