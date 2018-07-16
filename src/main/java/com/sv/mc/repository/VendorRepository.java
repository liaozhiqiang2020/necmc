package com.sv.mc.repository;

import com.sv.mc.pojo.HeadQuartersEntity;
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
    @Query(value="select b.name from BranchEntity b where b.id=:branchId")
    String findBranchNameById(@Param("branchId")int branchId);

    /**
     * 使用headId查总公司名
     */
    @Query(value="select b.name from HeadQuartersEntity b where b.id=:headId")
    String findHeadNameById(@Param("headId")int headId);

}
