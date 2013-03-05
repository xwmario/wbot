package bot.script.methods;

import java.awt.event.KeyEvent;

import nl.wbot.bot.Bot;

import bot.script.util.Random;
import bot.script.wrappers.Tile;

public class Camera extends Methods{
	public static int getAngle(){
		return (int) (Bot.get().getMainClass().getCameraCurveX() / 5.69);
	}
	
	public static int getPitch() {
		return (int) (((double) Bot.get().getMainClass().getCameraCurveY() / 390) * 100);
	}
	
	public static void turnToTile(Tile tile) {
		int angle = getTileAngle(tile);
		setAngle(angle);
	}
	
	
	public static void setAngle(int degrees) {
		if (getAngleTo(degrees) > 5) {
			Keyboard.pressKey((char) KeyEvent.VK_LEFT);
			while (getAngleTo(degrees) > 5) {
				sleep(10);
			}
			Keyboard.releaseKey((char) KeyEvent.VK_LEFT);
		} else if (getAngleTo(degrees) < -5) {
			Keyboard.pressKey((char) KeyEvent.VK_RIGHT);
			while (getAngleTo(degrees) < -5) {
				sleep(10);
			}
			Keyboard.releaseKey((char) KeyEvent.VK_RIGHT);
		}
	}
	
	public static void setCompass(char direction) {
		switch (direction) {
			case 'n':
				setAngle(359);
				break;
			case 'w':
				setAngle(89);
				break;
			case 's':
				setAngle(179);
				break;
			case 'e':
				setAngle(269);
				break;
			default:
				setAngle(359);
				break;
		}
	}
	
	public static boolean setPitch(int percent) {
		int curAlt = getPitch();
		int lastAlt = 0;
		if (curAlt == percent)
			return true;
		else if (curAlt < percent) {
			Keyboard.pressKey((char) KeyEvent.VK_UP);
			long start = System.currentTimeMillis();
			while (curAlt < percent && System.currentTimeMillis() - start < Random.nextInt(50, 100)) {
				if (lastAlt != curAlt) {
					start = System.currentTimeMillis();
				}
				lastAlt = curAlt;

				sleep(Random.nextInt(5, 10));
				curAlt = getPitch();
			}
			Keyboard.releaseKey((char) KeyEvent.VK_UP);
			return true;
		} else {
			Keyboard.pressKey((char) KeyEvent.VK_DOWN);
			long start = System.currentTimeMillis();
			while (curAlt > percent && System.currentTimeMillis() - start < Random.nextInt(50, 100)) {
				if (lastAlt != curAlt) {
					start = System.currentTimeMillis();
				}
				lastAlt = curAlt;
				sleep(Random.nextInt(5, 10));
				curAlt = getPitch();
			}
			Keyboard.releaseKey((char) KeyEvent.VK_DOWN);
			return true;
		}
	}

	public static int getAngleTo(int degrees) {
		int ca = getAngle();
		if (ca < degrees) {
			ca += 360;
		}
		int da = ca - degrees;
		if (da > 180) {
			da -= 360;
		}
		return da;
	}
	
	public static int getTileAngle(Tile t) {
		int a = (Calculations.angleToTile(t) - 90) % 360;
		return a < 0 ? a + 360 : a;
	}
}
