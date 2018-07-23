package com.sv.mc.controller;

import com.sv.mc.pojo.PriceEntity;
import com.sv.mc.pojo.PriceHistoryEntity;
import com.sv.mc.service.JMSProducer;
import com.sv.mc.service.OrderService;
import com.sv.mc.service.PriceService;
import com.sv.mc.service.WeiXinPayService;
import com.sv.mc.util.WxUtil;
import com.sv.mc.weixinpay.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @Resource
    private JMSProducer jmsProducer;

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
    public Json wxPay(String openid, HttpServletRequest request) {
       return this.weiXinPayService.wxPay(openid,request);
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
    public String findPaidOrderList(String openCode, int state,String chairCode) {
       return this.orderService.findPaidOrderList(openCode,state);
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
    public int createPaidOrder(String openid, int mcTime, String deviceCode, String promoCode, BigDecimal money, String unPaidOrderCode,int state) {
       return this.orderService.createPaidOrder(openid,mcTime,deviceCode,promoCode,money,unPaidOrderCode,state);
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
    public String getMcCode(int orderId) {
        return this.orderService.getMcCode(orderId);
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
    public void sendStartChairMsg(String chairId) throws Exception{
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(chairId);
        jmsProducer.sendMessage("faaf0f09"+chairCode+"3c0000");//按摩椅20000002，60min
    }

    /**
     * 发送停止按摩椅命令
     */
    @RequestMapping("/sendEndChairMsg")
    @ResponseBody
    public void sendEndChairMsg(String chairId)throws Exception{
        WxUtil wxUtil = new WxUtil();
        String chairCode = wxUtil.convertStringToHex(chairId);
        jmsProducer.sendMessage("faaf0e10"+chairCode+"0000");//按摩椅20000002
    }






}