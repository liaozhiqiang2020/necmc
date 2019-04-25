package com.sv.mc.repository;

import com.sv.mc.pojo.SupplierEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DAO层 供应商
 * author:赵政博
 */
@Repository
public interface SupplierRepository extends BaseRepository<SupplierEntity, Long>, PagingAndSortingRepository<SupplierEntity, Long> {

    /**
     * 根据Id 来查询供应商信息
     * @param id 主键Id
     * @return 供应商信息
     */
    @Query("from SupplierEntity as s where s.id = :id")
    SupplierEntity findSupplierById(@Param("id") int id);


    /**
     * 根据供应商名称来查询 供应商信息
     * @param name 供应商名称
     * @return 供应商信息
     */

    @Query("from SupplierEntity as s where s.supplierName=:name")
    SupplierEntity getSupplierBySName(@Param("name") String name);
}
