package com.sv.mc.repository;

import com.sv.mc.pojo.ProvinceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
/**
 * DAO层
 * author:赵政博
 */
public interface ProvinceRepository extends BaseRepository<ProvinceEntity, Long>, PagingAndSortingRepository<ProvinceEntity, Long> {


    @Query("from ProvinceEntity as b where b.id = :id")
    ProvinceEntity findProvinceById(@Param("id") int id);


}
