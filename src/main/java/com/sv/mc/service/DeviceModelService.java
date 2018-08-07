package com.sv.mc.service;

import com.sv.mc.pojo.DeviceModelEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface DeviceModelService {

    List<DeviceModelEntity> findAll();

    DeviceModelEntity findDeviceModel(int id);

    /**
     * 查询所有机器类型（大小）
     * @return
     */
   Map<String,String> findDeviceModelAll();






    /**
     * 根据设备名称型号大小查询是否存在
     */

    DeviceModelEntity getDeviceByType(String name,String model);

}
