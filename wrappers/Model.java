package bot.script.wrappers;

import java.awt.Point;
import java.awt.Polygon;
import java.util.LinkedList;

import bot.script.methods.Calculations;

/**
 * 
 * @author Webjoch
 *
 */
public class Model {
	bot.accessors.Model accessor;
	
	int localX, localY;
	
	public Model(bot.accessors.Model accessor, int localX, int localY){
		this.accessor = accessor;
		this.localX = localX;
		this.localY = localY;
	}
	
	public Polygon[] getTriangles() {
		LinkedList<Polygon> polygons = new LinkedList<Polygon>();
		int len = accessor.getXPoints().length;
		
		int height = Calculations.tileHeight(localX, localY);
		for (int i = 0; i < len; ++i) {
			Point one = Calculations.worldToScreen(localX + accessor.getXPoints()[accessor.getIndices1()[i]], localY + accessor.getZPoints()[accessor.getIndices1()[i]], height + accessor.getYPoints()[accessor.getIndices1()[i]]);
			Point two = Calculations.worldToScreen(localX + accessor.getXPoints()[accessor.getIndices2()[i]], localY + accessor.getZPoints()[accessor.getIndices2()[i]], height + accessor.getYPoints()[accessor.getIndices2()[i]]);
			Point three = Calculations.worldToScreen(localX + accessor.getXPoints()[accessor.getIndices3()[i]], localY + accessor.getZPoints()[accessor.getIndices3()[i]], height + accessor.getYPoints()[accessor.getIndices3()[i]]);

			if (one.x >= 0 && two.x >= 0 && three.x >= 0) {
				polygons.add(new Polygon(new int[]{one.x, two.x, three.x}, new int[]{one.y, two.y, three.y}, 3));
			}
		}
		return polygons.toArray(new Polygon[polygons.size()]);
	}
}
