package me.kapehh.TownyWorldRegen.TWRCommon;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

/**
 * Created by Karen on 05.07.2014.
 */
public class TownyBlockItem {
    private int id;
    private int data;
    private int chance;
    private double remove;
    private boolean isActive;

    public TownyBlockItem() {

    }

    public TownyBlockItem(int id, int data, int chance, double remove) {
        this.id = id;
        this.data = data;
        this.chance = chance;
        this.remove = remove;
    }

    public boolean run(Block block) {
        if (!isActive) {
            return false;
        }
        if (block.getTypeId() != id) {
            return false;
        }
        if (data >= 0 && (block.getData() != (byte)data)) {
            return false;
        }
        block.setType(Material.AIR);
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public double getRemove() {
        return remove;
    }

    public void setRemove(double remove) {
        this.remove = remove;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
