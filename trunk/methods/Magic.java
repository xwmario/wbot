package bot.script.methods;

import bot.script.enums.Spell;

/**
 * Author iJava
 */
public class Magic {

    public static boolean castSpell(Spell spell) {
        if (bot.script.methods.Game.getTab() != bot.script.enums.Tab.MAGIC) {
            bot.script.methods.Game.openTab(bot.script.enums.Tab.MAGIC);
        }
        if (spell.getComponent() != null) {
            return spell.getComponent().interact("Cast");
        }
        return false;
    }

}
