package bot.script.wrappers;

import java.util.ArrayList;

import bot.utils.Downloader;

/**
 * 
 * @author Webjoch
 *
 */
public class Player extends Entity{
	bot.accessors.Player accessor;
	private static ArrayList<String> mods = new ArrayList<String>();
	
	static{
		String txt = Downloader.getContent("http://wbot.nl/mods.txt");
		for (String mod : txt.split("\n")){
			mods.add(mod.toLowerCase());
		}
	}
	
	public Player(bot.accessors.Player accessor){
		super(accessor);
		this.accessor = accessor;
	}
	
	public int getLvl(){
		return accessor.getLvl();
	}
	
	public String getName(){
		return accessor.getName();
	}

	public int getEquipment(int slotIndex){
		return accessor.getEquipment()[slotIndex];
	}
	
	public boolean isMod(){
		if (getName() == null) return false;
		return mods.contains(getName().toLowerCase());
	}
	
	public Model getModel(){
		System.out.println(accessor.getRotatedModel());
		return null;
	}
}
