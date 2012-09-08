package bot.script;

import nl.wbot.service.ScriptDefinition;


public abstract class Random extends BotScript{
	
	public abstract boolean isValid();
	
	public abstract String getName();
	
	@Override
	public boolean onStart(){
		ScriptDefinition def = new ScriptDefinition();
		def.name = getName();
		setScriptDefinition(def);
		return isValid();
	}
}
