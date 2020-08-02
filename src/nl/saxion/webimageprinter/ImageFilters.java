package nl.saxion.webimageprinter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageFilters {


    static int BoxCompress(BufferedImage image,int x, int y, int width, int height) {
        long pixel = 0;
        int den = width * height;
        int red =0, green = 0, blue = 0;
        for (int bx = 0; bx < width ;bx++) {
            for (int by = 0; by < height ;by++) {
                pixel = image.getRGB(x + bx, y + by);
                red += ((pixel & 0xff0000) >> 16);
                green += ((pixel & 0x00ff00) >> 8);
                blue += ((pixel & 0x0000ff));
            }
        }
        red = red / den;
        green = green / den;
        blue = blue / den;

        try {
            //simulate a long process
            //do not change this!!!
            Thread.sleep(10);
        } catch(Exception e) {
            e.printStackTrace();
        }

        if (red > 255) red = 255;
        if (red < 0) red = 0;
        if (green > 255) green = 255;
        if (green < 0) green = 0;
        if (blue > 255) blue = 255;
        if (blue <0) blue = 0;
        int p = (255 << 24) | (red<<16) | (green<<8) | blue;
        return p;
    }

}
