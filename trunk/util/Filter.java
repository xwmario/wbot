package bot.script.util;

public interface Filter<T> {

	public boolean accept(T t);

}
