package bot.script.wrappers;

/**
 * 
 * @author Webjoch
 *
 */
public class Player extends Entity{
	bot.accessors.Player accessor;
	
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
}
