package com.sv.mc.repository;

import com.sv.mc.pojo.AreaEntity;
import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.ProvinceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO层 大区查询
 * author:赵政博
 */
@Repository
public interface AreaRepository extends BaseRepository<AreaEntity, Long>, PagingAndSortingRepository<AreaEntity, Long> {
    /**
     * 根据大区Id查询大区Id
     * @param id  大区Id
     * @return  大区结果对象
     */
    @Query("from AreaEntity as b where b.id = :id")
    AreaEntity findAreaById(@Param("id") int id);

    /**
     *
     *
     * @return 查询所有大区
     */

    @Query("from AreaEntity as a where a.areaState=1")
    List<AreaEntity> findArea();



}
