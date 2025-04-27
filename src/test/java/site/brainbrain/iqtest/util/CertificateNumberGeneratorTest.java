package site.brainbrain.iqtest.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CertificateNumberGeneratorTest {

    @Test
    void 인증서_넘버를_생성한다() {
        //given & when
        final String number = CertificateNumberGenerator.generate();

        //then
        assertThat(number.length()).isEqualTo(15);
        assertThat(number).doesNotContain("-");
    }
}
