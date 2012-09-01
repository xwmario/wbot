package bot.script.methods;

import java.awt.*;
import java.util.Random;

import nl.wbot.bot.Bot;
import nl.wbot.bot.accessors.Client;

import bot.script.wrappers.Tile;

public class Calculations{
	Random random = new Random();
	
	private static final int[] CURVESIN = new int[2048];
	private static final int[] CURVECOS = new int[2048];

	static {
		for (int i = 0; i < 2048; i++) {
			CURVESIN[i] = (int) (65536.0 * Math.sin(i * 0.0030679615));
			CURVECOS[i] = (int) (65536.0 * Math.cos(i * 0.0030679615));
		}
	}
	
	public static int angleToTile(Tile t) {
		Tile me = Players.getLocal().getLocation();
		int angle = (int) Math.toDegrees(Math.atan2(t.getY() - me.getY(), t.getX() - me.getX()));
		return angle >= 0 ? angle : 360 + angle;
	}

	public static double distanceTo(Tile tile){
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
        return worldToScreen((x - Bot.get().getMainClass().getBaseX()) * 128, (y - Bot.get().getMainClass().getBaseY()) * 128, height);
    }
	
	public static int tileHeight(int x, int y) {
		final Client client = Bot.get().getMainClass();
        final int[][][] groundHeights = client.getGroundData();
        if(groundHeights == null)
            return 0;
        int x1 = x >> 7;
        int y1 = y >> 7;
        if(x1 < 0 || y1 < 0 || x1 > 103 || y1 > 103)
            return 0;
        int x2 = x & 0x7f;
        int y2 = y & 0x7f;
        int zIndex = client.getPlane();
        if(zIndex > 3 && (client.getGroundByteArray()[1][x1][y1] & 2) == 2)
            zIndex++;
        int i2 = (((-x2 + 128) * groundHeights[zIndex][x1][y1]) + (x2 * groundHeights[zIndex][x1 + 1][y1])) >> 7;
        int j2 = ((groundHeights[zIndex][x1][1 + y1] * (128 - x2)) + (groundHeights[zIndex][1 + x1][1 + y1] * x2)) >> 7;
        return ((i2 * (128 - y2)) - -(y2 * j2)) >> 7;
    }

	public static Point worldToScreen(double X, double Y, int height) {
		X += 5;
		Y -= 11;
		if (X < 128 || Y < 128 || X > 13056 || Y > 13056 || Bot.get().getMainClass().getGroundData() == null) {
			return new Point(-1,-1);
		}
		int tileCalculation = tileHeight((int) X, (int) Y) - height;
		X -= Bot.get().getMainClass().getXCameraPos();
		tileCalculation -= Bot.get().getMainClass().getZCameraPos();
		int curvexsin = CURVESIN[Bot.get().getMainClass().getXCameraCurve()];
		int curvexcos = CURVECOS[Bot.get().getMainClass().getXCameraCurve()];
		Y -= Bot.get().getMainClass().getYCameraPos();
		int curveysin = CURVESIN[Bot.get().getMainClass().getYCameraCurve()];
		int curveycos = CURVECOS[Bot.get().getMainClass().getYCameraCurve()];
		int calculation = curvexsin * (int) Y + ((int) X * curvexcos) >> 16;
		Y = -(curvexsin * (int) X) + (int) Y * curvexcos >> 16;
		X = calculation;
		calculation = curveycos * tileCalculation - curveysin * (int) Y >> 16;
		Y = curveysin * tileCalculation + ((int) Y * curveycos) >> 16;
		tileCalculation = calculation;
		if (Y < 50) return new Point(-1,-1);
		
		int ScreenX = ((int) X << 9) / (int) Y + 256;
		int ScreenY = (tileCalculation << 9) / (int) Y + 167;

		Point p = new Point(ScreenX, ScreenY);
		return p;
	}
	
	private static Point worldToMinimap(int regionX, int regionY){
	    int angle = Bot.get().getMainClass().getMinimapInt1() + Bot.get().getMainClass().getMinimapInt2() & 0x7FF;
	    int j = regionX * regionX + regionY * regionY;
	 
	    if (j > 6400)
	        return new Point(-1, -1);
	 
	    int sin = Calculations.CURVESIN[angle] * 256 / (Bot.get().getMainClass().getMinimapInt3() + 256);
	    int cos = Calculations.CURVECOS[angle] * 256 / (Bot.get().getMainClass().getMinimapInt3() + 256);
	 
	    int x = regionY * sin + regionX * cos >> 16;
	    int y = regionY * cos - regionX * sin >> 16;
	 
	    return new Point(644 + x, 80 - y);
	}
}
