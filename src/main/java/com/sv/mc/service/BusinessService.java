package com.sv.mc.service;

import com.sv.mc.pojo.BusinessEntity;

import java.util.List;

/**
 * 接口
 * author:赵政博
 */
public interface BusinessService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param business 保存行业分类数据
     * @return       AreaEntity
     */
    BusinessEntity save(BusinessEntity business);

    /**
     * 查询行业分类详细数据
     * @param id 判断一二级
     * @return BusinessEntity
     */
    BusinessEntity fianBusinessById(int id);

    /**
     * 插入数据
     * @param businessEntity
     * @return
     */
    BusinessEntity insertBusiness(BusinessEntity businessEntity);

    /**
     * 修改数据
     * @param businessEntity
     * @return
     */
    BusinessEntity updateBussiness(BusinessEntity businessEntity);

    /**
     * 删除数据
     * @param businessId
     */
    void deleteBussiness(int businessId);

    /**
     * 分页查询行业分类信息
     */
    String findAllBusinessByPage(int page, int pageSize);
}
