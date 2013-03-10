package bot.script.methods;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import nl.wbot.bot.Bot;

import bot.script.util.Random;

/**
 *
 * @author Roflgod
 *
 */
public class Keyboard extends Methods {

	public static void pressKey(final char ch, final int code, final int delay, final int mask) {
		final KeyEvent e = getKeyEvent(KeyEvent.KEY_PRESSED, delay, mask, code, ch);
		getKeyboard()._keyPressed(e);
		// Action keys do not send key typed events.
		// The character of an action key is VK_UNDEFINED ((char) 65535).
		if (ch != KeyEvent.VK_UNDEFINED) {
			getKeyboard()._keyTyped(e);
		}
	}

	/**
	 * Used for pressing action keys. Eg. left, right, up, down, home
	 *
	 * @param code Key code from {@link KeyEvent}.
	 * @param delay The time until the key is held down.
	 */
	public static void pressKey(final int code, final int delay) {
		pressKey(KeyEvent.CHAR_UNDEFINED, code, delay, 0);
	}

	public static void pressKey(final int code) {
		pressKey(KeyEvent.CHAR_UNDEFINED, code, 0, 0);
	}

	/**
	 * Used for pressing "typeable" keys. Eg. 'a', 'b', 'c', '6'.
	 *
	 * @param ch The char to press.
	 * @param delay The time until the key is held down.
	 * @param mask The mask to press this key with.
	 */
	public static void pressKey(final char ch, final int delay, final int mask) {
		pressKey(ch, ch, delay, mask);
	}

	public static void pressKey(final char ch, final int delay) {
		pressKey(ch, ch, delay, 0);
	}

	public static void pressKey(final char ch) {
		pressKey(ch, ch, 0, 0);
	}

	public static void releaseKey(final char ch, final int code, final int delay, final int mask) {
		final KeyEvent e = getKeyEvent(KeyEvent.KEY_RELEASED, delay, mask, code, ch);
		getKeyboard()._keyReleased(e);
	}

	/**
	 * Used for releasing action keys. Eg. left, right, up, down, home
	 *
	 * @param code Key code from {@link KeyEvent}.
	 * @param delay The time until the key is held down.
	 */
	public static void releaseKey(final int code, final int delay) {
		releaseKey(KeyEvent.CHAR_UNDEFINED, code, delay, 0);
	}

	public static void releaseKey(final int code) {
		releaseKey(KeyEvent.CHAR_UNDEFINED, code, 0, 0);
	}

	/**
	 * Used for pressing "typeable" keys. Eg. 'a', 'b', 'c', '6'.
	 *
	 * @param ch The char to press.
	 * @param delay The time until the key is held down.
	 * @param mask The mask to press this key with.
	 */
	public static void releaseKey(final char ch, final int delay, final int mask) {
		releaseKey(ch, ch, delay, mask);
	}

	public static void releaseKey(final char ch, final int delay) {
		releaseKey(ch, ch, delay, 0);
	}

	public static void releaseKey(final char ch) {
		releaseKey(ch, ch, 0, 0);
	}

	/**
	 * @param type
	 * @param delay
	 * @param mask
	 * @param code
	 * @param ch
	 * @return The {@link KeyEvent} associated with the given information.
	 */
	private static KeyEvent getKeyEvent(final int type, final int delay, final int mask, final int code, final char ch) {
		return new KeyEvent(Bot.get().getClient(), type, System.currentTimeMillis() + delay, mask, code, ch, getLocation(code, ch));
	}

	/**
	 * Returns the standard location of the character on the keyboard for masks.
	 *
	 * @param ch The character to determine the position of.
	 * @return The key location value.
	 */
	private static int getLocation(final int code, final char ch) {
		if (KeyEvent.VK_SHIFT <= code  && code <= KeyEvent.VK_ALT) {
			return Random.nextInt(KeyEvent.KEY_LOCATION_LEFT, KeyEvent.KEY_LOCATION_RIGHT + 1);
		}
		return KeyEvent.KEY_LOCATION_STANDARD;
	}

	/**
	 * Types a single character.
	 *
	 * @param ch The key to type.
	 */
	public static void sendKey(final char ch) {
		sendKey(ch, 0);
	}

	/**
	 * Presses and holds the given character for the delay, then releases.
	 *
	 * @param ch    The character to type.
	 * @param delay The time to hold the key for.
	 */
	public static void sendKey(char ch, final int delay) {
		int code = ch;
		if (ch >= 'a' && ch <= 'z') {
			code -= 32;
		}
		sendKey(ch, code, delay);
	}

	/**
	 * Presses and holds the given character for the delay, then releases.
	 *
	 * @param ch    The character to type.
	 * @param code  Key code for special characters
	 * @param delay The time to hold the key for.
	 */
	public static void sendKey(char ch, int code, final int delay) {
		final boolean shift = ch >= 'A' && ch <= 'Z' ? true : false;
		pressKey(ch, code, delay, shift ? InputEvent.SHIFT_DOWN_MASK: 0);
		sleep(50, 100);
		releaseKey(ch, code, delay, shift ? InputEvent.SHIFT_DOWN_MASK : 0);
	}

	/**
	 * Types an array of characters.
	 *
	 * @param text       The text to send.
	 * @param pressEnter <tt>true</tt> to press enter; otherwise <tt>false</tt>.
	 */
	public static void sendText(final String text, final boolean pressEnter) {
		sendText(text, pressEnter, 100, 200);
	}

	/**
	 * Types an array of characters with the given delay between keys.
	 *
	 * @param text       The text to enter into the game.
	 * @param pressEnter <tt>true</tt> to press enter; otherwise <tt>false</tt>.
	 * @param minDelay   The lowest possible wait (inclusive).
	 * @param maxDelay   The largest possible wait (exclusive).
	 */
	public static void sendText(final String text, final boolean pressEnter, final int minDelay, final int maxDelay) {
		final char[] chars = text.toCharArray();
		for (final char element : chars) {
			final int wait = Random.nextInt(minDelay, maxDelay);
			sendKey(element, wait);
			if (wait > 0) {
				sleep(wait);
			}
		}
		if (pressEnter) {
			sendKey((char) KeyEvent.VK_ENTER, Random.nextInt(minDelay, maxDelay));
		}
	}

	private static nl.wbot.bot.input.Keyboard getKeyboard() {
		return Bot.get().getKeyboard();
	}
}