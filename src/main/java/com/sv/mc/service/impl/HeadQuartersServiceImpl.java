package com.sv.mc.service.impl;

import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.repository.HeadQuartersRepository;
import com.sv.mc.service.HeadQuartersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:赵政博
 * 业务层
 */
@Service
public class HeadQuartersServiceImpl implements HeadQuartersService {
        @Autowired
        private HeadQuartersRepository headQuartersRepository;
    /**
     * 保存
     * @param headQuarters 总公司数据
     * @return HeadQuartersEntity
     */
    @Override
    public HeadQuartersEntity save(HeadQuartersEntity headQuarters) {
        return headQuartersRepository.save(headQuarters);
    }
    /**
     * 根据总公司id更改总公司数据
     * @param id  总公司id
     * @return HeadQuartersEntity
     */
    @Override
    public HeadQuartersEntity findHeadQuartersById(int id) {
        return headQuartersRepository.findHeadQuartersById(id);
    }


    @Override
    public List findAllEntities() {
        return headQuartersRepository.findAll();
    }
}
