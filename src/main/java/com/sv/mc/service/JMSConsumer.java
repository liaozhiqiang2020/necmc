package com.sv.mc.service;

import com.sv.mc.conf.JmsConfig;
import com.sv.mc.util.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class JMSConsumer {
    private final static Logger logger = LoggerFactory.getLogger(JMSConsumer.class);

    @JmsListener(destination = "youTopic",containerFactory = "jmsListenerContainerTopic")
    public void onTopicMessage(String msg) {
        System.out.println(msg);
        String s2 = "";
        String[] newMsg = msg.split(",");
        for (int i = 0; i <newMsg.length ; i++) {
            String msg2 = newMsg[i];
            int s =Integer.parseInt(msg2);
            int s1=0;
            if(s==48){
                s1=0;
            }else if(s>48){
                s1 = s-48;
            }
            String s3 = Integer.toString(s1);
            s2=s2+s3;
        }
        logger.error("接收到topic消息：{}",s2);
    }

    @JmsListener(destination = JmsConfig.QUEUE,containerFactory = "jmsListenerContainerQueue")
    public void onQueueMessage(String msg) {
        logger.error("接收到queue消息：{}",msg);
    }
}
