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
     * @return    增加的设备信息
     */
    DeviceEntity save(DeviceEntity device);

    /**2
     * 根据设备id查询对应设备数据
     * @param id  设备id
     * @return 查询到的设备信息
     */
    DeviceEntity findDeviceById(int id);


    /**3
     * 根据设备id更改对应的设备数据
     * @param id  设备id
     * @param device  设备信息
     * @return DeviceEntity 更改的内容
     */
    DeviceEntity updateDeviceById(int id, DeviceEntity device);

    /**4
     * 插入一条设备数据
     * @param device 插入的内容
     * @return DeviceEntity  插入的内容
     */
    DeviceEntity insertDevice(DeviceEntity device);

    /**
     * 更新设备信息
     * @param device 修改的数据
     * @return 修改后的数据
     */
    DeviceEntity updateDevice(DeviceEntity device);

    /**
     * 分页查询设备信息
     * @param page 起始时间
     * @param pageSize 截止时间
     * @return  设备信息
     */
    String findAllDeviceByPage(int page, int pageSize);


    /**
     *  不分页查询设备数据
     * @param session  用户信息
     * @return 查询用户的设备
     */
    String findAllDevice(HttpSession session);



    /**
     * 根据id修改状态
     * @param deviceId 设备id
     */
    void deleteDevice(int deviceId);

    /**
     *  根据椅子sn修改椅子运行状态
     * @param deviceCode 设备sn
     * @param mcStatus 设备状态
     */
    void findChairRuningStatus(String deviceCode, int mcStatus);


    /**
     * 根据椅子sn修改椅子按摩强度
     * @param deviceCode 设备sc
     * @param strength 按摩椅强度
     */
    void findChairStrength(String deviceCode, int strength);


    /**
     * 根据场地查询所有的设备编码
     * @param id  场地id
     * @return 设备编码集合
     */
    List<String> getFill_SN(int id);


    /**
     * 根据权限查询设备
     * @param id 权限Id
     * @return 设备信息
     */
    List<DeviceEntity>geyDeviceByPid(int id);


    /**
     *  根据场地ID查询所有设备
     * @param id 场地Id
     * @return 设备集合
     */
    List<DeviceEntity> getDeviceByplace_id(int id);

    /**
     * 无条件查询所有设备
     * @return 设备集合
     */
    List<DeviceEntity> findDevice2();



    /**
     * 导出设备Excel
     * @param request 页面请求
     * @param response  相应excel
     * @param session 用户信息
     */

     void getAllExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session);

    /**
     *  导入excel
     * @param file 接收的文件
     * @return 导入结果
     * @throws IOException
     */
    List<ExcelSetDeviceResult> setAllExcel(MultipartFile file) throws IOException;


    /**
     * 提供导入设备模板
     * @param response 响应模板给页面
     */

     void getExcelModel(HttpServletResponse response);

    /**
     * 根据椅子SN查询椅子是否存在
     * @param sn 椅子sn
     * 查询结果  设备信息
     */

     DeviceEntity selectDeviceBYSN(String sn);


    /**
     * 导出ExcEL 失败数据
     * @param response  响应excel
     * @param excelError1  失败数据
     */
    void getExcelError(Set excelError1, HttpServletResponse response);

    /**
     * 修改按摩椅状态为未查询
     */
    void updateMcStatusToZero();

}
