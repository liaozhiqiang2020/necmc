package com.sv.mc.service;


import com.sv.mc.pojo.PriceEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PriceService {

    /**
     * 分页查询所有数据
     *
     * @param page     分页规则
     * @param pageSize 分页规则
     * @return 价格分页集合
     */
    String findAllPagePrice(int page, int pageSize);

    /**
     * 查询所有价格数据
     *
     * @return 价格集合
     */
    List<PriceEntity> findAllPrice();

    /**
     * 查询状态为使用中的价格
     *
     * @return价格集合数组
     */
    String findStatusPrice();


    /**
     * 更新价格
     *
     * @param map 价格对象（价格对象必须包含主键ID）
     * @return 消息
     */
    PriceEntity updatePrice(Map<String, Object> map);


    /**
     * 删除价格
     *
     * @param priceEntity 价格
     * @return 消息
     */
    void deletePrice(PriceEntity priceEntity);


    /**
     * 根据id查询价格
     *
     * @param Id 价格ID
     * @return
     */
    PriceEntity findPriceById(int Id);

    /**
     * 添加价格
     *
     * @param map     价格对象
     * @param request 页面请求
     * @return 消息
     */
    PriceEntity addPrice(Map<String, Object> map, HttpServletRequest request);

    /**
     * 批量修改或者保存价格数据
     *
     * @param priceEntityList
     * @return 页面需要回显新的价格数据
     */
    List<PriceEntity> batchSaveOrUpdatePrice(List<PriceEntity> priceEntityList);

    /**
     * 批量删除
     *
     * @param priceEntityList
     * @return 页面需要回显新的价格数据
     */
    List<PriceEntity> batchDeletePrice(List<PriceEntity> priceEntityList);

    /**
     * 根据设备id的价格进行查询
     *
     * @param deviceId 设备Id
     * @return 当前机器的价格集合
     */
    List<Object[]> findPriceByDeviceId(int deviceId);

    /**
     * 根据设备Id查询出当前设备的所有绑定的价格
     *
     * @param deviceId 设备Id
     * @return 价格集合
     */
    Set<PriceEntity> findDevicePrice(int deviceId);

    /**
     * 根据当前设备查询所有未绑定的价格方案
     *
     * @param deviceId 当前设备
     * @return 未绑定的价格集合
     */
    List<PriceEntity> findUnDevicePrice(int deviceId);

    /**
     * 给机器绑定价格
     *
     * @return 成功消息
     */
    List<PriceEntity> deviceSavePrice(Map<String, Object> listMap);

    /**
     * 给机器解绑价格方案
     *
     * @param listMap 设备实体类
     * @return 设备
     */
    List<PriceEntity> deviceDeletePrice(Map<String, Object> listMap);

    /**
     * 给场地上的某种类型的所有机器绑定价格
     *
     * @param listMap 场地id 与 价格id集合
     * @return
     */
    List<PriceEntity> placeAddPrice(Map<String, Object> listMap);

    /**
     * 根据设备编号查询价格列表
     *
     * @param deviceCode
     * @return
     */
    List<Map<String, Object>> queryPriceAndTime(String deviceCode);

    /**
     * 查询所有可用的价格
     *
     * @param
     * @return
     */
    String findPriceEntitiesByEnd();

    /**
     * 分页查询所有数据
     *
     * @return 价格集合
     */
    List<PriceEntity> findAllPagePrice();

    /**
     * 查询机器可用价格
     * @return
     */
    List<PriceEntity> findDeviceAllPrice(String deviceCode);


    /**
     * 解析用户上传的excel 文件
     */

    List getExcel(MultipartFile multipartFile) throws IOException;


}
