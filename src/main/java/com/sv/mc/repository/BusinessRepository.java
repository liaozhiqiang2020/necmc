package com.sv.mc.repository;

import com.sv.mc.pojo.BusinessEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DAO层
 * author:赵政博
 */
@Repository
public interface BusinessRepository extends BaseRepository<BusinessEntity, Long>, PagingAndSortingRepository<BusinessEntity, Long> {

    @Query("from BusinessEntity as b where b.id = :id")
    BusinessEntity findBusinessById(@Param("id") int id);

}
