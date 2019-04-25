package com.sv.mc.service;

import com.sv.mc.pojo.CityEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 接口
 * author:赵政博
 */
public interface CityService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param city 要增加的市
     * @return    增加的市对象
     */
    CityEntity save(CityEntity city);

    /**
     * 根据id 查询市
     * @param id 市Id
     * @return CityEntity 查询到的市数据
     */
    CityEntity findCityById(int id);


    /**
     *  四级权限查询市
     * @param pid 上级iD
     * @return 查询到的市
     */
    List<CityEntity> getCityByPlace_ID(int pid);

}
