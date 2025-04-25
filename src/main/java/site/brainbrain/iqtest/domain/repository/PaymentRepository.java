package site.brainbrain.iqtest.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import site.brainbrain.iqtest.domain.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
