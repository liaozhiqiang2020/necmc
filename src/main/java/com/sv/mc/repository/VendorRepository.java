package com.sv.mc.repository;

import com.sv.mc.pojo.VendorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DAO层
 * author:赵政博
 */
@Repository
public interface VendorRepository extends BaseRepository<VendorEntity, Long>, PagingAndSortingRepository<VendorEntity, Long> {
    /**
     * 根据代理商id查询对应的代理商数据
     * @param   id 代理商id
     * @return  VendorEntity
     */
    @Query(value = "from VendorEntity as b where b.id = :id")
    VendorEntity findVendorById(@Param("id") int id);



}
