package com.sv.mc.controller;

import com.sv.mc.alipay.AliPayConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
public class WXController {
    /**
     * 跳转到支付宝/微信判断网页
     */
//    @GetMapping("/wx")
//    public void turnToTestPage(String sn){
//        System.out.println(sn);
//    }

    /**
     * 跳转到支付宝/微信判断网页
     */
    @GetMapping("/wx/wxApp")
    public void toHintPage(HttpServletResponse response,HttpServletRequest request) throws IOException {
//        ModelAndView mv = new ModelAndView();

//        //页面回调地址 必须与应用中的设置一样
        String return_url = "https://www.infhp.cn/mc/wx/wxApp2";
//        //回调地址必须经encode
//        return_url = URLEncoder.encode(return_url,"UTF-8");
//
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("app_id", AliPayConfig.appId );
//        mv.addObject("scope", "auth_user");
//        mv.addObject("redirect_uri", return_url);
////        mv.setViewName("./error/hint");
//        mv.setViewName("./alipay/alipayUser");
        String sn = request.getParameter("sn");
        response.sendRedirect(AliPayConfig.alipayurl+"?app_id=" + AliPayConfig.appId + "&scope=auth_user&redirect_uri=" + return_url+"&state="+sn);

//        return void;
    }

    /**
     * 跳转到支付宝/微信判断网页
     */
    @GetMapping("/wx/wxApp2")
    public ModelAndView toHintPage2(HttpServletRequest request) {
        String sn = request.getParameter("state");
        System.out.println(sn);
        ModelAndView mv = new ModelAndView();
        mv.addObject("sn",sn);
        mv.setViewName("./error/hint");
//        mv.setViewName("./alipay/alipayScan");
        return mv;
    }


    /**
     * 跳转到支付宝首页
     */
    @GetMapping("/alipay/alipayScan")
    public ModelAndView toalipayScanPage(HttpServletRequest request) {
//        String sn = request.getParameter("state");
//        System.out.println(sn);
        ModelAndView mv = new ModelAndView();
//        mv.addObject("sn",sn);
        mv.setViewName("./alipay/alipayScan");
        return mv;
    }

}
