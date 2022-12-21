package com.activemq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String user;
    private String typeOfGoods;
    private double amount;
    private double total;

}
