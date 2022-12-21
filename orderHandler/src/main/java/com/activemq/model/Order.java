package com.activemq.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Order {
    private String user;
    private String typeOfGoods;
    private double amount;
    private double total;

}
