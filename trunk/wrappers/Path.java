package bot.script.wrappers;

import java.awt.Graphics;
import java.util.ArrayList;

import bot.script.methods.Calculations;

public class Path {
	private Tile[] tiles;
	private ArrayList<Tile> walked = new ArrayList<Tile>(); 
	private boolean isGenerated = false;
	
	/**
	 * @param tiles - All tiles in the path
	 */
	public Path(Tile[] tiles){
		if (tiles.length > 5){
			int distance = 0;
			for (int i = 0; i < 5; i++){
				distance += Calculations.distanceBetween(tiles[0], tiles[i]);
			}
			isGenerated = distance < 8;
		}
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
		if (isGenerated){
			boolean next = false;
			int count = 0;
			for (Tile t : tiles){
				if (!next && t.distance() < 4){
					next = true;
				}
				if (next){
					count++;
					if (t.distance() > 13 || count > 15){
						return t;
					}
				}
			}
		}
		for (Tile t : tiles){
			if (!walked.contains(t)){
				if (t.distance() < 6){
					walked.add(t);
					continue;
				}
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
		}
	}
}
