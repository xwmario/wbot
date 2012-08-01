package bot.script;

import java.awt.Graphics;

import bot.Bot;
import bot.script.methods.Game;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;
import bot.service.ScriptDefinition;

public abstract class BotScript extends Methods implements Runnable{
	private ScriptDefinition scriptDefinition;
	private Thread thread = new Thread(this);
	private boolean pause = false;
	public boolean stop = false;
	
	public Thread getThread(){
		return thread;
	}
	
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
	
	public void pause(){
		pause = !pause;
	}
	
	public void stop(){
		stop = true;
	}
	
	@Override
	public void run(){
		try{
			stop = !onStart();
		}catch(Exception e){
			stop = true;
			e.printStackTrace();
		}
		mainLoop:
		while(!stop){
			try{
				while(pause){
					sleep(75);
					if (stop)
						break mainLoop;
				}
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
				
				int wait = -1;
				if (!(this instanceof Random))
					for(Random r : Bot.getInstance().getRandoms()){
						if (r.isValid()){
							log(r.getName() + "event started");
							r.onStart();
							while(r.isValid() && !stop && !r.stop){
								wait = r.loop();
								if (wait < 0) break;
								sleep(wait);
							}
							r.onFinish();
							log(r.getName() + "event stopped");
						}
					}
				Bot.getInstance().getAntiban().scriptLoop();
				
				wait = loop();
				if (wait < 0){
					break;
				}
				sleep(wait);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		log.info(scriptDefinition.name + " stopped");
		Bot.getInstance().scriptStopped();
	}
	
}
