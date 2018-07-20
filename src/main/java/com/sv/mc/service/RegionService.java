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
    /**1
     *
     * @return      获取 大区
     */
   public List<AreaEntity> getRegionOne();


    /**1
     *
     * @return      获取省
     */
    public List<ProvinceEntity> getRegionPrivince(int id);


    /**1
     *
     * @return      获取市
     */
    public List<CityEntity> getRegionityCity(int id);






}
