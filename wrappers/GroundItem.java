package bot.script.wrappers;

import bot.Bot;

public class GroundItem {
	bot.accessors.Item accessor;
	int x, y;
	
	public GroundItem(bot.accessors.Item item, int x, int y){
		this.accessor = item;
		this.x = x + Bot.getClient().getBaseX();
		this.y = x + Bot.getClient().getBaseY();
	}
	
	public int getId(){
		return accessor.getId();
	}
	
	public Tile getLocation(){
		return new Tile(x, y);
	}
	
	public double distance(){
		return getLocation().distance();
	}
}
