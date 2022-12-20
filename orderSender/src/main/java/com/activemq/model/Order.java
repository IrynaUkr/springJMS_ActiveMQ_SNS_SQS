package com.activemq.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Order {
    private final String user;
    private final String typeOfOrder;
    private final double amount;
    private final double total;

}
