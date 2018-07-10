package com.sv.mc.repository;

import com.sv.mc.pojo.AreaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DAO层
 * author:赵政博
 */
@Repository
public interface AreaRepository extends BaseRepository<AreaEntity, Long>, PagingAndSortingRepository<AreaEntity, Long> {

    @Query("from AreaEntity as b where b.id = :id")
    AreaEntity findAreaById(@Param("id") int id);

}
