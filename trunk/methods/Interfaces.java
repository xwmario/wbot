package bot.script.methods;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import nl.wbot.bot.Bot;

import bot.script.wrappers.Interface;
/**
 * 
 * @author Webjoch
 *
 */
public class Interfaces {
	/**
	 * Gets all top level interfaces
	 * @return An array of interfaces
	 */
	public static Interface[] getParentInterfaces(){
		Map<Integer, Interface> topInterfaces = new HashMap<Integer, Interface>();
		if (Bot.get().getMainClass().getInterfaces() != null)
			for (nl.wbot.bot.accessors.Interface iface : Bot.get().getMainClass().getInterfaces()){
				if (iface == null) continue;
				if (topInterfaces.get(iface.getId()) == null && iface.getParentId() == iface.getId()){
					topInterfaces.put(iface.getId(), new Interface(iface, 0, 0));
				}
			}
		topInterfaces = new TreeMap<Integer, Interface> (topInterfaces);
		return topInterfaces.values().toArray(new Interface[topInterfaces.size()]);
	}
	
	public static Interface getParentInterface(int id){
		for (Interface iface : getParentInterfaces()){
			if (iface.getId() == id) return iface;
		}
		return null;
	}
	
	/**
	 * Gets an interfaces
	 * @param parent the id of the parent interface
	 * @param child the id of the interface you need
	 * @return the interface
	 */
	public static Interface getInterface(int parent, int child){
		return getParentInterface(parent).getChild(child);
	}
	
	/**
	 * Try to avoid this method and use {@link Interfaces#getInterface(int, int)}
	 * @param id - the id of the interface you need
	 * @return the interface
	 */
	public static Interface getInterface(int id){
		nl.wbot.bot.accessors.Interface iface = Bot.get().getMainClass().getInterfaces()[id];
		if (iface.getId() == iface.getParentId()) return new Interface(iface, 0, 0);
		return getInterface(iface.getParentId(), id);
	}
}
