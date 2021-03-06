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

import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.TableRenderer;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class TableTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/layout/TableTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/layout/TableTest/";

    static final String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
            "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
            "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
    static final String shortTextContent = "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";
    static final String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
            "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";


    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void simpleTableTest01() throws IOException, InterruptedException {
        String testName = "tableTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{50, 50})
                .addCell(new Cell().add(new Paragraph("cell 1, 1")))
                .addCell(new Cell().add(new Paragraph("cell 1, 2")));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
        Assert.assertEquals("Cell[row=0, col=0, rowspan=1, colspan=1]", table.getCell(0, 0).toString());
    }

    @Test
    public void simpleTableTest02() throws IOException, InterruptedException {
        String testName = "tableTest02.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{50, 50})
                .addCell(new Cell().add(new Paragraph("cell 1, 1")))
                .addCell(new Cell().add(new Paragraph("cell 1, 2")))
                .addCell(new Cell().add(new Paragraph("cell 2, 1")))
                .addCell(new Cell().add(new Paragraph("cell 2, 2")))
                .addCell(new Cell().add(new Paragraph("cell 3, 1")))
                .addCell(new Cell());
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest03() throws IOException, InterruptedException {
        String testName = "tableTest03.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent1 = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n";

        String textContent2 = "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n" +
                "Aenean nec lorem. In porttitor. Donec laoreet nonummy augue.\n" +
                "Suspendisse dui purus, scelerisque at, vulputate vitae, pretium mattis, nunc. Mauris eget neque at sem venenatis eleifend. Ut nonummy.\n";

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + textContent1)))
                .addCell(new Cell().add(new Paragraph("cell 1, 2\n" + textContent1 + textContent2)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + textContent2 + textContent1)))
                .addCell(new Cell().add(new Paragraph("cell 2, 2\n" + textContent2)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest04() throws IOException, InterruptedException {
        String testName = "tableTest04.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + textContent)));
        table.addCell(new Cell(3, 1).add(new Paragraph("cell 1, 2:3\n" + textContent + textContent + textContent)));
        table.addCell(new Cell().add(new Paragraph("cell 2, 1\n" + textContent)));
        table.addCell(new Cell().add(new Paragraph("cell 3, 1\n" + textContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest05() throws IOException, InterruptedException {
        String testName = "tableTest05.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell(3, 1).add(new Paragraph("cell 1, 1:3\n" + textContent + textContent + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 2\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 2\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 2\n" + textContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest06() throws IOException, InterruptedException {
        String testName = "tableTest06.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + textContent)))
                .addCell(new Cell(3, 1).add(new Paragraph("cell 1, 2:3\n" + textContent + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 1\n" + textContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest07() throws IOException, InterruptedException {
        String testName = "tableTest07.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell(3, 1).add(new Paragraph("cell 1, 1:3\n" + textContent + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 2\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 2\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 2\n" + textContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest08() throws IOException, InterruptedException {
        String testName = "tableTest08.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String shortTextContent = "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        Table table = new Table(new float[]{130, 130, 260})
                .addCell(new Cell(3, 2).add(new Paragraph("cell 1:2, 1:3\n" + textContent + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 3\n" + middleTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest09() throws IOException, InterruptedException {
        String testName = "tableTest09.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String shortTextContent = "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        Table table = new Table(new float[]{130, 130, 260})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 3\n" + middleTextContent)))
                .addCell(new Cell(3, 2).add(new Paragraph("cell 2:2, 1:3\n" + textContent + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 3\n" + middleTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest10() throws IOException, InterruptedException {
        String testName = "tableTest10.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        doc.add(new Paragraph("Table 1"));
        Table table = new Table(new float[]{100, 100})
                .addCell(new Cell().add(new Paragraph("1, 1")))
                .addCell(new Cell().add(new Paragraph("1, 2")))
                .addCell(new Cell().add(new Paragraph("2, 1")))
                .addCell(new Cell().add(new Paragraph("2, 2")));
        doc.add(table);

        doc.add(new Paragraph("Table 2"));

        Table table2 = new Table(new float[]{50, 50})
                .addCell(new Cell().add(new Paragraph("1, 1")))
                .addCell(new Cell().add(new Paragraph("1, 2")))
                .addCell(new Cell().add(new Paragraph("2, 1")))
                .addCell(new Cell().add(new Paragraph("2, 2")));
        doc.add(table2);

        doc.add(new Paragraph("Table 3"));

        PdfImageXObject xObject = new PdfImageXObject(ImageDataFactory.createPng(UrlUtil.toURL(sourceFolder + "itext.png")));
        Image image = new Image(xObject, 50);

        Table table3 = new Table(new float[]{100, 100})
                .addCell(new Cell().add(new Paragraph("1, 1")))
                .addCell(new Cell().add(image))
                .addCell(new Cell().add(new Paragraph("2, 1")))
                .addCell(new Cell().add(new Paragraph("2, 2")));
        doc.add(table3);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest11() throws IOException, InterruptedException {
        String testName = "tableTest11.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String shortTextContent = "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 2\n" + shortTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 5, 1\n" + middleTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 5, 2\n" + shortTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 6, 1\n" + middleTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 6, 2\n" + shortTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 7, 1\n" + middleTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 7, 2\n" + middleTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest12() throws IOException, InterruptedException {
        String testName = "tableTest12.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String shortTextContent = "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 2\n" + shortTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 5, 1\n" + middleTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 5, 2\n" + shortTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 6, 1\n" + middleTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 6, 2\n" + shortTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 7, 1\n" + middleTextContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 7, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 8, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 8, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 9, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 9, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 10, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 10, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 11, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 11, 2\n" + shortTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest13() throws IOException, InterruptedException {
        String testName = "tableTest13.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);
        String shortTextContent = "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";
        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 7, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 7, 2\n" + middleTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest14() throws IOException, InterruptedException {
        String testName = "tableTest14.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String shortTextContent = "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        Table table = new Table(new float[]{130, 130, 260})
                .addCell(new Cell(3, 2).add(new Paragraph("cell 1:2, 1:3\n" + textContent + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 3\n" + middleTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest15() throws IOException, InterruptedException {
        String testName = "tableTest15.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String shortTextContent = "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        Table table = new Table(new float[]{130, 130, 260})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 3\n" + middleTextContent)))
                .addCell(new Cell(3, 2).add(new Paragraph("cell 3:2, 1:3\n" + textContent + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 7, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 7, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 7, 3\n" + middleTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest16() throws IOException, InterruptedException {
        String testName = "tableTest16.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        String longTextContent = "1. " + textContent + "2. " + textContent + "3. " + textContent + "4. " + textContent
                + "5. " + textContent + "6. " + textContent + "7. " + textContent + "8. " + textContent + "9. " + textContent;

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + longTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 2\n" + middleTextContent)).setBorder(new SolidBorder(Color.RED, 2)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + middleTextContent + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 2\n" + longTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 1)
    })
    public void simpleTableTest17() throws IOException, InterruptedException {
        String testName = "tableTest17.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{50, 50, 50})
                .addCell(new Cell().add(new Paragraph("cell 1, 1")))
                .addCell(new Cell().add(new Paragraph("cell 1, 2")))
                .addCell(new Cell().add(new Paragraph("cell 1, 3")));

        String longText = "Long text, very long text. ";
        for (int i = 0; i < 4; i++) {
            longText += longText;
        }
        table.addCell(new Cell().add(new Paragraph("cell 2.1\n" + longText).setKeepTogether(true)));
        table.addCell("cell 2.2\nShort text.");
        table.addCell(new Cell().add(new Paragraph("cell 2.3\n" + longText)));

        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 1)
    })
    public void simpleTableTest18() throws IOException, InterruptedException {
        String testName = "tableTest18.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        doc.add(new Paragraph(textContent));

        Table table = new Table(new float[]{50, 50, 50})
                .addCell(new Cell().add(new Paragraph("cell 1, 1")))
                .addCell(new Cell().add(new Paragraph("cell 1, 2")))
                .addCell(new Cell().add(new Paragraph("cell 1, 3")));

        String longText = "Long text, very long text. ";
        for (int i = 0; i < 4; i++) {
            longText += longText;
        }
        table.addCell(new Cell().add(new Paragraph("cell 2.1\n" + longText).setKeepTogether(true)));
        table.addCell("cell 2.2\nShort text.");
        table.addCell(new Cell().add(new Paragraph("cell 2.3\n" + longText)));

        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 1)
    })
    public void simpleTableTest19() throws IOException, InterruptedException {
        String testName = "tableTest19.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{130, 130, 260})
                .addCell(new Cell(3, 2).add(new Paragraph("cell 1:2, 1:3\n" + textContent + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 1, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 3\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 3\n" + textContent)))
                .addCell(new Cell().add(new Image(ImageDataFactory.create(sourceFolder + "red.png"))))
                .addCell(new Cell().add(new Paragraph("cell 4, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 3\n" + middleTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 1)
    })
    public void simpleTableTest20() throws IOException, InterruptedException {
        String testName = "tableTest20.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{130, 130, 260})
                .addCell(new Cell().add(new Image(ImageDataFactory.create(sourceFolder + "red.png"))))
                .addCell(new Cell().add(new Paragraph("cell 4, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 3\n" + middleTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 1)
    })
    public void simpleTableTest21() throws IOException, InterruptedException {
        String testName = "tableTest21.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String shortTextContent = "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        doc.add(new Paragraph(textContent));

        Table table = new Table(new float[]{130, 130, 260})
                .addCell(new Cell().add(new Image(ImageDataFactory.create(sourceFolder + "red.png"))))
                .addCell(new Cell().add(new Paragraph("cell 4, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 1\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 2\n" + shortTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 3\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 6, 3\n" + middleTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void simpleTableTest22() throws IOException, InterruptedException {
        String testName = "tableTest22.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(new UnitValue[]{UnitValue.createPointValue(30), UnitValue.createPointValue(30), UnitValue.createPercentValue(30), UnitValue.createPercentValue(30)})
                .addCell(new Cell().add(new Paragraph("cell 1, 1")))
                .addCell(new Cell().add(new Paragraph("cell 1, 2")))
                .addCell(new Cell().add(new Paragraph("cell 1, 3")))
                .addCell(new Cell().add(new Paragraph("cell 1, 4")));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void bigRowspanTest01() throws IOException, InterruptedException {
        String testName = "bigRowspanTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        String longTextContent = "1. " + textContent + "2. " + textContent + "3. " + textContent + "4. " + textContent
                + "5. " + textContent + "6. " + textContent + "7. " + textContent + "8. " + textContent + "9. " + textContent;

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + textContent)))
                .addCell(new Cell(5, 1).add(new Paragraph("cell 1, 2\n" + longTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 1\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 1\n" + middleTextContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void bigRowspanTest02() throws IOException, InterruptedException {
        String testName = "bigRowspanTest02.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        String longTextContent = "1. " + textContent + "2. " + textContent + "3. " + textContent + "4. " + textContent
                + "5. " + textContent + "6. " + textContent + "7. " + textContent + "8. " + textContent + "9. " + textContent;

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + textContent)))
                .addCell(new Cell(5, 1).add(new Paragraph("cell 1, 2\n" + longTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 1\n" + textContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void bigRowspanTest03() throws IOException, InterruptedException {
        String testName = "bigRowspanTest03.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + textContent)))
                .addCell(new Cell(5, 1).add(new Paragraph("cell 1, 2\n" + middleTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 4, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 1\n" + textContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void bigRowspanTest04() throws IOException, InterruptedException {
        String testName = "bigRowspanTest04.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String middleTextContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.";

        String longTextContent = "1. " + textContent + "2. " + textContent + "3. " + textContent + "4. " + textContent
                + "5. " + textContent + "6. " + textContent + "7. " + textContent + "8. " + textContent + "9. " + textContent;

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + textContent)))
                .addCell(new Cell(5, 1).add(new Paragraph("cell 1, 2\n" + longTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 1\n" + textContent)))
                .addCell(new Cell().setKeepTogether(true).add(new Paragraph("cell 4, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 5, 1\n" + textContent)));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void bigRowspanTest05() throws IOException, InterruptedException {
        String testName = "bigRowspanTest05.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n";
        String longTextContent = "1. " + textContent + "2. " + textContent + "3. " + textContent + "4. " + textContent
                + "5. " + textContent + "6. " + textContent + "7. " + textContent + "8. " + textContent + "9. " + textContent;

        Table table = new Table(new float[]{250, 250})
                .addCell(new Cell().add(new Paragraph("cell 1, 1\n" + textContent)))
                .addCell(new Cell(2, 1).add(new Paragraph("cell 1, 1 and 2\n" + longTextContent)))
                .addCell(new Cell().add(new Paragraph("cell 2, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 1\n" + textContent)))
                .addCell(new Cell().add(new Paragraph("cell 3, 2\n" + textContent)));

        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void bigRowspanTest06() throws IOException, InterruptedException {
        String testName = "bigRowspanTest06.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(2)
                .addCell(new Cell(2, 1).add("col 1 row 2"))
                .addCell(new Cell(2, 1).add("col 2 row 2"))
                .addCell(new Cell(1, 1).add("col 1 row 3"))
                .addCell(new Cell(1, 1).add("col 2 row 3"));

        table.setBorderTop(new SolidBorder(Color.GREEN, 50)).setBorderBottom(new SolidBorder(Color.ORANGE, 40));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void bigRowspanTest07() throws IOException, InterruptedException {
        String testName = "bigRowspanTest07.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(2);

        for (int i = 0; i < 100; i++) {
            Cell cell = new Cell();
            cell.add("Cell " + i);

            Cell cell2 = new Cell(2, 1);
            cell2.add("Cell with Rowspan");

            Cell cell3 = new Cell();
            cell3.add("Cell " + i + ".2");

            table.addCell(cell);
            table.addCell(cell2);
            table.addCell(cell3);
        }

        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void differentPageOrientationTest01() throws IOException, InterruptedException {
        String testName = "differentPageOrientationTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        final PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textContent1 = "Video provides a powerful way to help you prove your point. When you click Online Video, you can paste in the embed code for the video you want to add. You can also type a keyword to search online for the video that best fits your document.";
        String textContent2 = "To make your document look professionally produced, Word provides header, footer, cover page, and text box designs that complement each other. For example, you can add a matching cover page, header, and sidebar. Click Insert and then choose the elements you want from the different galleries.";
        String textContent3 = "Themes and styles also help keep your document coordinated. When you click Design and choose a new Theme, the pictures, charts, and SmartArt graphics change to match your new theme. When you apply styles, your headings change to match the new theme.";

        Table table = new Table(3);
        for (int i = 0; i < 20; i++) {
            table.addCell(new Cell().add(new Paragraph(textContent1)))
                    .addCell(new Cell().add(new Paragraph(textContent3)))
                    .addCell(new Cell().add(new Paragraph(textContent2)))

                    .addCell(new Cell().add(new Paragraph(textContent3)))
                    .addCell(new Cell().add(new Paragraph(textContent2)))
                    .addCell(new Cell().add(new Paragraph(textContent1)))

                    .addCell(new Cell().add(new Paragraph(textContent2)))
                    .addCell(new Cell().add(new Paragraph(textContent1)))
                    .addCell(new Cell().add(new Paragraph(textContent3)));
        }
        doc.setRenderer(new RotatedDocumentRenderer(doc, pdfDoc));
        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    private static class RotatedDocumentRenderer extends DocumentRenderer {
        private final PdfDocument pdfDoc;

        public RotatedDocumentRenderer(Document doc, PdfDocument pdfDoc) {
            super(doc);
            this.pdfDoc = pdfDoc;
        }

        @Override
        protected PageSize addNewPage(PageSize customPageSize) {
            PageSize pageSize = currentPageNumber % 2 == 1 ? PageSize.A4 : PageSize.A4.rotate();
            pdfDoc.addNewPage(pageSize);
            return pageSize;
        }
    }

    @Test
    public void extendLastRowTest01() throws IOException, InterruptedException {
        String testName = "extendLastRowTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        PdfImageXObject xObject = new PdfImageXObject(ImageDataFactory.createPng(UrlUtil.toURL(sourceFolder + "itext.png")));
        Image image = new Image(xObject, 100);

        Table table = new Table(2);
        for (int i = 0; i < 20; i++) {
            table.addCell(image);
        }
        doc.add(new Paragraph("Extend the last row on each page"));
        table.setExtendBottomRow(true);
        doc.add(table);
        doc.add(new Paragraph("Extend all last rows on each page except final one"));
        table.setExtendBottomRow(false);
        table.setExtendBottomRowOnSplit(true);
        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 1)
    })
    @Test
    public void toLargeElementWithKeepTogetherPropertyInTableTest01() throws IOException, InterruptedException {
        String testName = "toLargeElementWithKeepTogetherPropertyInTableTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(1)
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedLayout();
        Cell cell = new Cell();
        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String result = "";
        for (int i = 0; i < 53; i++) {
            result += str;
        }
        Paragraph p = new Paragraph(new Text(result));
        p.setProperty(Property.KEEP_TOGETHER, true);
        cell.add(p);
        table.addCell(cell);
        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 1)
    })

    @Test
    public void toLargeElementInTableTest01() throws IOException, InterruptedException {
        String testName = "toLargeElementInTableTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + "toLargeElementInTableTest01.pdf"));
        Document doc = new Document(pdfDoc);

        Table table = new Table(new float[]{5});
        table.setWidth(5).setProperty(Property.TABLE_LAYOUT, "fixed");
        Cell cell = new Cell();
        Paragraph p = new Paragraph(new Text("a"));
        cell.add(p);
        table.addCell(cell);
        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void nestedTablesCollapseTest01() throws IOException, InterruptedException {
        String testName = "nestedTablesCollapseTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Cell cell;
        Table outertable = new Table(1);
        Table innertable = new Table(2);

        // first row
        // column 1
        cell = new Cell().add("Record Ref:");
        cell.setBorder(Border.NO_BORDER);
        innertable.addCell(cell);
        // column 2
        cell = new Cell().add("GN Staff");
        cell.setPaddingLeft(2);
        innertable.addCell(cell);
        // spacing
        cell = new Cell(1, 2);
        cell.setHeight(3);
        cell.setBorder(Border.NO_BORDER);
        innertable.addCell(cell);
        // second row
        // column 1
        cell = new Cell().add("Hospital:");
        cell.setBorder(Border.NO_BORDER);
        innertable.addCell(cell);
        // column 2
        cell = new Cell().add("Derby Royal");
        cell.setPaddingLeft(2);
        innertable.addCell(cell);
        // spacing
        cell = new Cell(1, 2);
        cell.setHeight(3);
        cell.setBorder(Border.NO_BORDER);
        innertable.addCell(cell);

        // first nested table
        cell = new Cell().add(innertable);
        outertable.addCell(cell);
        // add the table
        doc.add(outertable);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void nestedTableSkipHeaderFooterTest() throws IOException, InterruptedException {
        String testName = "nestedTableSkipHeaderFooter.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        Table table = new Table(5);
        table.addHeaderCell(new Cell(1, 5).
                add(new Paragraph("Table XYZ (Continued)")));
        table.addFooterCell(new Cell(1, 5).
                add(new Paragraph("Continue on next page")));
        table.setSkipFirstHeader(true);
        table.setSkipLastFooter(true);
        for (int i = 0; i < 350; i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1))));
        }

        Table t = new Table(1);
        t.addCell(new Cell().
                setBorder(new SolidBorder(Color.RED, 1)).
                setPaddings(3, 3, 3, 3).
                add(table));

        doc.add(t);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void nestedTablesWithMarginsTest01() throws IOException, InterruptedException {
        String testName = "nestedTablesWithMarginsTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc, PageSize.A8.rotate());

        Table innerTable = new Table(1);
        for (int i = 0; i < 4; i++) {
            innerTable.addCell(new Cell().add("Hello" + i));
        }

        Table outerTable = new Table(1)
                .addCell(new Cell().add(innerTable));
        outerTable.setMarginTop(10);
        doc.add(outerTable);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 1)
    })
    @Test
    public void splitTableOnShortPage() throws IOException, InterruptedException {
        String testName = "splitTableOnShortPage.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc, new PageSize(300, 98));

        doc.add(new Paragraph("Table with setKeepTogether(true):"));
        Table table = new Table(3);
        table.setKeepTogether(true);
        Cell cell = new Cell(3, 1);
        cell.add("G");
        cell.add("R");
        cell.add("P");
        table.addCell(cell);
        table.addCell("middle row 1");
        cell = new Cell(3, 1);
        cell.add("A");
        cell.add("B");
        cell.add("C");
        table.addCell(cell);
        table.addCell("middle row 2");
        table.addCell("middle row 3");
        doc.add(table);

        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table with setKeepTogether(false):"));
        table.setKeepTogether(false);
        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void imageInTableTest_HA() throws IOException, InterruptedException {
        String testName = "imageInTableTest_HA.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        PdfImageXObject xObject = new PdfImageXObject(ImageDataFactory.createPng(UrlUtil.toURL(sourceFolder + "itext.png")));
        Image imageL = new Image(xObject);
        imageL.setHorizontalAlignment(HorizontalAlignment.LEFT);
        Image imageC = new Image(xObject);
        imageC.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Image imageR = new Image(xObject);
        imageR.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        doc.add(new Paragraph("Table"));
        Table table = new Table(1)
                .addCell(new Cell().add(imageL))
                .addCell(new Cell().add(imageC))
                .addCell(new Cell().add(imageR));
        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void cellAlignmentAndSplittingTest01() throws IOException, InterruptedException {
        String testName = "cellAlignmentAndSplittingTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(1);
        for (int i = 0; i < 20; i++) {
            table.addCell(new Cell().add(i + " Liberté!\nÉgalité!\nFraternité!").setHeight(100).setVerticalAlignment(VerticalAlignment.MIDDLE));
        }
        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void cellAlignmentAndKeepTogetherTest01() throws IOException, InterruptedException {
        String testName = "cellAlignmentAndKeepTogetherTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(1);
        for (int i = 0; i < 20; i++) {
            table.addCell(new Cell().add(i + " Liberté!\nÉgalité!\nFraternité!").setHeight(100).setKeepTogether(true).setVerticalAlignment(VerticalAlignment.MIDDLE));
        }
        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.CLIP_ELEMENT, count = 3)
    })
    @Test
    public void tableWithSetHeightProperties01() throws IOException, InterruptedException {
        String testName = "tableWithSetHeightProperties01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textByron =
                "When a man hath no freedom to fight for at home,\n" +
                        "    Let him combat for that of his neighbours;\n" +
                        "Let him think of the glories of Greece and of Rome,\n" +
                        "    And get knocked on the head for his labours.\n";


        doc.add(new Paragraph("Default layout:"));

        Table table = new Table(3)
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.RED, 5)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.GRAY, 7)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.BLUE, 12)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.CYAN, 1)));
        table.setBorder(new SolidBorder(Color.GREEN, 2));
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's height is bigger than needed:"));
        table = new Table(3)
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.RED, 5)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.GRAY, 7)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.BLUE, 12)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.CYAN, 1)));
        table.setBorder(new SolidBorder(Color.GREEN, 2));
        table.setHeight(1700);
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's height is shorter than needed:"));
        table = new Table(3)
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.RED, 5)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.GRAY, 7)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.BLUE, 12)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.CYAN, 1)));
        table.setBorder(new SolidBorder(Color.GREEN, 2));
        table.setHeight(200);
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Some cells' heights are set:"));
        table = new Table(3)
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)).setHeight(300))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)).setHeight(40))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.RED, 5)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.GRAY, 7)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.BLUE, 12)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.CYAN, 1)).setHeight(20));
        table.setBorder(new SolidBorder(Color.GREEN, 2));
        table.setHeight(1700);
        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.CLIP_ELEMENT, count = 3)
    })
    @Test
    public void tableWithSetHeightProperties02() throws IOException, InterruptedException {
        String testName = "tableWithSetHeightProperties02.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textByron =
                "When a man hath no freedom to fight for at home,\n" +
                        "    Let him combat for that of his neighbours;\n" +
                        "Let him think of the glories of Greece and of Rome,\n" +
                        "    And get knocked on the head for his labours.\n";


        doc.add(new Paragraph("Default layout:"));

        Table table = new Table(3)
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.RED, 5)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.GRAY, 7)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.BLUE, 12)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.CYAN, 1)));
        table.setBorder(new SolidBorder(Color.GREEN, 2));
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's max height is bigger than needed:"));
        table = new Table(3)
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.RED, 5)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.GRAY, 7)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.BLUE, 12)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.CYAN, 1)));
        table.setBorder(new SolidBorder(Color.GREEN, 2));
        table.setMaxHeight(1300);
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's max height is shorter than needed:"));
        table = new Table(3)
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.RED, 5)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.GRAY, 7)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.BLUE, 12)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.CYAN, 1)));
        table.setBorder(new SolidBorder(Color.GREEN, 2));
        table.setMaxHeight(300);
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's min height is bigger than needed:"));
        table = new Table(3)
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.RED, 5)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.GRAY, 7)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.BLUE, 12)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.CYAN, 1)));
        table.setBorder(new SolidBorder(Color.GREEN, 2));
        table.setMinHeight(1300);
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's min height is shorter than needed:"));
        table = new Table(3)
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.RED, 5)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.GRAY, 7)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.BLUE, 12)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.CYAN, 1)));
        table.setBorder(new SolidBorder(Color.GREEN, 2));
        table.setMinHeight(300);
        doc.add(table);
        doc.add(new AreaBreak());


        doc.add(new Paragraph("Some cells' heights are set:"));
        table = new Table(3)
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)).setMinHeight(300))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.GREEN, 1)))
                .addCell(new Cell(1, 2).add(textByron).setBorder(new SolidBorder(Color.YELLOW, 3)).setMaxHeight(40))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.RED, 5)))
                .addCell(new Cell(2, 1).add(textByron).setBorder(new SolidBorder(Color.GRAY, 7)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.BLUE, 12)))
                .addCell(new Cell().add(textByron).setBorder(new SolidBorder(Color.CYAN, 1)).setMaxHeight(20));
        table.setBorder(new SolidBorder(Color.GREEN, 2));
        table.setHeight(1700);
        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void tableWithSetHeightProperties03() throws IOException, InterruptedException {
        String testName = "tableWithSetHeightProperties03.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        String textByron =
                "When a man hath no freedom to fight for at home,\n" +
                        "    Let him combat for that of his neighbours;\n" +
                        "Let him think of the glories of Greece and of Rome,\n" +
                        "    And get knocked on the head for his labours.\n";

        String textFrance = "Liberte Egalite Fraternite";

        doc.add(new Paragraph("Default layout:"));

        Table table = new Table(1)
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.RED))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.GREEN))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.BLUE));
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's height is bigger than needed:"));

        table = new Table(1)
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.RED))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.GREEN))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.BLUE));
        table.setHeight(600);
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's height is bigger than needed and some cells have HEIGHT property:"));

        table = new Table(1)
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.RED))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.GREEN).setHeight(30))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.BLUE));
        table.setHeight(600);
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's height is bigger than needed and all cells have HEIGHT property:"));

        table = new Table(1)
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.RED).setHeight(25))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.GREEN).setHeight(75))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.BLUE).setHeight(50));
        table.setHeight(600);
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's height is bigger than needed and some cells have HEIGHT property:"));

        table = new Table(2)
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.RED).setHeight(25))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.BLUE))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.GREEN))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.RED))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.BLUE).setHeight(50))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.GREEN));
        table.setHeight(600);
        doc.add(table);
        doc.add(new AreaBreak());

        doc.add(new Paragraph("Table's height is bigger than needed, some cells have big rowspan and HEIGHT property:"));

        table = new Table(2)
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.RED))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.BLUE))
                .addCell(new Cell(2, 1).add(textFrance).setBackgroundColor(Color.GREEN))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.RED))
                .addCell(new Cell().add(textFrance).setBackgroundColor(Color.GREEN).setHeight(50));
        table.setHeight(600);
        doc.add(table);


        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void tableWithHeaderInTheBottomOfPageTest() throws IOException, InterruptedException {
        String testName = "tableWithHeaderInTheBottomOfPageTest.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        for (int i = 0; i < 28; i++) {
            doc.add(new Paragraph("Text"));
        }

        Table table = new Table(UnitValue.createPercentArray(new float[] {10, 10}));
        table.addHeaderCell(new Cell().add("Header One"));
        table.addHeaderCell(new Cell().add("Header Two"));
        table.addCell(new Cell().add("Hello"));
        table.addCell(new Cell().add("World"));

        doc.add(table);

        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA)})
    public void bigFooterTest01() throws IOException, InterruptedException {
        String testName = "bigFooterTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(1);
        table.addFooterCell(new Cell().add("Footer").setHeight(650).setBorderTop(new SolidBorder(Color.GREEN, 100)));
        table.addCell(new Cell().add("Body").setHeight(30));

        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA)})
    public void bigFooterTest02() throws IOException, InterruptedException {
        String testName = "bigFooterTest02.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(1);
        table.addFooterCell(new Cell().add("Footer").setHeight(380).setBackgroundColor(Color.YELLOW));
        table.addHeaderCell(new Cell().add("Header").setHeight(380).setBackgroundColor(Color.BLUE));
        table.addCell(new Cell().add("Body"));

        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }


    @Test
    public void tableWithDocumentRelayoutTest() throws IOException, InterruptedException {
        String testName = "tableWithDocumentRelayoutTest.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc, PageSize.A4, false);

        Table table = new Table(UnitValue.createPercentArray(new float[] {10}));
        for (int i = 0; i < 40; i++) {
            table.addCell(new Cell().add("" + (i + 1)));
        }

        doc.add(table);

        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void tableWithKeepTogetherOnCells() throws IOException, InterruptedException {
        String testName = "tableWithKeepTogetherOnCells.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        Document document = new Document(new PdfDocument(new PdfWriter(outFileName)));

        Table table = new Table(UnitValue.createPercentArray(new float[] { 1.3f, 1f, 1f, 1f, 1f, 1f, 1f }));
        table.setWidthPercent(100f).setFixedLayout();
        for (int i = 1; i <= 7 * 100; i++) {
            Cell cell = new Cell().setKeepTogether(true).setMinHeight(45).add("" + i);
            table.addCell(cell);
        }
        document.add(table);
        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void emptyTableTest01() throws IOException, InterruptedException {
        String testName = "emptyTableTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        doc.add(new Table(1)
                .setBorderTop(new SolidBorder(Color.ORANGE, 50))
                .setBorderBottom(new SolidBorder(Color.MAGENTA, 100))
        );

        doc.add(new Table(1).setBorder(new SolidBorder(Color.ORANGE, 2)).addCell("Is my occupied area correct?"));
        doc.add(new AreaBreak());

        doc.add(new Table(1)
                .addCell(new Cell().setPadding(0).setMargin(0).setBorder(Border.NO_BORDER))
                .addCell(new Cell().setPadding(0).setMargin(0).setBorder(Border.NO_BORDER))
                .addCell(new Cell().setPadding(0).setMargin(0).setBorder(Border.NO_BORDER))
                .addCell(new Cell().setPadding(0).setMargin(0).setBorder(Border.NO_BORDER))
                .addCell(new Cell().add("Hello"))
        );
        doc.add(new Table(1).setBorder(new SolidBorder(Color.ORANGE, 2)).addCell("Is my occupied area correct?"));
        doc.add(new AreaBreak());

        doc.add(new Table(1).setMinHeight(300).setBorderRight(new SolidBorder(Color.ORANGE, 5)).setBorderTop(new SolidBorder(100)).setBorderBottom(new SolidBorder(Color.BLUE, 50)));
        doc.add(new Table(1).setBorder(new SolidBorder(Color.ORANGE, 2)).addCell("Is my occupied area correct?"));

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.LAST_ROW_IS_NOT_COMPLETE, count = 2)})
    public void tableWithIncompleteFooter() throws IOException, InterruptedException {
        String testName = "tableWithIncompleteFooter.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(3);

        table.addCell("Liberte");
        table.addCell("Egalite");
        table.addCell("Fraternite");
        table.addFooterCell(new Cell(1, 2).add("Liberte Egalite"));

        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.LAST_ROW_IS_NOT_COMPLETE, count = 1)})
    public void tableWithCustomRendererTest01() throws IOException, InterruptedException {
        String testName = "tableWithCustomRendererTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(2);
        table.setBorder(new SolidBorder(Color.GREEN, 100));

        for (int i = 0; i < 10; i++) {
            table.addCell(new Cell().add("Cell No." + i));
        }
        table.setNextRenderer(new CustomRenderer(table, new Table.RowRange(0, 10)));

        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    // This test checks that the table occupies exactly one page and does not draw its footer.
    // A naive algorithm would have this table on two pages with only one row with data on the second page
    // However, as setSkipLastFooter is true, we can lay out that row with data on the first page and avoid unnecessary footer placement.
    public void skipLastRowTest() throws IOException, InterruptedException {
        String testName = "skipLastRowTest.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(2);
        table.addHeaderCell("Header 1");
        table.addHeaderCell("Header 2");
        table.addFooterCell(new Cell(1, 2).add("Footer"));
        table.setSkipLastFooter(true);
        for (int i = 0; i < 33; i++) {
            table.addCell("text 1");
            table.addCell("text 2");
        }

        doc.add(table);
        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void skipFooterTest01() throws IOException, InterruptedException {
        String testName = "skipFooterTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(1);
        for (int i = 0; i < 19; i++) {
            table.addCell(new Cell().add(i + " Liberté!\nÉgalité!\nFraternité!").setHeight(100));
        }
        table.addFooterCell(new Cell().add("Footer").setHeight(116).setBackgroundColor(Color.RED));
        // the next line cause the reuse
        table.setSkipLastFooter(true);
        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void tableSplitTest01() throws IOException, InterruptedException {
        String testName = "tableSplitTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        String gretzky = "Make Gretzky great again!";

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc, PageSize.A8.rotate());

        Table table = new Table(2);
        table.setBorder(new SolidBorder(Color.GREEN, 15));

        table.addCell(new Cell().add(gretzky));
        table.addCell(new Cell().add(gretzky));

        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void tableSplitTest02() throws IOException, InterruptedException {
        String testName = "tableSplitTest02.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        String gretzky = "Make Gretzky great again!";

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc, PageSize.A7.rotate());

        Table table = new Table(2);
        table.setBorder(new SolidBorder(Color.GREEN, 15));

        PdfImageXObject xObject = new PdfImageXObject(ImageDataFactory.createPng(UrlUtil.toURL(sourceFolder + "itext.png")));
        Image image = new Image(xObject, 50);


        table.addCell(new Cell().add(gretzky));
        table.addCell(new Cell().add(gretzky));
        table.addCell(new Cell().add(image));
        table.addCell(new Cell().add(gretzky));
        table.addCell(new Cell().add(gretzky));
        table.addCell(new Cell().add(gretzky));


        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void tableSplitTest03() throws IOException, InterruptedException {
        String testName = "tableSplitTest03.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        String gretzky = "Make Gretzky great again!";

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc, PageSize.A8.rotate());

        Table table = new Table(2);
        table.setBorder(new SolidBorder(Color.GREEN, 15));

        table.addCell(new Cell().add(gretzky));
        table.addCell(new Cell(2, 1).add(gretzky));
        table.addCell(new Cell().add(gretzky));
        table.addCell(new Cell().add(gretzky));
        table.addCell(new Cell().add(gretzky));

        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void tableSplitTest04() throws IOException, InterruptedException {
        String testName = "tableSplitTest04.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        String gretzky = "Make Gretzky great again!";

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc, PageSize.A7.rotate());

        Table table = new Table(2);
        table.setBorder(new SolidBorder(Color.GREEN, 15));

        PdfImageXObject xObject = new PdfImageXObject(ImageDataFactory.createPng(UrlUtil.toURL(sourceFolder + "itext.png")));
        Image image = new Image(xObject, 50);


        table.addCell(new Cell().add(gretzky));
        table.addCell(new Cell(2, 1).add(gretzky));
        table.addCell(new Cell().add(image));
        table.addCell(new Cell().add(gretzky));
        table.addCell(new Cell().add(gretzky));

        doc.add(table);

        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.LAST_ROW_IS_NOT_COMPLETE),
            @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA)
    })
    public void tableNothingResultTest() throws IOException, InterruptedException {
        String testName = "tableNothingResultTest.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc);

        Table table = new Table(UnitValue.createPercentArray(new float[] {30, 30}));
        table.setKeepTogether(true);
        for (int i = 0; i < 40; i++) {
            table.addCell(new Cell().add("Hello"));
            table.addCell(new Cell().add("World"));
            table.startNewRow();
        }
        doc.add(table);

        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    @Test
    public void autoLayoutTest01() throws IOException, InterruptedException {
        String testName = "autoLayoutTest01.pdf";
        String outFileName = destinationFolder + testName;
        String cmpFileName = sourceFolder + "cmp_" + testName;

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(outFileName));
        // Initialize document
        Document doc = new Document(pdf);

        doc.add(new Paragraph("Simple cell:"));

        Table table = new Table(new float[1]);
        table.addCell("A cell");
        doc.add(table);

        doc.add(new Paragraph("A cell with bold text:"));

        table = new Table(new float[1]);
        table.addCell("A cell").setBold();
        doc.add(table);

        doc.add(new Paragraph("A cell with italic text:"));

        table = new Table(new float[1]);
        table.addCell("A cell").setItalic();
        doc.add(table);


        doc.close();
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, testName + "_diff"));
    }

    static class CustomRenderer extends TableRenderer {
        public CustomRenderer(Table modelElement, Table.RowRange rowRange) {
            super(modelElement, rowRange);
        }
    }
}
