package nl.saxion.webimageprinter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WebImage {

    private  String name;
    private  BufferedImage image;
    private  URL url;


    public WebImage(String address) throws IOException {
        this.url = new URL(address);
        this.name = url.getFile();
        System.out.println("Starting " + name);
        this.image = ImageIO.read(url);
        System.out.println("Ending " + name);
    }

    public String getName() {
        return name;
    }
    

    /**
     *
     * @param scale
     * @return
     *
     * ToDo: scale the image using the class ImageFilters.
     */
    public Image getImage(int scale) {
        if (scale == 100) {
            return this.image;
        } else {
            ArrayList<Thread> threads = new ArrayList<>();
            int orignalWidth , originalHeight;
            orignalWidth = image.getWidth();
            originalHeight = image.getHeight();
            int scaledWidth,scaledHeight;
            scaledWidth =  (int) ((float)orignalWidth * ((float)scale / 100f));
            scaledHeight =  (int) ((float)originalHeight * ((float)scale / 100f));
            BufferedImage img = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
            int blockx = orignalWidth/scaledWidth;
            int blocky = originalHeight/scaledHeight;
            for(int x = 0; x < scaledWidth; x++) {
                for (int y = 0; y < scaledHeight; y++) {
                    int pixelColor = ImageFilters.BoxCompress(image,x * blockx , y * blocky,blockx,blocky);
                    img.setRGB(x,y,pixelColor);

                }
            }

            return img;
        }
    }


}
