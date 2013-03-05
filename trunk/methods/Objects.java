package bot.script.methods;

import java.util.ArrayList;

import nl.wbot.bot.Bot;
import nl.wbot.client.GroundTile;

import bot.script.enums.ObjectType;
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
		return getObjectInRegion(x - Game.getRegion().getX(), y - Game.getRegion().getY());
	}
	
	/**
	 * Gets an object in a region
	 * @param x 
	 * @param y
	 * @return
	 */
	public static GameObject getObjectInRegion(int x, int y){
		if (x < 0 || x > 105 || y < 0 || y > 105)
			return null;
		GroundTile ground = Bot.get().getMainClass().getSceneGraph().getTiles()[Game.getPlane()][x][y];
		
		if (ground == null)
			return null;
		nl.wbot.client.GameObject obj1 = ground.getWallObject();
		nl.wbot.client.GameObject obj2 = ground.getWallDecoration();
		nl.wbot.client.GameObject obj3 = ground.getGroundDecoration();
		nl.wbot.client.GameObject[] obj5 = ground.getInteractiveObjects();

		
		for(nl.wbot.client.GameObject obj : obj5){
			if (obj != null && (obj.getHash() >> 29 & 0x3) == 2){
				return new GameObject(obj, ObjectType.INTERACTABLE);
			}
		}
		if (obj1 != null)
			return new GameObject(obj1, ObjectType.WALL_OBJECT);
		
		if (obj2 != null)
			return new GameObject(obj2, ObjectType.WALL_DECORATION);
		
		if (obj3 != null)
			return new GameObject(obj3, ObjectType.GROUND_DECORATION);
		return null;
	}
	
	
	/**
	 * Gets all objects in the loaded area
	 * @return an array of GameObjects from the loaded array
	 */
	public static GameObject[] getLoaded(){
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
				
			for(int x = 0; x < 104; x++){
				for(int y = 0; y < 104; y++){
					GameObject obj = getObjectInRegion(x, y);
					if (obj != null)
						objects.add(obj);
				}
			}
		
		return objects.toArray(new GameObject[objects.size()]);
	}
	
	public static GameObject[] getLoaded(Filter<GameObject> filter) {
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		for(GameObject o : getLoaded()){
			if (filter.accept(o))
				objects.add(o);
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
