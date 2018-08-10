package com.sv.mc.controller;

import com.sv.mc.pojo.PriceEntity;
import com.sv.mc.pojo.PriceHistoryEntity;
import com.sv.mc.service.*;
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

    @Resource
    private JMSProducer jmsProducer;
    @Resource
    private JMSConsumer jmsConsumer;

    /**
     * 小程序后台登录，向微信平台发送获取access_token请求，并返回openId
     * @param code
     * @return openid
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
     * @param openid
     * @param request
     * @author: lzq
     * @date: 2018年7月3日
     */
    @RequestMapping("/wxPay")
    @ResponseBody
    public Json wxPay(String openid, HttpServletRequest request,String paidOrderId,String money) {
       return this.weiXinPayService.wxPay(openid,request,paidOrderId,money);
    }

    /**
     * 微信支付
     * @throws Exception
     * @throws WeixinException
     * @author: lzq
     * @date: 2018年7月3日
     */
    @RequestMapping(value = "/wxNotify")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
       this.weiXinPayService.wxNotify(request,response);
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
       return this.weiXinPayService.getUserInfo(sessionkey,encryptedData,iv,openid,userInfos);
    }



//---------------------------------------------------------已支付订单相关-----------------------------------------------------------

    /**
     * 查询订单
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/findPaidOrderList")
    @ResponseBody
    public String findPaidOrderList(String openCode, int state) {
       return this.orderService.findPaidOrderList(openCode,state);
    }

    /**
     * 分页查询订单
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/findPaidOrderListByPage")
    @ResponseBody
    public String findPaidOrderListByPage(String openCode, int state,int pageNumber,int pageSize) {
        return this.orderService.findPaidOrderListByPage(openCode,state,pageNumber,pageSize);
    }

    /**
     * 根据订单号查询订单
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
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/createPaidOrder")
    @ResponseBody
    public int createPaidOrder(String openid, int mcTime, String deviceCode, String promoCode, BigDecimal money, String unPaidOrderCode,int state,int strength) {
       return this.orderService.createPaidOrder(openid,mcTime,deviceCode,promoCode,money,unPaidOrderCode,state,strength);
    }

    /**
     * 获取按摩剩余时间
     * @author: lzq
     * @date: 2018年7月6日
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
     */
    @RequestMapping("/getMcCode")
    @ResponseBody
    public Map<String,Object> getMcCode(int orderId) {
        return this.orderService.getMcCodeForMap(orderId);
    }

    /**
     * 根据订单id更新订单状态
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/updatePaidOrderById")
    @ResponseBody
    public void updatePaidOrderById(int orderId,int state,String description) {
       this.orderService.updateOrderById(orderId,state,description);
    }

    /**
     * 根据订单code更新订单状态
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/updatePaidOrderByCode")
    @ResponseBody
    public void updatePaidOrderByCode(String code,int state) {
        this.orderService.updateOrderByCode(code,state);
    }

    /**
     * 修改订单按摩开始时间，付款时间和结束时间
     * @param orderId
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/updateOrderDetail")
    @ResponseBody
    public void updateOrderDetail(int orderId,int state,int mcTime) {
        this.orderService.updateOrderDetail(orderId,state,mcTime);
    }

    /**
     * 查看服务中列表中订单状态，如果时间结束状态为1，改为2
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/servingOrderState")
    @ResponseBody
    public void servingOrderState(int orderId){
        this.orderService.servingOrderState(orderId);
    }

    /**
     * 根据设备编号查询价格列表
     * @param deviceCode
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/queryPriceAndTime")
    @ResponseBody
    public List<Map<String,Object>> queryPriceAndTime(String deviceCode){
        List<Map<String,Object>> priceList = priceService.queryPriceAndTime(deviceCode);

        return priceList;
    }

    /**
     * 发送启动按摩椅命令
     */
    @RequestMapping("/sendStartChairMsg")
    @ResponseBody
    public void sendStartChairMsg(String chairId,Integer mcTime) throws Exception{
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(chairId);
        String time = mcTime.toHexString(mcTime);
//        String time = wxUtil.convertStringToHex(String.valueOf(mcTime));
//        String message = "faaf0f09"+chairCode+"3c0000";//按摩椅20000002，60min
        if(time.length()<2){
            time = "0"+time;
        }
        String message = "faaf0f09"+chairCode+time;//按摩椅20000002，60min

        byte[] srtbyte = toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = SumCheck(srtbyte,2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte);  //byte[]转16进制字符串
        message = message+res;

        System.out.println(message);

        jmsProducer.sendMessage(message);
        deviceService.findChairRuningStatus(chairId,0);//如果设备开启成功，修改椅子状态为运行中

    }

    /**
     * 发送停止按摩椅命令
     */
    @RequestMapping("/sendEndChairMsg")
    @ResponseBody
    public void sendEndChairMsg(String chairId)throws Exception{
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(chairId);
//        jmsProducer.sendMessage("faaf0e10"+chairCode+"0000");//按摩椅20000002

        String message = "faaf0e10"+chairCode;//按摩椅20000002，60min

        byte[] srtbyte = toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = SumCheck(srtbyte,2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte);  //byte[]转16进制字符串
        message = message+res;

        jmsProducer.sendMessage(message);

        deviceService.findChairRuningStatus(chairId,1);//如果设备停止成功，修改椅子状态为空闲中
    }


    /**
     * 发送控制按摩椅强度指令
     */
    @RequestMapping("/sendStrengthChairMsg")
    @ResponseBody
    public void sendStrengthChairMsg(String chairId,int strength) throws Exception{
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(chairId);

        String message="";

        if(strength==0){//弱
            message = "faaf0e15"+chairCode;
        }else if(strength==1){//中
            message = "faaf0e16"+chairCode;
        }else if(strength==2){//强
            message = "faaf0e17"+chairCode;
        }

        byte[] srtbyte = toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = SumCheck(srtbyte,2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte);  //byte[]转16进制字符串
        message = message+res;

        jmsProducer.sendMessage(message);
        deviceService.findChairStrength(chairId,strength);
    }

    /**
     * 发送充电指令
     */
    @RequestMapping("/sendChargeMsg")
    @ResponseBody
    public void sendChargeMsg(String chairId,int chargeState) throws Exception{
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(chairId);

        String message = "";

        if(chargeState==0){//充电
            message="faaf0f13"+chairCode+"3c";
        }else if(chargeState==1){//断电
            message="faaf0e14"+chairCode;
        }

        byte[] srtbyte = toByteArray(message);  //字符串转化成byte[]
        byte[] newByte = SumCheck(srtbyte,2);  //计算校验和
        String res = wxUtil.bytesToHexString(newByte);  //byte[]转16进制字符串
        message = message+res;

        jmsProducer.sendMessage(message);
    }


    /**
     * 校验和
     *
     * @param msg 需要计算校验和的byte数组
     * @param length 校验和位数
     * @return 计算出的校验和数组
     */
    private byte[] SumCheck(byte[] msg, int length) {
        long mSum = 0;
        byte[] mByte = new byte[length];

        /** 逐Byte添加位数和 */
        for (byte byteMsg : msg) {
            long mNum = ((long)byteMsg >= 0) ? (long)byteMsg : ((long)byteMsg + 256);
            mSum += mNum;
        } /** end of for (byte byteMsg : msg) */

        /** 位数和转化为Byte数组 */
        for (int liv_Count = 0; liv_Count < length; liv_Count++) {
            mByte[length - liv_Count - 1] = (byte)(mSum >> (liv_Count * 8) & 0xff);
        } /** end of for (int liv_Count = 0; liv_Count < length; liv_Count++) */

        return mByte;
    }

    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

}