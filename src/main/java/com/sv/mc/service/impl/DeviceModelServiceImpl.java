package com.sv.mc.service.impl;

import com.sv.mc.pojo.DeviceModelEntity;
import com.sv.mc.repository.DeviceModelRepository;
import com.sv.mc.service.DeviceModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeviceModelServiceImpl implements DeviceModelService {

    @Resource
    DeviceModelRepository deviceModelRepository;

    @Override
    @Transactional
    public List<DeviceModelEntity> findAll() {
        return deviceModelRepository.findAll();
    }
}
