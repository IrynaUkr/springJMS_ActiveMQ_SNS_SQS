package com.activemq.service;


import com.activemq.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SenderService {
    @Value("${spring.activemq.topic}")
    String topic;

    @Value("${spring.activemq.queue}")
    String queue;


    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessageToQueue(Order order) throws JsonProcessingException {
        try {
            jmsTemplate.convertAndSend(queue, order);
        } catch (Exception ex) {
            log.error("ERROR in sending message to queue", ex);
        }
    }

    public void sendMessageToTopic(Order order) throws JsonProcessingException {
        if (order.getTypeOfGoods().toString().equals("SOLID")) {
            log.info("type");
            jmsTemplate.convertAndSend(topic, order,
                    messagePostProcessor -> {
                        messagePostProcessor.setStringProperty("typeOfGoods",
                                "SOLID");
                        return messagePostProcessor;
                    });
        } else if (order.getTypeOfGoods().toString().equals("LIQUID")) {
            jmsTemplate.convertAndSend(topic, order,
                    messagePostProcessor -> {
                        messagePostProcessor.setStringProperty("typeOfGoods",
                                "LIQUID");
                        return messagePostProcessor;
                    });
        }
    }

}
