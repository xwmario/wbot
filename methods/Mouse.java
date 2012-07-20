package bot.script.methods;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Random;

import bot.Bot;

public class Mouse extends Methods{
	private static byte dragLength = 0;
	private static Random random = new Random();
	static int side = random.nextInt(5) + 1;
	
	public static boolean isOnCanvas(final int x, final int y) {
		return x > 0 && x < Bot.getApplet().getWidth() && y > 0 && y < Bot.getApplet().getHeight();
	}
	
	public static int getX(){
		return Bot.getApplet().getMouseX();
	}
	
	public static int getY(){
		return Bot.getApplet().getMouseY();
	}
	
	public static void click(boolean left) {
		Bot.getApplet().pressed = true;
		pressMouse(getX(), getY(), left);
		sleep(150);
		Bot.getApplet().pressed = false;
		releaseMouse(getX(), getY(), left);
	}
	
	public static void hop(final int x, final int y) {
		final MouseEvent me = new MouseEvent(Bot.getApplet(), MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, 0, false);
		Bot.getApplet().sendEvent(me);
	}
	
	public static void move(int x, int y){
		hop(x, y);
	}
	
	public static void move(Point p){
		hop(p.x, p.y);
	}
	
	private static void pressMouse(final int x, final int y, final boolean left) {
		MouseEvent me = new MouseEvent(Bot.getApplet(), MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, x, y, 1, false, left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
		Bot.getApplet().sendEvent(me);
	}
	
	private static void releaseMouse(final int x, final int y, final boolean leftClick) {
		MouseEvent me = new MouseEvent(Bot.getApplet(), MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0, x, y, 1, false, leftClick ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
		Bot.getApplet().sendEvent(me);

		if ((dragLength & 0xFF) <= 3) {
			me = new MouseEvent(Bot.getApplet(), MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, x, y, 1, false, leftClick ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
			Bot.getApplet().sendEvent(me);
		}
		dragLength = 0;
	}
}
