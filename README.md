### Task AWS:JMS + SQS, SNS

Create demo applications that implement the following order processing flow.

1. First application (OrderSender) reads the following order details from console and sends it to the order queue (orders):

* The user who makes the order;
* Type of goods for the order – liquids or countable item;
* The volume of order for liquids;
* Number of items for countable items;
* Order total.

2. Second application(OrderHandler) processes orders with the following rules:

* If order total greater than some threshold – order should be rejected;
* Summary information for accepted and rejected logs should be passed to other queues or topics.

3. Third application(OrderLogger) logs summary about accepted and rejected orders into file.

Used message selectors to split orders for liquids and countable items.
Used topics to implement message exchange.
Added trigger to S3 bucket that will send message to SQS that file was changed.

ActiveMQ start server:
go to the activeMQ folder, then find and go to the bin folder, and then use command:./activemq start

localhost:8161/admin
by default credentials are
username admin
password admin

to stop server use command ./activemq stop

Sources:
* https://docs.google.com/document/d/1pVXFCStP3C3Pk5FOiJk-kyVxvhHEOwJYCm4f5E1ENCE/edit
* https://medium.com/geekculture/queues-vs-topics-and-examples-with-java-spring-boot-and-apache-activemq-d945c474bc3e
* https://memorynotfound.com/spring-boot-activemq-publish-subscribe-topic-configuration-example/
* message selectors
* https://codenotfound.com/spring-jms-message-selector-example.html
* upload to s3 bucket
* https://medium.com/oril/uploading-files-to-aws-s3-bucket-using-spring-boot-483fcb6f8646
