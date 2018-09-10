package com.sv.mc.service;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.vo.ExcelSetDeviceResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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
    String findAllDevice(HttpSession session);


    /**
      * 根据id修改状态
      */
    void deleteDevice(int deviceId);


    /**
     * 根据椅子sn修改椅子运行状态
     */
    void findChairRuningStatus(String deviceCode,int mcStatus);

    /**
     * 根据椅子sn修改椅子按摩强度
     */
    void findChairStrength(String deviceCode,int strength);


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

    /**
     * 无条件查询所有设备
     */
    List<DeviceEntity> findDevice2();


    /**
     * 导出设备Excel
     */

     void getAllExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session);

    /**
     * 导入设备Excel
     */
    List<ExcelSetDeviceResult> setAllExcel(MultipartFile file) throws IOException;


    /**
     * 提供导入设备模板
     */

     void getExcelModel(HttpServletResponse response);

    /**
     * 根据椅子SN查询椅子是否存在
     */
     DeviceEntity selectDeviceBYSN(String sn);


    /**
     * 导出ExcEL 失败数据
     */
    void getExcelError(Set excelError1,HttpServletResponse response);

    /**
     * 修改按摩椅状态为未查询
     */
    void updateMcStatusToZero();

}
