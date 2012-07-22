package bot.script.wrappers;

import java.awt.Point;

import bot.Bot;
import bot.accessors.ItemDef;
import bot.script.methods.Calculations;
import bot.script.methods.Menu;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;
import bot.script.util.Random;

public class GroundItem {
	bot.accessors.Item accessor;
	int x, y, realX, realY;
	
	public GroundItem(bot.accessors.Item item, int x, int y){
		this.accessor = item;
		this.realX = x;
		this.realY = y;
		this.x = x + Bot.getClient().getBaseX();
		this.y = y + Bot.getClient().getBaseY();
	}
	
	public int getId(){
		return accessor.getId() + 1;
	}
	
	public Tile getLocation(){
		return new Tile(x, y);
	}
	
	public Point getPoint(){		
		return Calculations.worldToScreen(realX * 128 + 64, realY * 128 + 64, 0);
	}
	
	public boolean interact(String action){
		for (int i = 1; i < 10; i++){
			Point p = getPoint();
			Mouse.move(p.x + Random.nextInt(i, i*2) - i*2, p.y + Random.nextInt(i, i*2) - i*2);
			Methods.sleep(100);
			if (Menu.contains(action)) break;
		}
		return Menu.interact(action);
	}
	
	public double distance(){
		return getLocation().distance();
	}
	
	public ItemDef getDef(){
		return Bot.getClient().getItemDef(getId());
	}
	
	public boolean isValid(){
		return accessor != null;
	}
}
