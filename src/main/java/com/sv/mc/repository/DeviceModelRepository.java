package com.sv.mc.repository;

import com.sv.mc.pojo.DeviceModelEntity;
import com.sv.mc.pojo.PriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备类型dao
 */
@Repository
public interface DeviceModelRepository extends BaseRepository<DeviceModelEntity,Long>, PagingAndSortingRepository<DeviceModelEntity,Long>{
    /**
     * 根据Id 查询设备类型信息
     * @param dId
     * @return 设备类型信息
     */
    @Query("from DeviceModelEntity as d where d.id = :dId")
    DeviceModelEntity findById (@Param("dId") int dId);

    @Query(value = "select distinct d.model from mc_device_model d ",nativeQuery = true)
    List<String> findDeviceModelAll();

    /**
     * 根据 设备名称查询设备
     * @param name 设备类型名称
     * @param model  设备型号
     * @return  查询设备
     */
    @Query(value = "from DeviceModelEntity AS d where d.name=:name and d.model=:model")
    DeviceModelEntity getDeviceByName(@Param("name") String name,@Param("model") String model);







}
