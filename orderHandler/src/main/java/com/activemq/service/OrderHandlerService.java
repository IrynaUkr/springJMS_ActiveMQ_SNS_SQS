package com.activemq.service;

import com.activemq.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class OrderHandlerService {
    private static final Logger log = LoggerFactory.getLogger(OrderHandlerService.class);
    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = "ordersQueue")
    public void receiveMessageFromQueue(Order order) {
        log.info("Received from queue {}", order);
        if (order.getTotal() > 100) {
            log.info("order rejected");
            jmsTemplate.convertAndSend("rejected-queue", order);

        } else {
            log.info("order confirmed");
            jmsTemplate.convertAndSend("confirmed-queue", order);
        }
    }
//    @JmsListener(destination = "ordersTopic", containerFactory = "empJmsContFactory")
//    public void receiveMessageFromTopicLiquid(Order order) {
//        log.info("Received from topic <<<< {} >>>>", order);
//    }

//    @JmsListener(destination = "ordersTopic", containerFactory = "empJmsContFactory", selector = "typeOfGoods = 'LIQUID'")
//    public void receiveMessageFromTopicLiquid(Order order) {
//        log.info("Received from topic <<<< {} >>>>", order);
//    }
    @JmsListener(destination = "ordersTopic", containerFactory = "empJmsContFactory", selector = "typeOfGoods = 'SOLID'")
    public void receiveMessageFromTopicSolid(Order order) {
        log.info("Received from topic <<<< {} >>>>", order);
    }

}
