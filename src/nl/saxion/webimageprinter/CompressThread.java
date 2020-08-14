package nl.saxion.webimageprinter;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class CompressThread extends java.lang.Thread {

    BufferedImage image;
    int x;
    int y;
    int blockx;
    int blocky;
    int pixelColor;



    public CompressThread(BufferedImage image, int x, int y, int blockx, int blocky) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.blockx = blockx;
        this.blocky = blocky;
    }

    @Override
    public void run() {
        System.out.println("Thread no. "+this.getId());
        pixelColor = ImageFilters.BoxCompress(image,x  , y,blockx,blocky);
        image.setRGB(x,y,pixelColor);
    }

    public BufferedImage getImage() {
        return image;
    }
}
