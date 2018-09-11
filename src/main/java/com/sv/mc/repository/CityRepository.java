package com.sv.mc.repository;

import com.sv.mc.pojo.CityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO层
 * author:赵政博
 */
@Repository
public interface CityRepository extends BaseRepository<CityEntity, Long>, PagingAndSortingRepository<CityEntity, Long> {

    @Query("from CityEntity as b where b.id = :id")
    CityEntity findCityById(@Param("id") int id);

    /**
     * 根据省份id查询市
     * @param provinceId
     * @return
     * @auther liaozhiqiang
     * @date 2018//7/11
     */
    @Query(value="select c.* from mc_city c,mc_province p where c.province_id=p.Id and p.Id=:provinceId",nativeQuery = true)
    List<CityEntity> findCityEntitiesByProvinceId(@Param("provinceId") int provinceId);


    /**
     * 根据p_id 查询所有市
     */

    @Query(value = " select  c.*  FROM mc_city as c where c.Id in (SELECT p.city_id from mc_place as p where p.superior_id=:pid)",nativeQuery = true)
    List<CityEntity> getCityByP_ID(@Param("pid") int pid);

    /**
     * 四级查询市
     */
    @Query(value = " select  c.*  FROM mc_city as c where c.Id in (SELECT p.city_id from mc_place as p where p.id=:pid)",nativeQuery = true)
    List<CityEntity> getCityByPlace_ID(@Param("pid") int pid);

}
