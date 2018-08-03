package com.sv.mc.service;

import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.VendorEntity;

import java.util.List;
import java.util.Map;

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


    /**
     * 分页查询代理商数据
     */
    String findAllVendorByPage(int page, int pageSize);


    /**
     * 根据代理商id更改对应的代理商数据
     * @param
     * @return BranchEntity
     */
    VendorEntity updateVendorDataById(Map<String,Object> map);

    /**4
     * 插入一条分代理商数据
     * @param
     * @return VendorEntity
     */
    VendorEntity insertVendor(Map map);

    /**
     * 根据id修改状态
     */
    void deleteVendor(int headId);

    /**
     * 根据代理商id查询下面的场地
     * @param vendorId
     * @return
     */
    List<PlaceEntity> findAllPlaceByVendorId(int vendorId);


    /**
     * 代理商绑定场地
     * @param vendorId
     * @param placeId
     */
    void vendorBoundPlace(int vendorId, int placeId);


    /**
     * 根据代理商id查询下面的合同
     */
    String findContractByVendorId(int vendorId);


    /**
     * 根据代理商id查询合同历史
     */
    String findHistoryContractByVendorId(int vendorId);
}
