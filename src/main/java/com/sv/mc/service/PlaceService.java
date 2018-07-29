package com.sv.mc.service;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.PlaceEntity;

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
     * @param id
     * @return
     */
    List findPlace(int id);


    /**
     * 分页查询场地数据
     */
    String findAllPlaceByPage(int page, int pageSize);

    /**
     * 不分页查询场地数据
     */
    String findAllPlace();

    /**
     * 不分页查询场地数据
     */
    List<PlaceEntity> findAllPlaces();

    /**4
     * 插入一条场地数据
     * @param
     * @return PlaceEntity
     */
    PlaceEntity insertPlace(Map<String,Object> map);

    /**
     * 根据分公司id更改对应的场地数据
     * @param
     * @return placeEntity
     */
    PlaceEntity updatePlace(Map<String,Object> map);

    /**4
     * 插入一条子场地数据
     * @param
     * @return PlaceEntity
     */
    PlaceEntity insertPlaceChild(Map<String,Object> map);

    /**
     * 根据分公司id更改对应的场地数据
     * @param
     * @return placeEntity
     */
    PlaceEntity updatePlaceChild(Map<String,Object> map);


    /**
     * 根据id修改状态
     */
    void deletePlace(int placeId);

    /**
     * 根据场地Id查询该场地的所有设备
     * @param placeId
     * @return
     */
    List<DeviceEntity> findDeviceByPlace(int placeId);

    /**
     * 根据场地Id查询该场地的所有设备
     * @param placeId
     * @return
     */
    List<DeviceEntity> findDeviceByPlaceId(int placeId);

    /**
     * 根据场地id查下一级信息
     */
    String findPlaceByParentId(int placeId);

    /**
     * 根据市id 查询场地
     */
    List<PlaceEntity> getPlace(int cityId);

    /**
     * 不分页查询一级场地数据
     */
    List<PlaceEntity> findAllPlaceFirst();
}
