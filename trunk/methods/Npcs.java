package bot.script.methods;

import bot.script.util.Filter;
import bot.script.wrappers.NPC;
import nl.wbot.bot.Bot;
import nl.wbot.client.Npc;

import java.util.ArrayList;

/**
 * @author Webjoch
 */
public class Npcs extends Methods {

    public static NPC[] getLoaded() {
        ArrayList<NPC> npcs = new ArrayList<NPC>();
        for (Npc n : Bot.get().getMainClass().getNpcArray()) {
            if (n == null) continue;
            npcs.add(new NPC(n));
        }
        return npcs.toArray(new NPC[npcs.size()]);
    }

    public static NPC getNearest(int id) {
        NPC nearest = null;
        double distance = 999999999;
        for (NPC npc : getLoaded()) {
            if (npc.getId() == id && npc.distance() < distance) {
                nearest = npc;
                distance = npc.distance();
            }
        }
        return nearest;
    }

    public static NPC getNearest(int[] ids) {
        NPC nearest = null;
        double distance = 999999999;
        for (NPC npc : getLoaded()) {
            if (inArray(ids, npc.getId()) && npc.distance() < distance) {
                nearest = npc;
                distance = npc.distance();
            }
        }
        return nearest;
    }

    public static NPC getNearest(String name) {
        NPC nearest;
        double dist = 999999;
        for (NPC npc : getLoaded()) {
            if (npc.getDef().getName().equals(name) && npc.distance() < dist) {
                nearest = npc;
                dist = npc.distance();
            }
        }
        return nearest;
    }

    public static NPC getNearest(Filter<NPC> filter) {
        NPC cur = null;
        double dist = -1;
        for (NPC npc : getLoaded()) {
            if (filter.accept(npc)) {
                double distTmp = npc.distance();
                if (cur == null) {
                    dist = distTmp;
                    cur = npc;
                } else if (distTmp < dist) {
                    cur = npc;
                    dist = distTmp;
                }
                break;
            }
        }
        return cur;
    }
}
