package com.activemq.service;

import com.activemq.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@Component
public class LoggerService {

    @Autowired
    JmsTemplate jmsTemplate;
    boolean append = true;
    FileHandler handler;

    {
        try {
            handler = new FileHandler("default.log", append);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Logger logger = Logger.getLogger("com.activemq");

    @JmsListener(destination = "confirmedTopic", containerFactory = "jmsLogContainerFactory")
    public void receiveMessageFromTopicLiquid(Order order) {
        logger.addHandler(handler);
        logger.info("order confirmed " + order);

    }

    @JmsListener(destination = "rejectedTopic", containerFactory = "jmsLogContainerFactory")
    public void receiveMessageFromTopicSolid(Order order) {
        logger.addHandler(handler);
        logger.info("order rejected  " + order);
    }

}
