package bot.script;

import java.awt.Graphics;
import java.util.ArrayList;

import nl.wbot.service.ScriptDefinition;

import bot.script.listeners.ScriptListener;
import bot.script.methods.Methods;

public abstract class BotScript extends Methods implements Runnable{
	private ArrayList<ScriptListener> listeners = new ArrayList<ScriptListener>();
	private ScriptDefinition scriptDefinition;
	private Thread thread = new Thread(this);
	private boolean pause = false;
	private boolean isPaused = false;
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
	
	public boolean isPaused(){
		return isPaused;
	}
	
	public void pause(){
		pause = !pause;
	}
	
	public void stop(){
		stop = true;
	}
	
	public void addScriptListener(ScriptListener l){
		listeners.add(l);
	}
	
	@Override
	public void run(){
		for(ScriptListener listener : listeners)
			listener.scriptStarted(this);
		try{
			stop = !onStart();
		}catch(Exception e){
			stop = true;
			e.printStackTrace();
		}
		log.info(scriptDefinition.name + " started");
		mainLoop:
		while(!stop){
			try{
				if (pause){
					for(ScriptListener listener : listeners)
						listener.scriptPaused(this);
				}
				while(pause){
					isPaused = true;
					sleep(75);
					if (stop)
						break mainLoop;
				}
				if (isPaused){
					for(ScriptListener listener : listeners)
						listener.scriptResumed(this);
					isPaused = false;
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
		onFinish();
		for(ScriptListener listener : listeners)
			listener.scriptStopped(this);
	}
	
}
