package bot.script.wrappers;

import java.awt.Point;

import nl.wbot.bot.Bot;
import nl.wbot.bot.accessors.ObjectDef;

import bot.script.methods.Calculations;
import bot.script.methods.Game;
import bot.script.methods.Menu;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;
import bot.script.util.Random;

/**
 * 
 * @author Webjoch
 *
 */
public class GameObject extends Methods{
	nl.wbot.bot.accessors.GameObject accessor;
	Tile location;
	ObjectDef def;
	
	int x;
	int y;
	
	public GameObject(nl.wbot.bot.accessors.GameObject accessor){
		this.accessor = accessor;
		this.x = accessor.getId() & 0x7F;
		this.y = accessor.getId() >> 7 & 0x7F;
		this.location = new Tile(x + Game.getRegion().getX(), y + Game.getRegion().getY());
	}
	
	public int getId(){
		return accessor.getId() >> 14 & 0x7fff;
	}
	
	public Tile getLocation(){
		return location;
	}
	
	public Point getPoint(){
		Point p = Calculations.worldToScreen((x + 0.5D) * 128.0D, (y + 0.5D) * 128.0D, Game.getPlane());
		return p;
	}
	
	/**
	 * Try to avoid this method. It can crash the bot.
	 * @return
	 */
	public ObjectDef getDef(){
		if (def != null)
			return def;
		if (getId() > 0 && getId() <= 14973){
			def = Bot.get().getMainClass().getObjectDef(getId());
			return def;
		}
		return null;
	}
	
	public boolean isVisible(){
		Point p = getPoint();
		return p.x > 10 && p.y > 10 && p.x < 506 && p.y < 330;
	}
	
	public double distance(){
		return Calculations.distanceTo(getLocation());
	}
	
	public boolean interact(String action){
		for (int i = 1; i < 15; i++){
			Point p = getPoint();
			Mouse.move(p.x + Random.nextInt(i, i*2) - i*2, p.y + Random.nextInt(i, i*2) - i*2);
			sleep(100);
			if (Menu.contains(action)) break;
		}
		return Menu.interact(action);
	}
	
	public boolean click(){
		if (!isVisible()) return false;
		Mouse.move(getPoint());
		sleep(200);
		Mouse.click(true);
		return true;
	}
}
