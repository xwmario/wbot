package bot.script.wrappers;

/**
 * 
 * @author Webjoch
 *
 */
public class NPC extends Entity{
	bot.accessors.NPC accessor;
	
	public NPC(bot.accessors.NPC accessor) {
		super(accessor);
		this.accessor = accessor;
	}
	
	public EntityDef getDef(){
		return new EntityDef(accessor.getDef());
	}
	
	public int getId(){
		if (getDef() == null) return -1;
		return (int) getDef().getType();
	}
}
