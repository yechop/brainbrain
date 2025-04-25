package site.brainbrain.iqtest.domain.enums;

import lombok.Getter;

@Getter
public enum Question {

    Q1(QuestionType.A, 1),
    Q2(QuestionType.A, 1),
    Q3(QuestionType.A, 1),
    Q4(QuestionType.A, 1),
    Q5(QuestionType.A, 1),
    Q6(QuestionType.A, 2),
    Q7(QuestionType.A, 2),
    Q8(QuestionType.A, 2),
    Q9(QuestionType.A, 2),
    Q10(QuestionType.A, 2),
    Q11(QuestionType.A, 3),
    Q12(QuestionType.A, 3),
    Q13(QuestionType.A, 3),
    Q14(QuestionType.A, 3),
    Q15(QuestionType.A, 3),
    Q16(QuestionType.A, 4),
    Q17(QuestionType.A, 4),
    Q18(QuestionType.A, 4),
    Q19(QuestionType.A, 4),
    Q20(QuestionType.A, 4),
    Q21(QuestionType.A, 5),
    Q22(QuestionType.A, 5),
    Q23(QuestionType.A, 5),
    Q24(QuestionType.A, 5),
    Q25(QuestionType.A, 5),
    Q26(QuestionType.A, 6),
    Q27(QuestionType.A, 6),
    Q28(QuestionType.A, 6),
    Q29(QuestionType.A, 6),
    Q30(QuestionType.A, 6),
    Q31(QuestionType.A, 7),
    Q32(QuestionType.A, 7),
    Q33(QuestionType.A, 7),
    Q34(QuestionType.A, 7),
    Q35(QuestionType.A, 7),
    Q36(QuestionType.A, 8),
    Q37(QuestionType.A, 8),
    Q38(QuestionType.A, 8),
    Q39(QuestionType.A, 8),
    Q40(QuestionType.A, 8),
    Q41(QuestionType.A, 8),
    Q42(QuestionType.A, 8)
    ;

    private final QuestionType questionType;
    private final int answer;

    Question(final QuestionType questionType, final int answer) {
        this.questionType = questionType;
        this.answer = answer;
    }
}
