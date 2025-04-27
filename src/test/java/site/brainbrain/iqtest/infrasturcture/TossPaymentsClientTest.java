package site.brainbrain.iqtest.infrasturcture;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import site.brainbrain.iqtest.controller.dto.PaymentRequest;
import site.brainbrain.iqtest.exception.PaymentException;
import site.brainbrain.iqtest.infrasturcture.payment.TossPaymentsClient;

@SpringBootTest
public class TossPaymentsClientTest {

    @Autowired
    TossPaymentsClient tossPaymentsClient;

    @Test
    void 토스_결제_승인에_실패하면_예외가_발생한다() {
        //given
        final PaymentRequest request = new PaymentRequest("0", 0, "0");

        //then
        assertThatThrownBy(() -> tossPaymentsClient.confirmPayment(request))
                .isInstanceOf(PaymentException.class)
                .hasMessageContaining("토스 결제 승인 오류가 발생했습니다");
    }
}
