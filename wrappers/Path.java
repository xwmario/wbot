package bot.script.wrappers;

import java.util.ArrayList;

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
}
