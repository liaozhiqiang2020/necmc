package com.sv.mc.service.impl;

import com.sv.mc.pojo.AreaEntity;
import com.sv.mc.repository.AreaRepository;
import com.sv.mc.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    /**
     * 保存数据
     * @param branch 大区域
     * @return       AreaEntity
     */
    @Override
    public AreaEntity save(AreaEntity branch) {
        return areaRepository.save(branch);
    }

    /**
     * 查询所有大区域
     * @return List
     */
    @Override
    public List<AreaEntity> findAllEntities() {
        return areaRepository.findAll();
    }



}
