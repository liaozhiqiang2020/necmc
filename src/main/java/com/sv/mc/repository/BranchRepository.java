package com.sv.mc.repository;

import com.sv.mc.pojo.BranchEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 分页查询分公司信息
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_branch as b where b.discard_status=1 LIMIT :offset,:pageSize",nativeQuery=true)
    List<BranchEntity> findAllBranchByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询数量
     * @return
     */
    @Query(value="select count(*) from mc_branch as b where b.discard_status=1",nativeQuery = true)
    int findBranchTotal();
}
