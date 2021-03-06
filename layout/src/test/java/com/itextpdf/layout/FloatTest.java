/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.layout;


import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class FloatTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/layout/FloatTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/layout/FloatTest/";

    private static final String text =
            "Video provides a powerful way to help you prove your point. When you click Online Video, you can paste in the embed code for the video you want to add. You can also type a keyword to search online for the video that best fits your document. " +
            "To make your document look professionally produced, Word provides header, footer, cover page, and text box designs that complement each other. For example, you can add a matching cover page, header, and sidebar. Click Insert and then choose the elements you want from the different galleries. " +
            "Themes and styles also help keep your document coordinated. When you click Design and choose a new Theme, the pictures, charts, and SmartArt graphics change to match your new theme. When you apply styles, your headings change to match the new theme. " +
            "Save time in Word with new buttons that show up where you need them. To change the way a picture fits in your document, click it and a button for layout options appears next to it. When you work on a table, click where you want to add a row or a column, and then click the plus sign. " +
            "Reading is easier, too, in the new Reading view. You can collapse parts of the document and focus on the text you want. If you need to stop reading before you reach the end, Word remembers where you left off - even on another device. ";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void floatParagraphTest01() throws IOException, InterruptedException {
        String cmpFileName = sourceFolder + "cmp_floatParagraphTest01.pdf";
        String outFile = destinationFolder + "floatParagraphTest01.pdf";

        PdfWriter writer = new PdfWriter(outFile);

        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        Paragraph p = new Paragraph();
        p.add("paragraph1");
        p.setWidth(70);
        p.setHeight(100);
        p.setBorder(new SolidBorder(1));
        p.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);
        Paragraph p1 = new Paragraph();
        p1.add("paragraph2");
        p1.setWidth(70);
        p1.setHeight(100);
        p1.setBorder(new SolidBorder(1));
        p1.setProperty(Property.FLOAT, FloatPropertyValue.RIGHT);

        doc.add(p);
        doc.add(p1);
        Paragraph p2 = new Paragraph();
        p2.add("paragraph3");
        p2.setBorder(new SolidBorder(1));
        doc.add(p2);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFileName, destinationFolder, "diff01_"));
    }

    @Test
    public void floatParagraphTest02() throws IOException, InterruptedException {
        String cmpFileName = sourceFolder + "cmp_floatParagraphTest02.pdf";
        String outFile = destinationFolder + "floatParagraphTest02.pdf";

        PdfWriter writer = new PdfWriter(outFile);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph();
        p.add("paragraph1");
        p.setWidth(70);
        p.setHeight(100);
        p.setBorder(new SolidBorder(1));
        Paragraph p1 = new Paragraph();
        p1.add("paragraph2");
        p1.setBorder(new SolidBorder(1));

        p.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);
        doc.add(p);
        doc.add(p1);
        Paragraph p2 = new Paragraph();
        p2.add("paragraph3");
        p2.setBorder(new SolidBorder(1));
        doc.add(p2);
        doc.add(p2);
        Paragraph p3 = new Paragraph("paragraph4aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        p3.setBorder(new SolidBorder(1));
        doc.add(p3);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFileName, destinationFolder, "diff02_"));
    }

    @Test
    public void floatDivTest01() throws IOException, InterruptedException {
        String cmpFileName = sourceFolder + "cmp_floatDivTest01.pdf";
        String outFile = destinationFolder + "floatDivTest01.pdf";

        PdfWriter writer = new PdfWriter(outFile);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Div div = new Div();
        div.setWidth(70);

        Paragraph p = new Paragraph();
        p.add("div1");
        div.setBorder(new SolidBorder(1));
        p.setBorder(new SolidBorder(1));
        div.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);
        div.add(p);
        doc.add(div);
        doc.add(new Paragraph("div2"));

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFileName, destinationFolder, "diff03_"));
    }

    @Test
    public void floatDivTest02() throws IOException, InterruptedException {
        String cmpFileName = sourceFolder + "cmp_floatDivTest02.pdf";
        String outFile = destinationFolder + "floatDivTest02.pdf";

        PdfWriter writer = new PdfWriter(outFile);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Div div = new Div();
        div.setMargin(0);
        div.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);

        Paragraph p = new Paragraph();
        p.add("More news");
        div.add(p);
        doc.add(div);

        div = new Div();
        div.setMargin(0);
        div.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);
        p = new Paragraph();
        p.add("Even more news");
        div.add(p);
        doc.add(div);

        Div coloredDiv = new Div();
        coloredDiv.setMargin(0);
        coloredDiv.setBackgroundColor(Color.RED);
        Paragraph p1 = new Paragraph();
        p1.add("Some div");
        coloredDiv.add(p1);
        doc.add(coloredDiv);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFileName, destinationFolder, "diff04_"));
    }

    @Test
    public void floatDivTest03() throws IOException, InterruptedException {
        String cmpFileName = sourceFolder + "cmp_floatDivTest03.pdf";
        String outFile = destinationFolder + "floatDivTest03.pdf";

        PdfWriter writer = new PdfWriter(outFile);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Div div = new Div();
        div.setMargin(0);
        div.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);
        div.setHeight(760);
        div.setWidth(523);
        div.setBorder(new SolidBorder(1));

        Paragraph p = new Paragraph();
        p.add("More news");
        div.add(p);
        doc.add(div);

        div = new Div();
        div.setMargin(0);
        div.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);

        div.setBorder(new SolidBorder(1));
        p = new Paragraph();
        p.add("Even more news");
        div.add(p);
        doc.add(div);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFileName, destinationFolder, "diff05_"));
    }

    @Test
    public void floatingImageInCell() throws IOException, InterruptedException {
        String cmpFileName = sourceFolder + "cmp_floatingImageInCell.pdf";
        String outFile = destinationFolder + "floatingImageInCell.pdf";
        String imageSrc = sourceFolder + "itis.jpg";

        Document document = new Document(new PdfDocument(new PdfWriter(outFile)));

        Image img1 = new Image(ImageDataFactory.create(imageSrc)).scaleToFit(100, 100);
        Image img2 = new Image(ImageDataFactory.create(imageSrc)).scaleToFit(100, 100);
        img2.setMarginRight(10);
        img2.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);

        Table table = new Table(UnitValue.createPercentArray(new float[]{30, 70}));
        table.addCell(new Cell().add(img1));
        table.addCell(new Cell().add(img2).add("Video provides a powerful way to help you prove your point. When you click Online Video, you can paste in the embed code for the video you want to add. You can also type a keyword to search online for the video that best fits your document.\n" +
                "To make your document look professionally produced, Word provides header, footer, cover page, and text box designs that complement each other. For example, you can add a matching cover page, header, and sidebar. Click Insert and then choose the elements you want from the different galleries.\n" +
                "Themes and styles also help keep your document coordinated. When you click Design and choose a new Theme, the pictures, charts, and SmartArt graphics change to match your new theme. When you apply styles, your headings change to match the new theme.\n" +
                "Save time in Word with new buttons that show up where you need them. To change the way a picture fits in your document, click it and a button for layout options appears next to it. When you work on a table, click where you want to add a row or a column, and then click the plus sign.\n" +
                "Reading is easier, too, in the new Reading view. You can collapse parts of the document and focus on the text you want. If you need to stop reading before you reach the end, Word remembers where you left off - even on another device.\n"));

        document.add(table);
        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFileName, destinationFolder, "diff06_"));
    }

    @Test
    @Ignore("DEVSIX-1254")
    public void floatingImageToNextPage() throws IOException, InterruptedException {
        String cmpFileName = sourceFolder + "cmp_floatingImageToNextPage.pdf";
        String outFile = destinationFolder + "floatingImageToNextPage.pdf";
        String imageSrc = sourceFolder + "itis.jpg";

        Document document = new Document(new PdfDocument(new PdfWriter(outFile)));

        Image img1 = new Image(ImageDataFactory.create(imageSrc)).scaleToFit(100, 100);
        Image img2 = new Image(ImageDataFactory.create(imageSrc)).scaleAbsolute(100, 500);
        img1.setMarginLeft(10);
        img1.setProperty(Property.FLOAT, FloatPropertyValue.RIGHT);
        img2.setMarginRight(10);
        img2.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);

        document.add(img1);
        document.add(new Paragraph(text));
        document.add(new Paragraph(text));
        document.add(img2);
        document.add(new Paragraph(text));
        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFileName, destinationFolder, "diff07_"));
    }

    @Test
    @Ignore("DEVSIX-1254")
    public void floatingTwoImages() throws IOException, InterruptedException {
        String cmpFileName = sourceFolder + "cmp_floatingTwoImages.pdf";
        String outFile = destinationFolder + "floatingTwoImages.pdf";
        String imageSrc = sourceFolder + "itis.jpg";

        Document document = new Document(new PdfDocument(new PdfWriter(outFile)));

        Image img1 = new Image(ImageDataFactory.create(imageSrc)).scaleToFit(400, 400);
        Image img2 = new Image(ImageDataFactory.create(imageSrc)).scaleToFit(400, 400);
        img1.setMarginRight(10);
        img1.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);
        img2.setMarginRight(10);
        img2.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);

        document.add(img1);
        document.add(img2);
        document.add(new Paragraph(text));
        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFileName, destinationFolder, "diff08_"));
    }

    @Test
    @Ignore("DEVSIX-1254")
    public void floatingTwoImagesLR() throws IOException, InterruptedException {
        String cmpFileName = sourceFolder + "cmp_floatingTwoImagesLR.pdf";
        String outFile = destinationFolder + "floatingTwoImagesLR.pdf";
        String imageSrc = sourceFolder + "itis.jpg";

        Document document = new Document(new PdfDocument(new PdfWriter(outFile)));

        Image img1 = new Image(ImageDataFactory.create(imageSrc)).scaleToFit(350, 350);
        Image img2 = new Image(ImageDataFactory.create(imageSrc)).scaleToFit(350, 350);
        img1.setMarginLeft(10);
        img1.setProperty(Property.FLOAT, FloatPropertyValue.RIGHT);
        img2.setMarginRight(10);
        img2.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);

        document.add(img1);
        document.add(img2);
        document.add(new Paragraph(text));
        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFileName, destinationFolder, "diff09_"));
    }

}
