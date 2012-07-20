package bot.script.methods;

import bot.Bot;
/**
 * 
 * @author Webjoch
 *
 */
public class Skills{
	/** 
	 * @author Webjoch
	 * The indexes aren't complete!
	 */
	public enum Skill{
		ATACK(0),
		STRENGTH(2),
		DEFENCE(1),
		RANGED(4),
		PRAYER(5),
		MAGIC(6),
		RUNECRAFT(20),
		HITPOINTS(3),
		AGILITY(16),
		HERBLORE(15),
		THIEVING(17),
		CRAFTING(12),
		FLETCHING(9),
		SLAYER(18),
		MINING(14),
		SMITHING(13),
		FISHING(10),
		COOKING(7),
		FIREMAKING(11),
		WOODCUTTING(8),
		FARMING(19);
		
		int index;
		Skill(int index){
			this.index = index;
		}
		public int getIndex(){
			return index;
		}
	}
	
	/**
	 * Get the xp of the given skill index
	 * @param skill The skill index (0-25)
	 * @return the xp
	 */
	public static int getXp(int skill){
		return Bot.getClient().getCurrentXp()[skill];
	}
	
	public static int getXp(Skill skill){
		return getXp(skill.getIndex());
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
