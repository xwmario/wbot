package bot.script;

public abstract class RandomSolver extends BotScript{
	
	public abstract boolean isValid();
	
	public abstract String getName();

    public abstract String getAuthor();

	@Override
	public boolean onStart(){
		/*ScriptDefinition def = new ScriptDefinition();
		def.name = getName();
		setScriptDefinition(def);  */
		return isValid();
	}
}
