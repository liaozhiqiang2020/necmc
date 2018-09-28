package com.sv.mc.service;


import com.sv.mc.pojo.SupplierEntity;

/**
 * Author:赵政博
 * service 供应商
 */
public interface SupplierService<T> extends BaseService<T> {
    /**
     * 保存数据
     * @param supplier 供应商
     * @return 供应商对象
     */
    SupplierEntity save(SupplierEntity supplier);

    /**
     * 根据id查询生产厂家数据
     * @param id 供应商Id
     * @return 供应商结果集
     */
    SupplierEntity findSupplierById(int id);

    /**
     * 插入一条生产厂家数据
     * @param supplier 插入的供应商
     * @return  插入的供应商
     */
    SupplierEntity insertSupplier(SupplierEntity supplier);

    /**
     * 根据供应商名称查询供应商
     * @param name 供应商名称
     * @return  供应商结果
     */
    SupplierEntity getSupplierBySName(String name);


}
