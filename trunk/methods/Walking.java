package bot.script.methods;

import bot.script.wrappers.Component;
import nl.wbot.bot.Bot;
import bot.script.enums.Tab;
import bot.script.util.Random;
import bot.script.wrappers.Path;
import bot.script.wrappers.Tile;

/**
 * 
 * @author Webjoch
 *
 */
public class Walking extends Methods{
	/**
	 * Walks to the given tile using the minimap.
	 *
	 * @param tile to walk to.
	 */
	public static void walkTo(Tile dest){
		dest = getClosestTileOnMap(dest);
		dest.clickMinimap();
	}
	

	/**
	 * Walks to the given tile using the minimap with given randomness.
	 *
	 * @param tile to walk to.
	 * @param x randomness
	 * @param y randomness
	 */
	public static void walkTo(Tile dest, int x, int y){
		Tile tile = new Tile(dest.getX() + Random.nextInt(0, x+1)*2-x*2, dest.getY() + Random.nextInt(0, y+1)*2-y*2);
		walkTo(tile);
	}
	
	/**
	 * Walks the given path. Should be used in a loop.
	 * @param path
	 */
	public static void walkPath(Path path){
		if (path.isValid()){
			Tile next = path.getNext();
			if (next != null)
				next.clickMinimap();
		}
	}
	
	public static Tile getClosestTileOnMap(Tile tile) {
		if (!tile.onMinimap()){
			Tile loc = Players.getLocal().getLocation();
			Tile walk = new Tile((loc.getX() + tile.getX()) / 2, (loc.getY() + tile.getY()) / 2);
			return walk.onMinimap() ? walk : getClosestTileOnMap(walk);
		}
		for (int i = 1; i < 50; i++){
			if (!tile.isWalkable()){
				int factor = i / 10 + 1;
				tile = new Tile(tile.getX() + Random.nextInt(-factor, factor), tile.getY() + Random.nextInt(-factor, factor));
			}
		}
		return tile;
	}
	
	public static void setRun(boolean on){
		if (Game.getTab() != Tab.SETTINGS){
			Game.openTab(Tab.SETTINGS);
			sleep(200);
		}
		/*if (Game.getTab() == Tab.SETTINGS){
			Component iface = Interfaces.getInterface(147, on ? 153 : 152);
			iface.click();
		}   */
	}
	
	public static boolean isRunOn(){
		return Bot.get().getMainClass().getSettings()[173] == 1;
	}
	
	/**
	 * Gets the of the players using the flag on the minimap
	 * @return The tile of the flag in the minimap
	 */
	public static Tile getDestination(){
		int x = Bot.get().getMainClass().getDestX() + Bot.get().getMainClass().getBaseX();
		int y = Bot.get().getMainClass().getDestY() + Bot.get().getMainClass().getBaseY();
		return new Tile(x, y);
	}
	
	/**
	 * Determines if its time to set a new flag in the minimap.
	 * @param destination the final destination
	 * @return returns true if ready for now flag, otherwise false
	 */
	
	public static boolean readyForNextFlag(Tile destination){
		Tile base = new Tile(Bot.get().getMainClass().getBaseX(), Bot.get().getMainClass().getBaseY());
		if (getDestination().getX() == base.getX() && getDestination().getY() == base.getY()) return true;
		return getDestination().distance() < 5;
	}
}