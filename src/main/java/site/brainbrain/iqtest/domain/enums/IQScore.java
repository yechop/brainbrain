package site.brainbrain.iqtest.domain.enums;

import java.util.Arrays;

import lombok.Getter;
import site.brainbrain.iqtest.exception.IQScoreException;

@Getter
public enum IQScore {

    //todo 아래 쭉 이름짓고 점수 계산해서 넣기
    PERFECT(42, "0.1","172", "148", "145");
//    P1(41, ),...

    private final int answerCount;
    private final String percentile;
    private final String iQSd24;
    private final String iQSd16;
    private final String iQSd15;

    IQScore(final int answerCount,
            final String percentile,
            final String iQSd24,
            final String iQSd16,
            final String iQSd15) {
        this.answerCount = answerCount;
        this.percentile = percentile;
        this.iQSd24 = iQSd24;
        this.iQSd16 = iQSd16;
        this.iQSd15 = iQSd15;
    }

    public static IQScore of(final int answerCount) {
        return Arrays.stream(values())
                .filter(score -> score.answerCount == answerCount)
                .findFirst()
                .orElseThrow(() ->
                        new IQScoreException("정답 개수와 일치하는 아이큐가 없습니다.")
                );
    }
}
