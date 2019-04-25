package com.sv.mc.service;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.PlaceEntity;
import org.springframework.data.repository.query.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 接口
 * author:赵政博
 */
public interface PlaceService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param place 场地数据
     * @return       PlaceEntity
     */
    PlaceEntity save(PlaceEntity place);

    /**2
     * 根据场地id查询对应场地数据
     * @param id  场地id
     * @return PlaceEntity
     */
    PlaceEntity findPlaceById(int id);


    /**3
     * 根据场地id更改对应的场地数据
     * @param id  场地id
     * @param place 场地
     * @return PlaceEntity
     */
    PlaceEntity updatePlaceById(int id, PlaceEntity place);



    /**
     * 查询详细数据
     * @param id 主键Id
     * @return 场地详细数据
     */
    List findPlace(int id);


    /**
     * 分页查询场地数据
     * @param page 起始个数
     * @param pageSize 截至个数
     * @return 场地信息
     */
    String findAllPlaceByPage(int page, int pageSize);


    /**
     * 不分页查询场地数据
     * @param map 查询的信息
     * @param session 用户信息
     * @return 场地数据
     */
    String findAllPlace(Map<String, Object> map, HttpSession session);

    /**
     * 不分页查询场地数据
     */
    List<PlaceEntity> findAllPlaces();

    /**4
     * 插入一条场地数据
     * @param map 插入的数据
     * @return PlaceEntity 场地数据
     */
    PlaceEntity insertPlace(Map<String, Object> map);

    /**
     * 根据分公司id更改对应的场地数据
     * @param map 分公司Id
     * @return placeEntity 场地对象
     */
    PlaceEntity updatePlace(Map<String, Object> map);

    /**4
     * 插入一条子场地数据
     * @param map 子场地数据
     * @return PlaceEntity 场地数据
     */
    PlaceEntity insertPlaceChild(Map<String, Object> map);

    /**
     * 根据分公司id更改对应的场地数据
     * @param map 分公司信息
     * @return placeEntity 更改的场地数据
     */
    PlaceEntity updatePlaceChild(Map<String, Object> map);


    /**
     * 根据场地id修改状态
     */
    void deletePlace(int placeId);

    /**
     * 根据场地Id查询该场地的所有设备
     * @param placeId 场地id
     * @return  所有设备
     */
    String findDeviceByPlace(int placeId);

    /**
     * 根据场地Id查询该场地的所有设备
     * @param placeId 场地Id
     * @param session 用户信息
     * @param deviceId 设备id
     * @return 设备信息
     */
    String findDeviceByPlaceId(int placeId, String deviceId, HttpSession session) throws ParseException;


    /**
     * 根据场地id查下一级信息
     * @param placeId 场地Id
     * @return 场地信息
     */
    String findPlaceByParentId(int placeId);


    /**
     * 根据市id 查询场地
     * @param cityId 市id
     * @return 场地集合
     */
    List<PlaceEntity> getPlace(int cityId);

    /**
     * 不分页查询一级场地数据
     * @param session 用户信息
     * @return 场地数据
     */
    List<PlaceEntity> findAllPlaceFirst(HttpSession session);


    /**
     * 把图片存到数据库中
     * @param placeId 场地Id
     * @param uploadpath 上传地址
     * @param fileName 文件名
     */
    void saveFileToDB(int placeId, String uploadpath, String fileName);


    /**4
     * 插入一条场地数据
     * @param map 场地数据
     * @return PlaceEntity 场地集合
     */
    PlaceEntity insertPlaceTree(Map<String, Object> map);

    /**
     * 根据分公司id更改对应的场地数据
     * @param map 分公司id 场地信息
     * @return placeEntity 场地数据
     */
    PlaceEntity updatePlaceTree(Map<String, Object> map);

    /**
     * 根据场地Id查询该场地的所有设备
     * @param placeId 场地Id
     * @return 该场地所有设备
     */
    String findDeviceBypId(int placeId);

    /**
     * 根据用户查询该用户权限下的所有从场地
     * @param request 请求的用户
     * @return 场地信息
     */
    String findDeviceByUser(HttpServletRequest request);

    /**
     * 根据场地名称查询场地
      * @param name 场地名称
     * @return 场地数据
     */
    PlaceEntity getPlaceByName(String name);
    /**
     * 四级权限查询场地
     * @param pid 场地Id
     * @return  场地对象
     */
    List<PlaceEntity>getPlaceBy_ID(int pid);

    /**
     * 查询所有未删除场地
     * @return 场地集合
     */
    List<PlaceEntity> allPlaceUnDelete();
}
