package com.sv.mc.repository;

import com.sv.mc.pojo.SupplierEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DAO层
 * author:赵政博
 */
@Repository
public interface SupplierRepository extends BaseRepository<SupplierEntity, Long>, PagingAndSortingRepository<SupplierEntity, Long> {


    @Query("from SupplierEntity as s where s.id = :id")
    SupplierEntity findSupplierById(@Param("id") int id);

}
