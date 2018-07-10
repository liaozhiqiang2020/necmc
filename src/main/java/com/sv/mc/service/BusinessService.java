package com.sv.mc.service;

import com.sv.mc.pojo.BusinessEntity;

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


}
