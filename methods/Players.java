package bot.script.methods;

import java.util.ArrayList;

import bot.Bot;
import bot.script.wrappers.Player;
/**
 * 
 * @author Webjoch
 *
 */
public class Players {
	/**
	 * Gets your players in game
	 * @return your character
	 */
	public static Player getLocal(){
		if (Bot.getClient().getMyPlayer() == null) return null;
		return new Player(Bot.getClient().getMyPlayer());
	}
	
	/**
	 * Gets all loaded players (all white dots in the minimap)
	 * @return an array of the loaded players
	 */
	
	public static Player[] getLoaded(){
		ArrayList<Player> players = new ArrayList<Player>();
		for(bot.accessors.Player p : Bot.getClient().getPlayers()){
			if (p == null) continue;
			players.add(new Player(p));
		}
		return players.toArray(new Player[players.size()]);
	}
}
