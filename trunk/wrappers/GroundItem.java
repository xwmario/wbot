package bot.script.wrappers;

import java.awt.Point;

import bot.script.methods.Calculations;
import bot.script.methods.Menu;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;
import bot.script.util.Random;

import nl.wbot.bot.Bot;

public class GroundItem {
	nl.wbot.client.GroundItem accessor;
	int x, y, realX, realY;

	public GroundItem(nl.wbot.client.GroundItem item, int x, int y){
		this.accessor = item;
		this.realX = x;
		this.realY = y;
		this.x = x + Bot.get().getMainClass().getBaseX();
		this.y = y + Bot.get().getMainClass().getBaseY();
	}
	
	public int getId(){
		return accessor.getId() + 1;
	}
	
	public Tile getLocation(){
		return new Tile(x, y);
	}
	
	public Point getPoint(){
        int x = realX * 128 + 64;
        int y = realY * 128 + 64;
		return Calculations.worldToScreen(x, y, Calculations.tileHeight(x, y));
	}
	
	public boolean interact(String action){
		return interact(action, "");
	}
   
	public boolean interact(String action, String option){
		for (int i = 1; i < 10; i++){
			Point p = getPoint();
			Mouse.move(p.x + Random.nextInt(i, i*2) - i*2, p.y + Random.nextInt(i, i*2) - i*2);
			Methods.sleep(100);
			if (Menu.contains(action) && Menu.contains(option) break;
		}
		return Menu.interact(action, option);
	}
	
	public double distance(){
		return getLocation().distance();
	}
	
	public boolean isValid(){
		return accessor != null;
	}
}
