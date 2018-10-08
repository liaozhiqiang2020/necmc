package com.sv.mc.controller;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.PriceEntity;
import com.sv.mc.pojo.PriceHistoryEntity;
import com.sv.mc.pojo.WxUserInfoEntity;
import com.sv.mc.service.*;
import com.sv.mc.util.SingletonHungary;
import com.sv.mc.util.WxUtil;
import com.sv.mc.weixinpay.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.weixin4j.WeixinException;
import org.weixin4j.WeixinSupport;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 微信小程序相关控制
 *
 * @author: lzq
 * @date: 2018年7月3日
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController extends WeixinSupport {
    @Autowired
    private OrderService orderService;
    @Autowired
    private WeiXinPayService weiXinPayService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private WxUserInfoService wxUserInfoService;

    @Resource
    private JMSProducer jmsProducer;
    @Resource
    private JMSConsumer jmsConsumer;

    /**
     * 小程序后台登录，向微信平台发送获取access_token请求，并返回openId
     *
     * @param code
     * @return openid 用户唯一标识
     * @throws WeixinException
     * @throws IOException
     * @author: lzq
     * @date: 2018年7月3日
     * @since Weixin4J 1.0.0
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String code) throws WeixinException, IOException {
        return this.weiXinPayService.login(code);
    }

    /**
     * 发起微信支付
     *
     * @param openid 用户唯一标识
     * @param request
     * @author: lzq
     * @date: 2018年7月3日
     */
    @RequestMapping("/wxPay")
    @ResponseBody
    public Json wxPay(String openid, HttpServletRequest request, String paidOrderId, String money) {
        return this.weiXinPayService.wxPay(openid, request, paidOrderId, money);
    }

    /**
     * 微信支付
     *
     * @throws Exception
     * @throws WeixinException
     * @author: lzq
     * @date: 2018年7月3日
     */
    @RequestMapping(value = "/wxNotify")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.weiXinPayService.wxNotify(request, response);
    }


    /**
     * 获取用户信息
     * @throws WeixinException
     * @throws IOException
     * @author: lzq
     * @date: 2018年7月3日
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public String getUserInfo(String sessionkey, String encryptedData, String iv, String openid, String userInfos) {
        return this.weiXinPayService.getUserInfo(sessionkey, encryptedData, iv, openid, userInfos);
    }

    /**
     * 保存用户信息
     * @param userInfo  用户信息
     * @param openId 用户唯一标识
     */
    @RequestMapping("/saveUserInfo")
    @ResponseBody
    public void saveUserInfo(String openId, String userInfo) {

        this.wxUserInfoService.saveUserInfoAndPhoneAndOpenId(openId, userInfo);
    }

    /**
     * 查询用户信息
     * @param openId 用户唯一标识
     * @return 用户结果对象
     */
    @RequestMapping("/findWxUserInfoByOpenId")
    @ResponseBody
    public WxUserInfoEntity findWxUserInfoByOpenId(String openId) {
        WxUserInfoEntity wxUserInfoEntity = this.wxUserInfoService.findWxUserInfoByOpenId(openId);
        return wxUserInfoEntity;
    }

//-----------------------------------------------小程序查询设备状态---start-------------------------------
    /**
     * 查询设备状态(小程序)
     * @param chairId 设备id
     * @return
     */
    @RequestMapping("/findChairStatus")
    @ResponseBody
    public int findChairStatus(String chairId) {
        int mcStatus = 5;//没有收到返回的消息
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);//获取设备信息
        if (deviceEntity != null) {
            String deviceId = deviceEntity.getLoraId();//获取模块id
            Object object = SingletonHungary.getSingleTon().get(deviceEntity.getLoraId() + "status");//从map中取值
            if (object != null) {
                String info = object.toString();
                String chairId2 = info.split("_")[0];
                if (chairId2.equals(deviceId)) {
                    mcStatus = Integer.parseInt(info.split("_")[1]);
                }
            }
        }
        return mcStatus;
    }

    /**
     * 查询设备是否开启成功(小程序)
     * @param chairId 椅子Id
     * @return 椅子状态
     */
    @RequestMapping("/findChairRuning")
    @ResponseBody
    public int findChairRuning(String chairId) {
        int mcRuning = 4;//没有收到返回的消息
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);//获取设备信息
        if (deviceEntity != null) {
            String deviceId = deviceEntity.getLoraId();//获取模块id
            Object object = SingletonHungary.getSingleTon().get(deviceEntity.getLoraId() + "runing");//从map中取值
            if (object != null) {
                String info = object.toString();
                String chairId2 = info.split("_")[0];
                if (chairId2.equals(deviceId)) {
                    mcRuning = Integer.parseInt(info.split("_")[1]);
                }
            }
        }
        return mcRuning;
    }


    /**
     * 清空设备状态
     * @param chairId 设备Id
     */
    @RequestMapping("/cleanDeviceStatus")
    @ResponseBody
    public void cleanDeviceStatus(String chairId) {
//        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(deviceSn);//获取设备信息
        SingletonHungary.getSingleTon().remove(chairId+"status");
        SingletonHungary.getSingleTon().remove(chairId+"runing");
    }
//-----------------------------------------------小程序查询设备状态---end-------------------------------


//-----------------------------------------------后台管理系统查询设备状态---start-------------------------------
    /**
     * 查询设备状态(后台管理系统)
     *
     * @return 设备状态
     */
    @RequestMapping("/findChairStatusSys")
    @ResponseBody
    public int findChairStatusSys(String chairId) {
        int mcStatus = 5;//没有收到返回的消息
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);//获取设备信息
        if (deviceEntity != null) {
            String deviceId = deviceEntity.getLoraId();//获取模块id
            Object object = SingletonHungary.getSingleTon().get(deviceEntity.getLoraId() + "statusSys");//从map中取值
            if (object != null) {
                String info = object.toString();
                String chairId2 = info.split("_")[0];
                if (chairId2.equals(deviceId)) {
                    mcStatus = Integer.parseInt(info.split("_")[1]);
                }
            }
        }
        return mcStatus;
    }

    /**
     * 查询设备是否开启成功(后台管理系统)
     * @param chairId 椅子Id
     * @return 开启状态
     */
    @RequestMapping("/findChairRuningSys")
    @ResponseBody
    public int findChairRuningSys(String chairId) {
        int mcRuning = 4;//没有收到返回的消息
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);//获取设备信息
        if (deviceEntity != null) {
            String deviceId = deviceEntity.getLoraId();//获取模块id
            Object object = SingletonHungary.getSingleTon().get(deviceEntity.getLoraId() + "runingSys");//从map中取值
            if (object != null) {
                String info = object.toString();
                String chairId2 = info.split("_")[0];
                if (chairId2.equals(deviceId)) {
                    mcRuning = Integer.parseInt(info.split("_")[1]);
                }
            }
        }
        return mcRuning;
    }
//-----------------------------------------------后台管理系统查询设备状态---end-------------------------------


//---------------------------------------------------------已支付订单相关-----------------------------------------------------------

    /**
     * 查询订单
     * @param state  订单状态
     * @param openCode 订单编码
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/findPaidOrderList")
    @ResponseBody
    public String findPaidOrderList(String openCode, int state) {
        return this.orderService.findPaidOrderList(openCode, state);
    }

    /**
     * 分页查询订单
     * @author: lzq
     * @date: 2018年7月6日
     * @param openCode 订单编码
     * @param state  订单状态
     * @param pageSize  截至个数
     * @param pageNumber 起始个数
     * @return 订单结果
     */
    @RequestMapping("/findPaidOrderListByPage")
    @ResponseBody
    public String findPaidOrderListByPage(String openCode, int state, int pageNumber, int pageSize) {
        return this.orderService.findPaidOrderListByPage(openCode, state, pageNumber, pageSize);
    }

    /**
     * 根据订单号查询订单
     * @param orderId 订单Id
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/findPaidOrderById")
    @ResponseBody
    public String findPaidOrderById(int orderId) {
        return this.orderService.findPaidOrderById(orderId);
    }


    /**
     * 创建订单
     * @param openid 用户 唯一标识
     * @param mcTime 按摩时长
     * @param deviceCode 设备编码
     * @param promoCode  优惠码
     * @param money 价格
     * @param unPaidOrderCode 未付款订单号
     * @param state 状态
     * @param strength 按摩强度
     * @return 创建的订单条数
     */
    @RequestMapping("/createPaidOrder")
    @ResponseBody
    public int createPaidOrder(String openid, int mcTime, String deviceCode, String promoCode, BigDecimal money, String unPaidOrderCode, int state, int strength) {
        return this.orderService.createPaidOrder(openid, mcTime, deviceCode, promoCode, money, unPaidOrderCode, state, strength);
    }

    /**
     * 获取按摩剩余时间
     * @param orderId 订单id
     * @author: lzq
     * @date: 2018年7月6日
     * @return 按摩剩余时间
     */
    @RequestMapping("/getMcRemainingTime")
    @ResponseBody
    public long getMcRemainingTime(int orderId) {
        return this.orderService.getMcRemainingTime(orderId);
    }

    /**
     * 获取按摩椅code
     * @author: lzq
     * @date: 2018年7月6日
     * @param orderId  订单id
     * @return  按摩椅code
     */
    @RequestMapping("/getMcCode")
    @ResponseBody
    public Map<String, Object> getMcCode(int orderId) {
        return this.orderService.getMcCodeForMap(orderId);
    }


    /**
     * 根据orderId查询按摩椅编号
     * @param orderId 订单id
     * @author: lzq
     * @date: 2018年7月6日
     * @return 按摩椅编号
     */
    @RequestMapping("/getChairIdByOrderId")
    @ResponseBody
    public String getChairIdByOrderId(int orderId) {
        return this.orderService.getMcCode(orderId);
    }

    /**
     * 根据订单id更新订单状态
     * @author: lzq
     * @date: 2018年7月6日
     * @param orderId 订单Id
     * @param state 订单状态
     * @param description 订单描述
     */
    @RequestMapping("/updatePaidOrderById")
    @ResponseBody
    public void updatePaidOrderById(int orderId, int state, String description) {
        this.orderService.updateOrderById(orderId, state, description);
    }

    /**
     * 根据订单code更新订单状态
     * @param state 订单状态
     * @param code  订单编码
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/updatePaidOrderByCode")
    @ResponseBody
    public void updatePaidOrderByCode(String code, int state) {
        this.orderService.updateOrderByCodeState(code, state);
    }

    /**
     * 修改订单按摩开始时间，付款时间和结束时间
     *
     * @param orderId 订单Id
     * @param state  订单状态
     * @param mcTime  按摩时间
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/updateOrderDetail")
    @ResponseBody
    public void updateOrderDetail(int orderId, int state, int mcTime) {
        this.orderService.updateOrderDetail(orderId, state, mcTime);
    }

    /**
     * 查看服务中列表中订单状态，如果时间结束状态为1，改为2
     * @param orderId 订单Id
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/servingOrderState")
    @ResponseBody
    public void servingOrderState(int orderId) {
        this.orderService.servingOrderState(orderId);
    }
    /**
     * 根据设备编号查询价格列表
     * @param deviceCode 设备编码
     * @return 价格列表
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/queryPriceAndTime")
    @ResponseBody
    public List<Map<String, Object>> queryPriceAndTime(String deviceCode) {
        List<Map<String, Object>> priceList = priceService.queryPriceAndTime(deviceCode);

        return priceList;
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    /**
     * 发送查询查询设备状态指令
     * @param chairId 椅子Id
     */
    @RequestMapping("/sendFindChairStatus")
    @ResponseBody
    public String sendFindChairStatus(String chairId) throws Exception {
        String result = "设备异常！";
        WxUtil wxUtil = new WxUtil();
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);//获取设备信息
        if (deviceEntity != null) {
            String deviceId = deviceEntity.getLoraId();//获取模块id
            String chairCode = wxUtil.convertStringToHex(deviceId);
            String gatewayId = deviceEntity.getGatewayEntity().getGatewaySn();//网关sn

            String message = "faaf0e08" + chairCode;

            byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
            byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
            String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
            message = message + res + "_" + gatewayId;

            jmsProducer.sendMessage(message);
            result = null;
        }
        return result;
    }

    /**
     * 发送启动按摩椅命令
     * @param chairId 椅子id
     * @param mcTime 按摩时长
     */
    @RequestMapping("/sendStartChairMsg")
    @ResponseBody
    public void sendStartChairMsg(String chairId, Integer mcTime) throws Exception {
        WxUtil wxUtil = new WxUtil();
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);//获取设备信息
        if (deviceEntity != null) {
            String deviceId = deviceEntity.getLoraId();//获取模块id
            String chairCode = wxUtil.convertStringToHex(deviceId);
            String gatewayId = deviceEntity.getGatewayEntity().getGatewaySn();//网关sn
            String time = mcTime.toHexString(mcTime);
            if (time.length() < 2) {
                time = "0" + time;
            }
            String message = "faaf0f09" + chairCode + time;//按摩椅20000002，60min

            byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
            byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
            String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
            message = message + res + "_" + gatewayId;

            jmsProducer.sendMessage(message);
        }
//        deviceService.findChairRuningStatus(chairId,0);//如果设备开启成功，修改椅子状态为运行中
    }

    /**
     * 发送停止按摩椅命令
     * @param chairId 设备id
     */
    @RequestMapping("/sendEndChairMsg")
    @ResponseBody
    public void sendEndChairMsg(String chairId) throws Exception {
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(deviceEntity.getLoraId());
        String gatewayId = deviceEntity.getGatewayEntity().getGatewaySn();//网关sn
//        jmsProducer.sendMessage("faaf0e10"+chairCode+"0000");//按摩椅20000002

        String message = "faaf0e10" + chairCode;//按摩椅20000002，60min

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message + res + "_" + gatewayId;

        jmsProducer.sendMessage(message);

//        deviceService.findChairRuningStatus(chairId,1);//如果设备停止成功，修改椅子状态为空闲中
    }


    /**
     * 发送控制按摩椅强度指令
     * @param chairId  椅子id
     * @param strength  按摩强度
     */
    @RequestMapping("/sendStrengthChairMsg")
    @ResponseBody
    public void sendStrengthChairMsg(String chairId, int strength) throws Exception {
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(deviceEntity.getLoraId());
        String gatewayId = deviceEntity.getGatewayEntity().getGatewaySn();//网关sn

        String message = "";

        if (strength == 0) {//弱
            message = "faaf0e15" + chairCode;
        } else if (strength == 1) {//中
            message = "faaf0e16" + chairCode;
        } else if (strength == 2) {//强
            message = "faaf0e17" + chairCode;
        }

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message + res + "_" + gatewayId;

        jmsProducer.sendMessage(message);
        deviceService.findChairStrength(chairId, strength);
    }

    /**
     * 发送继续和暂停按摩椅命令
     * @param chairId 椅子Id
     * @param continueType 按摩指令
     */
    @RequestMapping("/sendContinueChairMsg")
    @ResponseBody
    public void sendContinueChairMsg(String chairId, int continueType) throws Exception {
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(deviceEntity.getLoraId());
        String gatewayId = deviceEntity.getGatewayEntity().getGatewaySn();//网关sn

        String message = "";
        if (continueType == 1) {//继续
            message = "faaf0e19" + chairCode;
        } else if (continueType == 0) {//暂停
            message = "faaf0e18" + chairCode;
        }

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message + res + "_" + gatewayId;

        jmsProducer.sendMessage(message);
    }

    /**
     * 查询按摩椅状态
     * @param chairId 椅子Id
     * @throws Exception
     */
    @RequestMapping("/selectMcStatus")
    @ResponseBody
    public void selectMcStatus(String chairId) throws Exception {
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(deviceEntity.getLoraId());
        String gatewayId = deviceEntity.getGatewayEntity().getGatewaySn();//网关sn

        String message = "faaf0e08" + chairCode;

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message + res + "_" + gatewayId;

        jmsProducer.sendMessage(message);
    }


    /**
     * 发送充电指令
     * @param chairId 椅子Id
     * @param mcTime 按摩时长
     */
    @RequestMapping("/sendChargeMsg")
    @ResponseBody
    public void sendChargeMsg(String chairId, Integer mcTime) throws Exception {
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(deviceEntity.getLoraId());
        String gatewayId = deviceEntity.getGatewayEntity().getGatewaySn();//网关sn
        String time = mcTime.toHexString(mcTime);
        if (time.length() < 2) {
            time = "0" + time;
        }

        String message = "faaf0f13" + chairCode + time;

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message + res + "_" + gatewayId;

        jmsProducer.sendMessage(message);
    }

    /**
     * 发送断电指令
     * @param chairId 椅子Id
     */
    @RequestMapping("/sendUnChargeMsg")
    @ResponseBody
    public void sendUnChargeMsg(String chairId) throws Exception {
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(deviceEntity.getLoraId());
        String gatewayId = deviceEntity.getGatewayEntity().getGatewaySn();//网关sn

        String message = "faaf0e14" + chairCode;

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message + res + "_" + gatewayId;

        jmsProducer.sendMessage(message);
    }

    /**
     * 发送修改椅子频道指令
     * @param chairId 椅子Id
     * @param channel 频道指令
     */
    @RequestMapping("/sendUpdateChannel")
    @ResponseBody
    public void sendUpdateChannel(String chairId, Integer channel) throws Exception {
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(deviceEntity.getLoraId());
        String gatewayId = deviceEntity.getGatewayEntity().getGatewaySn();//网关sn

        String time = channel.toString();
        if (time.length() < 2) {
            time = "0" + time;
        }

        String message = "faaf0f11" + chairCode + time;

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message + res + "_" + gatewayId;

        jmsProducer.sendMessage(message);
    }


    /**
     * 发送修改椅子编号指令
     * @param chairId 椅子Id
     * @param newChairId 新椅子id
     */
    @RequestMapping("/sendUpdateCode")
    @ResponseBody
    public void sendUpdateCode(String chairId, String newChairId) throws Exception {
        WxUtil wxUtil = new WxUtil();
        DeviceEntity deviceEntity = this.deviceService.selectDeviceBYSN(chairId);
        String gatewayId = deviceEntity.getGatewayEntity().getGatewaySn();//网关sn

        String chairCode = wxUtil.convertStringToHex(deviceEntity.getLoraId());

        deviceEntity.setLoraId(newChairId);
        this.deviceService.save(deviceEntity);

        String newChairCode = wxUtil.convertStringToHex(newChairId);

        String message = "faaf1612" + chairCode + newChairCode;

        byte[] srtbyte = wxUtil.toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
        message = message + res + "_" + gatewayId;

        jmsProducer.sendMessage(message);
    }

}