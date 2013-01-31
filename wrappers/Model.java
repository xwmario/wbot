package bot.script.wrappers;

import java.awt.Point;
import java.awt.Polygon;
import java.util.LinkedList;

import bot.script.methods.Calculations;
import bot.script.util.Random;

/**
 * 
 * @author Webjoch
 *
 */
public class Model {
	nl.wbot.bot.accessors.Model accessor;
	Entity entity;
	
	int x;
	int y;
	
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
	
	public Model(nl.wbot.bot.accessors.Model accessor, int x, int y){
		this.accessor = accessor;
		
		this.x = x;
		this.y = y;
		
		xPoints = accessor.getXPoints();
		yPoints = accessor.getYPoints();
		zPoints = accessor.getZPoints();
		indices1 = accessor.getIndices1();
		indices2 = accessor.getIndices2();
		indices3 = accessor.getIndices3();
		
	}
	
	public Polygon[] getTriangles() {		
		LinkedList<Polygon> polygons = new LinkedList<Polygon>();
				
		int len = indices1.length;		
		for (int i = 0; i < len; ++i) {
			Point p1 = Calculations.worldToScreen(x + xPoints[indices1[i]], y + zPoints[indices1[i]],  - yPoints[indices1[i]]);
			Point p2 = Calculations.worldToScreen(x + xPoints[indices2[i]], y + zPoints[indices2[i]],  - yPoints[indices2[i]]);
			Point p3 = Calculations.worldToScreen(x + xPoints[indices3[i]], y + zPoints[indices3[i]],  - yPoints[indices3[i]]);

			if (p1.x >= 0 && p2.x >= 0 && p2.x >= 0) {
				polygons.add(new Polygon(new int[]{p1.x, p2.x, p3.x}, new int[]{p1.y, p2.y, p3.y}, 3));
			}
		}
		return polygons.toArray(new Polygon[polygons.size()]);
	}
	
	public Point getRandomPoint(){
		Polygon[] triangles = getTriangles();
		for(int i = 0; i < 100; i++){
			Polygon p = triangles[Random.nextInt(0, triangles.length)];
			Point point = new Point(p.xpoints[Random.nextInt(0, p.xpoints.length)], p.ypoints[Random.nextInt(0, p.ypoints.length)]);
			if (Calculations.onScreen(point))
				return point;
		}
		return new Point(-1, -1);
	}
}
