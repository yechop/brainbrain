package site.brainbrain.iqtest.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import site.brainbrain.iqtest.exception.CertificateException;

@ExtendWith(MockitoExtension.class)
class CertificateTemplateTest {

    @Test
    void 템플릿_이미지를_새로운_이미지로_복사한다() throws IOException {
        //given
        final String validPath = "/valid.png";
        final ImageLoader loader = Mockito.mock(ImageLoader.class);

        final BufferedImage dummyImage = new BufferedImage(10, 20, BufferedImage.TYPE_INT_ARGB);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(dummyImage, "png", byteArrayOutputStream);
        final byte[] pngBytes = byteArrayOutputStream.toByteArray();
        final InputStream inputStream = new ByteArrayInputStream(pngBytes);
        Mockito.when(loader.load(validPath)).thenReturn(inputStream);

        //when
        final CertificateTemplate certificateTemplate = CertificateTemplate.of(loader, validPath);

        final BufferedImage image1 = certificateTemplate.getCopyImage();
        final BufferedImage image2 = certificateTemplate.getCopyImage();

        //then
        assertThat(image1).isNotSameAs(image2);
        assertThat(image1.getWidth()).isEqualTo(10);
        assertThat(image1.getHeight()).isEqualTo(20);
    }

    @Test
    void 이미지를_읽지_못하면_예외가_발생한다() throws IOException {
        //given
        final String invalidPath = "/invalid.png";
        final ImageLoader loader = Mockito.mock(ImageLoader.class);
        Mockito.when(loader.load(invalidPath))
                .thenThrow(new IOException());

        //when & then
        assertThatThrownBy(() -> CertificateTemplate.of(loader, invalidPath))
                .isInstanceOf(CertificateException.class)
                .hasMessageContaining("인증서 템플릿을 불러오는데 실패했습니다");
    }
}
