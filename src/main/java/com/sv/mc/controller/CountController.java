package com.sv.mc.controller;

import com.sv.mc.pojo.qo.ProvinceQo;
import com.sv.mc.service.CountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
public class CountController {
    @Resource
    CountService countService;

    @GetMapping
    private ProvinceQo findProvinceById(@RequestParam int pId, @RequestParam Date start ,@RequestParam Date end){
        return this.countService.findProvinceById(pId,start,end);
    }
}
