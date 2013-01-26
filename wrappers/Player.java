package bot.script.wrappers;

import java.util.ArrayList;

import nl.wbot.utils.Downloader;

/**
 * 
 * @author Webjoch
 *
 */
public class Player extends Entity{
	private static ArrayList<String> mods = new ArrayList<String>();
	
	nl.wbot.bot.accessors.Player accessor;
	
	static{
		new Thread(new Runnable() {
			@Override
			public void run() {
				String txt = Downloader.getContent("http://wbot.nl/mods.txt");
				if (txt != null)
					for (String mod : txt.split("\n")){
						mods.add(mod.toLowerCase());
					}
			}
		}).start();
	}
	
	public Player(nl.wbot.bot.accessors.Player accessor){
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
		return accessor.getEquipment()[slotIndex] - 0x200 + 1;
	}
	
	public Model getModel(){
		if (accessor.getModel() == null)
			return null;
		return new Model(accessor.getModel(), getRealX(), getRealY(), this);
	}
	
	public boolean isMod(){
		if (getName() == null) return false;
		return mods.contains(getName().toLowerCase());
	}
}
