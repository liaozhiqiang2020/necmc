package com.sv.mc.repository;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.GatewayEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface GatewayRepository extends BaseRepository<GatewayEntity, Long>, PagingAndSortingRepository<GatewayEntity, Long> {
    //根据id查网关信息
    @Query("from GatewayEntity as d where d.id = :id")
    GatewayEntity findGatewayById(@Param("id") int id);

    //根据sn网关信息
    @Query("from GatewayEntity as d where d.gatewaySn = :sn")
    GatewayEntity findGatewayBySn(@Param("sn") String sn);


}
