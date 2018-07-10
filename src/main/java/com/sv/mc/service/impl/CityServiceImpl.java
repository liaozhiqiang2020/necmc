package com.sv.mc.service.impl;

import com.sv.mc.pojo.CityEntity;
import com.sv.mc.repository.CityRepository;
import com.sv.mc.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;

    /**
     * 保存数据
     * @param branch 市
     * @return       AreaEntity
     */
    @Override
    public CityEntity save(CityEntity branch) {
        return cityRepository.save(branch);
    }

    /**
     * 根据id查询市
     * @param id
     * @return
     */
    @Override
    public CityEntity findCityById(int id) {
        return cityRepository.findCityById(id);
    }

    /**
     * 查询所有市
     * @return List
     */
    @Override
    public List<CityEntity> findAllEntities() {
        return cityRepository.findAll();
    }



}
