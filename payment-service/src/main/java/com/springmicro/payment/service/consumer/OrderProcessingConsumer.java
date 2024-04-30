package com.springmicro.payment.service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmicro.payment.service.dto.Order;
import com.springmicro.payment.service.dto.User;
import com.springmicro.payment.service.entity.Payment;
import com.springmicro.payment.service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class OrderProcessingConsumer {
    public static final String USER_SERVICE_URL="http://localhost:9090/users/";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PaymentRepository paymentRepository;
    @KafkaListener(topics = "ORDER_PAYMENT_TOPIC")
    public void processOrder(String orderJsonString){
        try {
            Order order=new ObjectMapper()
                    .readValue(orderJsonString, Order.class);
            //build payment request
            Payment payment=Payment.builder()
                    .payMode(order.getPaymentMode())
                    .amount(order.getPrice())
                    .paidDate(new Date())
                    .userId(order.getUserId())
                    .orderId(order.getOrderId())
                    .build();
            if(payment.getPayMode().equals("COD")){
                payment.setPaymentStatus("PENDING");
                //do rest call to user
            }else{
                //validation
              User user=  restTemplate.getForObject(USER_SERVICE_URL+"/"+payment.getUserId(), User.class);
              if(payment.getAmount()>user.getAvailableAmount()){
                  throw new RuntimeException("Insufficient Amount ! ");
              }else{
                  payment.setPaymentStatus("PAID");
                  paymentRepository.save(payment);
                  restTemplate.put(USER_SERVICE_URL+"/"+payment.getUserId()+"/"+payment.getAmount(),null);
              }

            }
            paymentRepository.save(payment);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }
}
