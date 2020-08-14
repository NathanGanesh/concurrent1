package nl.saxion.webimageprinter;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Thread  extends java.lang.Thread {
    int k;
    String webImage2;

    WebImage image;

    public Thread(String webImage, int i) {
            k = i;
            this.webImage2 = webImage;

    }

    public WebImage getImage() {
        return image;
    }

    @Override
    public void run() {
        System.out.println("Thread no. "+k);
        try {
            image = (new WebImage(webImage2));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
