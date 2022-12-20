package com.activemq;


import com.activemq.model.Order;
import com.activemq.model.TypeOfGoods;
import com.activemq.service.SenderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
@EnableJms
public class OrderApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext appContext = SpringApplication.run(OrderApplication.class, args);
        SenderService producerService = appContext.getBean(SenderService.class);
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("enter user name");
            String userName = in.nextLine();
            System.out.println("enter typeOfGoods, LIQUID or SOLID");
            TypeOfGoods typeOfGoods = TypeOfGoods.valueOf(in.nextLine());
            System.out.println("enter amount");
            double amount = Double.parseDouble(in.nextLine());
            System.out.println("enter total");
            double total = Double.parseDouble(in.nextLine());
            Order order = new Order(userName, typeOfGoods, amount, total);
            System.out.println(order);
            producerService.sendMessageToQueue(order);
            producerService.sendMessageToTopic(order);
        }
    }

}
