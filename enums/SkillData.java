package bot.script.enums;

import java.util.concurrent.TimeUnit;

import bot.script.enums.Skill;
import bot.script.methods.Skills;

/**
 * SkillData
 *
 * @author Nex
 */
public enum SkillData {

    HITPOINTS(Skill.HITPOINTS),
    PRAYER(Skill.PRAYER),
    STRENGTH(Skill.STRENGTH),
    ATTACK(Skill.ATTACK),
    DEFENCE(Skill.DEFENCE),
    RANGED(Skill.RANGED),
    MAGIC(Skill.MAGIC),
    MINING(Skill.MINING),
    WOODCUTTING(Skill.WOODCUTTING),
    RUNECRAFT(Skill.RUNECRAFT),
    SLAYER(Skill.SLAYER),
    HERBLORE(Skill.HERBLORE),
    FLETCHING(Skill.FLETCHING),
    AGILITY(Skill.AGILITY),
    SMITHING(Skill.SMITHING),
    COOKING(Skill.COOKING),
    CRAFTING(Skill.CRAFTING),
    FARMING(Skill.FARMING),
    FIREMAKING(Skill.FIREMAKING),
    FISHING(Skill.FISHING),
    THIEVING(Skill.THIEVING);

    private final String name;
    private final Skill skill;
    private final int startXp;
    private final int startLevel;

    private SkillData(final Skill skill) {
        this.name = skill.toString().toLowerCase();
        this.skill = skill;
        startXp = Skills.getXp(skill);
        startLevel = Skills.getLevel(skill);
    }

    public String getName() {
        return name;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getStartXp() {
        return startXp;
    }

    public int getStartLevel() {
        return startLevel;
    }

    public String formatTime(long millis) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    public static final int getPerHour(final double value, final long time) {
        return (int) (value * 3600000D / (time));
    }

    public final int getXpGained() {
        return Skills.getXp(skill) - startXp;
    }

    public final int getLevelsGained() {
        return Skills.getLevel(skill) - startLevel;
    }

    public final int getXpPerHour(final long time) {
        return getPerHour(getXpGained(), time);
    }

    public final String getTTL(final long time) {
        final int level = Skills.getLevel(skill);
        if (level == 99) {
            return "00:00:00";
        }
        final int hour = getXpPerHour(time);
        return (hour > 0 ? formatTime(Skills.xpForNextLevel(skill) * 360 / hour * 10000) : "00:00:00");
    }

    public final String toString(final long time) {
        final int level = getLevelsGained();
        return "" + name + ": " + getXpGained() + "XP | " + getXpPerHour(time) + "/hr | levels: "
                + level + " | current: " + Skills.getLevel(skill) + " | TTL: " + getTTL(time) + "";
    }
}
