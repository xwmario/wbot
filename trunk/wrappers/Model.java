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
	nl.wbot.bot.accessors.Model accessor;
	Entity entity;
	
	int localX, localY;
	
	protected int[] xPoints;
	protected int[] yPoints;
	protected int[] zPoints;

	protected int[] indices1;
	protected int[] indices2;
	protected int[] indices3;

	public static final int[] SIN_TABLE = new int[16384];
	public static final int[] COS_TABLE = new int[16384];

	static {
		final double d = 0.00038349519697141029D;
		for (int i = 0; i < 16384; i++) {
			SIN_TABLE[i] = (int) (32768D * Math.sin(i * d));
			COS_TABLE[i] = (int) (32768D * Math.cos(i * d));
		}
	}
	
	public Model(nl.wbot.bot.accessors.Model accessor, int localX, int localY, Entity entity){
		this.accessor = accessor;
		this.entity = entity;
		this.localX = localX;
		this.localY = localY;
		
		xPoints = accessor.getXPoints();
		yPoints = accessor.getYPoints();
		zPoints = accessor.getZPoints();
		indices1 = accessor.getIndices1();
		indices2 = accessor.getIndices2();
		indices3 = accessor.getIndices3();
	}
	
	public Polygon[] getTriangles() {
		int theta = entity.getOrientation() & 0x3fff;
		int sin = SIN_TABLE[theta];
		int cos = COS_TABLE[theta];
		for (int i = 0; i < xPoints.length; ++i) {
			xPoints[i] = xPoints[i] * cos + zPoints[i] * sin >> 15;
			zPoints[i] = zPoints[i] * cos - xPoints[i] * sin >> 15;
		}
		
		LinkedList<Polygon> polygons = new LinkedList<Polygon>();
		int len = indices1.length;
		int height = 0;
		for (int i = 0; i < len; ++i) {
			Point one = Calculations.worldToScreen(localX + xPoints[indices1[i]], localY + zPoints[indices1[i]], height + yPoints[indices1[i]]);
			Point two = Calculations.worldToScreen(localX + xPoints[indices2[i]], localY + zPoints[indices2[i]], height + yPoints[indices2[i]]);
			Point three = Calculations.worldToScreen(localX + xPoints[indices3[i]], localY + zPoints[indices3[i]], height + yPoints[indices3[i]]);

			if (one.x >= 0 && two.x >= 0 && three.x >= 0) {
				polygons.add(new Polygon(new int[]{one.x, two.x, three.x}, new int[]{one.y, two.y, three.y}, 3));
			}
		}
		return polygons.toArray(new Polygon[polygons.size()]);
	}
}
