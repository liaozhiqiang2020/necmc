package com.sv.mc.service.impl;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.GatewayEntity;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.repository.GatewayRepository;
import com.sv.mc.repository.PlaceRepository;
import com.sv.mc.service.GatewayService;
import com.sv.mc.service.JMSProducer;
import com.sv.mc.service.PlaceService;
import com.sv.mc.util.DateJsonValueProcessor;
import com.sv.mc.util.WxUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 网关
 */
@Service
public class GatewayServiceImpl implements GatewayService {
    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private JMSProducer jmsProducer;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * 保存数据
     * @param gatewayEntity 网关信息
     * @return 网关数据
     */
    @Override
    public GatewayEntity save(GatewayEntity gatewayEntity) {
        return this.gatewayRepository.save(gatewayEntity);
    }

    /**
     * 根据id查询网关信息
     * @param id 网关Id
     * @return 网关信息
     */
    @Override
    public GatewayEntity findGatewayInfoById(int id) {
        return this.gatewayRepository.findGatewayById(id);
    }

    /**
     * 查询所有网关(不分页)
     * @return 网关集合
     */
    @Override
    public List findAllEntities() {
        return this.gatewayRepository.findAll();
    }

    /**
     * 修改网关信息
     * @param gatewayEntity 修改的网关对象
     * @return 网关对象
     */
    @Override
    public GatewayEntity updateGatewayInfo(GatewayEntity gatewayEntity){
        return this.save(gatewayEntity);
    }


    /**
     * 修改网关域名端口
     * @param domainName 域名
     * @param port 通讯端口
     * @param gatewaySn sn
     * @throws Exception
     */
    @Override
    public void updateGatewayPort(String domainName, String port,String gatewaySn)throws Exception {
        GatewayEntity gatewayEntity = this.gatewayRepository.findGatewayBySn(gatewaySn);
        gatewayEntity.setDomainName(domainName);
        gatewayEntity.setPort(port);
        this.gatewayRepository.save(gatewayEntity);

        WxUtil wxUtil = new WxUtil();
        domainName = WxUtil.strTo16(domainName);//获取域名
        int length = domainName.length();
        if(length<40){
            for (int i = 0; i < 40-length; i++) {
                domainName = domainName+"0";
            }
        }

        int gatewayPort = Integer.parseInt(port);  //获取端口号

        String strHex = Integer.toHexString(gatewayPort);//十进制转十六进制并输出

        String message = "faaf1c03"+domainName+strHex;

        byte[] srtbyte = WxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte,2);  //计算校验和
        String res = WxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message+res+"_"+gatewaySn;
        jmsProducer.sendMessage(message);
    }


    /**
     * 修改网关频道
     * @param channel 网关通讯频道
     * @param gatewaySn sn
     * @throws Exception
     */
    @Override
    public void updateGatewayChannel(String channel,String gatewaySn) throws Exception {
        GatewayEntity gatewayEntity = this.gatewayRepository.findGatewayBySn(gatewaySn);
        gatewayEntity.setChannel(channel);
        this.gatewayRepository.save(gatewayEntity);

        WxUtil wxUtil = new WxUtil();

        if (channel.length() < 2) {
            channel = "0" + channel;
        }

        String time2 = WxUtil.intToHex(Integer.parseInt(channel));//10进制转16进制
        String message = "faaf0704"+time2;
//        System.out.println(message);

        byte[] srtbyte = WxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte,2);  //计算校验和
        String res = WxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message+res+"_"+gatewaySn;
        jmsProducer.sendMessage(message);
    }


    /**
     * 重启网关
     * @param gatewaySn sn
     * @throws Exception
     */
    @Override
    public void restartGateway(String gatewaySn) throws Exception {
        WxUtil wxUtil = new WxUtil();

        String message = "faaf0605";

        byte[] srtbyte3 = WxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte3 = wxUtil.SumCheck(srtbyte3,2);  //计算校验和
        String res3 = WxUtil.bytesToHexString(newByte3).toLowerCase();  //byte[]转16进制字符串
        message = message+res3+"_"+gatewaySn;
        jmsProducer.sendMessage(message);
    }

    /**
     * 根据网关SN查询网关信息
     * @param sn 网关sn
     * @return  网关信息
     */
    @Override
    public GatewayEntity selectGateBySn(String sn) {
        return this.gatewayRepository.findGatewayBySn(sn);
    }

    /**
     * 查询所有网关信息(不分页,json数据)
     */
    @Override
    public String selectAllGatewayEnties() {
        List<GatewayEntity> gatewayEntityList = this.gatewayRepository.findAll();
        int total = this.gatewayRepository.findGatewayEntitsCount();
        WxUtil wxUtil = new WxUtil();

        Timestamp nowTime = wxUtil.getNowDate();

        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        config.setExcludes(new String[]{"priceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray

        JSONArray jsonArray = JSONArray.fromObject(gatewayEntityList,config);
        JSONArray jsonArray1 = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        for (int i = 0; i < jsonArray.size(); i++) {
            GatewayEntity gatewayEntity = gatewayEntityList.get(i);

            String placeName = "";
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int placeId = Integer.parseInt(jsonObject.get("placeId").toString());

            if(gatewayEntity.getLastCorrespondTime()!=null){
                Timestamp time = Timestamp.valueOf(jsonObject.get("lastCorrespondTime").toString());
                long result = (nowTime.getTime()-time.getTime())/(1000);  //计算两个时间戳相差的秒数(当前时间-最后通信时间)

                if(result<120 && result>=0){//如果当前时间和网关最后通信时间相差2分钟以内，网关在线
                    gatewayEntity.setStatus(1);
                    this.gatewayRepository.save(gatewayEntity);
                    jsonObject.put("status", 1);
                    jsonObject.put("statusName", "在线");
                }else{
                    gatewayEntity.setStatus(0);
                    this.gatewayRepository.save(gatewayEntity);
                    jsonObject.put("status", 0);
                    jsonObject.put("statusName", "不在线");
                }
            }else{
                jsonObject.put("lastCorrespondTime", "");
                gatewayEntity.setStatus(0);
                this.gatewayRepository.save(gatewayEntity);
                jsonObject.put("status", 0);
                jsonObject.put("statusName", "不在线");
            }

            placeName = this.placeRepository.findPlaceById(placeId).getName();//查询场地名称

            jsonObject.put("placeName", placeName);

            if(gatewayEntity.getProtocolType()==1){
                jsonObject.put("protocolTypeName", "老协议");
            }
            if(gatewayEntity.getProtocolType()==2){
                jsonObject.put("protocolTypeName", "眯会儿协议");
            }

            jsonArray1.add(jsonObject);


        }
        jsonObject1.put("data",jsonArray1.toString());
        jsonObject1.put("total",total);

        return jsonObject1.toString();
    }

    /**
     * 根据网关sn查询网关下所有设备
     * @return 网关下设备
     */
    @Override
    public List<String> findAllDeviceByGatewayCode(String sn) {
        List<String> list = this.deviceRepository.findAllDeviceByGatewayCode(sn);
        return list;
    }

    /**
     * 删除网关
     * @param sn
     * @return
     */
    @Override
    public String deleteGateway(String sn) {
        String msg = "";
        List<String> deviceEntities = this.deviceRepository.findAllDeviceByGatewayCode(sn);
        if(deviceEntities.size()>0){
            msg = "请先删除该网关下的设备";
        }else{
            int result = this.gatewayRepository.deleteGateway(sn);
            if(result>0){
                msg="删除成功";
            }else{
                msg="删除失败";
            }
        }

        return msg;
    }


    @Override
    public GatewayEntity insertGateway(Map map) {


        return null;
    }

    @Override
    public GatewayEntity updateGateway(Map map) {
        return null;
    }


}
