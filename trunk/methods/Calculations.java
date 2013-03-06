package bot.script.methods;

import java.awt.*;
import java.util.Random;

import nl.wbot.bot.Bot;

import bot.script.wrappers.Tile;
import nl.wbot.client.Client;

public class Calculations{
	Random random = new Random();
	
	public static final int[] SIN_TABLE = new int[2048];
	public static final int[] COS_TABLE = new int[2048];

	static {
		for (int i = 0; i < 2048; i++) {
            SIN_TABLE[i]
                    = (int) (Math.sin((double) i * 0.0030679615) * 65536.0);
            COS_TABLE[i]
                    = (int) (Math.cos((double) i * 0.0030679615) * 65536.0);
		}
	}
	
	public static boolean onScreen(Point p){
		return p.x > 10 && p.y > 10 && p.x < 506 && p.y < 330;
	}
	
	public static int angleToTile(Tile t) {
		Tile me = Players.getLocal().getLocation();
		int angle = (int) Math.toDegrees(Math.atan2(t.getY() - me.getY(), t.getX() - me.getX()));
		return angle >= 0 ? angle : 360 + angle;
	}

	public static double distanceTo(Tile tile){
		if (Players.getLocal() == null)
			return 0;
		return distanceBetween(Players.getLocal().getLocation(), tile);
	}
	
	public static double distanceBetween(Tile curr, Tile dest) {
		return Math.sqrt((curr.getX() - dest.getX()) * (curr.getX() - dest.getX()) + (curr.getY() - dest.getY()) * (curr.getY() - dest.getY()));
	}
	
	public static Point tileToMinimap(Tile tile) {
		int x = tile.getX() - Bot.get().getMainClass().getBaseX();
		int y = tile.getY() - Bot.get().getMainClass().getBaseY();
		return worldToMinimap((x * 4 + 2) - Players.getLocal().getRealX() / 32, (y * 4 + 2) - Players.getLocal().getRealY() / 32);
	}
	
	public static Point tileToScreen(int x, int y, int height) {
        x = (x - Bot.get().getMainClass().getBaseX()) * 128;
        y = (y - Bot.get().getMainClass().getBaseY()) * 128;
        return worldToScreen(x, y, tileHeight(x, y) - height);
    }
	
	public static int tileHeight(int x, int y) {
		final Client client = Bot.get().getMainClass();
        final int[][][] groundHeights = client.getTileHeights();
        if(groundHeights == null)
            return 0;
        int x1 = x >> 7;
        int y1 = y >> 7;
        if(x1 < 0 || y1 < 0 || x1 > 103 || y1 > 103)
            return 0;
        int x2 = x & 0x7f;
        int y2 = y & 0x7f;
        int zIndex = client.getPlane();
        if(zIndex < 3 && (client.getTileSettings()[1][x1][y1] & 2) == 2)
            zIndex++;
        int i2 = (((-x2 + 128) * groundHeights[zIndex][x1][y1]) + (x2 * groundHeights[zIndex][x1 + 1][y1])) >> 7;
        int j2 = ((groundHeights[zIndex][x1][1 + y1] * (128 - x2)) + (groundHeights[zIndex][1 + x1][1 + y1] * x2)) >> 7;
        return ((i2 * (128 - y2)) - -(y2 * j2)) >> 7;
    }



    public static  Point worldToScreen(int x, int y, int height){
        x -= Bot.get().getMainClass().getCameraY();
        height -= Bot.get().getMainClass().getCameraZ();
        y -= Bot.get().getMainClass().getCameraX();
        int var5 = SIN_TABLE[Bot.get().getMainClass().getCameraCurveY()];
        int var6 = COS_TABLE[Bot.get().getMainClass().getCameraCurveY()];
        int var7 = SIN_TABLE[Bot.get().getMainClass().getCameraCurveX()];
        int var8 = COS_TABLE[Bot.get().getMainClass().getCameraCurveX()];
        int var9 = var7 * y + var8 * x >> 16;
        y = y * var8 - var7 * x >> 16;
        x = var9;
        var9 = var6 * height - y * var5 >> 16;
        y = y * var6 + var5 * height >> 16;
        if(y == 0)
            return new Point(-1,-1);
        int ScreenX = ((int) x << 9) / (int) y + 256;
        int ScreenY = (var9 << 9) / (int) y + 167;

        Point p = new Point(ScreenX, ScreenY);
        return p;
    }

	private static Point worldToMinimap(int regionX, int regionY){
	    int angle = Bot.get().getMainClass().getViewRotation() + Bot.get().getMainClass().getMinimapRotation() & 0x7FF;
	    int j = regionX * regionX + regionY * regionY;
	 
	    if (j > 6400)
	        return new Point(-1, -1);
	 
	    int sin = Calculations.SIN_TABLE[angle] * 256 / (Bot.get().getMainClass().getMinimapZoom() + 256);
	    int cos = Calculations.COS_TABLE[angle] * 256 / (Bot.get().getMainClass().getMinimapZoom() + 256);
	 
	    int x = regionY * sin + regionX * cos >> 16;
	    int y = regionY * cos - regionX * sin >> 16;
	 
	    return new Point(644 + x, 80 - y);
	}
}
