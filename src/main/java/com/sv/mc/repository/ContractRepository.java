package com.sv.mc.repository;

import com.sv.mc.pojo.ContractEntity;
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
public interface ContractRepository extends BaseRepository<ContractEntity, Long>, PagingAndSortingRepository<ContractEntity, Long> {
    @Query("from ContractEntity as s where s.id = :id")
    ContractEntity findSignById(@Param("id") int id);

    /**
     * 根据分公司id查询合同
     */
    @Query(value="select con.* from mc_branch b,mc_contract con where con.`owner`=b.Id and con.flag=2 and con.use_flag=1 and b.Id=:branchId",nativeQuery = true)
    List<ContractEntity> findContractsByBranchId(@Param("branchId") int branchId);

    /**
     * 根据分公司id查询合同历史
     */
    @Query(value="select con.* from mc_branch b,mc_contract con where con.`owner`=b.Id and con.flag=2 and con.use_flag=0 and b.Id=:branchId",nativeQuery = true)
    List<ContractEntity> findHistoryContractByBranchId(@Param("branchId") int branchId);


    /**
     * 根据总公司id查询合同
     */
    @Query(value="select con.* from mc_head_quarters b,mc_contract con where con.`owner`=b.Id and con.flag=1 and con.use_flag=1 and b.Id=:headId",nativeQuery = true)
    List<ContractEntity> findContractsByHeadId(@Param("headId") int headId);

    /**
     * 根据总公司id查询合同历史
     */
    @Query(value="select con.* from mc_head_quarters b,mc_contract con where con.`owner`=b.Id and con.flag=1 and con.use_flag=0 and b.Id=:headId",nativeQuery = true)
    List<ContractEntity> findHistoryContractByHeadId(@Param("headId") int headId);

    /**
     * 根据代理商id查询合同
     */
    @Query(value="select con.* from mc_vendor b,mc_contract con where con.`owner`=b.Id and con.flag=3 and con.use_flag=1 and b.Id=:vendorId",nativeQuery = true)
    List<ContractEntity> findContractsByVendorId(@Param("vendorId") int vendorId);

    /**
     * 根据代理商id查询合同历史
     */
    @Query(value="select con.* from mc_vendor b,mc_contract con where con.`owner`=b.Id and con.flag=3 and con.use_flag=0 and b.Id=:vendorId",nativeQuery = true)
    List<ContractEntity> findHistoryContractByVendorId(@Param("vendorId") int vendorId);

    /**
     * 解绑前查询合同
     */
    @Query(value="select c.* from mc_contract c where c.`owner`=:supId and c.flag=:flagId and c.second=:placeId and c.use_flag=1",nativeQuery = true)
    ContractEntity findContractEntityBeforeUnBound(@Param("placeId")int placeId,@Param("supId")int supId,@Param("flagId")int flagId);
}
