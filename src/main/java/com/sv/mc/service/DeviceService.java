package com.sv.mc.service;

import com.sv.mc.pojo.DeviceEntity;

/**
 * 接口
 * author:赵政博
 */
public interface DeviceService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param device 设备数据
     * @return       DeviceEntity
     */
    DeviceEntity save(DeviceEntity device);

    /**2
     * 根据设备id查询对应设备数据
     * @param id  设备id
     * @return DeviceEntity
     */
    DeviceEntity findDeviceById(int id);


    /**3
     * 根据设备id更改对应的设备数据
     * @param id  设备id
     * @param device
     * @return DeviceEntity
     */
    DeviceEntity updateDeviceById(int id, DeviceEntity device);

    /**4
     * 插入一条设备数据
     * @param device
     * @return DeviceEntity
     */
    DeviceEntity insertDevice(DeviceEntity device);


}
