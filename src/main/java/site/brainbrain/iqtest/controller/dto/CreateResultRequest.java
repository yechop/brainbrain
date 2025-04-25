package site.brainbrain.iqtest.controller.dto;

public record CreateResultRequest(TesteeRequest testeeRequest, String answerSheet, PaymentRequest paymentRequest) {
}
