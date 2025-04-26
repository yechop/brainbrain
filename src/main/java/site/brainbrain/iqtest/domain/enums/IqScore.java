package site.brainbrain.iqtest.domain.enums;

import java.util.Arrays;

import lombok.Getter;
import site.brainbrain.iqtest.exception.IqScoreException;

@Getter
public enum IqScore {

    //todo 아래 쭉 이름짓고 점수 계산해서 넣기
    PERFECT(42, "0.1","172", "148", "145");
//    P1(41, ),...

    private final int answerCount;
    private final String percentile;
    private final String iqSd24;
    private final String iqSd16;
    private final String iqSd15;

    IqScore(final int answerCount,
            final String percentile,
            final String iqSd24,
            final String iqSd16,
            final String iqSd15) {
        this.answerCount = answerCount;
        this.percentile = percentile;
        this.iqSd24 = iqSd24;
        this.iqSd16 = iqSd16;
        this.iqSd15 = iqSd15;
    }

    public static IqScore of(final int answerCount) {
        return Arrays.stream(values())
                .filter(score -> score.answerCount == answerCount)
                .findFirst()
                .orElseThrow(() ->
                        new IqScoreException("정답 개수와 일치하는 아이큐가 없습니다.")
                );
    }
}
