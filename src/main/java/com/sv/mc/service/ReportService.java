package com.sv.mc.service;

import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.ProvinceEntity;

import java.util.List;

/**
 * 报表相关接口类
 * @auther liaozhiqiang
 * @date 2018/7/11
 */
public interface ReportService {

    /**
     * 查询所有省份
     * @return 省份集合
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    List<ProvinceEntity> queryAllProvince();

    /**
     * 根据省份id查询所属的市
     * @return 根据省Id查询市
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    List<CityEntity> queryAllCityByProId(int provinceId);

    /**
     * 根据市id查询场地
     * @param cityId
     * @return 场地信息
     * @auther liaozhiqiang
     * @date 2018//7/11
     */
    List<PlaceEntity> queryAllPlaceByCityId(int cityId);

}
