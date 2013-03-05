package bot.script.wrappers;

import bot.script.enums.Tab;
import bot.script.methods.*;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jeroen
 * Date: 4-3-13
 * Time: 12:18
 */
public class Item extends Methods {

    private int index;
    private Component iface;

    public Item(int index, Component iface) {
        this.index = index;
        this.iface = iface;
    }

    public int getIndex() {
        return index;
    }

    public int getId() {
        return iface.getInventoryItems()[index];
    }

    public int getStacksize() {
        return iface.getInventoryStackSizes()[index];
    }

    public boolean interact(String action) {
        if (Game.getTab() != Tab.INVENTORY) {
            Game.openTab(Tab.INVENTORY);
        }
        Mouse.move(getPoint());
        sleep(200);
        return bot.script.methods.Menu.interact(action);
    }

    public Point getPoint() {
        int col = (index % 4);
        int row = (index / 4);
        int x = 580 + (col * 42);
        int y = 228 + (row * 36);
        return new Point(x, y);
    }
}
