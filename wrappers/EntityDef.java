package bot.script.wrappers;
/**
 * 
 * @author Webjoch
 *
 */
public class EntityDef {
	bot.accessors.EntityDef accessor;
	
	public EntityDef(bot.accessors.EntityDef accessor){
		this.accessor = accessor;
	}
	
	public String[] getActions(){
		if (accessor == null) return null;
		return accessor.getActions();
	}
	
	public String getName(){
		return accessor.getName();
	}
	
	public int getLevel(){
		return accessor.getLevel();
	}
	
	public long getType(){
		return accessor == null ? 0 : accessor.getType();
	}
}
