package bot.script.methods;

import nl.wbot.bot.Bot;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Menu extends Methods {

    private static final Pattern htmlTags = Pattern.compile("\\<.+?\\>");
    private static HashMap<Bot, List<String>> actionCache = new HashMap<Bot, List<String>>();

    public static boolean isOpen() {
        return Bot.get().getMainClass().isMenuOpen();
    }

    /**
     * Gets the left click menu position. NOTE: Works not in the chat frame
     *
     * @return a point from the menu location
     */
    public static Point getPosition() {
        int x = Bot.get().getMainClass().getMenuX();
        int y = Bot.get().getMainClass().getMenuY();
        if (Mouse.getX() > 546 && x < 100) {
            x += 546;
            y += 201;
        }
        return new Point(x, y);
    }

    public static int getActionIndex(String action) {
        int i = 0;
        for (String anAction : getActions()) {
            if (anAction.toLowerCase().contains(action.toLowerCase())) {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    public static int getActionIndex(String action, String option) {
      int i = 0;
      for (String anAction : getActions()) {
          if (anAction.toLowerCase().contains(action.toLowerCase()) && anAction.toLowerCase().contains(option.toLowerCase())) {
              return i;
          }
          i++;
      }
      return -1;
    }

    public static boolean contains(String action) {
        return getActionIndex(action) >= 0;
    }

    public static String getUpText() {
        if (!actionCache.containsKey(Bot.get())) {
            return "";
        }
        return actionCache.get(Bot.get()).toArray()[0];
    }

    public static String[] getActions() {
        if (!actionCache.containsKey(Bot.get())) {
            return new String[0];
        }
        return actionCache.get(Bot.get()).toArray(new String[0]);
    }

    public static void update(Bot bot) {
        ArrayList<String> actions = new ArrayList<String>();
        for (int i = Bot.get().getMainClass().getMenuOptionsCount() - 1; i >= 0; i--) {
            String action = htmlTags.matcher(Bot.get().getMainClass().getMenuActions()[i] + " " + Bot.get().getMainClass().getMenuOptions()[i]).replaceAll("");
            if (action.endsWith(" ")) {
                action = action.substring(0, action.length() - 1);
            }
            actions.add(action);
        }
        if (actions.size() > 0) {
            actionCache.put(Bot.get(), actions);
        }
    }

    public static boolean interact(String action) {
        int index = getActionIndex(action);
        if (index < 0) return false;
        if (index == 0) {
            Mouse.click(true);
            return true;
        }
        Mouse.click(false);
        for (int i = 0; i < 100 && !isOpen(); i++) sleep(5);
        if (isOpen()) {
            sleep(100);
            int x = getPosition().x + 30;
            int y = getPosition().y + 29 + index * 15;
            Mouse.move(x, y);
            sleep(100);
            Mouse.click(true);
            return true;
        }
        return false;
    }
    
    public static boolean interact(String action, String option) {
      int index = getActionIndex(action, option);
      if (index < 0) return false;
      Mouse.click(false);
      for (int i = 0; i < 100 && !isOpen(); i++) sleep(5);
      if (isOpen()) {
          sleep(100);
          int x = getPosition().x + 30;
          int y = getPosition().y + 29 + index * 15;
          Mouse.move(x, y);
          sleep(100);
          Mouse.click(true);
          return true;
      }
      return false;
    }
}
