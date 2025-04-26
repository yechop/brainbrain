package site.brainbrain.iqtest.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

import site.brainbrain.iqtest.exception.FontException;

public class FontLoader {

    public static Font loadCustomFont(final String fontName, final int fontSize) {
        try (final InputStream inputStream = new ClassPathResource(fontName).getInputStream()) {
            final Font customFont = Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
            return customFont.deriveFont(Font.PLAIN, fontSize);
        } catch (final FontFormatException | IOException e) {
            throw new FontException("폰트 로드 실패");
        }
    }
}
