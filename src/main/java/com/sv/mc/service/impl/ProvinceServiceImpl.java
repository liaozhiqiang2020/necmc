package com.sv.mc.service.impl;

import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.repository.AreaRepository;
import com.sv.mc.repository.ProvinceRepository;
import com.sv.mc.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private AreaRepository areaRepository;
    /**
     * 保存数据
     * @param province 省
     * @return  ProvinceEntity 省对象
     */

    @Override
    public ProvinceEntity save(ProvinceEntity province) {
        return provinceRepository.save(province);
    }

    /**
     * 根据id查询省
     * @param id 省id
     * @return  ProvinceEntity
     */
    @Override
    public ProvinceEntity findProvinceById(int id) {
        return provinceRepository.findProvinceById(id);
    }



    /**
     * 查询所有省
     * @return List 省集合
     */
    @Override
    public List<ProvinceEntity> findAllEntities() {
        return provinceRepository.findAll();
    }


    /**
     * 删除省份
     * @param provinceEntity 省份信息
     */

    @Override
    public void removeProvince(ProvinceEntity provinceEntity) {
        provinceEntity.setAreaId(241);
        this.provinceRepository.save(provinceEntity);
    }

    /**
     * 增加省份
     * @param provinceEntity 增加的内容
     * @return  增加的对象
     */
    @Override
    public ProvinceEntity createProvince(ProvinceEntity provinceEntity) {
        return this.provinceRepository.save(provinceEntity);
    }

    /**
     * 查询所有的省份
     * @return 查询到的所有省
     */
    @Override
    public List<ProvinceEntity> selectProvince() {
        return this.provinceRepository.selectProvince();
    }

    /**
     *  查询所有省份
     * @param pid 场地Id
     * @return 查询到的所有省份集合
     */
    @Override
    public List<ProvinceEntity> getProvinceByID(int pid) {
        return this.provinceRepository.getProvinceByID(pid);
    }


}
