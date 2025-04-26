package site.brainbrain.iqtest.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.brainbrain.iqtest.controller.dto.PaymentRequest;
import site.brainbrain.iqtest.infrasturcture.payment.PaymentResponse;
import site.brainbrain.iqtest.infrasturcture.payment.TossPaymentsClient;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TossPaymentsClient tossPaymentsClient;

    public void pay(final PaymentRequest paymentRequest) {
        final PaymentResponse paymentResponse = tossPaymentsClient.confirmPayment(paymentRequest);
    }
}
