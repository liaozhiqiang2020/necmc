package com.sv.mc.service;

import com.sv.mc.conf.JmsConfig;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.GatewayEntity;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.repository.GatewayRepository;
import com.sv.mc.util.SingletonHungary;
import com.sv.mc.util.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class JMSConsumer {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private final static Logger logger = LoggerFactory.getLogger(JMSConsumer.class);

    @JmsListener(destination = "youTopic", containerFactory = "jmsListenerContainerTopic")
    public void onTopicMessage(byte[] byteStr) {
        WxUtil wxUtil = new WxUtil();
        int mcStatus = 0;
        String res = wxUtil.bytesToHexString(byteStr);
        String res16 = wxUtil.convertHexToString(res);//网关心跳包使用
        if (res.length() == 20) {
            String chairCode2 = res.substring(4, 20);//获取设备ascii
            String chairId2 = wxUtil.convertHexToString(chairCode2);//ascii转16进制
            SingletonHungary.getSingleTon().put(chairId2 + "status", chairId2 + "_" + 4);
            SingletonHungary.getSingleTon().put(chairId2 + "statusSys", chairId2 + "_" + 4);
            this.deviceService.findChairRuningStatus(chairId2, 4);
        }else if(res16.length() == 73){            //解析心跳包
            String gatewaySn = res16.split("_")[1];//截取网关sn

            Timestamp time = wxUtil.getNowDate();//获取当前时间戳
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String timeStr = sdf.format(time);
            GatewayEntity gatewayEntity = this.gatewayRepository.findGatewayBySn(gatewaySn);//获取网关信息
            gatewayEntity.setLastCorrespondTime(time);
            this.gatewayRepository.save(gatewayEntity);//保存网关最后通信时间

            List<String> deviceEntityList = this.deviceRepository.findAllDeviceByGatewayCode(gatewaySn);//获取网关下所有设备
            List<String> resMsgs = Arrays.asList(res16.split("_")[0].split(""));//截取心跳包数据

            String baseGateway = gatewaySn.substring(0,6);//截取网关sn的前6位

            for (int i = 1; i <resMsgs.size()+1 ; i++) {
                String resMsg = String.valueOf(i);
                int resInt = Integer.parseInt(resMsgs.get(i-1));
                if(i<10){
                    resMsg = "0"+resMsg;
                }
                String deviceSn = baseGateway+resMsg;

                if(deviceEntityList.contains(deviceSn)){
                    if(resInt==1){
                        this.deviceRepository.updateDeviceTimeByLoraId(deviceSn,resInt,timeStr);//修改按摩椅状态(0，不在线;1，在线)
                    }else{
                        this.deviceRepository.updateDeviceStatusByLoraId(deviceSn,resInt);
                    }
                }
            }
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
                SingletonHungary.getSingleTon().put(chairId + "statusSys", chairId + "_" + mcStatus);
                this.deviceService.findChairRuningStatus(chairId, mcStatus);
            } else if (type == 9) {//启动椅子
                if (returnMsg.equals("01")) {//成功
                    mcStatus = 1;
                } else if (returnMsg.equals("02")) {//指令错误
                    mcStatus = 2;
                } else { //未响应
                    mcStatus = 3;
                }
                SingletonHungary.getSingleTon().put(chairId + "runing", chairId + "_" + mcStatus);
                SingletonHungary.getSingleTon().put(chairId + "runingSys", chairId + "_" + mcStatus);
            } else if (type == 10) {//停止椅子
                SingletonHungary.getSingleTon().put(chairId + "statusSys", chairId + "_" + 4);
                this.deviceService.findChairRuningStatus(chairId, 4);//修改为未响应状态
            }
        }
    }

    @JmsListener(destination = JmsConfig.QUEUE, containerFactory = "jmsListenerContainerQueue")
    public void onQueueMessage(String msg) {
        logger.error("接收到queue消息：{}", msg);
    }
}
