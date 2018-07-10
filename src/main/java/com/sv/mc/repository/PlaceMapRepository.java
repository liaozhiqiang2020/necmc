package com.sv.mc.repository;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.PlaceMapEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceMapRepository extends BaseRepository<PlaceMapEntity, Long>, PagingAndSortingRepository<PlaceMapEntity, Long> {
    @Query("from PlaceMapEntity as d  where d.id = :id")
    PlaceMapEntity findDeviceById(@Param("id") int id);
}
