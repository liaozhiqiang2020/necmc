package com.sv.mc.controller;

import com.sv.mc.pojo.DeviceModelEntity;
import com.sv.mc.service.DeviceModelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DeviceModelController {
    @Resource
    DeviceModelService deviceModelService;

    @GetMapping("/deviceModel/all")
    public List<DeviceModelEntity> findAllModel(){
        return deviceModelService.findAll();
    }
}
