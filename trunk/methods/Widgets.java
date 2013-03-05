package bot.script.methods;

import bot.script.wrappers.Component;
import bot.script.wrappers.Widget;
import nl.wbot.bot.Bot;

import java.util.ArrayList;

/**
 * @author Webjoch
 */
public class Widgets {
    public static Widget get(int id) {
        if (id >= 0 && id < Bot.get().getMainClass().getWidgets().length) {
            return new Widget(Bot.get().getMainClass().getWidgets()[id], id);
        }
        return null;
    }

    public static Widget[] getLoaded() {
        ArrayList<Widget> widgets = new ArrayList<Widget>();
        for (int i = 0; i < Bot.get().getMainClass().getWidgets().length; i++) {
            if (Bot.get().getMainClass().getWidgets()[i] != null) {
                widgets.add(new Widget(Bot.get().getMainClass().getWidgets()[i], i));
            }
        }
        return widgets.toArray(new Widget[widgets.size()]);
    }

    public static Component getComponent(int widgetId, int compId) {
        if (widgetId < 0 || widgetId > Bot.get().getMainClass().getWidgets().length) {
            return null;
        }
        nl.wbot.client.Widget[] widget = Bot.get().getMainClass().getWidgets()[widgetId];
        if (widget != null && widget.length > compId && widget[compId] != null) {
            return new Component(widget[compId], compId);
        }
        return null;
    }
}
