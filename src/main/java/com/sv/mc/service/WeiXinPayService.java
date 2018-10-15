package com.sv.mc.service;

import com.sv.mc.weixinpay.vo.Json;
import org.weixin4j.WeixinException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 微信支付服务
 * @author: lzq
 * @date: 2018年7月3日
 */
public interface WeiXinPayService {
    /**
     * 小程序后台登录，向微信平台发送获取access_token请求，并返回openId
     * @param code 登陆请求
     * @return openid
     * @author: lzq
     * @date: 2018年7月3日
     */
    Map<String, Object> login(String code)throws WeixinException, IOException;


    /**
     * 发起微信支付
     * @param openid 用户唯一Id
     * @param request 请求
     * @param paidOrderId 订单号
     * @param money 钱数
     * @return 微信支付信息
     */
    Json wxPay(String openid, HttpServletRequest request,String paidOrderId,String money);

    /**
     * 微信支付
     * @param request 支付请求
     * @param response 响应
     * @throws Exception
     */
    void wxNotify(HttpServletRequest request, HttpServletResponse response)throws Exception ;

    /**
     * 获取用户信息
     * @param sessionkey 用户主键
     * @param encryptedData 加密信息
     * @param iv 输入
     * @param openid 用户唯一Id
     * @param userInfos 用户信息
     * @return 用户信息
     */
    String getUserInfo(String sessionkey, String encryptedData, String iv, String openid, String userInfos);

    /**
     * 发送启动按摩椅命令
     * @param chairId
     * @param mcTime
     * @throws Exception
     */
    void sendStartChairMsg(String chairId, Integer mcTime) throws Exception;
}
