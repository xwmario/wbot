package bot.script.wrappers;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

import bot.script.methods.Calculations;

/**
 *
 * @author Jewtage
 *
 */

public class Area {
	private final Polygon area;
	private final int plane;

	public Area(final Tile[] tiles) {
		this(tiles, 0);
	}

	public Area(final Tile[] tiles, final int plane) {
		area = tileArrayToPolygon(tiles);
		this.plane = plane;
	}

	public Area(final Tile sw, final Tile ne) {
		this(sw, ne, 0);
	}

	public Area(final Tile sw, final Tile ne, final int plane) {
		this(new Tile[]{sw, new Tile(ne.getX() + 1, sw.getY()),
				               new Tile(ne.getX() + 1, ne.getY() + 1),
				               new Tile(sw.getX(), ne.getY() + 1)}, plane);
	}

	public Area(final int swX, final int swY, final int neX, final int neY) {
		this(new Tile(swX, swY), new Tile(neX, neY), 0);
	}

	public boolean contains(final int x, final int y) {
		return this.contains(new Tile(x, y));
	}

	public boolean contains(final int plane, final Tile... tiles) {
		return this.plane == plane && this.contains(tiles);
	}

	public boolean contains(final Tile... tiles) {
		final Tile[] areaTiles = getTileArray();
		for (final Tile check : tiles) {
			for (final Tile space : areaTiles) {
				if (check.equals(space)) {
					return true;
				}
			}
		}
		return false;
	}

	public Rectangle getBounds() {
		return new Rectangle(area.getBounds().x + 1, area.getBounds().y + 1, getWidth(), getHeight());
	}

	public Tile getCentralTile() {
		if (area.npoints < 1) {
			return null;
		}
		int totalX = 0, totalY = 0;
		for (int i = 0; i < area.npoints; i++) {
			totalX += area.xpoints[i];
			totalY += area.ypoints[i];
		}
		return new Tile(Math.round(totalX / area.npoints), Math.round(totalY / area.npoints));
	}

	public Tile getNearestTile(final Tile base) {
		Tile currTile = null;
		for (final Tile tile : getTileArray()) {
			if (currTile == null || Calculations.distanceBetween(base, tile)
					                        < Calculations.distanceBetween(currTile, tile)) {
				currTile = tile;
			}
		}
		return currTile;
	}

	public int getPlane() {
		return plane;
	}
	public Tile[] getTileArray() {
		final ArrayList<Tile> list = new ArrayList<Tile>();
		for (int x = getX(); x <= getX() + getWidth(); x++) {
			for (int y = getY(); y <= getY() + getHeight(); y++) {
				if (area.contains(x, y)) {
					list.add(new Tile(x, y));
				}
			}
		}
		return list.toArray(new Tile[list.size()]);
	}

	public Tile[][] getTiles() {
		final Tile[][] tiles = new Tile[getWidth()][getHeight()];
		for (int i = 0; i < getWidth(); ++i) {
			for (int j = 0; j < getHeight(); ++j) {
				if (area.contains(getX() + i, getY() + j)) {
					tiles[i][j] = new Tile(getX() + i, getY() + j);
				}
			}
		}
		return tiles;
	}

	public int getWidth() {
		return area.getBounds().width;
	}

	public int getHeight() {
		return area.getBounds().height;
	}

	public int getX() {
		return area.getBounds().x;
	}

	public int getY() {
		return area.getBounds().y;
	}

	public Tile[][] getAreaTiles() {
		Tile[][] tileArray = new Tile[getWidth()][getHeight()];
		int i = 0;
		while (i < getHeight()) {
			int j = 0;
			while (j < getWidth()) {
				if (this.area.contains(getX() + i, getY() + j)) {
					tileArray[i][j] = new Tile(getX() + i, getY() + j);
				}
				j++;
			}
			i++;
		}
		return tileArray;
	}

	private Polygon tileArrayToPolygon(final Tile[] tiles) {
		final Polygon poly = new Polygon();
		for (final Tile t : tiles) {
			poly.addPoint(t.getX(), t.getY());
		}
		return poly;
	}
}