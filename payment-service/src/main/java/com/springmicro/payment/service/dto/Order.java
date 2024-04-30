package com.springmicro.payment.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private int id;
    private String name;
    private String category;
    private double price;
    private Date purchaseDate;
    private String orderId;
    private int userId;
    private String paymentMode;

}
