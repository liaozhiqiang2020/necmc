package com.sv.mc.service;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.BusinessEntity;
import com.sv.mc.util.DataSourceResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    /**
     * 分页查询分公司数据
     */
    String findAllBranchByPage(int page, int pageSize);

    /**
     * 根据分公司id更改对应的分公司数据
     * @param branch 新分公司名称
     * @return BranchEntity
     */
    BranchEntity updateBranchDataById(BranchEntity branch);

    /**
     * 插入一条分公司数据
     * @param branch
     * @return BranchEntity
     */
    BranchEntity insertBranch(BranchEntity branch);

    /**
     * 根据id修改状态
     */
    void deleteBranch(int branchId);
}
