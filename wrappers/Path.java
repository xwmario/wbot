package bot.script.wrappers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import bot.script.methods.Calculations;

public class Path {
	private Tile[] tiles;
	
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
		int closest = -1;
		for (int i = 0; i < tiles.length; i++){
			if (closest == -1 || tiles[i].distance() < tiles[closest].distance()){
				closest = i;
			}
		}
		int steps = (int) (16 - tiles[closest].distance());
		return tiles[closest+steps >= tiles.length ? tiles.length - 1 : closest+steps];
	}
	
	/**
	 * Gets the last tile in the path
	 * @return atile
	 */
	public Tile getEnd(){
		return tiles[tiles.length-1];
	}
	
	/**
	 * Reset all checkpoints in the path. Isn't needed anymore
	 */
	@Deprecated
	public void reset(){
		
	}
	
	/**
	 * Traverse the path
	 */
	public void traverse(){
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
