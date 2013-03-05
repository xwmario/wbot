package bot.script.enums;

import bot.script.methods.Widgets;
import bot.script.wrappers.Component;

import java.awt.Point;

/*
[Clan Chat]
[Friends List]
[Ignore List]
[Logout]
[Options]
[Emotes]
[Music Player]
[Combat Options]
[Stats]
[Quest List]
[Inventory]
[Worn Equipment]
[Prayer]
[Magic]
 */
public enum Tab {
    COMBAT("Combat Options"),
    SKILLS("Stats"),
    QUEST("Quest List"),
    INVENTORY("Inventory"),
    EQUIPMENT("Worn Equipment"),
    PRAYER("Prayer"),
    MAGIC("Magic"),
    CLAN_CHAT("Clan Chat"),
    FRIEND_LIST("Friends List"),
    IGNORE_LIST("Ignore List"),
    LOGOUT("Logout"),
    SETTINGS("Options"),
    EMOTES("Emotes"),
    MUSIC("Music Player");

    String name;

    Tab(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Point getPoint() {
        for (Component p : Widgets.get(548).getComponents()) {
            if (p.getActions() != null) {
                for (String s : p.getActions()) {
                    if (getName().equals(s)) {
                        return new Point(p.getX() + (p.getHeight() / 2), p.getY() + (p.getWidth() / 2));
                    }
                }
            }
        }
        return new Point(-1 , -1);
    }

}