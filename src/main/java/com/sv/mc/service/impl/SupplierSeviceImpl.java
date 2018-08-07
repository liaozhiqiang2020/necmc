package com.sv.mc.service.impl;

import com.sv.mc.pojo.SupplierEntity;
import com.sv.mc.repository.SupplierRepository;
import com.sv.mc.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:赵政博
 * service
 */
@Service
public class SupplierSeviceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * 保存数据
     * @param supplier
     * @return
     */
    @Override
    public SupplierEntity save(SupplierEntity supplier) {
        return supplierRepository.save(supplier);
    }

    /**
     * 根据id查询生产商
     * @param id
     * @return
     */
    @Override
    public SupplierEntity findSupplierById(int id) {
        return supplierRepository.findSupplierById(id);
    }

    /**
     * 插入一条新的生产商数据
     * @param supplier
     * @return
     */
    @Override
    public SupplierEntity insertSupplier(SupplierEntity supplier) {
        return supplierRepository.save(supplier);
    }



    /**
     * 查询所有生产商数据
     * @return
     */
    @Override
    public List<SupplierEntity> findAllEntities() {
        return supplierRepository.findAll();
    }


    /**
     * 根据供应商名称查询 供应商信息
     * @param name 供应商名称
     * @return
     */

    @Override
    public SupplierEntity getSupplierBySName(String name) {
        return this.supplierRepository.getSupplierBySName(name);
    }










}
