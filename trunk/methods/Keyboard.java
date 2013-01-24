package bot.script.methods;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import nl.wbot.bot.Bot;

import bot.script.util.Random;

public class Keyboard extends Methods{
	public static void typeText(String text, boolean enter){
		sendKeys(text, enter, 100, 200);
	}
	
	public static void pressKey(final char ch) {
		KeyEvent ke;
		ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, ch, getKeyChar(ch));
		Bot.get().getGameClient()._keyPressed(ke);
	}
	
	public static void sendKey(final char ch, final int delay) {
		boolean shift = false;
		int code = ch;
		if ((ch >= 'a') && (ch <= 'z')) {
			code -= 32;
		} else if ((ch >= 'A') && (ch <= 'Z')) {
			shift = true;
		}
		KeyEvent ke;
		if ((code == KeyEvent.VK_LEFT) || (code == KeyEvent.VK_UP) || (code == KeyEvent.VK_DOWN)) {
			ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_PRESSED, System.currentTimeMillis() + delay, 0, code, getKeyChar(ch), KeyEvent.KEY_LOCATION_STANDARD);
			Bot.get().getGameClient()._keyPressed(ke);
			final int delay2 = Random.nextInt(50, 120) + Random.nextInt(0, 100);
			ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_RELEASED, System.currentTimeMillis() + delay2, 0, code, getKeyChar(ch), KeyEvent.KEY_LOCATION_STANDARD);
			Bot.get().getGameClient()._keyReleased(ke);
		} else {
			if (!shift) {
				ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_PRESSED, System.currentTimeMillis() + delay, 0, code, getKeyChar(ch), KeyEvent.KEY_LOCATION_STANDARD);
				Bot.get().getGameClient()._keyPressed(ke);
				// Event Typed
				ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_TYPED, System.currentTimeMillis() + 0, 0, 0, ch, 0);
				Bot.get().getGameClient()._keyTyped(ke);
				// Event Released
				final int delay2 = Random.nextInt(50, 120) + Random.nextInt(0, 100);
				ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_RELEASED, System.currentTimeMillis() + delay2, 0, code, getKeyChar(ch), KeyEvent.KEY_LOCATION_STANDARD);
				Bot.get().getGameClient()._keyReleased(ke);
			} else {
				// Event Pressed for shift key
				final int s1 = Random.nextInt(25, 60) + Random.nextInt(0, 50);
				ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_PRESSED, System.currentTimeMillis() + s1, InputEvent.SHIFT_DOWN_MASK, KeyEvent.VK_SHIFT, (char) KeyEvent.VK_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT);
				Bot.get().getGameClient()._keyPressed(ke);

				// Event Pressed for char to send
				ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_PRESSED, System.currentTimeMillis() + delay, InputEvent.SHIFT_DOWN_MASK, code, getKeyChar(ch), KeyEvent.KEY_LOCATION_STANDARD);
				Bot.get().getGameClient()._keyPressed(ke);
				// Event Typed for char to send
				ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_TYPED, System.currentTimeMillis() + 0, InputEvent.SHIFT_DOWN_MASK, 0, ch, 0);
				Bot.get().getGameClient()._keyTyped(ke);
				// Event Released for char to send
				final int delay2 = Random.nextInt(50, 120) + Random.nextInt(0, 100);
				ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_RELEASED, System.currentTimeMillis() + delay2, InputEvent.SHIFT_DOWN_MASK, code, getKeyChar(ch), KeyEvent.KEY_LOCATION_STANDARD);
				Bot.get().getGameClient()._keyReleased(ke);

				// Event Released for shift key
				final int s2 = Random.nextInt(25, 60) + Random.nextInt(0, 50);
				ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_RELEASED, System.currentTimeMillis() + s2, InputEvent.SHIFT_DOWN_MASK, KeyEvent.VK_SHIFT, (char) KeyEvent.VK_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT);
				Bot.get().getGameClient()._keyReleased(ke);
			}
		}
	}
	private static void sendKeys(final String text, final boolean pressEnter, final int minDelay, final int maxDelay) {
		final char[] chs = text.toCharArray();
		for (final char element : chs) {
			sendKey(element, Random.nextInt(minDelay, maxDelay));
			sleep(Random.nextInt(minDelay, maxDelay));
		}
		if (pressEnter) {
			sendKey((char) KeyEvent.VK_ENTER, Random.nextInt(minDelay, maxDelay));
		}
	}
	
	public static void releaseKey(char ch) {
		KeyEvent ke;
		ke = new KeyEvent(Bot.get().getGameClient(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), InputEvent.ALT_DOWN_MASK, ch, getKeyChar(ch));
		Bot.get().getGameClient()._keyReleased(ke);
	}
	
	private static char getKeyChar(final char c){
		if ((c >= 36) && (c <= 40))
			return KeyEvent.VK_UNDEFINED;
		else
			return c;
	}
}
