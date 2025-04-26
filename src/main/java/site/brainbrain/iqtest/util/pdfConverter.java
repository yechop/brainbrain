package site.brainbrain.iqtest.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ByteArrayResource;

import site.brainbrain.iqtest.exception.pdfException;

public class pdfConverter {

    public static ByteArrayResource imageToPdfResource(final BufferedImage image) {
        try (final PDDocument pdf = new PDDocument();
             final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            final PDRectangle rect = new PDRectangle(
                    image.getWidth(),
                    image.getHeight()
            );
            final PDPage page = new PDPage(rect);
            pdf.addPage(page);

            final PDImageXObject pdfImage = LosslessFactory.createFromImage(pdf, image);
            try (final PDPageContentStream cs = new PDPageContentStream(pdf, page)) {
                cs.drawImage(pdfImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            }
            pdf.save(byteArrayOutputStream);
            return new ByteArrayResource(byteArrayOutputStream.toByteArray());
        } catch (final IOException e) {
            throw new pdfException("PDF로 변환하는데 실패했습니다.");
        }
    }
}
