package com.sv.mc.repository;

import com.sv.mc.pojo.BranchEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DAO层
 * author:赵政博
 */
@Repository
public interface BranchRepository extends BaseRepository<BranchEntity, Long>, PagingAndSortingRepository<BranchEntity, Long> {
    /**
     * 根据分公司id查询对应的分公司数据
     * @param   id 分公司id
     * @return  BranchEntity
     */
    @Query("from BranchEntity as b where b.id = :id")
    BranchEntity findBranchById(@Param("id") int id);
}
