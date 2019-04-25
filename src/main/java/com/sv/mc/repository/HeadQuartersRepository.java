package com.sv.mc.repository;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.pojo.PlaceEntity;
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
     * @return  总部信息
     */
    @Query("from HeadQuartersEntity as h where h.id = :id")
    HeadQuartersEntity findHeadQuartersById(@Param("id") int id);

    /**
     * 分页查询总公司信息
     * @param offset 起始个数
     * @param pageSize  结束个数
     * @return 总公司信息
     */
    @Query(value="select * from mc_head_quarters where delete_flag=1 LIMIT :offset,:pageSize",nativeQuery = true)
    List<HeadQuartersEntity> findAllHeadByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询数量
     * @return 总公司数量
     */
    @Query(value="select count(*) from HeadQuartersEntity where deleteFlag=1")
    int findHeadTotal();

    /**
     * 查询总公司信息不分页
     * @return 总公司信息
     */
    @Query("from HeadQuartersEntity where deleteFlag=1")
    List<HeadQuartersEntity> findAllHead();

    /**
     * 根据分公司id查询所在的总公司
     * @param branchId 分公司Id
     * @return 总公司信息
     */
    @Query(value = "select h.* from mc_branch b,mc_head_quarters h where b.head_quarters_id=h.id and b.id=:branchId",nativeQuery = true)
    HeadQuartersEntity findHeadByBranchId(@Param("branchId") int branchId);

    /**
     * 根据总公司名称查询总公司
     * @param name 总公司名称
     * @return 总公司信息
     */
    @Query("from HeadQuartersEntity h where h.name = :name")
    HeadQuartersEntity findHByName(@Param("name") String name);



    /**
     * 根据总公司Id 查询下面的场地
     * @param headId 总公司Id
     * @return 场地信息集合
     */
    @Query("from PlaceEntity p where p.levelFlag=1 and p.discardStatus=1 and p.superiorId=:headId and p.pId is null")
    List<PlaceEntity> findAllPlaceByHeadId(@Param("headId") int headId);


    /**
     * 查询所有未绑定的场地(总场地)
     * @return 场地信息
     */
    @Query("from PlaceEntity p where p.levelFlag is null and p.discardStatus=1 and p.superiorId is null and p.pId is null")
    List<PlaceEntity> findAllUnboundPlace();


}


