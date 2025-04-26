package site.brainbrain.iqtest.domain;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import site.brainbrain.iqtest.domain.enums.CertificateElementPosition;

public class CertificateImageRenderer {

    private final Graphics2D graphics;

    private CertificateImageRenderer(final Graphics2D graphics) {
        this.graphics = graphics;
    }

    public static CertificateImageRenderer from(final BufferedImage certificateImage) {
        final Graphics2D graphics = certificateImage.createGraphics();
        graphics.setClip(0, 0, certificateImage.getWidth(), certificateImage.getHeight());
        return new CertificateImageRenderer(graphics);
    }

    public void setRenderingHint() {
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    public void useFontAndColor(final Font font, final Color color) {
        graphics.setFont(font);
        graphics.setColor(color);
    }

    public void drawTesteeNameAndMainIQ(final String testeeName, final String mainIQ) {
        final Rectangle bounds = graphics.getClipBounds();
        final int imageCenterX = bounds.width / 2;
        final int imageCenterY = bounds.height / 2;

        final FontMetrics fontMetrics = graphics.getFontMetrics();
        final int nameX = imageCenterX - (fontMetrics.stringWidth(testeeName) / 2)
                + CertificateElementPosition.TESTEE_NAME.getX();
        final int nameY = imageCenterY - CertificateElementPosition.TESTEE_NAME.getY();
        graphics.drawString(testeeName, nameX, nameY);

        final int mainIQX = imageCenterX - (fontMetrics.stringWidth(mainIQ) / 2)
                + CertificateElementPosition.MAIN_IQ.getX();
        final int mainIQY = imageCenterY + CertificateElementPosition.MAIN_IQ.getY();
        graphics.drawString(mainIQ, mainIQX, mainIQY);
    }

    public void drawSubContent(final String text, final CertificateElementPosition position) {
        graphics.drawString(text, position.getX(), position.getY());
    }

    public void dispose() {
        graphics.dispose();
    }
}
