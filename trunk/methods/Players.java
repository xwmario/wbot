package bot.script.methods;

import java.util.ArrayList;

import nl.wbot.bot.Bot;

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
		if (Bot.get().getMainClass().getMyPlayer() == null) return null;
		return new Player(Bot.get().getMainClass().getMyPlayer());
	}
	
	/**
	 * Gets all loaded players (all white dots in the minimap)
	 * @return an array of the loaded players
	 */
	public static Player[] getLoaded(){
		ArrayList<Player> players = new ArrayList<Player>();
		for(nl.wbot.bot.accessors.Player p : Bot.get().getMainClass().getPlayers()){
			if (p == null) continue;
			players.add(new Player(p));
		}
		return players.toArray(new Player[players.size()]);
	}
}
