package com.sv.mc.controller;

import com.sv.mc.pojo.DeviceModelEntity;
import com.sv.mc.service.DeviceModelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class DeviceModelController {
    @Resource
    DeviceModelService deviceModelService;

    /**
     * 查询所有机器类型
     * @return
     */
    @GetMapping("/deviceModel/all")
    public List<DeviceModelEntity> findAllModel(){
        return deviceModelService.findAll();
    }

    /**
     * 查询所有机器型号
     */
    @GetMapping("/deviceModel/typeAll")
    public Map<String,String> findAllModelAll(){
        return deviceModelService.findDeviceModelAll();
    }

    /**
     * 查询所有机器型号
     */
    @GetMapping("/deviceModel/type")
    public DeviceModelEntity findModel(@RequestParam("id") int id){
        return deviceModelService.findDeviceModel(id);
    }
}
