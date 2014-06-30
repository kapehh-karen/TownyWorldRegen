package me.kapehh.TownyWorldRegen.TWRSet;

import com.sk89q.worldedit.blocks.BaseBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 30.06.2014.
 */
public class ParseBlockPattern {
    private List<RandomBaseBlock> listChanceOfBlock = new ArrayList<RandomBaseBlock>();

    public ParseBlockPattern() {

    }

    public ParseBlockPattern(String pattern) throws Exception {
        parse(pattern);
    }

    public List<RandomBaseBlock> parse(String pattern) throws Exception {
        double blockChance;
        double blockOldChance;
        double limitOfChance;

        listChanceOfBlock.clear();
        blockChance = 0;
        limitOfChance = 0;
        for (String blockString : pattern.split(",")) {
            blockOldChance = blockChance;
            BaseBlock blockItem = new BaseBlock(0);

            if (blockString.matches("[0-9]+(\\.[0-9]*)?%.*")) {
                String[] p = blockString.split("%");

                if (p.length == 2) {
                    blockChance += Double.parseDouble(p[0]);
                    blockString = p[1];
                }
            } else {
                blockChance = 100;
            }

            if (blockString.contains(":")) {
                String[] p = blockString.split(":");

                if (p.length == 2) {
                    blockItem.setIdAndData(
                            Integer.parseInt(p[0]),
                            Integer.parseInt(p[1])
                    );
                }
            } else {
                blockItem.setId(Integer.parseInt(blockString));
            }

            listChanceOfBlock.add(new RandomBaseBlock(blockOldChance, blockChance, blockItem));
            limitOfChance += (blockChance - blockOldChance);
        }

        if (limitOfChance > 100) {
            throw new Exception("Invalid pattern string");
        }

        return listChanceOfBlock;
    }

    public List<RandomBaseBlock> getListChanceOfBlock() {
        return listChanceOfBlock;
    }
}
