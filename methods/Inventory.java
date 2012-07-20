package bot.script.methods;

import java.util.ArrayList;

import bot.Bot;
import bot.script.wrappers.Interface;
import bot.script.wrappers.Item;

public class Inventory extends Methods{
	
	public static Item[] getItems(){
		ArrayList<Item> items = new ArrayList<Item>();
		int ifaceId = Bot.getClient().getInventoryInterfaceId() < 0 ? 3214 : Bot.getClient().getInventoryInterfaceId() + 1;
		Interface iface = Interfaces.getInterface(ifaceId);
		if (iface.getItems() == null) return null;
		int i = 0;
		for(int itemId : iface.getItems()){
			if (itemId > 0){
				items.add(new Item(i, itemId, iface));
			}
			i++;
		}
		return items.toArray(new Item[items.size()]);
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
}
