package bot.script.util;

public class Random {
	private static java.util.Random random = new java.util.Random();
	
	public static int nextInt(int begin, int end){
		return random.nextInt(end - begin) + begin;
	}
	
	public static double nextDouble(){
		return random.nextDouble();
	}
  
  public static int nextGaussian(final int min, final int max, final int sd) {
     if (min == max) {
        return min;
     }
     int x = min - 1;
     while (x < min || x >= max) {
        x = (int) (random.nextGaussian() * sd + (min + (max - min) / 2));
     }
     return x;
  }
}
