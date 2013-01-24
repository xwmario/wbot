package bot.script.wrappers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import nl.wbot.bot.Bot;

import bot.script.methods.Calculations;
import bot.script.methods.Game;
import bot.script.methods.Menu;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;

/**
 * 
 * @author Webjoch
 *
 */
public class Tile {
	private static int[][] data;
	private static Tile region;
	
	private int x;
	private int y;
	
	public Tile(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Point toScreen(){
		return Calculations.tileToScreen(x, y, 1);
	}
	
	public boolean isVisible(){
		Point p = toScreen();
		return p.x > 0 && p.y > 0 && p.x < 516 && p.y < 340;
	}
	
	public boolean interact(String action){
		Mouse.move(toScreen());
		return Menu.interact(action);
	}
	
	public boolean click(){
		return interact("Walk here");
	}
	
	public void clickMinimap(){
		Point p = Calculations.tileToMinimap(this);
		Mouse.move(p);
		Methods.sleep(50);
		Mouse.click(true);
	}
	
	public double distance(){
		return Calculations.distanceTo(this);
	}
	

	public boolean onMinimap(){
		return Calculations.distanceTo(this) < 17;
	}
	
	public boolean isWalkable(){
		update();
		if (data == null || data.length != 104)
			return false;
		int value = data[getX()-region.getX()][getY()-region.getY()];
		return (value & 0x1280180) == 0 ^ (value & 0x1280180) == 128;
	}
	
	public int getValue(){
		update();
		if (data.length != 104)
			return -1;
		int value = data[getX()-region.getX()][getY()-region.getY()];
		return value;
	}
	
	public String toString(){
		return "[Tile: x: "+x+" y:"+y+"]";
	}
	
	public Polygon getPolygon(){
		Polygon p = new Polygon();
		Tile[] tiles = new Tile[]{new Tile(getX(), getY() + 1), this, new Tile(getX() + 1, getY()), new Tile(getX() + 1, getY() + 1)};
		for(Tile t : tiles){
			p.addPoint(t.toScreen().x, t.toScreen().y);
		}
		return p;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.black);
		g.drawPolygon(getPolygon());
		g.setColor(new Color(255, 0, 0, 50));
		g.fillPolygon(getPolygon());
	}
	
	public void draw(Graphics g, Color c, boolean fill){
		g.setColor(c);
		if (fill)
			g.fillPolygon(getPolygon());
		else
			g.drawPolygon(getPolygon());
	}
	
	public boolean equals(Tile t){
		if (t == null)
			return false;
		return t.getX() == x && t.getY() == y;
	}
	
	private static void update(){
		if (Bot.get().getMainClass() == null || Bot.get().getMainClass().getTileInfo() == null || Game.getRegion() == null)
			return;
		if (data == null || region == null || !region.equals(Game.getRegion())){
			data = Bot.get().getMainClass().getTileInfo()[Game.getPlane()].getTileData();
			region = Game.getRegion();
		}
	}
}
