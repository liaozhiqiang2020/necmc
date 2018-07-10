package com.sv.mc.repository;

import com.sv.mc.pojo.CityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DAO层
 * author:赵政博
 */
@Repository
public interface CityRepository extends BaseRepository<CityEntity, Long>, PagingAndSortingRepository<CityEntity, Long> {

    @Query("from CityEntity as b where b.id = :id")
    CityEntity findCityById(@Param("id") int id);

}
