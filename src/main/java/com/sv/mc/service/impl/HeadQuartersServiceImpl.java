package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.repository.HeadQuartersRepository;
import com.sv.mc.service.HeadQuartersService;
import com.sv.mc.util.DataSourceResult;
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


    /**
     * 分页查询总公司数据
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public String findAllHeadByPage(int page, int pageSize) {
        Gson gson = new Gson();
        DataSourceResult<HeadQuartersEntity> branchEntityDataSourceResult = new DataSourceResult<>();
        int offset = ((page-1)*pageSize);
        List<HeadQuartersEntity> headQuartersEntities = this.headQuartersRepository.findAllHeadByPage(offset,pageSize);
        int total = this.headQuartersRepository.findHeadTotal();
        branchEntityDataSourceResult.setData(headQuartersEntities);
        branchEntityDataSourceResult.setTotal(total);
        return gson.toJson(branchEntityDataSourceResult);
    }

    /**
     * 修改数据
     * @param headQuartersEntity
     * @return
     */
    @Override
    public HeadQuartersEntity updateHeadDataById(HeadQuartersEntity headQuartersEntity) {
        return this.headQuartersRepository.save(headQuartersEntity);
    }

    /**
     * 新增数据
     * @param headQuartersEntity
     * @return
     */
    @Override
    public HeadQuartersEntity insertHead(HeadQuartersEntity headQuartersEntity) {
        headQuartersEntity.setDeleteFlag(1);
        return this.headQuartersRepository.save(headQuartersEntity);
    }

    /**
     * 逻辑删除数据
     * @param headId
     */
    @Override
    public void deleteHead(int headId) {
        HeadQuartersEntity headQuartersEntity = findHeadQuartersById(headId);
        headQuartersEntity.setDeleteFlag(0);
        this.headQuartersRepository.save(headQuartersEntity);
    }


    @Override
    public List<HeadQuartersEntity> findAllHead() {
        return this.headQuartersRepository.findAllHead();
    }

    @Override
    public HeadQuartersEntity findHeadByBranchId(int branchId) {
        return this.headQuartersRepository.findHeadByBranchId(branchId);
    }
}
