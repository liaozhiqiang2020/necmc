package com.sv.mc.service;

import com.sv.mc.conf.JmsConfig;
import com.sv.mc.util.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class JMSConsumer {
    @Autowired
    private DeviceService deviceService;

    private final static Logger logger = LoggerFactory.getLogger(JMSConsumer.class);

    @JmsListener(destination = "youTopic",containerFactory = "jmsListenerContainerTopic")
    public void onTopicMessage(byte[] byteStr) {
//        System.out.println(msg);
        WxUtil wxUtil = new WxUtil();
//        String s2 = "";
//        String[] newMsg = msg.split(",");
//        StringBuilder b=new StringBuilder();
//        for(String s:newMsg){
//            b.append(s);
//        }
//        byte[] byteStr =b.toString().getBytes();
        int mcStatus=0;
        String res = wxUtil.bytesToHexString(byteStr);
        int type = Integer.parseInt(res.substring(14,16));//获取协议类型
        String chairCode = res.substring(16,32);//获取设备ascii
        String returnMsg = res.substring(32,34);//返回类型
        String chairId = wxUtil.convertHexToString(chairCode);//ascii转16进制
        if(type==8){//查询状态
            if(returnMsg.equals("01")){
                mcStatus=1;
            }else if(returnMsg.equals("02")){
                mcStatus=2;
            }else if(returnMsg.equals("03")){
                mcStatus=3;
            }else{
                mcStatus=4;
            }
            deviceService.findChairRuningStatus(chairId,mcStatus);
        }


//        System.out.println(res);

//        for (int i = 0; i <newMsg.length ; i++) {
//            String msg2 = newMsg[i];
//            int s =Integer.parseInt(msg2);
//            int s1=0;
//            if(s==48){
//                s1=0;
//            }else if(s>48){
//                s1 = s-48;
//            }
//            String s3 = Integer.toString(s1);
//            s2=s2+s3;
//        }

//        System.out.println(s2);


//        logger.error("接收到topic消息：{}",s2);
    }

    @JmsListener(destination = JmsConfig.QUEUE,containerFactory = "jmsListenerContainerQueue")
    public void onQueueMessage(String msg) {
        logger.error("接收到queue消息：{}",msg);
    }
}
