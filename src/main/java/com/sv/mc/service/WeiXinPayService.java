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
     * @param code
     * @return
     * @author: lzq
     * @date: 2018年7月3日
     */
    Map<String, Object> login(String code)throws WeixinException, IOException;

    /**
     * 发起微信支付
     * @param openid
     * @return
     * @author: lzq
     * @date: 2018年7月3日
     */
    Json wxPay(String openid, HttpServletRequest request);

    /**
     * 微信支付
     * @author: lzq
     * @date: 2018年7月3日
     */
    void wxNotify(HttpServletRequest request, HttpServletResponse response)throws Exception ;

    /**
     * 获取用户信息
     * @param sessionkey
     * @param encryptedData
     * @param iv
     * @param openid
     * @param userInfos
     * @return
     */
    String getUserInfo(String sessionkey, String encryptedData, String iv, String openid, String userInfos);
}
