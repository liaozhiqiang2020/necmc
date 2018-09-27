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

    /**
     * 根据行业Id查询行业信息
     * @param id 行业主键Id
     * @return   行业信息对象
     */
    @Query("from BusinessEntity as b where b.id = :id")
    BusinessEntity findBusinessById(@Param("id") int id);


    /**
     * 分页查询行业分类信息
     * @param offset  起始个数
     * @param pageSize 截至个数
     * @return 行业分类信息结果
     */
    @Query(value="select * from mc_business as b where b.discard_status=1 LIMIT :offset,:pageSize",nativeQuery=true)
    List<BusinessEntity> findAllBussByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询行业数量
     * @return
     */
    @Query(value="select count(*) from mc_business as b where b.discard_status=1",nativeQuery = true)
    int findBusinessTotal();


    /**
     * 按照行业等级 id 查询行业信息
     * @param id 行业等级id
     * @return 行业信息结果
     */
    @Query("from BusinessEntity as b where b.level=:id and b.discardStatus=1")
    List<BusinessEntity> findBusinessByParentId(@Param("id") Integer id);


    /**
     * 根据一级行业id 查询行业信息
     * @param id  一级行业Id
     * @return  行业结果集
     */
    @Query("from BusinessEntity as b where b.parentId=:id and b.discardStatus=1")
    List<BusinessEntity> findBusinessByParentId2(@Param("id") int id);


    /**
     *   所有行业(未逻辑删除)
     */
    @Query(value="from BusinessEntity where discardStatus=1")
    List<BusinessEntity> allUseBusiness();










}
