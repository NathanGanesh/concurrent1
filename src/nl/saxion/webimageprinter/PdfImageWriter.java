package nl.saxion.webimageprinter;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class PdfImageWriter {

    private final ArrayList<WebImage> images;


    public PdfImageWriter(ArrayList<WebImage> images) {
        this.images = images;
    }


    public void printToPdf(String file) {
        Document document = new Document();
        try {
            Instant first = null;
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfContentByte pdfCB = new PdfContentByte(writer);
            for (WebImage image : images) {
                try {
                     first = Instant.now();
                    Image img = Image.getInstance(pdfCB, image.getImage(10), 1);
//                    Image img = Image.getInstance(pdfCB, image.getImage(100), 1); //1 second
                    document.add(img);
                    document.add(new Paragraph(image.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BadElementException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
            Instant second = Instant.now();
            Duration duration = Duration.between(first, second);
            System.out.println("Compress time of pictures: "+duration.getSeconds());
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (DocumentException e) {
            e.printStackTrace();
        }
    }


}
