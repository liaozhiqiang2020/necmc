package com.sv.mc.service.impl;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
        @Autowired
        private DeviceRepository deviceRepository;

        /**
         * 保存数据
         * @param device 设备数据
         * @return
         */
        @Override
        public DeviceEntity save(DeviceEntity device) {
                return deviceRepository.save(device);
        }

        /**
         * 根据id查找
         * @param id  设备id
         * @return
         */
        @Override
        public DeviceEntity findDeviceById(int id) {
                return deviceRepository.findDeviceById(id);
        }

        /**
         * 根据id修改
         * @param id  设备id
         * @param device
         * @return
         */
        @Override
        public DeviceEntity updateDeviceById(int id, DeviceEntity device) {
                return deviceRepository.save(device);
        }

        /**
         * 插入一条新设备数据
         * @param device
         * @return
         */
        @Override
        public DeviceEntity insertDevice(DeviceEntity device) {
                return deviceRepository.save(device);
        }

        /**
         * 查询所有设备数据
         * @return
         */
        @Override
        public List<DeviceEntity> findAllEntities() {
                return deviceRepository.findAll();
        }
}
