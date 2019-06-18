package com.sv.mc.controller;

import com.sv.mc.pojo.GatewayEntity;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.service.GatewayService;
import com.sv.mc.service.JMSProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 网关控制层
 */
@RestController
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;
    @Autowired
    JMSProducer jmsProducer;

    /**
     * 跳转到网关管理页面
     *
     * @return 网关页面
     */
    @GetMapping(value = "/gatewayMgr/turnToGatewayMgr")
    public ModelAndView turnToGatewayMgr() {
        return new ModelAndView("./baseInfo/gatewayMgr");
    }


    /**
     * 查询所有网关信息(不分页,json数据)
     *
     * @return 所有网关信息
     */
    @GetMapping(value = "/gatewayMgr/allGateway")
    public @ResponseBody
    String getAllGateway() {
        return this.gatewayService.selectAllGatewayEnties();
    }


    /**
     * 查询所有网关信息(不分页,list)
     *
     * @return 网关信息
     */
    @GetMapping(value = "/gatewayMgr/allGatewayList")
    public @ResponseBody
    List<GatewayEntity> allGatewayList() {
        return this.gatewayService.findAllEntities();
    }

    /**
     * 添加网关
     *
     * @return 添加的网关内容
     */
    @PostMapping(value = "/gatewayMgr/insertGateway")
    public @ResponseBody
    GatewayEntity insertGateway(@RequestBody GatewayEntity gatewayEntity) throws Exception {
        gatewayEntity.setStatus(0);

        this.gatewayService.restartGateway(gatewayEntity.getGatewaySn());

//        WxUtil wxUtil = new WxUtil();
//        String deviceSnStr = "";
//        String deviceSnStr2 = "";
//        String str = "";
//        String str2 = "";
//        if (gatewayEntity.getProtocolType() == 2) {
//            List<String> deviceEntityList = this.deviceRepository.findAllDeviceByGatewayCode(gatewayEntity.getGatewaySn());//获取网关下所有设备
//            if (deviceEntityList.size() != 0) {
//                for (int j = 0; j < 32; j++) {
////                 if(deviceEntityList.size()>=32){
//                    if (j >= deviceEntityList.size()) {
//                        deviceSnStr += "00000000";
//                    } else {
//                        String devivesn = deviceEntityList.get(j);
//                        deviceSnStr += devivesn;
//                    }
////                 }
//                }
//
//
//                for (int o = 1; o < 64; o++) {
//                    if (o >= 32) {
//                        if (deviceEntityList.size() >= 32) {
//                            if (o < deviceEntityList.size()) {
//                                String devivesn = deviceEntityList.get(o);
//                                deviceSnStr2 += devivesn;
//                            } else {
//                                deviceSnStr2 += "00000000";
//                            }
//                        } else {
//                            deviceSnStr2 += "00000000";
//                        }
//                    }
//                }
//
//                str = "faaf88210132" + deviceSnStr;
//                str2 = "faaf872102" + deviceSnStr2;
//
//                byte[] srtbyte = WxUtil.toByteArray(str);  //字符串转化成byte[]
//                byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
//                String res = WxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
//                str = str + res;
//
//                byte[] srtbyte2 = WxUtil.toByteArray(str2);  //字符串转化成byte[]
//                byte[] newByte2 = wxUtil.SumCheck(srtbyte2, 2);  //计算校验和
//                String res2 = WxUtil.bytesToHexString(newByte2).toLowerCase();  //byte[]转16进制字符串
//                str2 = str2 + res2;
//                System.out.println(str);
//                System.out.println(str2);
//
//                // 写入消息队列
//                Destination destination = new ActiveMQQueue(gatewayEntity.getGatewaySn() + ".queue1");
//                jmsProducer.sendMessage2(destination, str);
//
//                // 写入消息队列
//                Destination destination2 = new ActiveMQQueue(gatewayEntity.getGatewaySn() + ".queue2");
//                jmsProducer.sendMessage2(destination2, str2);
//            }
//        }
        return this.gatewayService.save(gatewayEntity);
    }

    /**
     * 修改网关信息
     *
     * @param gatewayEntity 修改的网关信息
     * @return 网关信息
     */
    @PostMapping(value = "/gatewayMgr/updateGateway")
    public @ResponseBody
    GatewayEntity updateGateway(@RequestBody GatewayEntity gatewayEntity) throws Exception {
        GatewayEntity gatewayEntity1 = this.gatewayService.findGatewayInfoById(gatewayEntity.getId());
        if (gatewayEntity.getDeviceCount() != gatewayEntity1.getDeviceCount()) {
            this.gatewayService.restartGateway(gatewayEntity.getGatewaySn());
//            WxUtil wxUtil = new WxUtil();
//            String deviceSnStr = "";
//            String deviceSnStr2 = "";
//            String str = "";
//            String str2 = "";
//            if (gatewayEntity.getProtocolType() == 2) {
//                List<String> deviceEntityList = this.deviceRepository.findAllDeviceByGatewayCode(gatewayEntity.getGatewaySn());//获取网关下所有设备
//                if (deviceEntityList.size() != 0) {
//                    for (int j = 0; j < 32; j++) {
////                 if(deviceEntityList.size()>=32){
//                        if (j >= deviceEntityList.size()) {
//                            deviceSnStr += "00000000";
//                        } else {
//                            String devivesn = deviceEntityList.get(j);
//                            deviceSnStr += devivesn;
//                        }
////                 }
//                    }
//
//
//                    for (int o = 1; o < 64; o++) {
//                        if (o >= 32) {
//                            if (deviceEntityList.size() >= 32) {
//                                if (o < deviceEntityList.size()) {
//                                    String devivesn = deviceEntityList.get(o);
//                                    deviceSnStr2 += devivesn;
//                                } else {
//                                    deviceSnStr2 += "00000000";
//                                }
//                            } else {
//                                deviceSnStr2 += "00000000";
//                            }
//                        }
//                    }
//
//                    str = "faaf88210132" + deviceSnStr;
//                    str2 = "faaf872102" + deviceSnStr2;
//
//                    byte[] srtbyte = WxUtil.toByteArray(str);  //字符串转化成byte[]
//                    byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
//                    String res = WxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
//                    str = str + res;
//
//                    byte[] srtbyte2 = WxUtil.toByteArray(str2);  //字符串转化成byte[]
//                    byte[] newByte2 = wxUtil.SumCheck(srtbyte2, 2);  //计算校验和
//                    String res2 = WxUtil.bytesToHexString(newByte2).toLowerCase();  //byte[]转16进制字符串
//                    str2 = str2 + res2;
//                    System.out.println(str);
//                    System.out.println(str2);
//
//                    // 写入消息队列
//                    Destination destination = new ActiveMQQueue(gatewayEntity.getGatewaySn() + ".queue1");
//                    jmsProducer.sendMessage2(destination, str);
//
//                    // 写入消息队列
//                    Destination destination2 = new ActiveMQQueue(gatewayEntity.getGatewaySn() + ".queue2");
//                    jmsProducer.sendMessage2(destination2, str2);
//                }
//            }
        }

        return this.gatewayService.updateGatewayInfo(gatewayEntity);
    }

    /**
     * 修改域名端口
     *
     * @param domainName 域名名称
     * @param port       端口
     * @param gatewaySn  编号sn
     */
    @PostMapping(value = "/gatewayMgr/updateGatewayPort")
    public @ResponseBody
    void updateGatewayPort(String domainName, String port, String gatewaySn) {
        try {
            this.gatewayService.updateGatewayPort(domainName, port, gatewaySn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改频道
     *
     * @param channel   频道信息
     * @param gatewaySn 网关sn
     */
    @PostMapping(value = "/gatewayMgr/updateGatewayChannel")
    public @ResponseBody
    void updateGatewayChannel(String channel, String gatewaySn) {
        try {
            this.gatewayService.updateGatewayChannel(channel, gatewaySn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启网关
     *
     * @param gatewaySn sn
     */
    @PostMapping(value = "/gatewayMgr/restartGateway")
    public @ResponseBody
    void restartGateway(String gatewaySn) {
        try {
            this.gatewayService.restartGateway(gatewaySn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
