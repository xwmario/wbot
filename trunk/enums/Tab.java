package bot.script.enums;

import java.awt.Point;

public enum Tab{
	COMBAT(0, new Point(560, 187)),
	SKILLS(1, new Point(585, 187)),
	QUEST(2, new Point(612, 187)),
	INVENTORY(3, new Point(650, 187)),
	EQUIPMENT(4, new Point(684, 187)),
	PRAYER(5, new Point(710, 187)),
	SPELLBOOK(6, new Point(740, 187)),
	FRIENDLIST(8, new Point(585, 483)),
	IGNORELIST(9, new Point(612, 483)),
	LOGOUT(10, new Point(648, 483)),
	SETTINGS(11, new Point(683, 483)),
	CONTROLS(12, new Point(712, 483)),
	MUSIC(13, new Point(737, 483));
	
	int id;
	Point point;
	Tab(int id, Point point){
		this.id = id;
		this.point = point;
	}
	public int getId(){
		return id;
	}
	public Point getPoint(){
		return point;
	}
}