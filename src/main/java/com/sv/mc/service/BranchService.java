package com.sv.mc.service;

import com.sv.mc.pojo.BranchEntity;

/**
 * 接口
 * author:赵政博
 */
public interface BranchService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param branch 分公司数据
     * @return       BranchEntity
     */
    BranchEntity save(BranchEntity branch);

    /**2
     * 根据分公司id查询对应分公司数据
     * @param id  分公司id
     * @return BranchEntity
     */
    BranchEntity findBranchById(int id);


    /**3
     * 根据分公司id更改对应的分公司数据
     * @param id  分公司id
     * @param branch 新分公司名称
     * @return BranchEntity
     */
    BranchEntity updateBranchDataById(int id ,BranchEntity branch);

    /**4
     * 插入一条分公司数据
     * @param branch
     * @return BranchEntity
     */
    BranchEntity insertBranch(BranchEntity branch);


}
