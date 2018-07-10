package com.sv.mc.service;

import com.sv.mc.pojo.CityEntity;

/**
 * 接口
 * author:赵政博
 */
public interface CityService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param city 市
     * @return       AreaEntity
     */
    CityEntity save(CityEntity city);

    /**
     * 根据id 查询市
     * @param id
     * @return CityEntity
     */
    CityEntity findCityById(int id);

}
