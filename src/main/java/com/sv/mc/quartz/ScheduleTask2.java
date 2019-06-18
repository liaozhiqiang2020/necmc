package com.sv.mc.quartz;

import com.sv.mc.service.ActivemqQueueConsumer;
import com.sv.mc.service.JMSProducer;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;

@Configuration
@Component
@EnableScheduling // 该注解必须要加
public class ScheduleTask2 {
    @Autowired
    JMSProducer jmsProducer;

    public void scheduleTest() throws JMSException {
        System.out.println("开始发送心跳数据");
        String res = "313131313131313131313131313131313131313131313131313030313130303131303131313031313131313131313131313131303030303030303030303030305F3131303030303030";
        // 写入消息队列
        Destination destination = new ActiveMQQueue("heartbeat.queue");

//        for (int i = 0; i <2000; i++) {

            jmsProducer.sendMessage2(destination, res);
//        }
        System.out.println("心跳数据发送完毕");

    }
}
