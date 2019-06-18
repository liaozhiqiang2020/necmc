package com.sv.mc.service;

import com.sv.mc.pojo.GatewayEntity;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.repository.GatewayRepository;
import com.sv.mc.util.SingletonHungary;
import com.sv.mc.util.WxUtil;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * activemq消费者
 */
@Component
public class JMSConsumer {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private JMSProducer jmsProducer;

    private final static Logger logger = LoggerFactory.getLogger(JMSConsumer.class);

    /**
     * 监听activemq的topic,拿到字节数组
     *
     * @param byteStr 从activemq中获取的命令字节数组
     */
    @JmsListener(destination = "youTopic", containerFactory = "jmsListenerContainerTopic")
    public void onTopicMessage(byte[] byteStr) {
        WxUtil wxUtil = new WxUtil();
        int mcStatus = 0;
        String res = WxUtil.bytesToHexString(byteStr);
        String res16 = wxUtil.convertHexToString(res);//网关心跳包使用
        if (res.length() == 20) {
            String chairCode2 = res.substring(4, 20);//获取设备ascii
            String chairId2 = wxUtil.convertHexToString(chairCode2);//ascii转16进制
            SingletonHungary.getSingleTon().put(chairId2 + "status", chairId2 + "_" + 4);//从map的单例中获取key为“statusXXX”的value
            SingletonHungary.getSingleTon().put(chairId2 + "statusSys", chairId2 + "_" + 4);
            this.deviceService.findChairRuningStatus(chairId2, 4);
        } else if (res16.length() == 73) {            //解析心跳包
            String gatewaySn = res16.split("_")[1];//截取网关sn

            Timestamp time = wxUtil.getNowDate();//获取当前时间戳
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String timeStr = sdf.format(time);
            GatewayEntity gatewayEntity = this.gatewayRepository.findGatewayBySn(gatewaySn);//获取网关信息
            gatewayEntity.setLastCorrespondTime(time);
            this.gatewayRepository.save(gatewayEntity);//保存网关最后通信时间

            List<String> deviceEntityList = this.deviceRepository.findAllDeviceByGatewayCode(gatewaySn);//获取网关下所有设备
            List<String> resMsgs = Arrays.asList(res16.split("_")[0].split(""));//截取心跳包数据

            String baseGateway = gatewaySn.substring(0, 6);//截取网关sn的前6位

            for (int i = 1; i < resMsgs.size() + 1; i++) {
                String resMsg = String.valueOf(i);
                int resInt = Integer.parseInt(resMsgs.get(i - 1));
                if (i < 10) {
                    resMsg = "0" + resMsg;
                }
                String deviceSn = baseGateway + resMsg;

                if (deviceEntityList.contains(deviceSn)) {
                    if (resInt == 1) {
                        this.deviceRepository.updateDeviceTimeByLoraId(deviceSn, resInt, timeStr);//修改按摩椅状态(0，不在线;1，在线)
                    } else {
                        this.deviceRepository.updateDeviceStatusByLoraId(deviceSn, resInt);
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

//    /**
//     * 监听activemq的queue,拿到字符串
//     * @param msg 从activemq中获取的string命令字符串
//     */
//    @JmsListener(destination = "myQueue", containerFactory = "jmsListenerContainerQueue")
//    public void onQueueMessage(String msg)
//    {
//        System.out.println("接收到queue消息：{"+msg+"}");
//    }

    /**
     * 监听心跳包
     *
     * @param
     */
    @JmsListener(destination = "heartbeat.queue", containerFactory = "jmsListenerContainerQueue")
    public void onQueueMessage(byte[] byteStr) {
        System.out.println("监听到心跳数据------------------");
        WxUtil wxUtil = new WxUtil();
        String res = WxUtil.bytesToHexString(byteStr);
//        System.out.println(res);
        String res16 = wxUtil.convertHexToString(res);//网关心跳包使用
        System.out.println(res16);
        String gatewaySn = res16.split("_")[1];//截取网关sn

//        if(gatewaySn.length()==16){
//            System.out.println("新网关");
//            gatewaySn = wxUtil.convertHexToString(gatewaySn);//截取网关sn
//            System.out.println(gatewaySn);
//
//            Timestamp time = wxUtil.getNowDate();//获取当前时间戳
//            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//            String timeStr = sdf.format(time);
//            GatewayEntity gatewayEntity = this.gatewayRepository.findGatewayBySn(gatewaySn);//获取网关信息
//            gatewayEntity.setLastCorrespondTime(time);
//            this.gatewayRepository.save(gatewayEntity);//保存网关最后通信时间
//
//            List<String> deviceEntityList = this.deviceRepository.findAllDeviceByGatewayCode(gatewaySn);//获取网关下所有设备
//            List<String> resMsgs = Arrays.asList(res16.split("_")[0].split(""));//截取心跳包数据
//
//            String baseGateway = gatewaySn.substring(0, 6);//截取网关sn的前6位
//
//            for (int i = 1; i < resMsgs.size() + 1; i++) {
//                String resMsg = String.valueOf(i);
//                int resInt = Integer.parseInt(resMsgs.get(i - 1));
//                if (i < 10) {
//                    resMsg = "0" + resMsg;
//                }
//                String deviceSn = baseGateway + resMsg;
//
//                if (deviceEntityList.contains(deviceSn)) {
//                    if (resInt == 1) {
//                        this.deviceRepository.updateDeviceTimeByLoraId(deviceSn, resInt, timeStr);//修改按摩椅状态(0，不在线;1，在线)
//                    } else {
//                        this.deviceRepository.updateDeviceStatusByLoraId(deviceSn, resInt);
//                    }
//                }
//            }
//        }else{
//            System.out.println("老网关");
        System.out.println(gatewaySn);
        Timestamp time = wxUtil.getNowDate();//获取当前时间戳
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String timeStr = sdf.format(time);
        GatewayEntity gatewayEntity = this.gatewayRepository.findGatewayBySn(gatewaySn);//获取网关信息
        gatewayEntity.setLastCorrespondTime(time);
        this.gatewayRepository.save(gatewayEntity);//保存网关最后通信时间

        List<String> deviceEntityList = this.deviceRepository.findAllDeviceByGatewayCode(gatewaySn);//获取网关下所有设备
        List<String> resMsgs = Arrays.asList(res16.split("_")[0].split(""));//截取心跳包数据

        String baseGateway = gatewaySn.substring(0, 6);//截取网关sn的前6位

        for (int i = 1; i < resMsgs.size() + 1; i++) {
            String resMsg = String.valueOf(i);
            int resInt = Integer.parseInt(resMsgs.get(i - 1));
            if (i < 10) {
                resMsg = "0" + resMsg;
            }
            String deviceSn = baseGateway + resMsg;

            if (deviceEntityList.contains(deviceSn)) {
                if (resInt == 1) {
                    this.deviceRepository.updateDeviceTimeByLoraId(deviceSn, resInt, timeStr);//修改按摩椅状态(0，不在线;1，在线)
                } else {
                    this.deviceRepository.updateDeviceStatusByLoraId(deviceSn, resInt);
                }
            }
        }
//        }
//        String gatewaySn = wxUtil.convertHexToString(gatewaySn2);//截取网关sn
//        System.out.println(gatewaySn);


    }


    /**
     * 网关注册
     *
     * @param
     */
    @JmsListener(destination = "register.queue", containerFactory = "jmsListenerContainerQueue")
    public void onQueueMessage2(byte[] byteStr) {
        WxUtil wxUtil = new WxUtil();
        String res333 = WxUtil.bytesToHexString(byteStr);
//        System.out.println(res333);
        String res16333 = wxUtil.convertHexToString(res333);
//        System.out.println(res16333);
        String gatewaySn2 = res16333.split("_")[0];//截取网关sn
        String count = res16333.split("_")[1];//截取网关sn
        String gatewaySn = wxUtil.convertHexToString(gatewaySn2);
        System.out.println(gatewaySn);
        System.out.println(count);


        String deviceSnStr = "";
        String deviceSnStr2 = "";
        String str = "";
        String str2 = "";
        List<String> deviceEntityList = this.deviceRepository.findAllDeviceByGatewayCode(gatewaySn);//获取网关下所有设备
        if (deviceEntityList.size() != 0) {


            for (int j = 0; j < 32; j++) {
//                 if(deviceEntityList.size()>=32){
                if (j >= deviceEntityList.size()) {
                    deviceSnStr += "00000000";
                } else {
                    String devivesn = deviceEntityList.get(j);
                    deviceSnStr += devivesn;
                }
//                 }
            }


            for (int o = 1; o < 64; o++) {
                if (o >= 32) {
                    if (deviceEntityList.size() >= 32) {
                        if (o < deviceEntityList.size()) {
                            String devivesn = deviceEntityList.get(o);
                            deviceSnStr2 += devivesn;
                        } else {
                            deviceSnStr2 += "00000000";
                        }
                    } else {
                        deviceSnStr2 += "00000000";
                    }
                }
            }

            str = "faaf88210132" + deviceSnStr;
            str2 = "faaf872102" + deviceSnStr2;

            if (Integer.parseInt(count) == 1) {
                System.out.println("网关注册1------------------");
                byte[] srtbyte = WxUtil.toByteArray(str);  //字符串转化成byte[]
                byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
                String res = WxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
                str = str + res+ "_" + gatewaySn;

                // 写入消息队列
                Destination destination = new ActiveMQQueue("queue1");
                jmsProducer.sendMessage2(destination, str);
            }

            if (Integer.parseInt(count) == 2) {
                System.out.println("网关注册2------------------");
                byte[] srtbyte2 = WxUtil.toByteArray(str2);  //字符串转化成byte[]
                byte[] newByte2 = wxUtil.SumCheck(srtbyte2, 2);  //计算校验和
                String res2 = WxUtil.bytesToHexString(newByte2).toLowerCase();  //byte[]转16进制字符串
                str2 = str2 + res2+ "_" + gatewaySn;

                // 写入消息队列
                Destination destination2 = new ActiveMQQueue("queue2");
                jmsProducer.sendMessage2(destination2, str2);
            }

//        }
        }
    }


}
