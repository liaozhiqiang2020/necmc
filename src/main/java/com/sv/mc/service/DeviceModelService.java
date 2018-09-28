package com.sv.mc.service;

import com.sv.mc.pojo.DeviceModelEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface DeviceModelService {

    /**
     * 查询所有按摩椅类型
     * @return 按摩椅类型信息
     */
    List<DeviceModelEntity> findAll();

    /**
     * 根据Id查询设备类型
     * @param id 主键Id
     * @return 设备类型信息
     */
    DeviceModelEntity findDeviceModel(int id);

    /**
     * 查询所有机器类型（大小）
     * @return 设备类型信息
     */
   Map<String,String> findDeviceModelAll();






    /**
     * 根据设备名称型号大小查询是否存在
     */

    DeviceModelEntity getDeviceByType(String name,String model);

}
