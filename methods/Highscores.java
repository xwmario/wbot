package methods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author iJava
 */
public class Highscores {

    private String raw;
    private int[][] highscores;
    private String name;

    public static final int OVERALL = 0;
    public static final int ATTACK = 1;
    public static final int DEFENCE = 2;
    public static final int STRENGTH = 3;
    public static final int HITPOINTS = 4;
    public static final int RANGED = 5;
    public static final int PRAYER = 6;
    public static final int MAGIC = 7;
    public static final int COOKING = 8;
    public static final int WOODCUTTING = 9;
    public static final int FLETCHING = 10;
    public static final int FISHING = 11;
    public static final int FIREMAKING = 12;
    public static final int CRAFTING = 13;
    public static final int SMITHING = 14;
    public static final int MINING = 15;
    public static final int HERBLORE = 16;
    public static final int AGILITY = 17;
    public static final int THIEVING = 18;
    public static final int SLAYER = 19;
    public static final int FARMING = 20;
    public static final int RUNECRAFTING = 21;
    public static final int HUNTER = 22;
    public static final int CONSTRUCTION = 23;

    public Highscores(String raw, String name) {
        this.raw = raw;
        this.name = name;
        highscores = new int[24][3];
        sort();
    }

    private void sort() {
        String[] categories = raw.split(":");
        for (int i = 0; i < categories.length; i++) {
            String[] scores = categories[i].split(",");
            for (int x = 0; x < scores.length; x++) {
                highscores[i][x] = Integer.parseInt(scores[x]);
            }
        }
    }

    public int getRank(int index) {
        return highscores[index][0];
    }

    public int getLevel(int index) {
        return highscores[index][1];
    }

    public int getExperience(int index) {
        return highscores[index][2];
    }

    public static Highscores lookup(String name) {
        String content = "";
        try {
            URL url = new URL("http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=" + name);
            URLConnection urlConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("-1")) {
                    continue;
                }
                content += line + ":";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Highscores(content, name);
    }
}