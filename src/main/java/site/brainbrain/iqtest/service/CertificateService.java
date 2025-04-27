package site.brainbrain.iqtest.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import site.brainbrain.iqtest.controller.dto.TesteeRequest;
import site.brainbrain.iqtest.domain.Certificate;
import site.brainbrain.iqtest.domain.CertificateImageRenderer;
import site.brainbrain.iqtest.domain.CertificateTemplate;
import site.brainbrain.iqtest.domain.ClasspathImageLoader;
import site.brainbrain.iqtest.domain.enums.CertificateElementPosition;
import site.brainbrain.iqtest.domain.enums.IqScore;
import site.brainbrain.iqtest.util.CertificateNumberGenerator;
import site.brainbrain.iqtest.util.FontLoader;

@Service
public class CertificateService {

    private static final String CERTIFICATE_FILE_NAME = "_certificate.pdf";
    private static final int TESTEE_NAME_FONT_SIZE = 330;
    public static final String TESTEE_NAME_COLOR = "#343434";
    private static final int SUB_FONT_SIZE = 50;
    public static final String SUB_COLOR = "#666666";
    public static final String ASIA_SEOUL = "Asia/Seoul";
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    private final CertificateTemplate certificateTemplate;

    @Value("${certificate.exmouth}")
    private String testeeNameFontPath;

    @Value("${certificate.gilda}")
    private String subFontPath;

    public CertificateService(
            @Value("${certificate.template}")
            final String certificateTemplatePath,
            final ClasspathImageLoader classpathImageLoader
    ) {
        this.certificateTemplate = CertificateTemplate.of(classpathImageLoader, certificateTemplatePath);
    }

    public Certificate generate(final TesteeRequest testeeRequest, final IqScore iQScore) {
        final BufferedImage certificateImage = certificateTemplate.getCopyImage();
        final String certificateNumber = CertificateNumberGenerator.generate();
        drawOnImage(certificateImage, testeeRequest.name(), iQScore, certificateNumber);
        return new Certificate(
                certificateImage,
                testeeRequest.name() + CERTIFICATE_FILE_NAME,
                certificateNumber);
    }

    private void drawOnImage(final BufferedImage certificateImage,
                             final String testeeName,
                             final IqScore iQScore,
                             final String certificateNumber) {
        final CertificateImageRenderer renderer = CertificateImageRenderer.from(certificateImage);
        try {
            renderer.setRenderingHint();
            drawTesteeNameAndMainIQ(testeeName, iQScore, renderer);
            drawSubContent(iQScore, certificateNumber, renderer);
        } finally {
            renderer.dispose();
        }
    }

    private void drawTesteeNameAndMainIQ(final String testeeName,
                                         final IqScore iQScore,
                                         final CertificateImageRenderer renderer) {
        final Font testeeNameFont = FontLoader.loadCustomFont(testeeNameFontPath, TESTEE_NAME_FONT_SIZE);
        renderer.useFontAndColor(testeeNameFont, Color.decode(TESTEE_NAME_COLOR));
        renderer.drawTesteeNameAndMainIq(testeeName, iQScore.getIqSd24());
    }

    private void drawSubContent(final IqScore iQScore, final String certificateNumber,
                                final CertificateImageRenderer renderer) {
        final Font subFont = FontLoader.loadCustomFont(subFontPath, SUB_FONT_SIZE);
        renderer.useFontAndColor(subFont, Color.decode(SUB_COLOR));

        final String percentile = iQScore.getPercentile() + "%";
        renderer.drawSubContent(percentile, CertificateElementPosition.PERCENTILE);
        renderer.drawSubContent(iQScore.getIqSd24(), CertificateElementPosition.SD24);
        renderer.drawSubContent(iQScore.getIqSd16(), CertificateElementPosition.SD16);
        renderer.drawSubContent(iQScore.getIqSd15(), CertificateElementPosition.SD15);

        final String issueDate = LocalDate
                .now(ZoneId.of(ASIA_SEOUL))
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        renderer.drawSubContent(issueDate, CertificateElementPosition.ISSUE_DATE);

        renderer.drawSubContent(certificateNumber, CertificateElementPosition.CERTIFICATION_NUMBER);
    }
}
