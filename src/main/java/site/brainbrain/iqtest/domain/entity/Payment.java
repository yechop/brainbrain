package site.brainbrain.iqtest.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import site.brainbrain.iqtest.infrasturcture.payment.PaymentResponse;

@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String paymentKey;
    private String status;
    private int totalAmount;
    private String method;

    @OneToOne
    private Testee testee;

    public Payment(final PaymentResponse paymentResponse) {
        this.orderId = paymentResponse.orderId();
        this.paymentKey = paymentResponse.paymentKey();
        this.status = paymentResponse.status();
        this.totalAmount = paymentResponse.totalAmount();
        this.method = paymentResponse.method();
    }
}
