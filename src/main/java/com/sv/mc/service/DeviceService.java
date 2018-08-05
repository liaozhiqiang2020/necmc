package com.sv.mc.service;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.DeviceEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    /**
     * 更新设备信息
     * @param device
     * @return
     */
    DeviceEntity updateDevice(DeviceEntity device);

    /**
      * 分页查询设备数据
      */
    String findAllDeviceByPage(int page, int pageSize);

    /**
     * 不分页查询设备数据
     */
    List<DeviceEntity> findAllDevice(HttpSession session);


    /**
      * 根据id修改状态
      */
    void deleteDevice(int deviceId);


    /**
     * 根据椅子sn修改椅子运行状态
     */
    void findChairRuningStatus(String deviceCode,int mcStatus);


    /**
     * 根据场地查询所有的设备编码
     * @param id  场地id
     */
    List<String> getFill_SN(int id);

    /**
     * 根据权限查询设备
     */

    List<DeviceEntity>geyDeviceByPid(int id);

    /**
     * 根据场地ID查询所有设备
     */
    List<DeviceEntity> getDeviceByplace_id(int id);


}
