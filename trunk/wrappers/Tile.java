package bot.script.wrappers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import nl.wbot.bot.Bot;
import nl.wbot.bot.accessors.CollisionMap;

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
	int x, y;
	
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
		CollisionMap[] info = Bot.get().getMainClass().getTileInfo();
		if (info == null || info[Game.getPlane()] == null)
			return false;
		int[][] data = info[Game.getPlane()].getTileData();
		try{
			return (data[getX()-Game.getRegion().getX()][getY()-Game.getRegion().getY()]) <= 128;
		}catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
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
}
