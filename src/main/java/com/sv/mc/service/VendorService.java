package com.sv.mc.service;

import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.pojo.VendorEntity;

/**
 * 接口
 * author:赵政博
 */
public interface VendorService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param vendor 代理商数据
     * @return       VendorEntity
     */
    VendorEntity save(VendorEntity vendor);

    /**2
     * 根据分公司id查询对应代理商数据
     * @param id  代理商id
     * @return VendorEntity
     */
    VendorEntity findVendorById(int id);


    /**3
     * 根据分公司id更改对应的代理商数据
     * @param id  代理商id
     * @param vendor 代理商数据
     * @return VendorEntity
     */
    VendorEntity updateVendorDataById(int id, VendorEntity vendor);



    /**4
     * 插入一条分代理商数据
     * @param vendor
     * @return VendorEntity
     */
    VendorEntity insertVendor(VendorEntity vendor);

    /**
     * 分页查询代理商数据
     */
    String findAllVendorByPage(int page, int pageSize);


    /**
     * 根据代理商id更改对应的代理商数据
     * @param vendorEntity
     * @return BranchEntity
     */
    VendorEntity updateVendorDataById(VendorEntity vendorEntity);

    /**
     * 根据id修改状态
     */
    void deleteVendor(int headId);


}