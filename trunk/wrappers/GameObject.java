package bot.script.wrappers;

import java.awt.Point;

import nl.wbot.bot.Bot;

import bot.script.enums.ObjectType;
import bot.script.methods.Calculations;
import bot.script.methods.Game;
import bot.script.methods.Menu;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;

/**
 * 
 * @author Webjoch
 *
 */
public class GameObject extends Methods{
	public nl.wbot.client.GameObject accessor;
	Tile location;
	ObjectType type;
	int regionX;
	int regionY;
	
	public GameObject(nl.wbot.client.GameObject accessor, ObjectType type){
		this.accessor = accessor;
		this.type = type;
		
		this.regionX = accessor.getHash() & 0x7F;
		this.regionY = accessor.getHash() >> 7 & 0x7F;
		this.location = new Tile(regionX + Game.getRegion().getX(), regionY + Game.getRegion().getY());
	}
	
	public int getId(){
		return accessor.getHash() >> 14 & 0x7fff;
	}
	
	public Tile getLocation(){
		return location;
	}
	
	public Point getPoint(){
		Point p = Calculations.worldToScreen(accessor.getX(), accessor.getY(), accessor.getZ());
		return p;
	}
	
	public ObjectType getType(){
		return type;
	}
	
	public boolean isVisible(){
		Point p = getPoint();
		return p.x > 10 && p.y > 10 && p.x < 506 && p.y < 330;
	}
	
	public double distance(){
		return Calculations.distanceTo(getLocation());
	}
	
	public boolean interact(String action){
		for (int i = 1; i < 3; i++){
			Point p = getPoint();
			Mouse.move(p.x, p.y);
			sleep(80);
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
