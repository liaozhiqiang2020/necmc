package com.sv.mc.repository;

import com.sv.mc.pojo.DeviceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends BaseRepository<DeviceEntity, Long>, PagingAndSortingRepository<DeviceEntity, Long> {
    @Query("from DeviceEntity as d   where d.id = :id")
    DeviceEntity findDeviceById(@Param("id") int id);
}
