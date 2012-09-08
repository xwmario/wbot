package bot.script.util;

public class Random {
	private static java.util.Random random = new java.util.Random();
	
	public static int nextInt(int begin, int end){
		return random.nextInt(end - begin) + begin;
	}
	
	public static double nextDouble(){
		return random.nextDouble();
	}
}
