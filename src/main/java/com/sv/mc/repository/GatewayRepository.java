package com.sv.mc.repository;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.GatewayEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 网关dao
 */
@Repository
public interface GatewayRepository extends BaseRepository<GatewayEntity, Long>, PagingAndSortingRepository<GatewayEntity, Long> {


    /**
     * 根据id 查询网关信息
     * @param id 主键id
     * @return 网关信息
     */
    @Query("from GatewayEntity as d where d.id = :id")
    GatewayEntity findGatewayById(@Param("id") int id);



    /**
     * 根据网关sn查询网关信息
     * @param sn 网关Sn编号
     * @return 网关信息
     */
    @Query("from GatewayEntity as d where d.gatewaySn = :sn")
    GatewayEntity findGatewayBySn(@Param("sn") String sn);



    /**
     * 查询网关总数
     * @return 网关数量
     */
    @Query("select count(id) from GatewayEntity")
    int findGatewayEntitsCount();



    /**
     * 根据网关sn修改网关当前状态
     * @param sn 网关Sn
     * @param status 网关状态
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="update mc_gateway set status=:status where gateway_sn=:sn",nativeQuery = true)
    void updateGatewayStatusBySn(@Param("sn") String sn, @Param("status") int status);
}
