package com.sv.mc.service;

import com.sv.mc.pojo.*;
import com.sv.mc.util.DataSourceResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 接口
 * author:赵政博
 */
public interface BranchService<T> extends BaseService<T>{
    /**
     * 保存数据
     * @param branch 分公司数据
     * @return       BranchEntity 创建的分公司对象
     */
    BranchEntity save(BranchEntity branch);

    /**
     * 根据分公司id查询对应分公司数据
     * @param id  分公司id
     * @return BranchEntity 分公司对象
     */
    BranchEntity findBranchById(int id);


    /**
     * 分页查询分公司数据
     * @param page 起始个数
     * @param pageSize 截至个数
     * @param session 用户session
     * @return 分公司信息
     */
    String findAllBranchByPage(int page, int pageSize, HttpSession session);

    /**
     * 根据分公司id更改对应的分公司数据
     * @param branch 新分公司名称
     * @return BranchEntity 修改后的分公司数据
     */
    BranchEntity updateBranchDataById(BranchEntity branch);

    /**
     * 插入一条分公司数据
     * @param branch 需要插入的分公司数据
     * @return BranchEntity 插入后的分公司对象
     */
    BranchEntity insertBranch(BranchEntity branch);


    /**
     * 根据分公司id修改状态
     * @param branchId 分公司Id
     */
    void deleteBranch(int branchId);


    /**
     * 查询所有总公司和分公司内容
     */
    String allBranchAndHead();


    /**
     * 查询所有总公司和分公司和代理商内容
     */
    String allBranchAndHeadAndVendor();

    /**
     * 查询所有状态为1的用户
     */
    List<UserEntity> findAllByStatus();


    /**
     * 根据分公司id查询下面的场地
     * @param branchId 分公司id
     * @return 查询到的场地信息
     */
    List<PlaceEntity> findAllPlaceByBranchId(int branchId);


    /**
     * 根据分公司id查询下面的合同
     * @param branchId 分公司Id
     * @return 合同信息
     */
    String findContractsByBranchId(int branchId);

    /**
     * 根据分公司id 查询下面历史的合同
     * @param branchId 分公司Id
     * @return 历史合同
     */
    String findHistoryContractByBranchId(int branchId);

    /**
     * 分公司绑定场地
     * @param branchId 分公司Id
     * @param placeId 场地Id
     */
    void branchBoundPlace(int branchId, int placeId);
}
