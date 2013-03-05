package bot.script.wrappers;

/**
 * 
 * @author Webjoch
 *
 */
public class Player extends Entity{
	nl.wbot.client.Player accessor;
	
	public Player(nl.wbot.client.Player accessor){
		super(accessor);
		this.accessor = accessor;
	}
	
	public int getLvl(){
		return accessor.getCombatLevel();
	}
	
	public String getName(){
		return accessor.getName();
	}
}
