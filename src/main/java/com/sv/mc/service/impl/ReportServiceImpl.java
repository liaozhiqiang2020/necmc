package com.sv.mc.service.impl;

import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.repository.CityRepository;
import com.sv.mc.repository.PlaceRepository;
import com.sv.mc.service.CityService;
import com.sv.mc.service.ProvinceService;
import com.sv.mc.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报表相关接口实现类
 * @auther liaozhiqiang
 * @date 2018/7/11
 */
@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private PlaceRepository placeRepository;

    /**
     * 查询所有省份
     * @return
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @Override
    public List<ProvinceEntity> queryAllProvince() {
        return this.provinceService.findAllEntities();
    }

    /**
     * 根据省份id查询所属的市
     * @return
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @Override
    public List<CityEntity> queryAllCityByProId(int provinceId) {
        return this.cityRepository.findCityEntitiesByProvinceId(provinceId);
    }


    /**
     * 根据市id查询场地
     * @param cityId
     * @return
     * @auther liaozhiqiang
     * @date 2018//7/11
     */
    @Override
    public List<PlaceEntity> queryAllPlaceByCityId(int cityId) {
        return this.placeRepository.queryPlaceEntitiesByCityId(cityId);
    }
}
