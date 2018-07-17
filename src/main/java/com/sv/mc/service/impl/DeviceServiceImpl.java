package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.service.DeviceService;
import com.sv.mc.util.BaseUtil;
import com.sv.mc.util.DataSourceResult;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                device.setDiscardStatus(1);
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



        @Override
        public DeviceEntity updateDevice(DeviceEntity device) {
                return this.deviceRepository.save(device);
        }

        @Override
        public String findAllDeviceByPage(int page, int pageSize) {
//                Gson gson = new Gson();
                DataSourceResult<DeviceEntity> deviceEntityDataSourceResult = new DataSourceResult<>();
                int offset = ((page-1)*pageSize);
                List<DeviceEntity> deviceEntityList = this.deviceRepository.findAllDeviceByPage(offset,pageSize);
                int total = this.deviceRepository.findDeviceTotal();
                deviceEntityDataSourceResult.setData(deviceEntityList);
                deviceEntityDataSourceResult.setTotal(total);
                JsonConfig config = new JsonConfig();
                config.setExcludes(new String[] { "priceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray
                JSONArray jsonArray = JSONArray.fromObject(deviceEntityDataSourceResult,config);


                return jsonArray.toString();
        }

        @Override
        public void deleteDevice(int deviceId) {
                DeviceEntity deviceEntity = findDeviceById(deviceId);
                deviceEntity.setDiscardStatus(0);
                this.deviceRepository.save(deviceEntity);
        }


        @Override
        public List<DeviceEntity> findAllDevice() {
                return this.deviceRepository.findAllDevice();
        }
}
