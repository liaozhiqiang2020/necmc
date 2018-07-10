package com.sv.mc.service.impl;

import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.repository.ProvinceRepository;
import com.sv.mc.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;
    /**
     * 保存数据
     * @param province 省
     * @return       ProvinceEntity
     */

    @Override
    public ProvinceEntity save(ProvinceEntity province) {
        return provinceRepository.save(province);
    }

    /**
     * 根据id查询省
     * @param id
     * @return  ProvinceEntity
     */
    @Override
    public ProvinceEntity findProvinceById(int id) {
        return provinceRepository.findProvinceById(id);
    }

    /**
     * 查询所有省
     * @return List
     */
    @Override
    public List<ProvinceEntity> findAllEntities() {
        return provinceRepository.findAll();
    }

}
