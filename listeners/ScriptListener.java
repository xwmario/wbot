package bot.script.listeners;

import bot.script.BotScript;

public interface ScriptListener {
	
	public void scriptStarted(BotScript script);
	
	public void scriptStopped(BotScript script);
	
	public void scriptPaused(BotScript script);
	
	public void scriptResumed(BotScript script);
}
