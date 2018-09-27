package com.sv.mc.repository;

import com.sv.mc.pojo.BranchEntity;
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
public interface BranchRepository extends BaseRepository<BranchEntity, Long>, PagingAndSortingRepository<BranchEntity, Long> {
    /**
     * 根据分公司id查询对应的分公司数据
     * @param   id 分公司id
     * @return  BranchEntity 分公司结果对象
     */
    @Query("from BranchEntity as b where b.id = :id")
    BranchEntity findBranchById(@Param("id") int id);


    /**
     * 分页查询分公司信息分公司，代理商，场地
     * @param offset 起始个数
     * @param pageSize 截至个数
     * @param userId 用户id
     * @return 分公司信息结果集
     */
    @Query(value="select * from mc_branch as b where b.discard_status=1 and b.user_id=:userId LIMIT :offset,:pageSize",nativeQuery=true)
    List<BranchEntity> findAllBranchByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("userId") Integer userId);
    /**
     * 分页查询分公司信息，总公司
     * @param offset  起始个数
     * @param pageSize 截至个数
     * @return 分公司信息结果集
     */
    @Query(value="select * from mc_branch as b where b.discard_status=1 LIMIT :offset,:pageSize",nativeQuery=true)
    List<BranchEntity> findAllBranchByPage2(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询分公司个数数量
     * @return 分公司个数
     */
    @Query(value="select count(*) from mc_branch as b where b.discard_status=1",nativeQuery = true)
    int findBranchTotal2();

    /**
     * 分公司个数
     * @param userId 用户Id
     * @return  指定用户的分公司个数
     */
    @Query(value="select count(*) from mc_branch as b where b.discard_status=1 and b.user_id=:userId",nativeQuery = true)
    int findBranchTotal(@Param("userId") Integer userId);

    /**
     * 根据场地名查询场地
     * @param name 场地名称
     * @return  场地信息对象
     */
    @Query("from BranchEntity b where b.name = :name")
    BranchEntity findBByName(@Param("name") String name);



    /**
     * 根据分公司id查询下面的主场地
     * @param branchId 分公司Id
     * @return  主场地结果集
     */
    @Query("from PlaceEntity p where p.levelFlag=2 and p.discardStatus=1 and p.superiorId=:branchId and p.pId is null")
    List<PlaceEntity> findAllPlaceByBranchId(@Param("branchId")int branchId);

}
