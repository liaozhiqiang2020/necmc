package com.sv.mc.service;

import com.sv.mc.conf.JmsConfig;
import com.sv.mc.util.SingletonHungary;
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

    @JmsListener(destination = "youTopic", containerFactory = "jmsListenerContainerTopic")
    public void onTopicMessage(byte[] byteStr) {
        WxUtil wxUtil = new WxUtil();
        int mcStatus = 0;
        String res = wxUtil.bytesToHexString(byteStr);
        if (res.length() == 20) {
            String chairCode2 = res.substring(4, 20);//获取设备ascii
            String chairId2 = wxUtil.convertHexToString(chairCode2);//ascii转16进制
            SingletonHungary.getSingleTon().put(chairId2 + "status", chairId2 + "_" + 4);
            deviceService.findChairRuningStatus(chairId2, 4);
        } else {
            int type = Integer.parseInt(res.substring(14, 16));//获取协议类型
            String chairCode = res.substring(16, 32);//获取设备ascii
            String returnMsg = res.substring(32, 34);//返回类型
            String chairId = wxUtil.convertHexToString(chairCode);//ascii转16进制
            if (type == 8) {//查询状态
                if (returnMsg.equals("01")) {//空闲
                    mcStatus = 1;
                } else if (returnMsg.equals("02")) {//工作中
                    mcStatus = 2;
                } else if (returnMsg.equals("03")) {//复位中
                    mcStatus = 3;
                } else { //未响应
                    mcStatus = 4;
                }
                SingletonHungary.getSingleTon().put(chairId + "status", chairId + "_" + mcStatus);
                deviceService.findChairRuningStatus(chairId, mcStatus);
            } else if (type == 9) {//启动椅子
                if (returnMsg.equals("01")) {//成功
                    mcStatus = 1;
                } else if (returnMsg.equals("02")) {//指令错误
                    mcStatus = 2;
                } else { //未响应
                    mcStatus = 3;
                }
                SingletonHungary.getSingleTon().put(chairId + "runing", chairId + "_" + mcStatus);
            }
        }
    }

    @JmsListener(destination = JmsConfig.QUEUE, containerFactory = "jmsListenerContainerQueue")
    public void onQueueMessage(String msg) {
        logger.error("接收到queue消息：{}", msg);
    }
}
