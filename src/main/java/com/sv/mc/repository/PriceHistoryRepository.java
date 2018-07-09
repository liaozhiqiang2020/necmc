package com.sv.mc.repository;

import com.sv.mc.pojo.PriceHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PriceHistoryRepository extends BaseRepository<PriceHistoryEntity, Long>, PagingAndSortingRepository<PriceHistoryEntity, Long> {
    //根据id查询价格
    @Query("from PriceHistoryEntity as p where p.id = :id")
    PriceHistoryEntity findPriceEntitiesById(@Param("id") int id);
}
