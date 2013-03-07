package bot.script.wrappers;

import java.util.ArrayList;

import bot.script.methods.Methods;
import bot.script.methods.Widgets;

/**
 * @author Webjoch
 */
public class Widget extends Methods {
    private nl.wbot.client.Widget[] accessor;
    private int index;

    public Widget(nl.wbot.client.Widget[] accessor, int index) {
        this.accessor = accessor;
        this.index = index;
    }

    public Component[] getComponents() {
        ArrayList<Component> childs = new ArrayList<Component>();
        int i = 0;
        for (nl.wbot.client.Widget child : accessor) {
                childs.add(new Component(child, i++));

        }
        return childs.toArray(new Component[childs.size()]);
    }

    public Component getChild(int id) {
        return new Component(accessor[id], id);
    }

    public int getIndex() {
        return index;
    }

    public boolean isValid(Widget w) {
        for (Widget i : Widgets.getLoaded()) {
            if (w.getIndex() == i.getIndex())
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Widget " + getIndex();
    }
}
