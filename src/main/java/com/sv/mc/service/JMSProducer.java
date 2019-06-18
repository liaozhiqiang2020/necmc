package com.sv.mc.service;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * activemq生产者
 */
@Component
public class JMSProducer {
    @Autowired
    private JmsTemplate jmsTemplate;

    //    @Scheduled(fixedDelay=5000) // 5s执行一次   只有无参的方法才能用该注解
    public void sendMessage2(Destination destination, String message){
//        jmsTemplate.convertAndSend(destinationName, payload, messagePostProcessor);
        this.jmsTemplate.convertAndSend(destination, message);
    }


    private static final String QUEUE_NAME = "myTopic";//发命令的topic

    /**
     * 传入命令字符串，发送给给activemq
     * @param message
     * @throws Exception
     */
    public void sendMessage(String message) throws Exception {
        //1.创建一个连接工厂
//        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://39.108.129.115:61616");
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://39.104.142.21:61616");
//        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //2.使用工厂创建Connection
        Connection connection = factory.createConnection();
        //3.开启连接
        connection.start();
        //4.创建一个Session
        //第一个参数：是否开启事务(一般不开启)，如果开启事务，第二个参数无意义
        //第二个参数：应答模式（自动/手动）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.通过Session创建一个Destination对象，两种形式：queue、topic
//        Queue queue = session.createQueue(QUEUE_NAME);
        Topic topic = session.createTopic(QUEUE_NAME);
        //6.通过Session创建一个Producer对象
        MessageProducer producer = session.createProducer(topic);
        //7.创建Message对象
//        TextMessage message = new ActiveMQTextMessage();
//        message.setText("");

        TextMessage textMessage = session.createTextMessage(message);
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }


}
