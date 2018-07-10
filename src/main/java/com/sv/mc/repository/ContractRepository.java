package com.sv.mc.repository;

import com.sv.mc.pojo.ContractEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DAO层
 * author:赵政博
 */
@Repository
public interface ContractRepository extends BaseRepository<ContractEntity, Long>, PagingAndSortingRepository<ContractEntity, Long> {
    @Query("from ContractEntity as s where s.id = :id")
    ContractEntity findSignById(@Param("id") int id);

}
