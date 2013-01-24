package bot.script.methods;

import java.util.ArrayList;

import nl.wbot.bot.Bot;
import nl.wbot.bot.accessors.Ground;

import bot.script.util.Filter;
import bot.script.wrappers.GameObject;
/**
 * 
 * @author Webjoch
 *
 */
public class Objects{	
	/**
	 * Gets the object at the given location
	 * @param x 
	 * @param y
	 * @return
	 */
	public static GameObject getObject(int x, int y){
		Ground ground = Bot.get().getMainClass().getWorldController().getGround()[Game.getPlane()][x][y];
		if (ground == null)
			return null;
		nl.wbot.bot.accessors.GameObject obj1 = ground.getObject1();
		nl.wbot.bot.accessors.GameObject obj2 = ground.getObject2();
		nl.wbot.bot.accessors.GameObject obj3 = ground.getObject3();
		nl.wbot.bot.accessors.GameObject obj4 = ground.getObject4();
		nl.wbot.bot.accessors.GameObject[] obj5 = ground.getObject5();

		if (obj4 != null)
			return new GameObject(obj4);
		
		for(nl.wbot.bot.accessors.GameObject obj : obj5){
			if (obj != null)
				return new GameObject(obj);
		}
		if (obj1 != null)
			return new GameObject(obj1);
		
		if (obj2 != null)
			return new GameObject(obj2);
		
		if (obj3 != null)
			return new GameObject(obj3);
		
		return null;
	}
	
	
	/**
	 * Gets all objects in the loaded area. NOTE: The method doesn't filter ground decorations
	 * @return an array of GameObjects from the loaded array
	 */
	public static GameObject[] getLoaded(){
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
				
			for(int x = 0; x < 104; x++){
				for(int y = 0; y < 104; y++){
					GameObject obj = getObject(x, y);
					if (obj != null)
						objects.add(obj);
				}
			}
		
		return objects.toArray(new GameObject[objects.size()]);
	}
	
	public static GameObject getNearest(int id){
		GameObject nearest = null;
		double distance = 9999999;
		for (GameObject object : getLoaded()){
			if (object.getId() == id && object.distance() < distance){
				nearest = object;
				distance = object.distance();
			}
		}
		return nearest;
	}
	
	public static GameObject getNearest(int[] ids){
		GameObject nearest = null;
		double distance = 9999999;
		for (GameObject object : getLoaded()){
			if (Methods.inArray(ids, object.getId()) && object.distance() < distance){
				nearest = object;
				distance = object.distance();
			}
		}
		return nearest;
	}
	
	public static GameObject getNearest(Filter<GameObject> filter) {
		GameObject cur = null;
		double dist = -1;
		for(GameObject o : getLoaded()){
			if (filter.accept(o)) {
				double distTmp = o.distance();
				if (cur == null) {
					dist = distTmp;
					cur = o;
				} else if (distTmp < dist) {
					cur = o;
					dist = distTmp;
				}
				break;
			}
		}
		return cur;
	}
}
