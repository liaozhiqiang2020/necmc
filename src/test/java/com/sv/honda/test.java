package com.sv.honda;

import com.sv.mc.pojo.DeviceModelEntity;
import com.sv.mc.pojo.PriceEntity;
import com.sv.mc.service.DeviceModelService;
import com.sv.mc.service.JMSProducer;
import com.sv.mc.service.PriceService;
import com.sv.mc.service.impl.PriceServiceImpl;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;
import java.math.BigDecimal;

public class test extends HondaApplicationTests{

//    @Resource
//    private JMSProducer jmsProducer;
//    @Resource
//    private Topic topic;
//    @Resource
//    private Queue queue;
    @Autowired
    PriceService priceService;
    @Resource
    DeviceModelService deviceModelService;
    @Autowired
    PriceServiceImpl bo;
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
    /*@Test
    public void  test1(){

        int p=120;
        BigDecimal b1= new BigDecimal("10.5");
        String ssn= "10000005";

        PriceEntity priceEntity = this.bo.findAllFlag1(p,b1,ssn);
        System.out.println(priceEntity);
        System.out.println("------------------------------------------------");
        System.out.println(priceEntity);
    }*/
}
