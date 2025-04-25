package site.brainbrain.iqtest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.brainbrain.iqtest.controller.dto.PaymentRequest;
import site.brainbrain.iqtest.infrasturcture.payment.PaymentResponse;
import site.brainbrain.iqtest.domain.entity.Payment;
import site.brainbrain.iqtest.domain.repository.PaymentRepository;
import site.brainbrain.iqtest.infrasturcture.payment.TossPaymentsClient;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TossPaymentsClient tossPaymentsClient;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void pay(final PaymentRequest paymentRequest) {
        final PaymentResponse paymentResponse = tossPaymentsClient.confirmPayment(paymentRequest);
        final Payment payment = new Payment(paymentResponse);
        paymentRepository.save(payment);
    }
}
