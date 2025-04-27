package site.brainbrain.iqtest.domain.enums;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import site.brainbrain.iqtest.exception.IqScoreException;

public class IqScoreTest {

    @ParameterizedTest
    @CsvSource({
            "42, PERFECT",
            "0, ZERO"
    })
    void 정답_개수와_일치하는_아이큐_점수를_반환한다(int count, IqScore expected) {
        assertThat(IqScore.of(count)).isEqualTo(expected);
    }

    @Test
    void 정답_개수와_일치하는_아이큐가_없으면_예외가_발생한다() {
        //given
        int invalidAnswerCount = 43;
        //when & then
        assertThatThrownBy(() -> IqScore.of(invalidAnswerCount))
                .isInstanceOf(IqScoreException.class)
                .hasMessageContaining("정답 개수와 일치하는 아이큐가 없습니다");
    }
}
