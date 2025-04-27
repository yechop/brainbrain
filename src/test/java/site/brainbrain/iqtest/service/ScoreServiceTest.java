package site.brainbrain.iqtest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import site.brainbrain.iqtest.domain.enums.IqScore;

@SpringBootTest
class ScoreServiceTest {

    @Autowired
    private ScoreService scoreService;

    @Test
    void 사용자가_입력한_정답지에_맞는_아이큐를_반환한다() {
        final String testeeAnswerSheet = "111112222233333444445555566666777778888888";
        final IqScore iqScore = scoreService.calculate(testeeAnswerSheet);

        assertThat(iqScore).isEqualTo(IqScore.PERFECT);
    }
}
