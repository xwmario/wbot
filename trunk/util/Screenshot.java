package util;

import nl.wbot.bot.Bot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author iJava
 * Unsure on some of the method names as I was just finding locations of fields/methods from an obfuscated version so I assumed getter
 * names would be as said.
 */
public class Screenshot {

    public static Image getImage() {
        return Bot.get().getMainClass().getCanvas().getScreen().getImage();
    }

    public static boolean saveScreenshot(File file) {
        Image image = getImage();
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            return ImageIO.write((RenderedImage) image, (file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length())), file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
