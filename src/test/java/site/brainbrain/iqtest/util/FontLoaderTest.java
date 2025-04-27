package site.brainbrain.iqtest.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import site.brainbrain.iqtest.exception.FontException;

public class FontLoaderTest {

    @Test
    void 폰트_로딩에_실패하면_예외가_발생한다() {
        //given
        final String invalid = "";

        //when & then
        assertThatThrownBy(() -> FontLoader.loadCustomFont(invalid, 1))
                .isInstanceOf(FontException.class)
                .hasMessageContaining("폰트 로드 실패");
    }
}
