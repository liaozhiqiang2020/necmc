package com.sv.mc.repository;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.VendorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO层 代理商
 * author:赵政博
 */
@Repository
public interface VendorRepository extends BaseRepository<VendorEntity, Long>, PagingAndSortingRepository<VendorEntity, Long> {
    /**
     * 根据代理商id查询对应的代理商数据
     * @param   id 代理商id
     * @return  代理商集合
     */
    @Query(value = "from VendorEntity as b where b.id = :id")
    VendorEntity findVendorById(@Param("id") int id);



    /**
     *  根据负责人查询代理商
     * @param offset 起始个数
     * @param pageSize 截至个数
     * @param userId 负责人
     * @return
     */
    @Query(value="select * from mc_vendor where discard_status=1 and user_id=:userId  LIMIT :offset,:pageSize",nativeQuery = true)
    List<VendorEntity> findAllVendorByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize, @Param("userId") Integer userId);

    /**
     * 分页查询代理商信息
     * @param offset 起始个数
     * @param pageSize 截至个数
     * @return  代理商集合
     */
    @Query(value="select * from mc_vendor where discard_status=1 LIMIT :offset,:pageSize",nativeQuery = true)
    List<VendorEntity> findAllVendorByPage2(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);


    /**
     *  根据负责人查询供应商数量
     * @param userId 负责人
     * @return 供应商数量
     */
    @Query(value="select count(*) from VendorEntity where discardStatus=1 and userId=:userId")
    int findVendorTotal(@Param("userId") Integer userId);

    /**
     * 查询数量
     * @return 供应商数量
     */
    @Query(value="select count(*) from VendorEntity where discardStatus=1")
    int findVendorTotal2();

    /**
     * 根据分公司id 查询分公司
     * @param branchId 分公司id
     * @return 分公司
     */

    @Query(value=" from BranchEntity b where b.id=:branchId")
    BranchEntity findBranchNameById(@Param("branchId") int branchId);

    /**
     * 使用headId查总公司名
     */
    /**
     * 根据总公司id 查询总公司名
     * @param headId 总公司id
     * @return 总公司
     */
    @Query(value=" from HeadQuartersEntity b where b.id=:headId")
    HeadQuartersEntity findHeadNameById(@Param("headId") int headId);


    /**
     * 根据 分公司Id 分公司名称 查询
     * @param branchId 分公司id
     * @param branchName 分公司名称
     * @return 分公司
     */
    @Query(value=" from BranchEntity b where b.id=:branchId and b.name=:branchName")
    BranchEntity findBranchNameByIdAndName(@Param("branchId") int branchId, @Param("branchName") String branchName);

    /**
     * 根据总公司id 总公司名称查询总公司
     * @param headId 总公司id
     * @param headName 总公司名称
     * @return 总公司
     */
    @Query(value=" from HeadQuartersEntity b where b.id=:headId and b.name=:headName")
    HeadQuartersEntity findHeadNameByIdAndName(@Param("headId") int headId, @Param("headName") String headName);


    /**
     * 根据name查代理商
     * @param name 代理商名称
     * @return 代理商
     */
    @Query("from VendorEntity v where v.name = :name")
    VendorEntity findVendorEntityByName(@Param("name") String name);


    /**
     * 根据代理商id 查询下面的场地
     * @param vendorId 代理商Id
     * @return 场地集合
     */
    @Query("from PlaceEntity p where p.levelFlag=3 and p.discardStatus=1 and p.superiorId=:vendorId and p.pId is null")
    List<PlaceEntity> findAllPlaceByVendorId(@Param("vendorId") int vendorId);

    /**
     * 根据分公司Id查出其下所有代理商
     * @param bId 分公司id
     * @return 代理商集合
     */
    @Query(value="select * from mc_vendor where discard_status=1 AND superior_id = :bId",nativeQuery = true)
    List<VendorEntity> findVendorEntityByPid(@Param("bId") int bId);
}
