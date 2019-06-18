package com.sv.mc.service;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActivemqQueueConsumer {
    private String name="";
//    private String subject = "TOOL.DEFAULT";
    private Destination destination = null;
    private Connection connection = null;       //JMS 客户端到JMS Provider 的连接,
    private Session session = null;    //JMS 客户端到JMS Provider 的连接
    private MessageConsumer consumer = null;

    public ActivemqQueueConsumer(String name){
        this.name = name;
    }

    public void initialize() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");       //连接工厂，JMS 用它创建连接

        Connection connection = connectionFactory.createConnection();   //构造从工厂得到连接对象
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);   // 获取操作连接
        connection.start();      // 启动

    }

    public void receive(){
        try {
            initialize();

            destination = session.createQueue(name);       //消息的目的地;消息发送给谁.
            consumer = session.createConsumer(destination);    //消费者，消息接收者

            System.out.println("Consumer("+name+"):----->Begin listening...");
//            int count=0;
//            while(count<10){
//            while () {
                Message message = consumer.receive();//主动接收消息(同步)
                System.out.println("consumer recive:"+((TextMessage)message).getText());
//            }

//                count++;
//                System.out.println(count);
//            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public void submit() throws JMSException {
        session.commit();
    }

    public void close() throws JMSException {
        System.out.println("Consumer:->Closing connection");
        if (consumer != null)
            consumer.close();
        if (session != null)
            session.close();
        if (connection != null)
            connection.close();
    }


}
