package com.activemq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class LoggerConf {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String brokerUsername;

    @Value("${spring.activemq.password}")
    private String brokerPassword;

    @Bean
    public DefaultJmsListenerContainerFactory jmsLogContainerFactory() {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        containerFactory.setPubSubDomain(true);
        containerFactory.setConnectionFactory(connectionLogFactory());
        containerFactory.setMessageConverter(jacksonLogJmsMessageConverter());
        return containerFactory;
    }

    @Bean
    public CachingConnectionFactory connectionLogFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        ActiveMQConnectionFactory activeMQConnFactory = new ActiveMQConnectionFactory();
        activeMQConnFactory.setBrokerURL(brokerUrl);
        activeMQConnFactory.setPassword(brokerUsername);
        activeMQConnFactory.setUserName(brokerPassword);
        factory.setTargetConnectionFactory(activeMQConnFactory);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionLogFactory());
        template.setMessageConverter(jacksonLogJmsMessageConverter());
        template.setPubSubDomain(true);
        template.setDeliveryPersistent(true);
        return template;
    }

    @Bean
    public MessageConverter jacksonLogJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}