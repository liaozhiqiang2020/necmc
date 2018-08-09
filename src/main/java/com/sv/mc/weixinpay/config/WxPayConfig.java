package com.sv.mc.weixinpay.config;

/**
 * @Description:
 * @author: lzq
 * @date: 2018年7月3日
 */
public class WxPayConfig {
    //小程序appid
    public static final String appid = "wx17d911f9c0ab6c6f";
    //微信支付的商户id
//    public static final String mch_id = "1510572611";
    public static final String mch_id = "1505783071";
    //微信支付的商户密钥
    public static final String key = "ed93wmr8gjek439fhjflw9gfjewmre98";
    //支付成功后的服务器回调url
    public static final String notify_url = "https://sv-wechat-dev.natapp4.cc/mc/weixin/wxNotify";
    //签名方式
    public static final String SIGNTYPE = "MD5";
    //交易类型
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
