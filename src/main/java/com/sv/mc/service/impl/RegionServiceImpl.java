package com.sv.mc.service.impl;

import com.sv.mc.pojo.AreaEntity;

import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.repository.AreaRepository;
import com.sv.mc.repository.CityRepository;
import com.sv.mc.repository.ProvinceRepository;
import com.sv.mc.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
   private AreaRepository areaRepository;
   @Autowired
   private ProvinceRepository provinceRepository;
   @Autowired
   private CityRepository cityRepository;

    /**
     * 查询所有大区
     * @return
     */
    @Override
    public List<AreaEntity> getRegionOne() {
        return this.areaRepository.findArea();
    }
    /**
     * 查询所有省
     * @return
     */
    @Override
    public List<ProvinceEntity> getRegionPrivince(int id) {
        return this.provinceRepository.getProvinceByAreaID(id);
    }
    /**
     * 查询所有市
     * @return
     */
    @Override
    public List<CityEntity> getRegionityCity(int id) {
        return this.cityRepository.findCityEntitiesByProvinceId(id);
    }























    @Override
    public List findAllEntities() {
        return null;
    }
}
