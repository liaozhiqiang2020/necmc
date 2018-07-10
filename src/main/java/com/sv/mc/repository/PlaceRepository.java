package com.sv.mc.repository;

import com.sv.mc.pojo.PlaceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends BaseRepository<PlaceEntity, Long>, PagingAndSortingRepository<PlaceEntity, Long> {
    @Query("from PlaceEntity as d   where d.id = :id")
    PlaceEntity findPlaceById(@Param("id") int id);

    @Query(value="select " +
            "p.*,b.name,c.name " +
            "from mc.mc_place p, mc.mc_business b, mc.mc_city c " +
            "where b.id = p.business_id  and p.id = :id  " ,
            nativeQuery = true)
    List findPlaceListById(@Param("id") int id);


}
