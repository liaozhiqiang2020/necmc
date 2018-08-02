package com.sv.mc.repository;

import com.sv.mc.pojo.DeviceModelEntity;
import com.sv.mc.pojo.PriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceModelRepository extends BaseRepository<DeviceModelEntity,Long>, PagingAndSortingRepository<DeviceModelEntity,Long>{

    @Query("from DeviceModelEntity as d where d.id = :dId")
    DeviceModelEntity findById (@Param("dId") int dId);

    @Query(value = "select distinct d.model from mc_device_model d ",nativeQuery = true)
    List<String> findDeviceModelAll();
}
