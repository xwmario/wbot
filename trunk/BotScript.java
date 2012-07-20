package bot.script;

import java.awt.Graphics;

import bot.Bot;
import bot.script.methods.Game;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;
import bot.service.ScriptDefinition;

public abstract class BotScript extends Methods implements Runnable{
	private ScriptDefinition scriptDefinition;
	private boolean stop = false;
	
	public void setScriptDefinition(ScriptDefinition def){
		this.scriptDefinition = def;
	}
	
	public abstract boolean onStart();
	
	public int loop(){
		return -1;
	}
	
	public void paint(Graphics g){
		
	}
	
	public void onFinish(){
		
	}
	
	public ScriptDefinition getDef(){
		return scriptDefinition;
	}
	
	public void stop(){
		stop = true;
	}
	
	@Override
	public void run(){
		stop = !onStart();
		
		while(!stop){
			try{
				if (!Game.inGame() && Bot.getClient().getUsername().length() > 1 && Bot.getClient().getPassword().length() > 1){
					if (Bot.getClient().getLoginState() == 2){
						Mouse.move(305, 325);
						Mouse.click(true);
						for(int i = 0; i < 100 && Game.inGame(); i++) sleep(50);
						sleep(Game.inGame() ? 3000 : 8000);
					}else if (Bot.getClient().getLoginState() == 0){
						Mouse.move(458, 292);
						Mouse.click(true);
					}else{
						break;
					}
					continue;
				}
				if (!Game.inGame()){
					break;
				}
				int wait = loop();
				if (wait < 0){
					break;
				}
				sleep(wait);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		log.info(scriptDefinition.name + " stopped");
	}
	
}
