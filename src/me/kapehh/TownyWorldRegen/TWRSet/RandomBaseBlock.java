package me.kapehh.TownyWorldRegen.TWRSet;

import com.sk89q.worldedit.blocks.BaseBlock;

/**
 * Created by Karen on 27.06.2014.
 */
public class RandomBaseBlock {
    private double chanceFrom;
    private double chanceTo;
    private BaseBlock baseBlock;

    public RandomBaseBlock(double chanceFrom, double chanceTo, BaseBlock baseBlock) {
        this.chanceFrom = chanceFrom;
        this.chanceTo = chanceTo;
        this.baseBlock = baseBlock;
    }

    public double getChanceFrom() {
        return chanceFrom;
    }

    public void setChanceFrom(double chanceFrom) {
        this.chanceFrom = chanceFrom;
    }

    public double getChanceTo() {
        return chanceTo;
    }

    public void setChanceTo(double chanceTo) {
        this.chanceTo = chanceTo;
    }

    public BaseBlock getBaseBlock() {
        return baseBlock;
    }

    public void setBaseBlock(BaseBlock baseBlock) {
        this.baseBlock = baseBlock;
    }
}
