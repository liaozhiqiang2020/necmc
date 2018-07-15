package com.sv.mc.service;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.HeadQuartersEntity;

import java.util.List;

/**
 * 接口
 * author:赵政博
 */
public interface HeadQuartersService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param headQuarters 总公司数据
     * @return       HeadQuartersEntity
     */
    HeadQuartersEntity save(HeadQuartersEntity headQuarters);

    /**2
     * 根据总公司id查询总公司数据
     * @param id  总公司id
     * @return allHeadQuarters
     */
    HeadQuartersEntity findHeadQuartersById(int id);

    /**
     * 分页查询总公司数据
     */
    String findAllHeadByPage(int page, int pageSize);

    /**
     * 分页查询总公司数据
     */
    List<HeadQuartersEntity> findAllHead();


    /**
     * 根据总公司id更改对应的总公司数据
     * @param headQuartersEntity
     * @return BranchEntity
     */
    HeadQuartersEntity updateHeadDataById(HeadQuartersEntity headQuartersEntity);

    /**
     * 插入一条总公司数据
     * @param headQuartersEntity
     * @return BranchEntity
     */
    HeadQuartersEntity insertHead(HeadQuartersEntity headQuartersEntity);

    /**
     * 根据id修改状态
     */
    void deleteHead(int headId);

    /**
     * 根据分公司id查询总公司信息
     * @param branchId
     * @return
     */
    HeadQuartersEntity findHeadByBranchId(int branchId);
}
