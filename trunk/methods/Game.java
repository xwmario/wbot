package bot.script.methods;

import java.awt.Point;

import bot.Bot;

/**
 * 
 * @author Webjoch
 *
 */
public class Game extends Methods{
	public enum Tabs{
		COMBAT(0, new Point(560, 187)),
		SKILLS(1, new Point(585, 187)),
		QUEST(2, new Point(612, 187)),
		INVENTORY(3, new Point(650, 187)),
		EQUIPMENT(4, new Point(684, 187)),
		PRAYER(5, new Point(710, 187)),
		SPELLBOOK(6, new Point(740, 187)),
		FRIENDLIST(8, new Point(585, 483)),
		IGNORELIST(9, new Point(612, 483)),
		LOGOUT(10, new Point(648, 483)),
		SETTINGS(11, new Point(683, 483)),
		CONTROLS(12, new Point(712, 483)),
		MUSIC(13, new Point(737, 483));
		
		int id;
		Point point;
		Tabs(int id, Point point){
			this.id = id;
			this.point = point;
		}
		public int getId(){
			return id;
		}
		public Point getPoint(){
			return point;
		}
	}
	
	public static int getPlane(){
		return Bot.getClient().getPlane();
	}
	
	public static boolean inGame(){
		return Bot.getClient().isLoggedIn();
	}
	
	public static Tabs getTab(){
		for(Tabs tab : Tabs.values()){
			if (tab.getId() == Bot.getClient().getTabId()){
				return tab;
			}
		}
		return null;
	}
	
	public static void openTab(Tabs tab){
		Mouse.move(tab.getPoint());
		sleep(50);
		Mouse.click(true);
	}
	
	public static void logout(){
		if (getTab() != Tabs.LOGOUT){
			openTab(Tabs.LOGOUT);
			sleep(50);
		}
		Interfaces.getInterface(2449, 2458).click();
	}
}
