package com.activemq.service;

import com.activemq.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@Component

public class LoggerService {

    @Autowired
    private AmazonService amazonService;

    @Autowired
    JmsTemplate jmsTemplate;
    boolean append = true;
    FileHandler handler;

    public static final String FILE_NAME = "default.log";
    public static final String S3LOG_FILE_NAME = "Log.txt";

    {
        try {
            handler = new FileHandler(FILE_NAME, append);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Logger logger = Logger.getLogger("com.activemq");

    @JmsListener(destination = "confirmedTopic", containerFactory = "jmsLogContainerFactory")
    public void receiveMessageFromTopicLiquid(Order order) {
        logger.addHandler(handler);
        logger.info("order confirmed " + order);
        amazonService.uploadFileTos3bucket(S3LOG_FILE_NAME, new File(FILE_NAME));
    }

    @JmsListener(destination = "rejectedTopic", containerFactory = "jmsLogContainerFactory")
    public void receiveMessageFromTopicSolid(Order order) {
        logger.addHandler(handler);
        logger.info("order rejected  " + order);
        amazonService.uploadFileTos3bucket(S3LOG_FILE_NAME, new File(FILE_NAME));
    }

}
