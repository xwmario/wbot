package bot.script.methods;

import java.util.ArrayList;

import bot.script.wrappers.Component;
import bot.script.wrappers.GameObject;
import bot.script.wrappers.Item;
import bot.script.wrappers.NPC;

public class Bank extends Methods{
	public static final int[] BANK_NPCS = {494, 495, 496};
	public static final int[] BANK_BOOTHS = {2213, 2215, 11758};
	public static final int[] BANK_CHESTS = {3194};
	
	public static boolean isOpen(){
		return false;//Bot.get().getMainClass().getInventoryInterfaceId() == 5063;
	}
	
	public static boolean open(){
		if (isOpen()) return false;
		
		GameObject object = Objects.getNearest(BANK_BOOTHS);
		if (object != null){
			return object.interact("Use-quickly");
		}
		
		GameObject chest = Objects.getNearest(BANK_CHESTS);
		if (chest != null){
			return chest.interact("Bank");
		}
		
		NPC npc = Npcs.getNearest(BANK_NPCS);
		if (npc != null){
			return npc.interact("Bank");
		}
		return false;
	}
	
	public static boolean close(){
		if (!isOpen()) return false;
		//Interfaces.getInterface(5292, 5384);
		return true;
	}
	
	public static Item[] getItems(){
		Component iface = Widgets.getComponent(12, 89);
		return iface.getItems();
    }
	
	public static Item getItem(int id){
		for(Item item : getItems()){
			if (item.getId() == id)
				return item;
		}
		return null;
	}
	
	public static int getCount(){
		return getItems().length;
	}

	public static int getCount(boolean countStackSize){
		if (!countStackSize)
			return getCount();
		int count = 0;
		for(Item item : getItems()){
			count += item.getStacksize();
		}
		return count;
	}
	
	public static boolean withdraw(int itemId, int amount){
		Item item = Bank.getItem(itemId);
		if (item == null) return false;
		int count = getCount(true);
		
		switch(amount){
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
			if (amount >= item.getStacksize() || amount == 0){
				item.interact("Withdraw All");
			}else{
				item.interact("Withdraw X");
				sleep(800, 1000);
				Keyboard.typeText(""+amount, true);
			}
		}
		for (int i = 0; i < 100; i++){
			sleep(40);
			if (getCount(true) != count) return true;
		}
		return false;
	}
	
	public static boolean deposit(int itemId, int amount){
		Item item = Inventory.getItem(itemId);
		if (item == null) return false;
		int count = getCount(true);
		
		switch(amount){
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
			if (amount >= item.getStacksize() || amount == 0){
				item.interact("Store All");
			}else{
				item.interact("Store X");
				sleep(800, 1000);
				Keyboard.typeText(""+amount, true);
			}
		}
		for (int i = 0; i < 100; i++){
			sleep(40);
			if (getCount(true) != count) return true;
		}
		return false;
	}
}
