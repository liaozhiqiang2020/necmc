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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
     *
     * @param gatewayEntity
     * @return
     */
    @Override
    public GatewayEntity save(GatewayEntity gatewayEntity) {
        return this.gatewayRepository.save(gatewayEntity);
    }

    /**
     * 根据id查询网关信息
     *
     * @param id
     * @return
     */
    @Override
    public GatewayEntity findGatewayInfoById(int id) {
        return this.gatewayRepository.findGatewayById(id);
    }

    /**
     * 查询所有网关(不分页)
     *
     * @return
     */
    @Override
    public List findAllEntities() {
        return this.gatewayRepository.findAll();
    }

    /**
     * 修改网关信息
     * @param gatewayEntity
     * @return
     */
    @Override
    public GatewayEntity updateGatewayInfo(GatewayEntity gatewayEntity){
        return this.save(gatewayEntity);
    }

    /**
     * 修改网关域名端口
     */
    @Override
    public void updateGatewayPort(String domainName, String port,String gatewaySn)throws Exception {
        GatewayEntity gatewayEntity = this.gatewayRepository.findGatewayBySn(gatewaySn);
        gatewayEntity.setDomainName(domainName);
        gatewayEntity.setPort(port);
        this.gatewayRepository.save(gatewayEntity);

        WxUtil wxUtil = new WxUtil();
        domainName = wxUtil.strTo16(domainName);//获取域名
        int length = domainName.length();
        if(length<40){
            for (int i = 0; i < 40-length; i++) {
                domainName = domainName+"0";
            }
        }

        int gatewayPort = Integer.parseInt(port);  //获取端口号

        String strHex = Integer.toHexString(gatewayPort);//十进制转十六进制并输出

        String message = "faaf1c03"+domainName+strHex;

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte,2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message+res+"_"+gatewaySn;
        jmsProducer.sendMessage(message);
    }

    /**
     * 修改网关频道
     */
    @Override
    public void updateGatewayChannel(String channel,String gatewaySn) throws Exception {
        GatewayEntity gatewayEntity = this.gatewayRepository.findGatewayBySn(gatewaySn);
        gatewayEntity.setChannel(channel);
        this.gatewayRepository.save(gatewayEntity);

        WxUtil wxUtil = new WxUtil();

        String message = "faaf0704"+channel;

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte,2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message+res+"_"+gatewaySn;
        jmsProducer.sendMessage(message);
    }

    /**
     * 重启网关
     */
    @Override
    public void restartGateway(String gatewaySn) throws Exception {
        WxUtil wxUtil = new WxUtil();

        String message = "faaf0605";

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte,2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message+res+"_"+gatewaySn;
        jmsProducer.sendMessage(message);
    }

    /**
     * 根据网关SN查询网关信息
     * @param sn
     * @return
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
            jsonArray1.add(jsonObject);
        }
        jsonObject1.put("data",jsonArray1.toString());
        jsonObject1.put("total",total);

        return jsonObject1.toString();
    }

    /**
     * 根据网关sn查询网关下所有设备
     * @return
     */
    @Override
    public List<String> findAllDeviceByGatewayCode(String sn) {
        List<String> list = this.deviceRepository.findAllDeviceByGatewayCode(sn);
        return list;
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
