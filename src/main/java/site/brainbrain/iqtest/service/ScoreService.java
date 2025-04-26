package site.brainbrain.iqtest.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import site.brainbrain.iqtest.domain.enums.IqScore;
import site.brainbrain.iqtest.domain.enums.Question;

@Service
public class ScoreService {

    private static final List<Integer> ANSWERS =
            Arrays.stream(Question.values())
                    .map(Question::getAnswer)
                    .toList();

    public IqScore calculate(final String testeeAnswerSheet) {
        final int answerCount = (int) IntStream.range(0, ANSWERS.size())
                .filter(i -> Character.getNumericValue(testeeAnswerSheet.charAt(i)) == ANSWERS.get(i))
                .count();
        return IqScore.of(answerCount);
    }
}
