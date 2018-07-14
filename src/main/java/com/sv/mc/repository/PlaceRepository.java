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

    /**
     * 根据市id查询场地
     * @param cityId
     * @return
     * @auther liaozhiqiang
     * @date 2018//7/11
     */
    @Query(value="select p.* from mc_city c,mc_place p where c.Id=p.city_id and c.Id=:cityId",nativeQuery = true)
    List<PlaceEntity> queryPlaceEntitiesByCityId(@Param("cityId") int cityId);

    /**
     * 分页查询场地方信息
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_place as b where b.discard_status=1 LIMIT :offset,:pageSize",nativeQuery = true)
    List<PlaceEntity> findAllPlaceByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询数量
     * @return
     */
    @Query(value="select count(*) from mc_place as b where b.discard_status=1",nativeQuery = true)
    int findPlaceTotal();
}
