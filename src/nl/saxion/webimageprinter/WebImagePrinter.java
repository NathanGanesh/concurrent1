package nl.saxion.webimageprinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
public class WebImagePrinter{

    public static void main(String[] args) {
        Instant first = Instant.now();
        new WebImagePrinter().print("http://concurrency.anthonyvandenberg.nl/");
        Refresh("http://concurrency.anthonyvandenberg.nl/");

        Instant second = Instant.now();
        Duration duration = Duration.between(first, second);
        System.out.println("Total seconds: "+duration.getSeconds());
    }

    public static void Refresh(String location){
        ArrayList<String> images = getImageList(location);
        ArrayList<WebImage> webImages = new ArrayList<WebImage>();
        ArrayList<Thread> threads = new ArrayList<>();
        int threadIndex =1;
        for (String image: images){
            System.out.println("Loading " + image);
            Thread temp = new Thread((location + "/" + image), threadIndex);
            threads.add(temp);
            threadIndex++;
            temp.start();
            try {
                temp.join(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Thread thread : threads) {
            try {
                thread.join();
                webImages.add(thread.getImage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        PdfImageWriter pdfwriter = new PdfImageWriter(webImages);
        pdfwriter.printToPdf("test.pdf");

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
                System.out.println(images.size());
                System.out.println("Loading " + image);
                webImages.add(new WebImage(location + "/" + image));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PdfImageWriter pdfwriter = new PdfImageWriter(webImages);
        pdfwriter.printToPdf("dogga.pdf");
     }


    /**
     *
     * @param location
     * @return List of location of images
     *
     * Reads a list of images from a web server.
     */
    private static ArrayList<String> getImageList(String location) {

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



}
