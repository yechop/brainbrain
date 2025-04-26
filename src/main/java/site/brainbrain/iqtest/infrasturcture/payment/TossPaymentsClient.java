package site.brainbrain.iqtest.infrasturcture.payment;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import lombok.RequiredArgsConstructor;
import site.brainbrain.iqtest.controller.dto.PaymentRequest;
import site.brainbrain.iqtest.exception.PaymentException;

@Component
@RequiredArgsConstructor
public class TossPaymentsClient {

    private static final String TOSS_CONFIRM_PAYMENT_URL = "/v1/payments/confirm";
    private static final String AUTH_HEADER_PREFIX = "Basic ";
    private static final String CREDENTIALS_SEPARATOR = ":";

    private final RestClient tossRestClient;

    @Value("${toss.secret-key}")
    private String testSecretKey;

    public PaymentResponse confirmPayment(final PaymentRequest request) {
        try {
            return tossRestClient
                    .post()
                    .uri(TOSS_CONFIRM_PAYMENT_URL)
                    .header(HttpHeaders.AUTHORIZATION, makeBasicAuthHeader())
                    .body(request)
                    .retrieve()
                    .toEntity(PaymentResponse.class)
                    .getBody();
        } catch (final RestClientException e) {
            throw new PaymentException("토스 결제 승인 오류가 발생했습니다.");
        }
    }

    private String makeBasicAuthHeader() {
        final String token = testSecretKey + CREDENTIALS_SEPARATOR;
        return AUTH_HEADER_PREFIX + Base64.getEncoder().encodeToString(token.getBytes());
    }
}
