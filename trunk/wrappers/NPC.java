package bot.script.wrappers;

import nl.wbot.client.Npc;

/**
 * 
 * @author Webjoch
 *
 */
public class NPC extends Entity{
	Npc accessor;
	
	public NPC(Npc accessor) {
		super(accessor);
		this.accessor = accessor;
	}
	
	public EntityDef getDef(){
		return new EntityDef(accessor.getDefinition());
	}
	
	public int getId(){
		if (getDef() == null) return -1;
		return (int) getDef().getType();
	}
}
