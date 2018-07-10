package com.sv.mc.repository;

import com.sv.mc.pojo.HeadQuartersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DAO层
 * author:赵政博
 */
@Repository
public interface HeadQuartersRepository extends BaseRepository<HeadQuartersEntity, Long>, PagingAndSortingRepository<HeadQuartersEntity, Long> {
    /**
     * 根据总公司id查询对应的总公司数据
     * @param   id 总公司id
     * @return  HeadQuartersEntity
     */
    @Query("from HeadQuartersEntity as h where h.id = :id")
    HeadQuartersEntity findHeadQuartersById(@Param("id") int id);
}


