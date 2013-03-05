package bot.script.methods;

import bot.script.wrappers.Component;
import nl.wbot.Account;
import nl.wbot.bot.Bot;
import bot.script.enums.Tab;
import bot.script.wrappers.Tile;

import java.util.Arrays;

/**
 * @author Webjoch
 */
public class Game extends Methods {
    public static int getPlane() {
        return Bot.get().getMainClass().getPlane();
    }

    public static Tab getTab() {
        for (Component p : Widgets.get(548).getComponents()) {
            if (p.getTextureId() != -1 && p.getActions() != null) {
                for (Tab tab : Tab.values()) {
                    for(String s : p.getActions()){
                        if(tab.getName().equals(s)){
                            return tab;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void openTab(Tab tab) {
        Mouse.move(tab.getPoint());
        sleep(50);
        Mouse.click(true);
    }

    public static void logout() {
        if (getTab() != Tab.LOGOUT) {
            openTab(Tab.LOGOUT);
            sleep(50);
        }
        //Interfaces.getInterface(2449, 2458).click();
    }

    public static int[][] getTileData() {
        return Bot.get().getMainClass().getCollisionMap()[getPlane()].getFlags();
    }

    public static Tile getRegion() {
        return new Tile(Bot.get().getMainClass().getBaseX(), Bot.get().getMainClass().getBaseY());
    }

    public int[] getSettings() {
        return Bot.get().getMainClass().getSettings();
    }

    public static Account getAccount() {
        return Bot.get().getScriptHandler().getAccount();
    }
}
