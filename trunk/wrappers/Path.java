package bot.script.wrappers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import bot.script.methods.Calculations;

public class Path {
	private Tile[] tiles;
	private ArrayList<Tile> walked = new ArrayList<Tile>(); 
	
	/**
	 * @param tiles - All tiles in the path
	 */
	public Path(Tile[] tiles){
		this.tiles = tiles;
	}
	
	/**
	 * Returns all tiles in the path
	 * @return an array of tiles
	 */
	public Tile[] getTileArray(){
		return tiles;
	}
	
	/**
	 * Checks if the path is a valid path
	 * @return true if it's a valid path - otherwise false
	 */
	public boolean isValid(){
		return tiles.length > 0 && getNext() != null;
	}
	
	/**
	 * Gets the first tile in the path
	 * @return a tile
	 */
	public Tile getStart(){
		return tiles[0];
	}
	
	/**
	 * Gets the next tile in the path
	 * @return a tile
	 */
	public Tile getNext(){
		if (getEnd().distance() < 12)
			return getEnd();
		for (Tile t : tiles){
			if (!walked.contains(t)){
				if (t.distance() < 12){
					walked.add(t);
					continue;
				}
				if (t.distance() > 15)
					walked.clear();
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Gets the last tile in the path
	 * @return atile
	 */
	public Tile getEnd(){
		return tiles[tiles.length-1];
	}
	
	/**
	 * Reset all checkpoints in the path. Sould be used when the character start again the path.
	 */
	public void reset(){
		walked.clear();
	}
	
	/**
	 * Traverse the path
	 */
	public void traverse(){
		reset();
		Tile[] newPath = new Tile[tiles.length];
		for (int i = 0; i < tiles.length; i++){
			newPath[i] = tiles[tiles.length-i-1];
		}
		tiles = newPath;
	}
	
	public void paint(Graphics g){
		for (Tile t : tiles){
			if (t.isVisible())
				t.draw(g);
			g.setColor(Color.black);
			Point p = Calculations.tileToMinimap(t);
			g.fillRect(p.x-1, p.y-1, 2, 2);
		}
	}
}
