package com.sv.mc.service;

import com.sv.mc.pojo.AreaEntity;
import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.ProvinceEntity;

import java.util.List;

/**
 * 接口
 * author:王雨辰
 */
public interface RegionService<T> extends BaseService<T>{
    /**
     * 查询大区
     * @return      获取 大区
     */
    List<AreaEntity> getRegionOne();


    /**
     * 根据大区Id查询省
     * @return      获取省
     */
    List<ProvinceEntity> getRegionPrivince(int id);


    /**
     * 根据省查询市
     * @return      获取市
     */
    List<CityEntity> getRegionityCity(int id);






}
