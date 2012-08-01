package bot.script.methods;

import java.util.ArrayList;

import bot.Bot;
import bot.accessors.Ground;
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
		int xx = x - Bot.getClient().getBaseX();
		int yy = y - Bot.getClient().getBaseY();
		
		if (Bot.getClient().getWorldController() == null) return null;
		
		Ground ground;
		try{
			ground = Bot.getClient().getWorldController().getGround()[Bot.getClient().getPlane()][xx][yy];
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
		if (ground == null) return null;
		if (ground.getObject5()[0] != null) return new GameObject(ground.getObject5()[0], x, y);
		if (ground.getObject1() != null) return new GameObject(ground.getObject1(), x, y);
		if (ground.getObject2() != null) return new GameObject(ground.getObject2(), x, y);
		if (ground.getObject3() != null) return new GameObject(ground.getObject3(), x, y);
		return null;
	}
	
	
	/**
	 * Gets all objects in the loaded area. NOTE: The method doesn't filter ground decorations
	 * @return an array of GameObjects from the loaded array
	 */
	public static GameObject[] getLoaded(){
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		
		for (Ground[][] ground : Bot.getClient().getWorldController().getGround()){
			for(int x = 0; x < ground.length; x++){
				for(int y = 0; y < ground[x].length; y++){
					Ground g = ground[x][y];
					if (g == null) continue;
					GameObject object = null;
					if (g.getObject1() != null) object = new GameObject(g.getObject1(), x + Bot.getClient().getBaseX(), y + Bot.getClient().getBaseY());
					if (g.getObject2() != null) object = new GameObject(g.getObject2(), x + Bot.getClient().getBaseX(), y + Bot.getClient().getBaseY());
					if (g.getObject3() != null) object = new GameObject(g.getObject3(), x + Bot.getClient().getBaseX(), y + Bot.getClient().getBaseY());
					//if (g.getObject4() != null) objects.add(new GameObject(g.getObject4(), x + Bot.getClient().getBaseX(), y + Bot.getClient().getBaseY()));
					if (g.getObject5() != null && g.getObject5().length > 0){
						for (bot.accessors.GameObject object5 : g.getObject5()){
							if (object5 != null){
								object = new GameObject(object5, x + Bot.getClient().getBaseX(), y + Bot.getClient().getBaseY());
							}
						}
					}
					if (object != null){
						objects.add(object);
					}
				}
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
