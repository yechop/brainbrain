package site.brainbrain.iqtest.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import site.brainbrain.iqtest.controller.dto.TesteeRequest;
import site.brainbrain.iqtest.domain.Certificate;
import site.brainbrain.iqtest.domain.enums.IQScore;
import site.brainbrain.iqtest.exception.CertificateException;

@Service
public class CertificateService {

    private static final String CERTIFICATE_TEMPLATE_PATH = "templates/certificate.png";
    private static final String CERTIFICATE_FILE_NAME = "_certificate.pdf";
    private static final String TESTEE_NAME_FONT_PATH = "fonts/exmouth.ttf";
    private static final int TESTEE_NAME_FONT_SIZE = 330;
    private static final String SUB_FONT_PATH = "fonts/gilda.ttf";
    private static final int SUB_FONT_SIZE = 50;

    private final BufferedImage certificateTemplate;

    public CertificateService() {
        try {
            this.certificateTemplate = ImageIO.read(new ClassPathResource(CERTIFICATE_TEMPLATE_PATH).getInputStream());
        } catch (final IOException e) {
            throw new CertificateException("인증서 템플릿을 불러오는데 실패했습니다. " + e.getMessage());
        }
    }

    public Certificate generate(final TesteeRequest testeeRequest, final IQScore iQScore) {
        final BufferedImage certificateImage = new BufferedImage(
                certificateTemplate.getWidth(),
                certificateTemplate.getHeight(),
                certificateTemplate.getType()
        );
        certificateImage.getGraphics().drawImage(certificateTemplate, 0, 0, null);
        final String certificateNumber = UUID.randomUUID().toString().substring(15).replace("-", "");

        drawOnImage(certificateImage, testeeRequest.name(), iQScore, certificateNumber);
        final byte[] pdf = toPdf(certificateImage);
        return new Certificate(pdf, testeeRequest.name() + CERTIFICATE_FILE_NAME, certificateNumber);
    }

    private void drawOnImage(final BufferedImage certificateImage,
                             final String testeeName,
                             final IQScore iQScore,
                             final String certificateNumber) {
        //todo 리팩토링
        final Graphics2D graphics = certificateImage.createGraphics();
        try {
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            final Font testeeNameFont = loadCustomFont(TESTEE_NAME_FONT_PATH, TESTEE_NAME_FONT_SIZE);
            graphics.setFont(testeeNameFont);
            graphics.setColor(Color.decode("#343434"));

            final int centerX = certificateImage.getWidth() / 2;
            final int centerY = certificateImage.getHeight() / 2;

            final FontMetrics fontMetrics = graphics.getFontMetrics();
            final int nameX = centerX - (fontMetrics.stringWidth(testeeName) / 2);
            final int nameY = centerY - 30;
            graphics.drawString(testeeName, nameX, nameY);

            final String iQSd24 = iQScore.getIQSd24();
            final int mainIQX = centerX - (fontMetrics.stringWidth(iQSd24) / 2) + 30;
            final int mainIQY = centerY + 380;
            graphics.drawString(iQSd24, mainIQX, mainIQY);

            final Font subFont = loadCustomFont(SUB_FONT_PATH, SUB_FONT_SIZE);
            graphics.setFont(subFont);
            graphics.setColor(Color.decode("#666666"));

            final String percentile = iQScore.getPercentile() + "%";
            final int percentileX = 2520;
            final int percentileY = 1765;
            graphics.drawString(percentile, percentileX, percentileY);

            final int iQSd24X = 2810;
            final int iQSd24Y = 1843;
            graphics.drawString(iQSd24, iQSd24X, iQSd24Y);

            final String iQSd16 = iQScore.getIQSd16();
            final int iQSd16X = 2810;
            final int iQSd16Y = 1904;
            graphics.drawString(iQSd16, iQSd16X, iQSd16Y);

            final String iQSd15 = iQScore.getIQSd15();
            final int iQSd15X = 2810;
            final int iQSd15Y = 1965;
            graphics.drawString(iQSd15, iQSd15X, iQSd15Y);

            final String issueDate = LocalDate
                    .now(ZoneId.of("Asia/Seoul"))
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            final int issueDateX = 536;
            final int issueDateY = 1810;
            graphics.drawString(issueDate, issueDateX, issueDateY);

            final int certificateNumberX = 536;
            final int certificateNumberY = 1974;
            graphics.drawString(certificateNumber, certificateNumberX, certificateNumberY);
        } finally {
            graphics.dispose();
        }
    }

    private Font loadCustomFont(final String fontName, final int fontSize) {
        try (final InputStream inputStream = new ClassPathResource(fontName).getInputStream()) {
            final Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            return customFont.deriveFont(Font.PLAIN, fontSize);
        } catch (final FontFormatException | IOException e) {
            throw new CertificateException("폰트 로드 실패");
        }
    }

    private byte[] toPdf(final BufferedImage certificateImage) {
        try (final PDDocument pdf = new PDDocument();
             final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            final PDRectangle rect = new PDRectangle(
                    certificateImage.getWidth(),
                    certificateImage.getHeight()
            );
            final PDPage page = new PDPage(rect);
            pdf.addPage(page);

            final PDImageXObject pdfImage = LosslessFactory.createFromImage(pdf, certificateImage);
            try (final PDPageContentStream cs = new PDPageContentStream(pdf, page)) {
                cs.drawImage(pdfImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            }
            pdf.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (final IOException e) {
            throw new CertificateException("인증서를 pdf로 변환하는데 실패했습니다.");
        }
    }
}
