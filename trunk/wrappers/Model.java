package bot.script.wrappers;

import java.awt.Polygon;

/**
 * 
 * @author Webjoch
 *
 */
public class Model {
	bot.accessors.Model accessor;
	
	int[] xPoints;
	int[] yPoints;
	
	public Model(bot.accessors.Model accessor){
		this.accessor = accessor;
	}
	
	public Polygon getTriangles() {
		Polygon polygon = new Polygon();
		
		if (xPoints == null){
			System.out.println("Test");
			xPoints = accessor.getXPoints();
			yPoints = accessor.getYPoints();
		}
		
		for (int i = 0; i < xPoints.length; i++){
			polygon.addPoint(xPoints[i] + 100, yPoints[i] + 100);
		}
		
		return polygon;
	}
}
