package com.sv.mc.repository;

import com.sv.mc.pojo.ProvinceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * DAO层
 * author:赵政博
 */
public interface ProvinceRepository extends BaseRepository<ProvinceEntity, Long>, PagingAndSortingRepository<ProvinceEntity, Long> {


    @Query("from ProvinceEntity as b where b.id = :id")
    ProvinceEntity findProvinceById(@Param("id") int id);


    /**
     * 根据大区ID 查询省信息
     * // 王雨辰
     */
    @Query("from ProvinceEntity  as p where p.areaId=:id")
    List<ProvinceEntity> getProvinceByAreaID(@Param("id") int id);



    /**
     * 查询所有的省份
     */
    @Query("from ProvinceEntity ")
    List<ProvinceEntity> selectProvince();


    /**
     * 根据p_id 查询所有省
     */
    @Query(value = "select p.*  from mc_province as p where p.Id in(select c.province_id  FROM mc_city as c where c.Id in (SELECT p.city_id from mc_place as p where p.superior_id=:pid))",nativeQuery = true)
    List<ProvinceEntity>getProvinceByP_ID(@Param("pid")int pid);


    /**
     * 四级查询省 查询所有省
     */
    @Query(value = "select p.*  from mc_province as p where p.Id in(select c.province_id  FROM mc_city as c where c.Id in (SELECT p.city_id from mc_place as p where p.id=:pid))",nativeQuery = true)
    List<ProvinceEntity>getProvinceByID(@Param("pid")int pid);

}
