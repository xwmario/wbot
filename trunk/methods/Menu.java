package bot.script.methods;

import java.awt.Point;
import java.util.ArrayList;

import nl.wbot.bot.Bot;

public class Menu extends Methods{
	public static boolean isOpen(){
		return Bot.get().getMainClass().isMenuOpen();
	}
	
	/**
	 * Gets the left click menu position. NOTE: Works not in the chat frame
	 * @return a point from the menu location
	 */
	public static Point getPosition(){
		int x = Bot.get().getMainClass().getMenuX();
		int y = Bot.get().getMainClass().getMenuY();
		if (Mouse.getX() > 546 && x < 100){
			x += 546;
			y += 201;
		}
		return new Point(x, y);
	}
	
	public static int getActionIndex(String action){
		int i = 0;
		for (String anAction : getActions()){
			if (anAction.toLowerCase().equals(action.toLowerCase())){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public static boolean contains(String action){
		return getActionIndex(action) >= 0;
	}
	
	public static String[] getActions(){
		ArrayList<String> actions = new ArrayList<String>();
		for (int i = Bot.get().getMainClass().getMenuOptionsCount()-1; i >= 0; i--){
            actions.add(Bot.get().getMainClass().getMenuActions()[i] + " " + Bot.get().getMainClass().getMenuOptions()[i]);
        }
		return actions.toArray(new String[actions.size()]);
	}
	
	public static boolean interact(String action){
		int index = getActionIndex(action);
		if (index < 0) return false;
		if (index == 0){
			Mouse.click(true);
			return true;
		}
		Mouse.click(false);
		for (int i = 0; i < 100 && !isOpen(); i++) sleep(5);
		if (isOpen()){
			sleep(100);
			int x = getPosition().x + 30;
			int y = getPosition().y + 29 + index * 15;
			Mouse.move(x, y);
			sleep(100);
			Mouse.click(true);
			return true;
		}
		return false;
	}
}
