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

    @JmsListener(destination = "ordersTopic", containerFactory = "jmsContainerFactory")
    public void receiveMessageFromTopic(Order order) {
        log.info("Received from topic without selector messages <<<< {} >>>>", order);
    }

    @JmsListener(destination = "ordersTopic", containerFactory = "jmsContainerFactory", selector = "typeOfGoods='LIQUID'")
    public void receiveMessageFromTopicLiquid(Order order) {
        log.info("Received from topic with selector typeOfGoods = 'LIQUID <<<< {} >>>>", order);
        if (order.getTotal() > 100) {
            log.info("order rejected");
            jmsTemplate.convertAndSend("rejected-queue", order);

        } else {
            log.info("order confirmed");
            jmsTemplate.convertAndSend("confirmed-queue", order);
        }
    }

    @JmsListener(destination = "ordersTopic", containerFactory = "jmsContainerFactory", selector = "typeOfGoods='SOLID'")
    public void receiveMessageFromTopicSolid(Order order) {
        log.info("Received from topic with selector typeOfGoods =SOLID <<<< {} >>>>", order);
        if (order.getTotal() > 100) {
            log.info("order rejected");
            jmsTemplate.convertAndSend("rejected", order);

        } else {
            log.info("order confirmed");
            jmsTemplate.convertAndSend("confirmed", order);
        }
    }

}
