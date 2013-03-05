package bot.script.wrappers;

import nl.wbot.client.NpcDefinition;

/**
 * 
 * @author Webjoch
 *
 */
public class EntityDef {
	nl.wbot.client.NpcDefinition accessor;
	
	public EntityDef(NpcDefinition accessor){
		this.accessor = accessor;
	}
	
	public String[] getActions(){
		if (accessor == null) return null;
		return null;//accessor.getActions();
	}
	
	public String getName(){
		return accessor.getName();
	}
	
	public int getLevel(){
		return accessor.getCombatLevel();
	}
	
	public long getType(){
		return accessor == null ? 0 : accessor.getId();
	}
}
