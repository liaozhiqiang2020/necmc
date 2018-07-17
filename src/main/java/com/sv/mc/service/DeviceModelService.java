package com.sv.mc.service;

import com.sv.mc.pojo.DeviceModelEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DeviceModelService {

    List<DeviceModelEntity> findAll();
}
