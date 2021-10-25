package com.example.serverpost.other;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//невикористовується
public class img {

    public static BufferedImage get(String url) {
        // читання
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(url));
        } catch(IOException e) {
            System.out.println("file not found");
        }
        return img;
    }

    public static void set(BufferedImage image, String name) {
        // збереження
        try {
            // retrieve image
            File outputFile = new File("/Users/andrijdutko/Desktop/ServerPost/src/main/resources/static/" + name);
            ImageIO.write(image, "png", outputFile);
        } catch(IOException e) {
            System.out.println("error");
        }
    }
}
