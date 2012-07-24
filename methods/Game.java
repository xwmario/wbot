package bot.script.methods;

import bot.Bot;
import bot.script.enums.Tab;

/**
 * 
 * @author Webjoch
 *
 */
public class Game extends Methods{
	public static int getPlane(){
		return Bot.getClient().getPlane();
	}
	
	public static boolean inGame(){
		return Bot.getClient().isLoggedIn();
	}
	
	public static Tab getTab(){
		for(Tab tab : Tab.values()){
			if (tab.getId() == Bot.getClient().getTabId()){
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
		return Bot.getClient().getEnergy();
	}
	
	public static int getWeight(){
		return Bot.getClient().getWeight();
	}
}
