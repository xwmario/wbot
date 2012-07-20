package bot.script.wrappers;

import java.awt.Point;

import bot.script.methods.Calculations;
import bot.script.methods.Menu;
import bot.script.methods.Mouse;

/**
 * 
 * @author Webjoch
 *
 */
public class Tile {
	int x, y;
	
	public Tile(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Point toScreen(){
		return Calculations.tileToScreen(x, y, 1);
	}
	
	public boolean interact(String action){
		Mouse.move(toScreen());
		return Menu.interact(action);
	}
	
	public boolean click(){
		return interact("Walk here");
	}
	
	public double distance(){
		return Calculations.distanceTo(this);
	}
	

	public boolean onMinimap(){
		return Calculations.distanceTo(this) < 17;
	}
	
	public String toString(){
		return "[Tile: x: "+x+" y:"+y+"]";
	}
}
