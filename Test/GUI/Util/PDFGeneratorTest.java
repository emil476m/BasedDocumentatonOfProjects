package GUI.Util;

import BE.Project;
import com.itextpdf.io.exceptions.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PDFGeneratorTest {


@DisplayName("PDF generation test")
    @Test
    public void PdfGenerationTestWithInvalidInput()
    {
        Project project = new Project("testProject","TestEmail@email.com", LocalDate.now(),"Yes","we did some stuff and things",3,false,3,true, Timestamp.valueOf("2023-05-16 09:23:00"),1,"testStreet 1", "171717");
        File file = new File("GUI/Images/WUAV.png");
        PDFGenerator pdfGenerator = new PDFGenerator(project, file);

        Exception err = Assertions.assertThrows(Exception.class, () -> {pdfGenerator.createDocument();});

        String expectedMessage = "Failed to create pdf";
        String accualMessage = err.getMessage();
        assertEquals(expectedMessage,accualMessage);
    }
}