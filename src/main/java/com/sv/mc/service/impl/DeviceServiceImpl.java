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
import org.springframework.transaction.annotation.Transactional;

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
        @Transactional
        public DeviceEntity save(DeviceEntity device) {
                return deviceRepository.save(device);
        }

        /**
         * 根据id查找
         * @param id  设备id
         * @return
         */
        @Override
        @Transactional
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
        @Transactional
        public DeviceEntity updateDeviceById(int id, DeviceEntity device) {
                return deviceRepository.save(device);
        }

        /**
         * 插入一条新设备数据
         * @param device
         * @return
         */
        @Override
        @Transactional
        public DeviceEntity insertDevice(DeviceEntity device) {
                device.setDiscardStatus(1);
                return deviceRepository.save(device);
        }

        /**
         * 查询所有设备数据
         * @return
         */
        @Override
        @Transactional
        public List<DeviceEntity> findAllEntities() {
                return deviceRepository.findAll();
        }



        @Override
        @Transactional
        public DeviceEntity updateDevice(DeviceEntity device) {
                return this.deviceRepository.save(device);
        }

        @Override
        @Transactional
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
        @Transactional
        public void deleteDevice(int deviceId) {
                DeviceEntity deviceEntity = findDeviceById(deviceId);
                deviceEntity.setDiscardStatus(0);
                this.deviceRepository.save(deviceEntity);
        }


        @Override
        @Transactional
        public List<DeviceEntity> findAllDevice() {
                return this.deviceRepository.findAllDevice();
        }


        /**
         * 根据椅子sn修改椅子运行状态
         */
        @Override
        public void findChairRuningStatus(String deviceCode,int mcStatus) {
                int deviceId = this.deviceRepository.queryDeviceIdByDeviceCode(deviceCode);
                DeviceEntity deviceEntity = this.findDeviceById(deviceId);
                deviceEntity.setMcStatus(mcStatus);
                this.deviceRepository.save(deviceEntity);
        }


        /**
         * 根据场地查询所有的设备编码
         * @param id  场地id
         */
        @Override
        public List<String> getFill_SN(int id) {
                List<String> deviceEntities = this.deviceRepository.findAllByPlaceId(id);
                return deviceEntities;
        }

        /**
         * 根据权限查询设备
         * @param id
         * @return
         */
        @Override
        public List<DeviceEntity> geyDeviceByPid(int id) {
                return this.deviceRepository.getDeviceByPid(id);
        }

        /**
         * 根据场地查询设备
         * @param id
         * @return
         */
        @Override
        public List<DeviceEntity> getDeviceByplace_id(int id) {
                return this.deviceRepository.findDevicesByPlaceId(id);
        }
}
