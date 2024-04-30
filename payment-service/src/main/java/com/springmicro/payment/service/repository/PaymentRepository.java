package com.springmicro.payment.service.repository;

import com.springmicro.payment.service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Payment findByOrderId(String orderId);

}
