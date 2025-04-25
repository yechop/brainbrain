package site.brainbrain.iqtest.infrasturcture.payment;

public record PaymentResponse(String orderId,
                              String paymentKey,
                              String status,
                              int totalAmount,
                              String method) {
}
