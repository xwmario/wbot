package bot.script.wrappers;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import bot.script.methods.Keyboard;
import bot.script.methods.Calculations;
import bot.script.methods.Game;
import bot.script.methods.Methods;
import bot.script.methods.Walking;
import bot.script.util.Random;
import bot.script.wrappers.Tile;

public class TilePath {
	private final Tile[] tiles;
	private TilePath reversed;

	public TilePath(final Tile[] tiles) {
		this.tiles = tiles;
	}

	public Tile getStart() {
		return tiles[0];
	}

	public Tile getEnd() {
		return tiles[tiles.length - 1];
	}

	/**
	 * Loops through the path to completely traverse it.
	 */
	public void traverseCompletely() {
		traverseCompletely(true);
	}

	public void traverseCompletely(final boolean run) {
		Tile next;
		while ((next = next()) != null && !isAtEnd(next)) {
			traverse(next, run);
		}
	}

	/**
	 * Traverses the path. This method must be looped.
	 */
	public void traverse() {
		traverse(true);
	}

	public void traverse(final boolean run) {
		final Tile next = next();
		traverse(next, run);
	}

	/**
	 * Walks one step in the path.
	 *
	 * @param next
	 *            The next <code>Tile</code>
	 */
	private void traverse(final Tile next, final boolean run) {
		if (Game.getGameState() == 30
				&& next != null && !isAtEnd(next)
				&& Calculations.distanceBetween(next, Walking.getDestination()) > 3) {
			if (run) {
				Keyboard.pressKey(KeyEvent.VK_CONTROL);
				Methods.sleep(80, 150);
			}
			Walking.walkTo(next);
			if (run) {
				Keyboard.releaseKey(KeyEvent.VK_CONTROL,
						Random.nextInt(80, 150));
				Methods.sleep(1000, 2000);
			}
		}
	}

	/**
	 * @param next
	 *            The next <code>Tile</code>
	 * @return Whether the given <code>Tile</code> is at the end of the path.
	 */
	private boolean isAtEnd(Tile next) {
		return Calculations.distanceTo(next) < 3
				|| Calculations.distanceTo(Walking.getDestination()) < 3;
	}

	/**
	 * @return The next walkable <code>Tile</code> on the minimap.
	 */
	private Tile next() {
		for (int i = tiles.length - 1; i >= 0; --i) {
			if (tiles[i].onMinimap() && tiles[i].isWalkable()) {
				return tiles[i];
			}
		}
		return null;
	}

	/**
	 * Lazily reverses this <code>TilePath</code>.
	 *
	 * @return The reversed <code>TilePath</code>
	 */
	public TilePath reverse() {
		if (reversed == null) {
			Tile[] reversedTiles = new Tile[tiles.length];
			for (int i = tiles.length - 1; i >= 0; i--) {
				reversedTiles[tiles.length - 1 - i] = tiles[i];
			}
			reversed = new TilePath(reversedTiles);
		}
		return reversed;
	}

	public void draw(Graphics g) {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].draw(g);
		}
	}

	@Override
	public String toString() {
		return Arrays.toString(tiles);
	}

}