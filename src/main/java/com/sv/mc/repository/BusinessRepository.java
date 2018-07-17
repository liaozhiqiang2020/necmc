package com.sv.mc.repository;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.BusinessEntity;
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
public interface BusinessRepository extends BaseRepository<BusinessEntity, Long>, PagingAndSortingRepository<BusinessEntity, Long> {

    @Query("from BusinessEntity as b where b.id = :id")
    BusinessEntity findBusinessById(@Param("id") int id);


    /**
     * 分页查询行业分类信息
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_business as b where b.discard_status=1 LIMIT :offset,:pageSize",nativeQuery=true)
    List<BusinessEntity> findAllBussByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询数量
     * @return
     */
    @Query(value="select count(*) from mc_business as b where b.discard_status=1",nativeQuery = true)
    int findBusinessTotal();


    /*
    查询一级目录
    */
    @Query("from BusinessEntity as b where b.level=:id and b.discardStatus=1")
    List<BusinessEntity> findBusinessByParentId(@Param("id") Integer id);



    /*
    查询二级目录
    */
    @Query("from BusinessEntity as b where b.parentId=:id and b.discardStatus=1")
    List<BusinessEntity> findBusinessByParentId2(@Param("id") int id);















}
