package bot.script.enums;

import java.awt.Point;

public enum InterfacePosition {
	GAME(new Point(4, 4)),
	CHAT(new Point(17, 357)),
	TAB(new Point(556, 208));
	
	public static final int[] TAB_POSITIONS = {147, 328, 3213, 2449, 5063};
	public static final int[] CHAT_POSITIONS = {139, 306, 310, 315, 321, 356, 359, 2459, 4882, 4887};
	
	private Point point;
	
	InterfacePosition(Point point){
		this.point = point;
	}
	
	public Point getPoint(){
		return point;
	}
}