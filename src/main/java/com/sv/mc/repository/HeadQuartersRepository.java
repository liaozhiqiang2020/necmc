package com.sv.mc.repository;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.HeadQuartersEntity;
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
public interface HeadQuartersRepository extends BaseRepository<HeadQuartersEntity, Long>, PagingAndSortingRepository<HeadQuartersEntity, Long> {
    /**
     * 根据总公司id查询对应的总公司数据
     * @param   id 总公司id
     * @return  HeadQuartersEntity
     */
    @Query("from HeadQuartersEntity as h where h.id = :id")
    HeadQuartersEntity findHeadQuartersById(@Param("id") int id);

    /**
     * 分页查询总公司信息
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_head_quarters where delete_flag=1 LIMIT :offset,:pageSize",nativeQuery = true)
    List<HeadQuartersEntity> findAllHeadByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询数量
     * @return
     */
    @Query(value="select count(*) from HeadQuartersEntity where deleteFlag=1")
    int findHeadTotal();

    /**
     * 查询总公司信息不分页
     * @return
     */
    @Query("from HeadQuartersEntity where deleteFlag=1")
    List<HeadQuartersEntity> findAllHead();

    /**
     * 根据分公司id查询所在的总公司
     * @param branchId
     * @return
     */
    @Query(value = "select h.* from mc_branch b,mc_head_quarters h where b.head_quarters_id=h.id and b.id=:branchId",nativeQuery = true)
    HeadQuartersEntity findHeadByBranchId(@Param("branchId") int branchId);
}


