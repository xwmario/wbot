package bot.script.methods;

import java.util.ArrayList;

import bot.script.wrappers.Component;
import bot.script.wrappers.GameObject;
import bot.script.wrappers.Item;

public class Inventory extends bot.script.methods.Methods {
	
	public static Item[] getItems(){
		ArrayList<Item> items = new ArrayList<Item>();
		//int ifaceId = 0;//Bot.get().getMainClass().getInventoryInterfaceId() < 0 ? 3214 : Bot.get().getMainClass().getInventoryInterfaceId() + 1;
		Component iface = bot.script.methods.Widgets.getComponent(149, 0);
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
	
	public static int getCount(int id, boolean stacksize){
		int count = 0;
		for(Item item : getItems()){
			if (item.getId() == id){
				count += stacksize ? item.getStacksize() : 1;
			}
		}
		return count;
	}
	
	public static boolean isFull(){
		return getItems().length == 28;
	}
	
	public static boolean contains(int id){
		return getItem(id) != null;
	}
	
	public static boolean contains(int[] ids){
		for(int id : ids){
			if (contains(id))
				return true;
		}
		return false;
	}
	
	public static boolean containsAll(int[] ids){
		for(int id : ids){
			if (!contains(id))
				return false;
		}
		return true;
	}
	
	public static boolean itemOnObject(Item item, GameObject object){
		if (item == null || object == null || !Inventory.contains(item.getId()) || !object.isVisible())
			return false;
		item.interact("use");
		sleep(500);
		object.click();
		return true;
	}
	
	public static boolean itemOnObject(int itemId, GameObject object){
		return itemOnObject(Inventory.getItem(itemId), object);
	}
}
