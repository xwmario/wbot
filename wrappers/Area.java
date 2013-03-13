package bot.script.wrappers;

import java.awt.*;

import bot.script.wrappers.Tile;

/**
 * Do not use this without permission.
 * User: harrynoob
 * Date: 13-3-13
 * Time: 20:26
 */
public class Area {
 
    private final Polygon p = new Polygon();
    public Area() {
    }
 
    public Area(final int x1, final int x2, final int y1, final int y2) {
        p.addPoint(x1 < x2 ? x1 : x2, y1 < y2 ? y1 : y2);
        p.addPoint(x1 < x2 ? x2 : x1, y1 < y2 ? y2 : y1);
    }
 
    public Area(final Tile t1, final Tile t2) {
        this(t1.getX(), t2.getX(), t1.getY(), t2.getY());
    }
 
    public boolean contains(final Tile t) {
        return p.contains(t.getX(), t.getY());
    }
 
    public void addPoints(final Tile... t) {
        for(final Tile t1 : t) {
            p.addPoint(t1.getX(), t1.getY());
        }
    }
 
    public Dimension getDimension() {
        return p.getBounds().getSize();
    }
 
    public int getX() {
        return (int)p.getBounds().getX();
    }
 
    public int getY() {
        return (int)p.getBounds().getY();
    }
 
    public Tile getNearestTile(final Tile base) {
        Tile closest = null;
        double dist = 9999;
        for(int i = 0; i < p.npoints - 1; i++) {
            double nD = Math.sqrt(Math.pow(p.xpoints[i], 2) + Math.pow(p.ypoints[i], 2));
            if(nD < dist) {
                closest = new Tile(p.xpoints[i], p.ypoints[i]);
                dist = nD;
            }
        }
        return closest;
    }
 
}