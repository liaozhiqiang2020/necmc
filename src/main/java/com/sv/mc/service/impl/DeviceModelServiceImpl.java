package com.sv.mc.service.impl;

import com.sv.mc.pojo.DeviceModelEntity;
import com.sv.mc.repository.DeviceModelRepository;
import com.sv.mc.service.DeviceModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备类型逻辑处理层
 */
@Service
public class DeviceModelServiceImpl implements DeviceModelService {

    @Resource
    DeviceModelRepository deviceModelRepository;

    /**
     * 查询所有设备类型
     * @return 设备类型
     */
    @Override
    @Transactional
    public List<DeviceModelEntity> findAll() {
        return deviceModelRepository.findAll();
    }

    /**
     * 根据Id查询设备类型
     * @param id 主键Id
     * @return 设备类型
     */
    @Override
    public DeviceModelEntity findDeviceModel(int id) {
        return this.deviceModelRepository.findById(id);
    }

    /**
     * 查询所有设备类型
     * @return 设备类型
     */
    @Override
    public Map<String, String> findDeviceModelAll() {
        List<String> list = this.deviceModelRepository.findDeviceModelAll();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put("类型", list.get(i));
        }
        return map;
    }

    /**
     *  根据设备名称  设备型号查询设备
     * @param name 设备名称
     * @param model 设备型号
     * @return 查询到的设备
     */
    @Override
    public DeviceModelEntity getDeviceByType(String name, String model) {

      return  this.deviceModelRepository.getDeviceByName(name,model);


    }


}
