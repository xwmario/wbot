package bot.script.methods;

import java.awt.Point;

import bot.Bot;
import bot.script.methods.Game.Tabs;
import bot.script.util.Random;
import bot.script.wrappers.Interface;
import bot.script.wrappers.Path;
import bot.script.wrappers.Tile;

/**
 * 
 * @author Webjoch
 *
 */
public class Walking extends Methods implements Runnable{
	/**
	 * Walks to the given tile using the minimap.
	 *
	 * @param The tile to walk to.
	 */
	public static void walkTo(Tile dest){
		dest = getClosestTileOnMap(dest);
		Point p = Calculations.tileToMinimap(dest);
		Mouse.move(p);
		sleep(50);
		Mouse.click(true);
	}
	
	/**
	 * Walks the given path. Should be used in a loop.
	 * @param path
	 */
	public static void walkPath(Path path){
		if (path.isValid())
			walkTo(path.getNext());
	}
	
	/**
	 * Walks to the given tile using the minimap with given randomness.
	 *
	 * @param The tile to walk to.
	 * @param The x randomness
	 * @param The y randomness
	 */
	public static void walkTo(Tile dest, int x, int y){
		Tile tile = new Tile(dest.getX() + Random.nextInt(0, x+1)*2-x*2, dest.getY() + Random.nextInt(0, y+1)*2-y*2);
		walkTo(tile);
	}
	
	public static Tile getClosestTileOnMap(Tile tile) {
		if (!tile.onMinimap()){
			Tile loc = Players.getLocal().getLocation();
			Tile walk = new Tile((loc.getX() + tile.getX()) / 2, (loc.getY() + tile.getY()) / 2);
			return walk.onMinimap() ? walk : getClosestTileOnMap(walk);
		}
		return tile;
	}
	
	public static void setRun(boolean on){
		if (Game.getTab() != Tabs.CONTROLS){
			Game.openTab(Tabs.CONTROLS);
			sleep(200);
		}
		if (Game.getTab() == Tabs.CONTROLS){
			Interface iface = Interfaces.getInterface(147, on ? 153 : 152);
			iface.click();
		}
	}
	
	public static boolean isRunOn(){
		return Bot.getClient().getSettings()[173] == 1;
	}
	
	/**
	 * Gets the of the players using the flag on the minimap
	 * @return The tile of the flag in the minimap
	 */
	public static Tile getDestination(){
		int x = Bot.getClient().getDestX() + Bot.getClient().getBaseX();
		int y = Bot.getClient().getDestY() + Bot.getClient().getBaseY();
		return new Tile(x, y);
	}
	
	/**
	 * Determines if its time to set a new flag in the minimap.
	 * @param destination the final destination
	 * @return returns true if ready for now flag, otherwise false
	 */
	
	public static boolean readyForNextFlag(Tile destination){
		Tile base = new Tile(Bot.getClient().getBaseX(), Bot.getClient().getBaseY());
		if (getDestination().getX() == base.getX() && getDestination().getY() == base.getY()) return true;
		return getDestination().distance() < 5 && Calculations.distanceBetween(getDestination(), destination) > 4;
	}

	@Override
	public void run() {
		Camera.setAngle(Camera.getAngle());
	}
}