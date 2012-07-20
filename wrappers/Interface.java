package bot.script.wrappers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import bot.Bot;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;
/**
 * 
 * @author Webjoch
 *
 */
public class Interface extends Methods{
	bot.accessors.Interface accessor;
	Point point;
	
	public Interface(bot.accessors.Interface accessor, int x, int y){
		this.accessor = accessor;
		this.point = new Point(x, y);
		switch(getId()){
		
		case 306:
		case 310:
		case 315:
		case 321:
		case 356:
		case 359:
		case 2459:
		case 4882:
		case 4887:
			this.point = new Point(13, 353);
			break;
			
		case 139:
			this.point = new Point(15, 350);
			break;
		
		case 147:
		case 328:
		case 3213:
		case 2449:
		case 5063:
			this.point = new Point(549, 201);
			break;
		}
	}
	
	/**
	 * Because the bot doesn't know the absolute location of the interface, you can set by yourself. NOTE: Use this only for top level interfaces (parent interfaces)!
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		this.point = new Point(x, y);
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
		return new Interface(Bot.getClient().getInterfaces()[getParentId()], -1, -1);
	}
	
	public int getParentId(){
		return accessor.getParentId();
	}
	
	/**
	 * Gets the absolute location in screen. NOTE: This can be the relative location if the interface is an tab or chat interface. Use {@link Interface#setPosition(int, int)} to correct this.
	 * @return the location in the screen
	 */
	public Point getPoint(){
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
		g.drawRect(getPoint().x, getPoint().y, getWidth(), getHeight());
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
			bot.accessors.Interface iface = Bot.getClient().getInterfaces()[childId];
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
