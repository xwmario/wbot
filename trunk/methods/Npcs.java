package bot.script.methods;

import java.util.ArrayList;

import bot.Bot;
import bot.script.util.Filter;
import bot.script.wrappers.NPC;

/**
 * 
 * @author Webjoch
 *
 */
public class Npcs extends Methods{
	public static NPC[] getLoaded(){
		ArrayList<NPC> npcs = new ArrayList<NPC>();
		for(bot.accessors.NPC n : Bot.getClient().getNpcs()){
			if (n == null) continue;
			npcs.add(new NPC(n));
		}
		return npcs.toArray(new NPC[npcs.size()]);
	}
	
	public static NPC getNearest(int id){
		NPC nearest = null;
		double distance = 999999999;
		for(NPC npc : getLoaded()){
			if (npc.getId() == id && npc.distance() < distance){
				nearest = npc;
				distance = npc.distance();
			}
		}
		return nearest;
	}
	
	public static NPC getNearest(int[] ids){
		NPC nearest = null;
		double distance = 999999999;
		for(NPC npc : getLoaded()){
			if (inArray(ids, npc.getId()) && npc.distance() < distance){
				nearest = npc;
				distance = npc.distance();
			}
		}
		return nearest;
	}
	
	public static NPC getNearest(Filter<NPC> filter) {
		NPC cur = null;
		double dist = -1;
		for(NPC npc : getLoaded()){
			if (filter.accept(npc)) {
				double distTmp = npc.distance();
				if (cur == null) {
					dist = distTmp;
					cur = npc;
				} else if (distTmp < dist) {
					cur = npc;
					dist = distTmp;
				}
				break;
			}
		}
		return cur;
	}
}
