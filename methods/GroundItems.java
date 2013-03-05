package bot.script.methods;

import java.util.ArrayList;

import nl.wbot.bot.Bot;

import bot.script.util.Filter;
import bot.script.wrappers.GroundItem;
import nl.wbot.client.Deque;

public class GroundItems {
	public static GroundItem[] getLoaded(){
		ArrayList<GroundItem> gi = new ArrayList<GroundItem>();
		for(Deque[][] nl : Bot.get().getMainClass().getGroundItems()){
			for(int x = 0; x < nl.length; x++){
				for(int y = 0; y < nl[x].length; y++){
                    Deque nodeList = nl[x][y];
					if (nodeList != null)
					if (nodeList != null && nodeList.getHead() != null && nodeList.getHead().getNext() instanceof nl.wbot.client.GroundItem){
						nl.wbot.client.GroundItem i = (nl.wbot.client.GroundItem) nodeList.getHead().getNext();
						gi.add(new GroundItem(i, x, y));

                    }
				}
			}
		}
		return gi.toArray(new GroundItem[gi.size()]);
	}
	
	public static GroundItem getNearest(final int id){
		return getNearest(new Filter<GroundItem>(){
			@Override
			public boolean accept(GroundItem t) {
				return t.getId() == id;
			}
		});
	}
	
	public static GroundItem getNearest(Filter<GroundItem> filter){
		GroundItem cur = null;
		double dist = -1;
		for(GroundItem gi : getLoaded()){
			if (filter.accept(gi)) {
				double distTmp = gi.distance();
				if (cur == null) {
					dist = distTmp;
					cur = gi;
				} else if (distTmp < dist) {
					cur = gi;
					dist = distTmp;
				}
				break;
			}
		}
		return cur;
	}
}
