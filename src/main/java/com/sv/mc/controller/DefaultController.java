package com.sv.mc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DefaultController {

    @PostMapping("/home")
    public ModelAndView index() {
        return new ModelAndView("/home");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping("/urpMgmt/roleIndex")
    public ModelAndView roleIndex(){
        return new ModelAndView("/userRolePermissionMgmt/roleList");
    }

    @GetMapping("/error")
    public String error403() {
        return "/error/403";
    }

}
