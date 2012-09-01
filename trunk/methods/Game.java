package bot.script.methods;

import nl.wbot.bot.Bot;
import bot.script.enums.Tab;

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
}
