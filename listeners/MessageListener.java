package bot.script.listeners;

import bot.script.events.MessageEvent;

public interface MessageListener {
	abstract void messageReceived(MessageEvent e);
}
