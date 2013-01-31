package bot.script.wrappers;

import java.awt.Point;

import nl.wbot.bot.Bot;
import nl.wbot.bot.accessors.ObjectDef;

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
	public nl.wbot.bot.accessors.GameObject accessor;
	Tile location;
	ObjectDef def;
	ObjectType type;
	
	int x;
	int y;
	
	int regionX;
	int regionY;
	
	public GameObject(nl.wbot.bot.accessors.GameObject accessor, ObjectType type){
		this.accessor = accessor;
		this.type = type;
		
		this.regionX = accessor.getId() & 0x7F;
		this.regionY = accessor.getId() >> 7 & 0x7F;
		
		if (type == ObjectType.INTERACTABLE){
			this.x = accessor.getX();
			this.y = accessor.getY();
		}else{
			this.x = (int) ((regionX + 0.5D) * 128.0D);
			this.y = (int) ((regionY + 0.5D) * 128.0D);
		}
		this.location = new Tile(regionX + Game.getRegion().getX(), regionY + Game.getRegion().getY());
	}
	
	public int getId(){
		return accessor.getId() >> 14 & 0x7fff;
	}
	
	public Tile getLocation(){
		return location;
	}
	
	public Point getPoint(){
		Point p = Calculations.worldToScreen(x, y, Game.getPlane());
		return p;
	}
	
	public Model getModel(){
		try{
			return new Model((nl.wbot.bot.accessors.Model) accessor.getModel(), x, y);
		}catch(ClassCastException e){
			return null;
		}
	}
	
	public ObjectType getType(){
		return type;
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
		for (int i = 1; i < 3; i++){
			Point p = null;
			if (getModel() == null)
				p = getPoint();
			else
				p= getModel().getRandomPoint();
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
