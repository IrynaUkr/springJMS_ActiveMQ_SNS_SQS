package com.activemq.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
    private String user;
    private String typeOfOrder;
    private double amount;
    private double total;

}
