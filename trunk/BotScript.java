package bot.script;

import java.awt.Graphics;

import nl.wbot.bot.Bot;
import nl.wbot.service.ScriptDefinition;

import bot.script.methods.Methods;

public abstract class BotScript extends Methods{
	private ScriptDefinition scriptDefinition;
	public boolean stop = false;
		
	public void setScriptDefinition(ScriptDefinition def){
		this.scriptDefinition = def;
	}
	
	public abstract boolean onStart();
	
	public abstract int loop();
	
	public void paint(Graphics g){
		
	}
	
	public void onFinish(){
		
	}
	
	public ScriptDefinition getDef(){
		return scriptDefinition;
	}
	
	public void stop(){
		Bot.get().getScriptHandler().stop();
	}
}
