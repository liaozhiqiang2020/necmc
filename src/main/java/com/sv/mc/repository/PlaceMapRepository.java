package com.sv.mc.repository;

import com.sv.mc.pojo.PlaceMapEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 场地方地图
 */
@Repository
public interface PlaceMapRepository extends BaseRepository<PlaceMapEntity, Long>, PagingAndSortingRepository<PlaceMapEntity, Long> {
    /**
     * 根据Id 查询场地方地图
     * @param id 主键iD
     * @return 场地方地图信息
     */
    @Query("from PlaceMapEntity as d  where d.id = :id")
    PlaceMapEntity findDeviceById(@Param("id") int id);
}
