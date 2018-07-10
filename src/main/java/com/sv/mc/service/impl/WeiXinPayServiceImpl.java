package com.sv.mc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv.mc.pojo.WxUserInfoEntity;
import com.sv.mc.service.WeiXinPayService;
import com.sv.mc.service.WxUserInfoService;
import com.sv.mc.util.WxUtil;
import com.sv.mc.weixinpay.config.WxPayConfig;
import com.sv.mc.weixinpay.utils.AESDecodeUtils;
import com.sv.mc.weixinpay.utils.IpUtils;
import com.sv.mc.weixinpay.utils.PayUtil;
import com.sv.mc.weixinpay.utils.StringUtils;
import com.sv.mc.weixinpay.vo.Json;
import com.sv.mc.weixinpay.vo.OAuthJsToken;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.weixin4j.WeixinException;
import org.weixin4j.http.HttpsClient;
import org.weixin4j.http.Response;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付接口实现
 * @author: lzq
 * @date: 2018年7月3日
 */
@Service
public class WeiXinPayServiceImpl implements WeiXinPayService{
    @Autowired
    private WxUserInfoService wxUserInfoService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String appid = "wxef6b91b1a0f63519";        //微信小程序appid
    private static final String secret = "0db1ee3366332ed66650ce0acce409a7";    //微信小程序密钥
    private static final String grant_type = "authorization_code";


    /**
     * 小程序后台登录，向微信平台发送获取access_token请求，并返回openId
     * @param code
     * @return
     * @author: lzq
     * @date: 2018年7月3日
     */
    @Override
    public Map<String, Object> login(String code) throws WeixinException,IOException {
        if (code == null || code.equals("")) {
            throw new WeixinException("invalid null, code is null.");
        }

        Map<String, Object> ret = new HashMap<>();
        //拼接参数
        String param = "?grant_type=" + grant_type + "&appid=" + appid + "&secret=" + secret + "&js_code=" + code;

        System.out.println("----------------------------------------------" + code);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //调用获取access_token接口
        Response res = http.get("https://api.weixin.qq.com/sns/jscode2session" + param);
        //根据请求结果判定，是否验证成功
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj != null) {
            Object errcode = jsonObj.get("errcode");
            if (errcode != null) {
                //返回异常信息
//                throw new WeixinException(getCause(Integer.parseInt(errcode.toString())));
                  throw new WeixinException(errcode.toString());
            }

            ObjectMapper mapper = new ObjectMapper();
            OAuthJsToken oauthJsToken = mapper.readValue(jsonObj.toString(), OAuthJsToken.class);

            logger.info("openid=" + oauthJsToken.getOpenid());

            WxUserInfoEntity wxUserInfoEntity = this.wxUserInfoService.findWxUserInfoByOpenId(oauthJsToken.getOpenid());//根据openId查询用户信息
            if(wxUserInfoEntity!=null){
                ret.put("phoneNumber",wxUserInfoEntity.getPhoneNumber());//绑定手机号
            }

            ret.put("openid", oauthJsToken.getOpenid());
            ret.put("sessionKey", oauthJsToken.getSession_key());
        }
        return ret;
    }

    /**
     * @param openid
     * @param request
     * @Description: 发起微信支付
     * @author: lzq
     * @date: 2018年7月3日
     */
    @Override
    public Json wxPay(String openid,HttpServletRequest request) {
        Json json = new Json();
        try {
            //生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
//            System.out.println(nonce_str);
            //商品名称
            String body = "测试商品名称";
            //获取本机的ip地址
            String spbill_create_ip = IpUtils.getIpAddr(request);

            String orderNo = "123456789";
            String money = "1";//支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", WxPayConfig.appid);
            packageParams.put("mch_id", WxPayConfig.mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", orderNo);//商户订单号
            packageParams.put("total_fee", money);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", WxPayConfig.notify_url);
            packageParams.put("trade_type", WxPayConfig.TRADETYPE);
            packageParams.put("openid", openid);

            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);
            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + WxPayConfig.appid + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + WxPayConfig.notify_url + "</notify_url>"
                    + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + orderNo + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + money + "</total_fee>"
                    + "<trade_type>" + WxPayConfig.TRADETYPE + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(WxPayConfig.pay_url, "POST", xml);

            System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码

            //返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            if (return_code == "SUCCESS" || return_code.equals(return_code)) {
                // 业务结果
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

                String stringSignTemp = "appId=" + WxPayConfig.appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=" + WxPayConfig.SIGNTYPE + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, WxPayConfig.key, "utf-8").toUpperCase();
                logger.info("=======================第二次签名：" + paySign + "=====================");

                response.put("paySign", paySign);

                //更新订单信息
                //业务逻辑代码
            }

            response.put("appid", WxPayConfig.appid);

            json.setSuccess(true);
            json.setData(response);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("发起失败");
        }
        return json;
    }

    /**
     * 微信支付
     * @author: lzq
     * @date: 2018年7月3日
     */
    @Override
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        System.out.println("接收到的报文：" + notityXml);

        Map map = PayUtil.doXMLParse(notityXml);

        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确
            if (PayUtil.verify(PayUtil.createLinkString(map), (String) map.get("sign"), WxPayConfig.key, "utf-8")) {
                /**此处添加自己的业务逻辑代码start**/


                /**此处添加自己的业务逻辑代码end**/

                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");

        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    /**
     * 获取用户信息
     * @param sessionkey
     * @param encryptedData
     * @param iv
     * @param openid
     * @param userInfos
     * @return
     * @author: lzq
     * @date: 2018年7月3日
     */
    @Override
    public String getUserInfo(String sessionkey, String encryptedData, String iv, String openid, String userInfos) {
        byte[] encrypData = Base64.decodeBase64(encryptedData);
        byte[] ivData = Base64.decodeBase64(iv);
        byte[] sessionKey = Base64.decodeBase64(sessionkey);

        String userInfo = "";

        AESDecodeUtils aesDecodeUtils = new AESDecodeUtils();
        try {
            //根据sessionKey和iv，对encrypData进行解码，获取用户信息
            userInfo = aesDecodeUtils.decrypt(sessionKey, ivData, encrypData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WxUserInfoEntity wxUserInfoEntity;

        //用户手机号信息
        JSONObject jsonObject = JSONObject.fromObject(userInfo.toString());
        String phoneNumber = jsonObject.get("phoneNumber").toString();
        String purePhoneNumber = jsonObject.get("purePhoneNumber").toString();
        String countryCode = jsonObject.get("countryCode").toString();

        //用户基本信息
        JSONObject jsonObject1 = JSONObject.fromObject(userInfos.toString());
        String nickName = jsonObject1.get("nickName").toString();
        String gender = jsonObject1.get("gender").toString();
        String language = jsonObject1.get("language").toString();
        String city = jsonObject1.get("city").toString();
        String province = jsonObject1.get("province").toString();
        String country = jsonObject1.get("country").toString();
        String avatarUrl = jsonObject1.get("avatarUrl").toString();

        WxUtil wxUtil = new WxUtil();

        //存入微信用户基本信息
        wxUserInfoEntity = this.wxUserInfoService.findWxUserInfoByOpenId(openid);
        //如果当前用户信息不为空，修改原来的信息
        if (wxUserInfoEntity != null) {
            wxUserInfoEntity.setId(wxUserInfoEntity.getId());
            wxUserInfoEntity.setPhoneNumber(phoneNumber);
            wxUserInfoEntity.setPurePhoneNumber(purePhoneNumber);
            wxUserInfoEntity.setCountryCode(countryCode);
            wxUserInfoEntity.setNickName(nickName);
            wxUserInfoEntity.setGender(gender);
            wxUserInfoEntity.setLanguage(language);
            wxUserInfoEntity.setCity(city);
            wxUserInfoEntity.setProvince(province);
            wxUserInfoEntity.setCountry(country);
            wxUserInfoEntity.setAvatarUrl(avatarUrl);
            wxUserInfoEntity.setUpdateDateTime(wxUtil.getNowDate());
            this.wxUserInfoService.updateWxUserInfo(wxUserInfoEntity);
            //如果当前用户信息为空，插入新的信息
        } else {
            wxUserInfoEntity = new WxUserInfoEntity();
            wxUserInfoEntity.setOpenCode(openid);
            wxUserInfoEntity.setPhoneNumber(phoneNumber);
            wxUserInfoEntity.setPurePhoneNumber(purePhoneNumber);
            wxUserInfoEntity.setCountryCode(countryCode);
            wxUserInfoEntity.setNickName(nickName);
            wxUserInfoEntity.setGender(gender);
            wxUserInfoEntity.setLanguage(language);
            wxUserInfoEntity.setCity(city);
            wxUserInfoEntity.setProvince(province);
            wxUserInfoEntity.setCountry(country);
            wxUserInfoEntity.setAvatarUrl(avatarUrl);//头像地址
            wxUserInfoEntity.setUpdateDateTime(wxUtil.getNowDate());
            this.wxUserInfoService.addWxUserInfo(wxUserInfoEntity);
        }

        return userInfo;
    }
}
