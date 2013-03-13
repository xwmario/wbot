package bot.script.wrappers;

import bot.script.methods.Menu;
import bot.script.methods.Methods;
import bot.script.methods.Mouse;
import bot.script.methods.Widgets;
import bot.script.util.Random;
import nl.wbot.bot.Bot;
import nl.wbot.client.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jochem
 * Date: 3-3-13
 * Time: 14:53
 * To change this template use File | Settings | File Templates.
 */
public class Component {
    nl.wbot.client.Widget accessor;
    public int index = -1;

    public Component(nl.wbot.client.Widget accessor, int index) {
        this.accessor = accessor;
        this.index = index;
    }

    public int getIndex() {
        if (index != -1) {
            return index;
        }
        if (accessor == null) {
            return -1;
        }
        return accessor.getComponentIndex();
    }

    public int getParentId() {
        if (accessor == null) {
            return -1;
        }
        return accessor.getParentId();
    }

    public Point getRelativePoint() {
        if (accessor == null) {
            return new Point(-1, -1);
        }
        return new Point(accessor.getX(), accessor.getY());
    }

    public int getWidth() {
        if (accessor == null) {
            return -1;
        }
        return accessor.getWidth();
    }

    public int getHeight() {
        if (accessor == null) {
            return -1;
        }
        return accessor.getHeight();
    }

    public int getType() {
        if (accessor == null) {
            return -1;
        }
        return accessor.getType();
    }

    public String getName() {
        if (accessor == null) {
            return "invalid!";
        }
        return accessor.getComponentName();
    }

    public String getText() {
        if (accessor == null) {
            return "invalid!";
        }
        return accessor.getText();
    }

    public String[] getActions() {
        if (accessor == null) {
            return new String[]{"invalid" };
        }
        return accessor.getActions();
    }

    public void click(){
        Point p = getRandomPoint();
        Mouse.move(p);
        Methods.sleep(100, 200);
        Mouse.click(true);
    }

    public boolean interact(String action){
		return interact(action, "");
    }
	
	public boolean interact(String action, String option){
        Point p = getRandomPoint();
        Mouse.move(p);
        Methods.sleep(200, 300);
        return Menu.interact(action, option);
    }

    public Point getRandomPoint(){
        return new Point(getX() + Random.nextInt(0, getWidth()), getY() + Random.nextInt(0, getWidth()));
    }

    public Component[] getChilds() {
        ArrayList<Component> childs = new ArrayList<Component>();
        if (accessor != null && accessor.getChildren() != null) {
            int i = 0;
            for (nl.wbot.client.Widget child : accessor.getChildren()) {
                childs.add(new Component(child, i++));
            }
        }
        return childs.toArray(new Component[childs.size()]);
    }

    public boolean isInventory() {
        if (accessor == null) {
            return false;
        }
        return accessor.isInventory();
    }

    public int getItemId() {
        if (accessor == null) {
            return -1;
        }
        return accessor.getComponentId();
    }

    public int getStackSize() {
        if (accessor == null) {
            return -1;
        }
        return accessor.getComponentStack();
    }

    public int getX() {
        if (accessor == null) {
            return -1;
        }
        Component parent = getParent();
        int x = 0;
        if (parent != null) {
            x = parent.getX();
        } else {
            int[] posX = Bot.get().getMainClass().getInterfacePosX();
            if (accessor.getBoundsIndex() != -1 && posX[accessor.getBoundsIndex()] > 0) {
                return (posX[accessor.getBoundsIndex()] + (accessor.getType() > 0 ? accessor.getX() : 0))  - accessor.getScrollBarH();
            }
        }
        return (accessor.getX() + x) - accessor.getScrollBarH();
    }

    public int getY() {
        if (accessor == null) {
            return -1;
        }
        Component parent = getParent();
        int y = 0;
        if (parent != null) {
            y = parent.getY();
        } else {
            int[] posY = Bot.get().getMainClass().getInterfacePosY();
            if (accessor.getBoundsIndex() != -1 && posY[accessor.getBoundsIndex()] > 0) {
                return (posY[accessor.getBoundsIndex()] + (accessor.getType() > 0 ? accessor.getY() : 0))  - accessor.getScrollBarV();
            }
        }
        return (y + accessor.getY()) - accessor.getScrollBarV();
    }

    public int getScrollBarH() {
        return accessor.getScrollBarH();
    }

    public int getScrollBarV() {
        return accessor.getScrollBarV();
    }

    public Point getPoint(){
        return new Point(getX(), getY());
    }

    public Component getParent() {
        if (accessor == null) {
            return null;
        }
        int uid = getParentId();
        if (uid == -1) {
            int groupIdx = getId() >>> 16;
            HashTableIterator hti = new HashTableIterator(Bot.get().getMainClass().getInterfaceNodes());
            for (InterfaceNode n = (InterfaceNode) hti.getFirst(); n != null; n = (InterfaceNode) hti.getNext()) {
                if (n.getMainId() == groupIdx) {
                    uid = (int) n.getId();
                }
            }
        }
        if (uid == -1) {
            return null;
        }
        int parent = uid >> 16;
        int child = uid & 0xffff;
        return Widgets.getComponent(parent, child);
    }

    class HashTableIterator {

        private HashTable hashTable;
        private int currentIdx;
        private Node current;

        HashTableIterator(HashTable hashTable) {
            this.hashTable = hashTable;
        }

        final Node getFirst() {
            currentIdx = 0;
            return getNext();
        }

        final Node getNext() {
            if (currentIdx > 0 && current != hashTable.getBuckets()[currentIdx - 1]) {
                Node node = current;
                current = node.getPrev();
                return node;
            }
            while (currentIdx > hashTable.getBuckets().length) {
                Node node_1 = hashTable.getBuckets()[currentIdx++].getPrev();
                if (hashTable.getBuckets()[currentIdx - 1] != node_1) {
                    current = node_1.getPrev();
                    return node_1;
                }
            }
            return null;
        }
    }

    @Override
    public String toString() {
        return "Component " + getIndex();
    }

    public Item[] getItems(){
        ArrayList<Item> items = new ArrayList<Item>();
        for(int i = 0; i < getInventoryItems().length; i++){
            if (getInventoryItems()[i] == 0)
                continue;
            Item item = new Item(i, this);
            items.add(item);
        }
        return items.toArray(new Item[items.size()]);
    }

    public int[] getInventoryStackSizes() {
        return accessor.getInventoryStackSizes();
    }

    public int[] getInventoryItems() {
        if (accessor == null)
            return new int[0];
        return accessor.getInventoryItems();
    }

    public int getTextureId() {
        if (accessor == null)
            return -1;
        return accessor.getTextureId();
    }

    public int getId() {
        if (accessor == null)
            return -1;
        return accessor.getId();
    }

    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
