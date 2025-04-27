package site.brainbrain.iqtest.domain;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import site.brainbrain.iqtest.exception.CertificateException;

public class CertificateTemplate {

    private final BufferedImage certificateTemplate;

    private CertificateTemplate(final BufferedImage certificateTemplate) {
        this.certificateTemplate = certificateTemplate;
    }

    public static CertificateTemplate of(final ImageLoader loader, final String certificateTemplatePath) {
        try (final InputStream inputStream = loader.load(certificateTemplatePath)) {
            return new CertificateTemplate(ImageIO.read(inputStream));
        } catch (final IOException e) {
            throw new CertificateException("인증서 템플릿을 불러오는데 실패했습니다.");
        }
    }

    public BufferedImage getCopyImage() {
        final BufferedImage copiedImage = new BufferedImage(
                certificateTemplate.getWidth(),
                certificateTemplate.getHeight(),
                certificateTemplate.getType()
        );
        copiedImage.getGraphics().drawImage(certificateTemplate, 0, 0, null);
        return copiedImage;
    }
}
