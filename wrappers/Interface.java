package bot.script.wrappers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import nl.wbot.bot.Bot;

import bot.script.enums.InterfacePosition;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;
/**
 * 
 * @author Webjoch
 *
 */
public class Interface extends Methods{
	nl.wbot.bot.accessors.Interface accessor;
	Point point;
	
	public Interface(nl.wbot.bot.accessors.Interface accessor, int x, int y){
		this.accessor = accessor;
		this.point = new Point(x, y);
		
		if (inArray(InterfacePosition.CHAT_POSITIONS, getId())){
			setPosition(InterfacePosition.CHAT);
		}
		if (inArray(InterfacePosition.TAB_POSITIONS, getId())){
			setPosition(InterfacePosition.TAB);
		}
	}
	
	@Deprecated
	public void setPosition(int x, int y){
		this.point = new Point(x, y);
	}
	
	/**
	 * Sets the relative position of the interface. This method is only needed for the parent interface.
	 * @param position
	 */
	public void setPosition(InterfacePosition position){
		this.point = position.getPoint();
	}
	
	public int getId(){
		return accessor.getId();
	}
	
	public int[] getChildsIds(){
		return accessor.getChildIds();
	}
	
	public int[] getChildX(){
		return accessor.getChildX();
	}
	
	public int[] getChildY(){
		return accessor.getChildY();
	}
	
	public Interface getParent(){
		return new Interface(Bot.get().getMainClass().getInterfaces()[getParentId()], -1, -1);
	}
	
	public int getParentId(){
		return accessor.getParentId();
	}
	
	/**
	 * Gets the absolute location in screen. NOTE: This can be the relative location if the interface is an tab or chat interface. Use {@link Interface#setPosition(int, int)} to correct this.
	 * @return the location in the screen
	 */
	public Point getPoint(){
		if (point.x == 0 && point.y == 0 && getParentId() == getId()){
			point = new Point(4, 4);
		}
		return point;
	}
	
	public int getWidth(){
		return accessor.getWidth();
	}
	
	public int getHeight(){
		return accessor.getHeight();
	}
	
	public boolean isInventory(){
		return accessor.isInventory();
	}
	
	public int[] getItems(){
		return accessor.getInventoryItems();
	}
	
	public int[] getStacksize(){
		return accessor.getInventoryStacksize();
	}
	
	public String[] getActions(){
		return accessor.getActions();
	}
	
	public void paint(Graphics g){
		g.setColor(Color.yellow);
		g.drawRect(getPoint().x -4, getPoint().y-3, getWidth(), getHeight());
	}
	
	public Interface getChild(int id){
		for(Interface iface : getChilds()){
			if (iface.getId() == id){
				return iface;
			}
		}
		return null;
	}
	
	public void click(){
		int x = getPoint().x + getWidth() / 2;
		int y = getPoint().y + getHeight() / 2;
		Mouse.move(x, y);
		sleep(50);
		Mouse.click(true);
	}
	
	public Interface[] getChilds(){
		ArrayList<Interface> childs = new ArrayList<Interface>();
		int i = 0;
		if (getChildsIds() == null) return null;
		for (int childId : getChildsIds()){
			nl.wbot.bot.accessors.Interface iface = Bot.get().getMainClass().getInterfaces()[childId];
			if (iface.getId() == getId()){
				childs.add(new Interface(iface, 0, 0));
			}else{
				int x = i >= getChildX().length ? 0 : getChildX()[i];
				int y = i >= getChildY().length ? 0 : getChildY()[i];
				//if (getId() != getParentId()){
					x += getPoint().x;
					y += getPoint().y;
				//}
				childs.add(new Interface(iface, x, y));
				i++;
			}
		}
		return childs.toArray(new Interface[childs.size()]);
	}
	
	public String getText(){
		return accessor.getText();
	}
	
	@Override
	public String toString(){
		return "Interface "+getId();
	}
}
