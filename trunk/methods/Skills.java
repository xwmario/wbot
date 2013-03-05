package bot.script.methods;

import nl.wbot.bot.Bot;
import bot.script.enums.Skill;
/**
 * 
 * @author Webjoch
 *
 */
public class Skills{
	/**
	 * Get the xp of the given skill index
	 * @param skill The skill index (0-25)
	 * @return the xp
	 */
	public static int getXp(int skill){
		return Bot.get().getMainClass().getSkillExperiences()[skill];
	}
	
	public static int getXp(Skill skill){
		return getXp(skill.getIndex());
	}
	
	public static int getTotalXp(){
		int xp = 0;
		for (int i = 0; i < 21; i++){
			xp += getXp(i);
		}
		return xp;
	}
	
	public static int getLevel(Skill skill){
		int xp = getXp(skill);
		for(int i = 1; i < 100; i++){
			if (xpAtLevel(i) > xp){
				return i == 1 ? 1 : i - 1;
			}
		}
		return 1;
	}
	
	public static int xpForNextLevel(Skill skill){
		int nextLvl = getLevel(skill) + 1;
		if (nextLvl > 99) nextLvl = 99;
		return xpAtLevel(nextLvl) - getXp(skill);
	}
	
	public static int xpAtLevel(int level){
		double total = 0;
        for (int i = 1; i < level; i++){
                total += Math.floor(i + 300 * Math.pow(2, i / 7.0));
        }
        return (int) Math.floor(total / 4);
	}
}
