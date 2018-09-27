package com.sv.mc.repository;

import com.sv.mc.pojo.PriceHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * 价格历史表
 */
public interface PriceHistoryRepository extends BaseRepository<PriceHistoryEntity, Long>, PagingAndSortingRepository<PriceHistoryEntity, Long> {

    /**
     * 根据id查询价格历史
     * @param id  主键Id
     * @return 价格历史信息
     */
    @Query("from PriceHistoryEntity as p where p.id = :id")
    PriceHistoryEntity findPriceEntitiesById(@Param("id") int id);
}
