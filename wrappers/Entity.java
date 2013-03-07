package bot.script.wrappers;

import java.awt.Point;

import nl.wbot.bot.Bot;

import bot.script.methods.Calculations;
import bot.script.methods.Menu;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;
import bot.script.util.Random;
/**
 * 
 * @author Webjoch
 *
 */
public class Entity extends Methods {
	nl.wbot.client.Entity accessor;
	
	public Entity(nl.wbot.client.Entity accessor){
		this.accessor = accessor;
	}
	
	public Tile getLocation(){
		if (accessor == null) return new Tile(-1, -1);
		int x = Bot.get().getMainClass().getBaseX() + (accessor.getX() >> 7);
		int y = Bot.get().getMainClass().getBaseY() + (accessor.getY() >> 7);
		return new Tile(x, y);
	}
	
	public int getRealX(){
		return accessor.getX();
	}
	
	public int getRealY(){
		return accessor.getY();
	}
	
	public int getHeight(){
		return accessor.getHeight();
	}
	
	public Point getPoint(){
		return Calculations.worldToScreen(accessor.getX(), accessor.getY(), Calculations.tileHeight(accessor.getX(), accessor.getY()) - (accessor.getHeight() / 2));
	}
	
	public int getAnimation(){
		return accessor.getAnimation();
	}
	
	public boolean isMoving(){
		return accessor.getWalkingQueueSize() > 0;
	}

    public int getWalkingQueueSize() {
        return accessor.getWalkingQueueSize();
    }

    public boolean isVisible(){
		Point p = getPoint();
		return p.x > 0 && p.y > 0 && p.x < 516 && p.y < 340;
	}
	
	public boolean interact(String action){
		for (int i = 1; i < 15; i++){
			Point p = getPoint();
			Mouse.move(p.x + Random.nextInt(i, i*2) - i, p.y + Random.nextInt(i, i*2) - i);
			sleep(100);
			if (Menu.contains(action)) break;
		}
		return Menu.interact(action);
	}
	
	public boolean isDead(){
		return getHealthPercent() == 0 || accessor == null;
	}
	
	public boolean inCombat(){
		return accessor.getLastHit() > Bot.get().getMainClass().getLoopCycle();
	}

	public double distance(){
		return Calculations.distanceTo(getLocation());
	}
	
	public int getInteractingIndex(){
		return accessor.getInteractingIndex();
	}

    public Entity getInteractingEntity(){
		int index = getInteractingIndex();
        if(index == -1){
            return null;
        }
		if(index < 32768){
			return new NPC(Bot.get().getMainClass().getNpcArray()[index]);
		}else{
			index -= 32768;
			return new Player(Bot.get().getMainClass().getPlayerArray()[index]);
		}
	}
	
	/**
	 * Only works if the entity is in combat.
	 * @return the enitities health in percent (0 = dead)
	 */
	public int getHealthPercent(){
		if (accessor.getHealth() == 0) return inCombat() ? 0 : 100;
		return (int) ((double) accessor.getHealth() / accessor.getMaxHealth() * 100);
	}

    public int getCurrentHealth(){
        return accessor.getHealth() / 30;
    }

    public int getMaxHealth() {
        return accessor.getMaxHealth();
    }
}