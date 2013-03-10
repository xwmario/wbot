package methods;

import bot.script.methods.*;
import bot.script.wrappers.Component;
import bot.script.wrappers.GameObject;
import enums.DepositBoxSlot;

/**
 * @author iJava
 */
public class DepositBox {

    public static final int[] DEPOSIT_BOX_IDS = {9398};
    private static final int WIDGET_ID = 11;
    private static final int ITEM_COMP_ID = 61;
    private static final int CLOE_COMP_ID = 62;

    public static boolean close() {
        if (!isOpen()) {
            return true;
        }
        Component close = Widgets.getComponent(WIDGET_ID, CLOE_COMP_ID);
        if (close != null) {
            close.click();
            bot.script.util.Timer t = new bot.script.util.Timer(4000);
            while (isOpen() && t.isRunning()) {
                Methods.sleep(100, 150);
            }
        }
        return !isOpen();
    }

    public static boolean deposit(int itemId, int amt) {
        int[] ids = Widgets.getComponent(WIDGET_ID, ITEM_COMP_ID).getInventoryItems();
        int count = 0;
        boolean stack = false;
        if (Inventory.getItem(itemId).getStacksize() > 0) {
            count = Inventory.getCount(itemId, true);
            stack = true;
        } else {
            count = Inventory.getCount(itemId, false);
        }
        int index = -1;
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == itemId) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }
        DepositBoxSlot[] slots = DepositBoxSlot.values();
        DepositBoxSlot slot = null;
        for (DepositBoxSlot slot1 : slots) {
            if (slot1.getInvIndex() == index) {
                slot = slot1;
                break;
            }
        }
        if (slot == null) {
            return false;
        }
        switch (amt) {
            case 1:
            case 5:
            case 10:
                if (!Menu.isOpen()) {
                    Mouse.click(slot.getRandomPoint(), false);
                    bot.script.util.Timer t = new bot.script.util.Timer(3000);
                    while (!Menu.isOpen() && t.isRunning()) {
                        Methods.sleep(100);
                    }
                }
                if (Menu.isOpen()) {
                    Menu.interact("Deposit " + amt);
                }
                break;
            default:
                if (!Menu.isOpen()) {
                    Mouse.click(slot.getRandomPoint(), false);
                    bot.script.util.Timer t = new bot.script.util.Timer(3000);
                    while (!Menu.isOpen() && t.isRunning()) {
                        Methods.sleep(100);
                    }
                }
                if (amt >= Inventory.getItem(itemId).getStacksize() || amt == 0) {
                    if (Menu.isOpen()) {
                        Menu.interact("Deposit All");
                    }
                } else {
                    if (Menu.isOpen()) {
                        Menu.interact("Withdraw X");
                        Methods.sleep(800, 1000);
                        Keyboard.typeText(String.valueOf(amt), true);
                    }
                }
                break;
        }
        if(stack) {
            return Inventory.getCount(itemId, true) == count - amt;
        }
        return Inventory.getCount(itemId, false) == count - amt;
    }

    public static boolean isOpen() {
        if (Widgets.getComponent(WIDGET_ID, 60) == null) {
            return false;
        }
        return Widgets.getComponent(WIDGET_ID, 60).getText().contains("Bank");
    }

    public static boolean open() {
        if (isOpen()) {
            return true;
        }
        GameObject box = Objects.getNearest(DEPOSIT_BOX_IDS);
        if (box != null) {
            if (!box.isVisible()) {
                Camera.turnToTile(box.getLocation());
            }
            box.interact("Deposit");
            bot.script.util.Timer t = new bot.script.util.Timer(4000);
            while (!isOpen() && t.isRunning()) {
                Methods.sleep(100, 150);
            }
        }
        return isOpen();
    }

}
