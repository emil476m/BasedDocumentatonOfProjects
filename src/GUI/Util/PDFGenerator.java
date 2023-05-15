package GUI.Util;

import BE.Device;
import BE.Project;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class PDFGenerator {
    Document document;
    String path ="PdfFiles//project.pdf";

    Project project;

    List<Device> devices;

    List<File> files;

    public PDFGenerator(Project project, List<Device> devices, List<File> files) throws FileNotFoundException {
        this.project = project;
        this.devices = devices;
        this.files = files;
    }


    /**
     * Creates the pdf document.
     * @throws IOException
     */
    public void createDocument() throws IOException {
        try {
        PdfWriter pdfWriter = new PdfWriter(path);
        File file = new File(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        document = new Document(pdfDocument);
        createHeader();
        createSolidSeparator();
        createComment();
        if(!devices.isEmpty())
        {
            createDeviceList();
        }
        if(!files.isEmpty())
        {
            createImages();
        }
        document.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IOException("Failed to create pdf", e);
        }

    }

    /**
     * Adds the header for the Document
     * @throws MalformedURLException
     */
    private void createHeader() throws MalformedURLException {
        ImageData imageData = ImageDataFactory.create("src/GUI/Images/smallWUAV.png");
        Image logo = new Image(imageData);
        String customerNameAndAddress = project.getCostumerName() + "\n" + project.getAddress() + "\n" + project.getZipCode();
        String date = "Date: " + project.getProjectDate();
        float twoCol = 285f;
        float twoCol150 = twoCol + 150f;
        float twoColumWidth[] ={twoCol150,twoCol};
        Table table = new Table(twoColumWidth);
        Table nestedTableLogo = new Table(new float[]{twoCol/2,twoCol/2});
        nestedTableLogo.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));
        Table nestedTableCostumer = new Table(new float[]{twoCol/2,twoCol/2});
        nestedTableCostumer.addCell(new Cell().add(new Paragraph(customerNameAndAddress)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(nestedTableLogo).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(nestedTableCostumer).setBorder(Border.NO_BORDER));
        Table nestedTableDate = new Table(new float[]{twoCol/2,twoCol/2});
        nestedTableDate.addCell(new Cell().add(new Paragraph(date)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(nestedTableDate).setBorder(Border.NO_BORDER));
        document.add(table);
    }

    /**
     * Adds a solid line to separate elements
     */
    private void createSolidSeparator()
    {
        Color color = new DeviceRgb(51,50,50);
        float towCol190 = 190f;
        float fullWidth[] = {towCol190*3};
        Border bb = new SolidBorder(color, 2f);
        Table devider = new Table(fullWidth);
        devider.setBorder(bb);
        document.add(devider);
    }

    /**
     * Adds a dotted line to separate elements.
     */
    private void createDotSeparator()
    {
        Color color = new DeviceRgb(51,50,50);
        float towCol190 = 190f;
        float fullWidth[] = {towCol190*3};
        Border bb = new DashedBorder(color, 0.5f);
        Table devider = new Table(fullWidth);
        devider.setBorder(bb);
        document.add(devider);
    }

    /**
     * adds the comment form the project.
     */
    private void createComment()
    {
        Paragraph paragraph = new Paragraph(project.getProjectDescription() +"\n");
        document.add(paragraph);
        createDotSeparator();
    }


    /**
     * Adds a table with all devices for a project
     */
    private void createDeviceList()
    {
        Color black = new DeviceRgb(0,0,0);
        Color white = new DeviceRgb(255,255,255);
        float threeCol = 190f;
        float threeColWidth[] = {threeCol,threeCol,threeCol};

        // Creates the list header.
        Table threeColTable1 = new Table(threeColWidth);
        threeColTable1.setBackgroundColor(black,0.7f);
        threeColTable1.addCell(new Cell().add(new Paragraph("Device Type").setBold().setFontColor(white)).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add(new Paragraph("Device username").setBold().setFontColor(white)).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add(new Paragraph("Device password").setBold().setFontColor(white)).setBorder(Border.NO_BORDER));
        document.add(threeColTable1);


        // creates device list
        Table threeColTable2 = new Table(threeColWidth);
        for (Device d: devices)
        {
            threeColTable2.addCell(new Cell().add(new Paragraph(d.getDeviceTypeString())).setBorder(Border.NO_BORDER).setMarginLeft(10f));
            threeColTable2.addCell(new Cell().add(new Paragraph(d.getDeviceUserName())).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(new Paragraph(d.getDevicePassWord())).setMarginRight(15f).setBorder(Border.NO_BORDER));
        }
        document.add(threeColTable2);
        createDotSeparator();
    }

    /**
     * Adds all images for a project to the pdf
     * @throws MalformedURLException
     */
    private void createImages() throws MalformedURLException {
        for (File f: files)
        {
            ImageData imageData = ImageDataFactory.create(f.getPath());
            Image image = new Image(imageData);
            document.add(image);
        }
    }
}

