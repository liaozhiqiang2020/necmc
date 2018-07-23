package com.sv.honda;

import com.sv.mc.service.JMSProducer;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

public class test extends HondaApplicationTests{

//    @Resource
//    private JMSProducer jmsProducer;
//    @Resource
//    private Topic topic;
//    @Resource
//    private Queue queue;

    @Test
    public void testJms() {
//        Destination destination = new ActiveMQQueue("springboot.queue.test");
//
//        for (int i=0;i<1000;i++) {
//            jmsProducer.sendMessage(destination,"hello,world!" + i);
//        }

//
//
//            jmsProducer.sendMessage(topic, "faaf0e0832303030303030320000");

    }
}
