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
 * DAO层
 * author:赵政博
 */
@Repository
public interface VendorRepository extends BaseRepository<VendorEntity, Long>, PagingAndSortingRepository<VendorEntity, Long> {
    /**
     * 根据代理商id查询对应的代理商数据
     * @param   id 代理商id
     * @return  VendorEntity
     */
    @Query(value = "from VendorEntity as b where b.id = :id")
    VendorEntity findVendorById(@Param("id") int id);


    /**
     * 分页查询代理商信息
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_vendor where discard_status=1 LIMIT :offset,:pageSize",nativeQuery = true)
    List<VendorEntity> findAllVendorByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询数量
     * @return
     */
    @Query(value="select count(*) from VendorEntity where discardStatus=1")
    int findVendorTotal();

    /**
     * 使用branchId查分公司名
     */
    @Query(value=" from BranchEntity b where b.id=:branchId")
    BranchEntity findBranchNameById(@Param("branchId")int branchId);

    /**
     * 使用headId查总公司名
     */
    @Query(value=" from HeadQuartersEntity b where b.id=:headId")
    HeadQuartersEntity findHeadNameById(@Param("headId")int headId);

    /**
     * 使用branchId,name查分公司名
     */
    @Query(value=" from BranchEntity b where b.id=:branchId and b.name=:branchName")
    BranchEntity findBranchNameByIdAndName(@Param("branchId")int branchId,@Param("branchName") String branchName);

    /**
     * 使用headId,name查总公司名
     */
    @Query(value=" from HeadQuartersEntity b where b.id=:headId and b.name=:headName")
    HeadQuartersEntity findHeadNameByIdAndName(@Param("headId")int headId,@Param("headName") String headName);


    /**
     * 根据name查代理商
     * @param name
     * @return
     */
    @Query("from VendorEntity v where v.name = :name")
    VendorEntity findVendorEntityByName(@Param("name")String name);


    /**
     * 根据代理商id查询下面的场地
     */
    @Query("from PlaceEntity p where p.levelFlag=3 and p.discardStatus=1 and p.superiorId=:vendorId and p.pId is null")
    List<PlaceEntity> findAllPlaceByVendorId(@Param("vendorId")int vendorId);

    /**
     * 根据分公司Id查出其下所有代理商
     * @param bId 分公司id
     * @return
     */
    @Query(value="select * from mc_vendor where discard_status=1 AND superior_id = :bId",nativeQuery = true)
    List<VendorEntity> findVendorEntityByPid(@Param("bId") int bId);
}
