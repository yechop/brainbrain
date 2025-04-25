package site.brainbrain.iqtest.controller.dto;

public record PaymentRequest(String orderId, int amount, String paymentKey) {
}
