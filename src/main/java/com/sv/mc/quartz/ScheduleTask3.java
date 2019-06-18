package com.sv.mc.quartz;

import com.sv.mc.pojo.GatewayEntity;
import com.sv.mc.repository.GatewayRepository;
import com.sv.mc.service.ActivemqQueueConsumer;
import com.sv.mc.service.JMSProducer;
import com.sv.mc.util.WxUtil;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.util.List;

@Configuration
@Component
@EnableScheduling // 该注解必须要加
public class ScheduleTask3 {
    @Autowired
    JMSProducer jmsProducer;

    @Autowired
    private GatewayRepository gatewayRepository;

    public void scheduleTest() throws JMSException {
        List<GatewayEntity> gatewayEntityList = this.gatewayRepository.findListByProtocalType(2);//查询协议类型为2的网关
        for (int i = 0; i < gatewayEntityList.size(); i++) {
            GatewayEntity gatewayEntity = gatewayEntityList.get(i);
            String gatewaySn = gatewayEntity.getGatewaySn();
            String gatewayQueueName = gatewaySn+".queue";
            ActivemqQueueConsumer activemqQueueConsumer = new ActivemqQueueConsumer(gatewayQueueName);
            activemqQueueConsumer.receive();
        }
    }

}
