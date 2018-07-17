package com.sv.mc.controller;

import com.sv.mc.pojo.BusinessEntity;
import com.sv.mc.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/bussinessMgr")
public class WycController {
    @Autowired
    private BusinessService businessService;


    /**
     * 跳转到行业分类管理页面
     * @return
     * @auther wangyuchen
     * @date 2018/7/17
     */
    @GetMapping(value="/turnTowyc")
    public ModelAndView turnToBussinessMgr(){
        return new ModelAndView("./baseInfo/wyc");
    }




}
