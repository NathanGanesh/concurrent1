package nl.saxion.webimageprinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class WebImagePrinter {


    public static void main(String[] args) {
        Instant first = Instant.now();
        new WebImagePrinter().print("http://concurrency.anthonyvandenberg.nl/");
        Instant second = Instant.now();
        Duration duration = Duration.between(first, second);
        System.out.println("Total duration: seconds"+duration.getSeconds()+ "Total Nano: "+duration.getNano());
    }


    /**
     *
     * @param location
     * @return List of location of images
     *
     * Reads a list of images from a web server.
     */
    private ArrayList<String> getImageList(String location) {

        ArrayList<String > images = new ArrayList<>();
        try {
            URL url = new URL(location);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String image;
            while ((image = in.readLine()) != null) {
                images.add(image);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }


    /**
     *
     * @param location
     * Todo: getting the images sequentially takes a lot of time.
     * Change the code so that each image is loaded in its own thread
     */
    public void print(String location) {
        ArrayList<String> images = getImageList(location);
        ArrayList<WebImage> webImages = new ArrayList<WebImage>();
        for(String image : images )
        {
            try {
                System.out.println("Loading " + image);
                webImages.add(new WebImage(location + "/" + image));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PdfImageWriter pdfwriter = new PdfImageWriter(webImages);
        pdfwriter.printToPdf("test.pdf");
     }

}
