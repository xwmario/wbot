package bot.script.methods;

import bot.script.wrappers.Component;
import bot.script.wrappers.GameObject;
import bot.script.wrappers.Item;
import bot.script.wrappers.NPC;

public class Bank extends Methods {

    public static final int[] BANK_NPCS = {5912, 5913};
    public static final int[] BANK_BOOTHS = {11402, 2213};
    public static final int[] BANK_CHESTS = {3194};

    public static boolean isOpen() {
        Component iface = Widgets.getComponent(12, 89);
        if (iface == null)
            return false;
        for (int i = 0; i < iface.getInventoryItems().length; i++) {
            if (iface.getInventoryItems()[i] == 0 && iface.getInventoryStackSizes()[i] > 0)
                return false;
        }
        return true;
    }

    public static boolean open() {
      if (isOpen()) {
      return true;
  }
        GameObject object = Objects.getNearest(BANK_BOOTHS);
        if (object != null) {
            return object.interact("Use-quickly");
        }

        GameObject chest = Objects.getNearest(BANK_CHESTS);
        if (chest != null) {
            return chest.interact("Bank");
        }

        NPC npc = Npcs.getNearest(BANK_NPCS);
        if (npc != null) {
            return npc.interact("Bank");
        }
        npc = Npcs.getNearest(new bot.script.util.Filter<NPC>() {
            @Override
            public boolean accept(NPC npc) {
                return npc.getDef().getName().equals("Banker");
            }
        });
        if(npc != null) {
            return npc.interact("Bank");
        }
        return false;
    }

    public static boolean close() {
      if (!isOpen()) {
      return true;
  }
        Component c = Widgets.getComponent(12, 102);
        if (c != null) {
            return c.interact("Close");
        }
        return false;
    }

    public static Item[] getItems() {
        Component iface = Widgets.getComponent(12, 89);
        if (iface == null)
            return new Item[0];
        return iface.getItems();
    }

    public static Item getItem(int id) {
        for (Item item : getItems()) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    public static int getCount() {
        return getItems().length;
    }

    public static int getCount(boolean countStackSize) {
        if (!countStackSize)
            return getCount();
        int count = 0;
        for (Item item : getItems()) {
            count += item.getStacksize();
        }
        return count;
    }

    public static boolean withdraw(int itemId, int amount) {
        Item item = Bank.getItem(itemId);
        if (item == null) return false;
        int count = getCount(true);

        switch (amount) {
            case 1:
                item.interact("Withdraw 1");
                break;
            case 5:
                item.interact("Withdraw 5");
                break;
            case 10:
                item.interact("Withdraw 10");
                break;
            default:
                if (amount >= item.getStacksize() || amount == 0) {
                    boolean b = item.interact("Withdraw All");
                    Methods.log(b);
                } else {
                    item.interact("Withdraw X");
                    sleep(800, 1000);
                    Keyboard.typeText(String.valueOf(amount), true);
                }
        }
        for (int i = 0; i < 100; i++) {
            sleep(40);
            if (getCount(true) != count) return true;
        }
        return false;
    }

    public static boolean deposit(int itemId, int amount) {
        Item item = Inventory.getItem(itemId);
        if (item == null) return false;
        int count = getCount(true);

        switch (amount) {
            case 1:
                item.interact("Store 1");
                break;
            case 5:
                item.interact("Store 5");
                break;
            case 10:
                item.interact("Store 10");
                break;
            default:
                if (amount >= item.getStacksize() || amount == 0) {
                    item.interact("Store All");
                } else {
                    item.interact("Store X");
                    sleep(800, 1000);
                    Keyboard.typeText(String.valueOf(amount), true);
                }
        }
        for (int i = 0; i < 100; i++) {
            sleep(40);
            if (getCount(true) != count) return true;
        }
        return false;
    }
}
