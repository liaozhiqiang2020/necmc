package com.sv.mc.quartz;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.GatewayEntity;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.repository.GatewayRepository;
import com.sv.mc.service.ActivemqQueueConsumer;
import com.sv.mc.service.DeviceService;
import com.sv.mc.service.GatewayService;
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
public class ScheduleTask {
    @Autowired
    JMSProducer jmsProducer;

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    public void scheduleTest() throws JMSException {
        List<GatewayEntity>  gatewayEntityList = this.gatewayRepository.findListByProtocalType(2);//查询协议类型为2的网关

        WxUtil wxUtil = new WxUtil();
        for (int i = 0; i < gatewayEntityList.size(); i++) {
            GatewayEntity gatewayEntity = gatewayEntityList.get(i);
//            int deviceCount = gatewayEntity.getDeviceCount();
            String gatewaySn = gatewayEntity.getGatewaySn();
            String deviceSnStr="";
            String  deviceSnStr2="";
            String str = "";
            String str2 = "";
            List<String> deviceEntityList = this.deviceRepository.findAllDeviceByGatewayCode(gatewaySn);//获取网关下所有设备
            for (int j = 0; j < 32; j++) {
//                 if(deviceEntityList.size()>=32){
                     if(j>=deviceEntityList.size()){
                         deviceSnStr+="00000000";
                     }else{
                         String devivesn = deviceEntityList.get(j);
                         deviceSnStr+=devivesn;
                     }
//                 }
            }


            for(int o = 1; o < 64; o++){
                if(o>=32){
                    if(deviceEntityList.size()>=32){
                        if(o<deviceEntityList.size()){
                            String devivesn = deviceEntityList.get(o);
                            deviceSnStr2 += devivesn;
                        }else{
                            deviceSnStr2+="00000000";
                        }
                    }else{
                        deviceSnStr2+="00000000";
                    }
                }
            }

            str = "faaf88210132"+deviceSnStr;
            str2 = "faaf872102"+deviceSnStr2;

            byte[] srtbyte = WxUtil.toByteArray(str);  //字符串转化成byte[]
            byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
            String res = WxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
            str = str + res;

            byte[] srtbyte2 = WxUtil.toByteArray(str2);  //字符串转化成byte[]
            byte[] newByte2 = wxUtil.SumCheck(srtbyte2, 2);  //计算校验和
            String res2 = WxUtil.bytesToHexString(newByte2).toLowerCase();  //byte[]转16进制字符串
            str2 = str2 + res2;
            System.out.println(str);
            System.out.println(str2);

            // 写入消息队列
            Destination destination = new ActiveMQQueue(gatewaySn+".queue1");
            jmsProducer.sendMessage2(destination, str);

            // 写入消息队列
            Destination destination2 = new ActiveMQQueue(gatewaySn+".queue2");
            jmsProducer.sendMessage2(destination2, str2);
        }

    }

}
