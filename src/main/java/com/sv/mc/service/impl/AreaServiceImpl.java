package com.sv.mc.service.impl;

import com.sv.mc.pojo.AreaEntity;
import com.sv.mc.repository.AreaRepository;
import com.sv.mc.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
/**
 * 大区
 */
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    /**
     * 保存数据
     * @param branch 大区域
     * @return       AreaEntity 大区信息
     */
    @Override
    public AreaEntity save(AreaEntity branch) {
        return areaRepository.save(branch);
    }


    /**
     * 删除大区
     * @param areaEntity 删除的大区
     */


    @Override
    public void removeArea(AreaEntity areaEntity) {
   areaEntity.setAreaState(0);
        this.areaRepository.save(areaEntity);
    }

    /**
     * 修改大区
     * @param areaEntity 修改了的内容
     * @return 修改后的内容
     */
    @Override
    public AreaEntity updateArea(AreaEntity areaEntity) {
       return this.areaRepository.save(areaEntity);
    }



    /**
     * 查询所有大区域
     * @return List 查询到的所有大区
     */
    @Override
    public List<AreaEntity> findAllEntities() {
        return areaRepository.findAll();
    }


    /**
     * 增加区域
     * @param areaEntity 增加的大区信息
     * @return 增加的大区
     */
    @Override
    public AreaEntity createArea(AreaEntity areaEntity) {
       areaEntity.setAreaState(1);
        return this.areaRepository.save(areaEntity);
    }


}
