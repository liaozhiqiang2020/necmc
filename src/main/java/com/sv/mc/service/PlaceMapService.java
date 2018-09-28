package com.sv.mc.service;

import com.sv.mc.pojo.PlaceMapEntity;

/**
 * 接口
 * author:赵政博
 */
public interface PlaceMapService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param place 场地图片数据
     * @return       PlaceMapEntity
     */
    PlaceMapEntity save(PlaceMapEntity place);

    /**2
     * 根据场地图片id查询对应场地图片数据
     * @param id  场地id
     * @return PlaceMapEntity
     */
    PlaceMapEntity findPlaceMapById(int id);


    /**3
     * 根据场地图片id更改对应的场地图片数据
     * @param id  场地id
     * @param placeMap 场地图片
     * @return PlaceMapEntity
     */
    PlaceMapEntity updatePlaceMapById(int id, PlaceMapEntity placeMap);

    /**4
     * 插入一条场地图片数据
     * @param placeMap 场地图片
     * @return PlaceMapEntity
     */
    PlaceMapEntity insertPlaceMap(PlaceMapEntity placeMap);



}
