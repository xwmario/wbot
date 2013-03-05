package bot.script;

import java.awt.Graphics;

import nl.wbot.bot.Bot;

import bot.script.methods.Methods;

public abstract class BotScript extends Methods{
	public boolean stop = false;

	public abstract boolean onStart();
	
	public abstract int loop();
	
	public void paint(Graphics g){
		
	}
	
	public void onFinish(){
		
	}
	
	public void stop(){
		Bot.get().getScriptHandler().stop();
	}
}
