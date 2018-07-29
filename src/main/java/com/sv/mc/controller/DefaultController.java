package com.sv.mc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DefaultController {

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/home");
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "不正确的用户名和密码");
        }
        if (logout != null) {
            model.addObject("msg", "你已经成功退出");
        }
        model.setViewName("login");
        return model;
    }

    @GetMapping("/urpMgmt/roleIndex")
    public ModelAndView roleIndex() {
        return new ModelAndView("/userRolePermissionMgmt/roleList");
    }

    @GetMapping("/error")
    public String error403() {
        return "/error/403";
    }
}
