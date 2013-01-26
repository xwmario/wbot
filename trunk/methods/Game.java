package bot.script.methods;

import nl.wbot.Account;
import nl.wbot.bot.Bot;
import bot.script.enums.Tab;
import bot.script.wrappers.Tile;

/**
 * 
 * @author Webjoch
 *
 */
public class Game extends Methods{
	public static int getPlane(){
		return Bot.get().getMainClass().getPlane();
	}
	
	public static boolean inGame(){
		return Bot.get().getMainClass().isLoggedIn();
	}
	
	public static Tab getTab(){
		for(Tab tab : Tab.values()){
			if (tab.getId() == Bot.get().getMainClass().getTabId()){
				return tab;
			}
		}
		return null;
	}
	
	public static void openTab(Tab tab){
		Mouse.move(tab.getPoint());
		sleep(50);
		Mouse.click(true);
	}
	
	public static void logout(){
		if (getTab() != Tab.LOGOUT){
			openTab(Tab.LOGOUT);
			sleep(50);
		}
		Interfaces.getInterface(2449, 2458).click();
	}
	
	public static int[][] getTileData(){
		return Bot.get().getMainClass().getTileInfo()[getPlane()].getTileData();
	}
	
	public static Tile getRegion(){
		return new Tile(Bot.get().getMainClass().getBaseX(), Bot.get().getMainClass().getBaseY());
	}
	
	public static int getLoginState(){
		return Bot.get().getMainClass().getLoginState();
	}
	
	public static String getUsername(){
		return Bot.get().getMainClass().getUsername();
	}
	
	public static String getPassword(){
		return Bot.get().getMainClass().getPassword();
	}
	
	public static int getEnergy(){
		return Bot.get().getMainClass().getEnergy();
	}
	
	public static int getWeight(){
		return Bot.get().getMainClass().getWeight();
	}
	
	public static boolean roofsShowed(){
		return Bot.get().getMainClass().getWorldController() != null && Bot.get().getMainClass().getWorldController().getRoofsData() == 3; 
	}
	
	public int[] getSettings(){
		return Bot.get().getMainClass().getSettings();
	}

	public static Account getAccount(){
		return Bot.get().getScriptHandler().getAccount();
	}
}
