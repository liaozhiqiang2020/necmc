package com.sv.mc.repository;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.GatewayEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GatewayRepository extends BaseRepository<GatewayEntity, Long>, PagingAndSortingRepository<GatewayEntity, Long> {
    //根据id查网关信息
    @Query("from GatewayEntity as d where d.id = :id")
    GatewayEntity findGatewayById(@Param("id") int id);

    //根据sn网关信息
    @Query("from GatewayEntity as d where d.gatewaySn = :sn")
    GatewayEntity findGatewayBySn(@Param("sn") String sn);

    //查询网关总数
    @Query("select count(id) from GatewayEntity")
    int findGatewayEntitsCount();

    //根据网关sn修改网关当前状态
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="update mc_gateway set status=:status where gateway_sn=:sn",nativeQuery = true)
    void updateGatewayStatusBySn(@Param("sn") String sn,@Param("status") int status);
}
