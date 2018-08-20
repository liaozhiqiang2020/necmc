package com.sv.mc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView toHintPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("./error/hint");
        return mv;
    }

}
