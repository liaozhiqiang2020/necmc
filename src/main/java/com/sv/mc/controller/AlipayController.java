package com.sv.mc.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.google.gson.Gson;
import com.sv.mc.alipay.AliPayConfig;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.OrderEntity;
import com.sv.mc.pojo.PriceEntity;
import com.sv.mc.pojo.vo.AlipayVo;
import com.sv.mc.service.*;
import com.sv.mc.util.WxUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import static com.alipay.api.AlipayConstants.APP_ID;
import static com.alipay.api.AlipayConstants.CHARSET;

/**
 * @Title: AlipayController.java
 * @Package cn.trmap.tdcloud.pay
 * @Description: 支付宝后台接口
 * @author nelson
 * @date 2018年3月21日 下午5:31:03
 */
@RequestMapping("/alipay")
@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AlipayController {

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
    @Autowired
    private GatewayService gatewayService;

    @Resource
    private JMSProducer jmsProducer;
    @Resource
    private JMSConsumer jmsConsumer;


    /**
     * 跳转到倒计时页面
     * @return
     */
    @GetMapping("/turnToAlipayMgr")
    public ModelAndView turnToAlipayMgr(HttpServletRequest request){
        String mcTime = request.getParameter("mcTime");
        String orderId = request.getParameter("orderId");
        String states = request.getParameter("states");
        String deviceId = request.getParameter("deviceId");

        ModelAndView view = new ModelAndView();
        view.addObject("mcTime",mcTime);
        view.addObject("orderId",orderId);
        view.addObject("states",states);
        view.addObject("deviceId",deviceId);
        view.setViewName("./alipay/alipayMgr");
        return view;
    }

    /**
     * 获取用户id
     * @param request
     * @param response
     * @return
     * @throws AlipayApiException
     * @throws IOException
     */
    @RequestMapping("/getUserId")
    @ResponseBody
    public String getUserId(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
        String authCode = request.getParameter("auth_code");
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AliPayConfig.appId, AliPayConfig.privateKey, "json", AliPayConfig.charset, AliPayConfig.alipayPublicKey, AliPayConfig.signType);
        AlipaySystemOauthTokenRequest trequest = new AlipaySystemOauthTokenRequest();
        trequest.setCode(authCode);
        trequest.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(trequest);
        System.out.println(oauthTokenResponse.getUserId());
        return oauthTokenResponse.getUserId();
    }

    /**
     * 发起支付宝支付
     * @param request
     * @param response
     * @return
     * @throws AlipayApiException
     * @throws IOException
     */
    @RequestMapping("/pay")
    @ResponseBody
    public String alipayPay(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        String WIDout_trade_no = request.getParameter("WIDout_trade_no");//商户订单号（唯一）
        String WIDsubject = request.getParameter("WIDsubject");//商品名称
        String WIDtotal_fee = request.getParameter("WIDtotal_fee");//金额
        String WIDbody = request.getParameter("WIDbody");//商品描述
        String sn = request.getParameter("sn");
        String paidOrderId = request.getParameter("paidOrderId");
        System.out.println(paidOrderId);

        response.addHeader("Access-Control-Allow-Origin","*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method","POST,GET");//允许访问的方式

        AlipayVo vo = new AlipayVo();
        vo.setOut_trade_no(WIDout_trade_no+"-3333");
        vo.setTotal_amount(WIDtotal_fee);
        vo.setSubject(WIDsubject);
        vo.setProduct_code("FAST_INSTANT_TRADE_PAY"); //这个是固定的
        vo.setBody(WIDbody);
        String json = new Gson().toJson(vo);
        System.out.println(json);

        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.apiUrl, AliPayConfig.appId, AliPayConfig.privateKey, "json", AliPayConfig.charset, AliPayConfig.alipayPublicKey, AliPayConfig.signType);
        // 设置请求参数
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        alipayRequest.setReturnUrl(AliPayConfig.returnUrl);
        alipayRequest.setNotifyUrl(AliPayConfig.notifyUrl);
        alipayRequest.setBizContent(json);
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println(result);

        // 建立请求
//        model.addAttribute("sHtmlText", result);

//        return new ResponseEntity(model, HttpStatus.OK);
        // 保存支付记录
//        hysWebMeetingAliService.insertSelective(sParaTemp);
//        return new ResponseEntity(model, HttpStatus.OK);
        return result; //这里生成一个表单，会自动提交
    }



    //支付宝回调处理
    @RequestMapping("/aliPayInquiryNotify")
    @ResponseBody
//    @ApiOperation(value = "支付宝回调接口",notes = "支付宝回调接口")
    public String aliPayNotify(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException, ParseException {
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            if (name.equals("trade_status")) {
//                logger.info("交易状态为：" + valueStr);
                valueStr = new String(valueStr.getBytes("ISO-8859-1"),"UTF-8");
                System.out.println("交易状态为：" + valueStr);
            }
            params.put(name, valueStr);
        }

        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        //付款金额
        String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

        //根据官网信息  支付宝回调的参数必须进行验证 一下是初步验证方法,  后续你还可以验证订单id和你提交支付的订单id是否一致等,根据个人
        boolean flag = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipayPublicKey, AliPayConfig.charset, AliPayConfig.signType);

        //以下是在写你自己的逻辑时可能用到的一些参数,如果你想使用更多可以去官网查看返回参数列表

//        //获取订单状态
//        String tradeStatus =params.get("trade_status");
//        //订单号
//        String out_trade_no = params.get("out_trade_no");
//        //支付宝交易号
//        String trade_no = params.get("trade_no");
//        //交易时间
//        String gmt_payment = params.get("gmt_payment");
//        //交易金额  单位为元
//        String total_amount =params.get("total_amount");
//        //商家在交易中实际收到的款项，单位为元
//        String receipt_amount =params.get("receipt_amount");
//
//        //订单描述
//        String 	body = params.get("body");

        if (flag) {//验证成功


            //订单状态为支付成功时,你可以在下面写你的业务逻辑.如:产生账单、订单状态变更为已支付订单、等逻辑
            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {

                System.out.println(Integer.parseInt(out_trade_no.split("-")[0]));
                try{
                    this.orderService.updateOrderById(Integer.parseInt(out_trade_no.split("-")[0]), 1, "");
                }catch (Exception ex){
                    ex.printStackTrace();
                }
//                System.out.println("8888888888888888888");
                //这里可以写你自己的操作逻辑
//                return "success";


//                System.out.println("fail");
//                return "fail";
            }
            //返回码
            return "success";
        } else {
            //验证失败
            System.out.println("支付宝App支付异步回调：验签失败");
            System.out.println(">>>>>验签失败" );
            System.out.println(">>>>>交易被关闭了");
            //这里写验证失败的业务处理
            return "fail";
        }
    }


    //支付宝回调处理
    @RequestMapping("/aliPayInquiryReturn")
    @ResponseBody
//    @ApiOperation(value = "支付宝回调接口",notes = "支付宝回调接口")
    public ModelAndView aliPayReturn(HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            if (name.equals("trade_status")) {
//                logger.info("交易状态为：" + valueStr);
                valueStr = new String(valueStr.getBytes("ISO-8859-1"),"UTF-8");
                System.out.println("交易状态为：" + valueStr);
            }
            params.put(name, valueStr);
        }

        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

//        //
//        String buyer_user_id = new String(request.getParameter("buyer_user_id").getBytes("ISO-8859-1"),"UTF-8");

        //付款金额
        String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");


        //根据官网信息  支付宝回调的参数必须进行验证 一下是初步验证方法,  后续你还可以验证订单id和你提交支付的订单id是否一致等,根据个人
        boolean flag = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipayPublicKey, AliPayConfig.charset, AliPayConfig.signType);

        if (flag) {//验证成功
            System.out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);

            OrderEntity orderEntity = orderService.findPaidOrderByOrderId(Integer.parseInt(out_trade_no.split("-")[0]));
            orderEntity.setCodeWx(trade_no);
            orderService.saveOrder(orderEntity);
            DeviceEntity deviceEntity  = deviceService.findDeviceById(orderEntity.getDeviceId());

            //发送启动按摩椅命令
            weiXinPayService.sendStartChairMsg(String.valueOf(deviceEntity.getMcSn()),orderEntity.getMcTime());

            ModelAndView mv = new ModelAndView();
            mv.addObject("deviceId",deviceEntity.getMcSn());
            mv.addObject("mcTime",orderEntity.getMcTime());
            mv.addObject("orderId",orderEntity.getId());
            mv.addObject("orderNo",orderEntity.getCode());
            mv.addObject("states",0);
//            mv.addObject("buyer_user_id",buyer_user_id);
            mv.setViewName("./alipay/alipayMgr");
            return mv;
        } else {
            //验证失败
            System.out.println("支付宝App支付异步回 调：验签失败");
            System.out.println(">>>>>验签失败");
            System.out.println(">>>>>交易被关闭了");
            //这里写验证失败的业务处理
            return new ModelAndView("./error/hint");
        }
    }


    /**
     *  根据设备编码查询价格
     * @param
     * @return 价格
     */
    @RequestMapping("/devicePriceByAlipay")
    @ResponseBody
    public List<PriceEntity> findDeviceAllPriceByAlipay(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin","*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method","POST,GET");//允许访问的方式

        String deviceCode = request.getParameter("deviceCode");
        List<PriceEntity> list = this.priceService.findDeviceAllPrice(deviceCode);
        return list;
    }


    /**
     * 创建支付宝订单
     *
     * @author: lzq
     * @date: 2018年7月6日
     */
    @RequestMapping("/createPaidOrder")
    @ResponseBody
    public int createPaidOrder(String openid, int mcTime, String deviceCode, String promoCode, BigDecimal money, String unPaidOrderCode, int state, int strength) {
        return this.orderService.createPaidOrderAlipay(openid, mcTime, deviceCode, promoCode, money, unPaidOrderCode, state, strength);
    }


    /**
     * 获取剩余时间
     * @return
     */
    @RequestMapping("/getMcTime")
    @ResponseBody
    public Map<String, Object> getTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin","*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method","POST,GET");//允许访问的方式
        String orderId = request.getParameter("orderId");
        OrderEntity orderEntity = orderService.findPaidOrderByOrderId(Integer.parseInt(orderId));
        DeviceEntity deviceEntity = deviceService.findDeviceById(orderEntity.getDeviceId());

        Timestamp orderTime = orderEntity.getMcEndDateTime();

        long differTime = 0;
        WxUtil wxUtil = new WxUtil();
        Timestamp nowTime = wxUtil.getNowDate();//获取当前时间

        if (nowTime.getTime() <= orderTime.getTime()) {//判断当前时间是否小于等于按摩结束时间，如果为true，获得时间差
            differTime = wxUtil.getDifference(nowTime, orderTime, 0);//获取时间差值
        }

        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("mcTime",differTime);
        map.put("deviceId",deviceEntity.getMcSn());
        return map;
    }


    /**
     * 跳转到订单查询页面
     * @return  订单查询 view 对象
     */
    @GetMapping("/turnToAlipayOrder")
    public ModelAndView turnToAlipayOrder(HttpServletRequest request){
        String auth_app_id = request.getParameter("auth_app_id");
        ModelAndView view = new ModelAndView();
        view.addObject("auth_app_id",auth_app_id);
        view.setViewName("./alipay/alipayOrder");
        return view;
    }

    /**
     * 跳转到首页扫码页面
     * @return
     */
    @GetMapping("/turnToAlipayScan")
    public ModelAndView turnToAlipayScan(HttpServletRequest request){
        String auth_app_id = request.getParameter("auth_app_id");
        ModelAndView view = new ModelAndView();
        System.out.println(auth_app_id);
        view.addObject("auth_app_id",auth_app_id);
        view.setViewName("./alipay/alipayScan");
        return view;
    }

    /**
     * 跳转到个人中心页面
     * @return
     */
    @GetMapping("/turnToAlipayOur")
    public ModelAndView turnToAlipayOur(HttpServletRequest request){
        String auth_app_id = request.getParameter("auth_app_id");
        ModelAndView view = new ModelAndView();
        view.addObject("auth_app_id",auth_app_id);
        view.setViewName("./alipay/alipayOur");
        return view;
    }
}