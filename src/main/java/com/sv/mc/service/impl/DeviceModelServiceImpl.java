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

@Service
public class DeviceModelServiceImpl implements DeviceModelService {

    @Resource
    DeviceModelRepository deviceModelRepository;

    @Override
    @Transactional
    public List<DeviceModelEntity> findAll() {
        return deviceModelRepository.findAll();
    }

    @Override
    public DeviceModelEntity findDeviceModel(int id) {
        return this.deviceModelRepository.findById(id);
    }

    @Override
    public Map<String, String> findDeviceModelAll() {
        List<String> list = this.deviceModelRepository.findDeviceModelAll();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put("类型", list.get(i));
        }
        return map;
    }

    @Override
    public DeviceModelEntity getDeviceByType(String name, String model) {

      return  this.deviceModelRepository.getDeviceByName(name,model);


    }


}
