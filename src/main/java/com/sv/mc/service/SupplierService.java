package com.sv.mc.service;


import com.sv.mc.pojo.SupplierEntity;

/**
 * Author:赵政博
 * service
 */
public interface SupplierService<T> extends BaseService<T> {
    /**
     * 保存数据
     * @param supplier
     * @return
     */
    SupplierEntity save(SupplierEntity supplier);

    /**
     * 根据id查询生产厂家数据
     * @param id
     * @return
     */
    SupplierEntity findSupplierById(int id);

    /**
     * 插入一条生产厂家数据
     * @param supplier
     * @return
     */
    SupplierEntity insertSupplier(SupplierEntity supplier);


}
