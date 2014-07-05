package me.kapehh.TownyWorldRegen.TWRCommon;

/**
 * Created by Karen on 05.07.2014.
 */
public class TownyBlockItem {
    private int id;
    private int data;
    private int chance;
    private int min;
    private int max;

    public TownyBlockItem() {

    }

    public TownyBlockItem(int id, int data, int chance, int min, int max) {
        this.id = id;
        this.data = data;
        this.chance = chance;
        this.min = min;
        this.max = max;
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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
