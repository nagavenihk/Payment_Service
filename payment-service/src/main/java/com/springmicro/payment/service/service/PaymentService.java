package com.springmicro.payment.service.service;

import com.springmicro.payment.service.entity.Payment;
import com.springmicro.payment.service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    public Payment getByOrderId(String orderId) {
        return repository.findByOrderId(orderId);
    }

}
