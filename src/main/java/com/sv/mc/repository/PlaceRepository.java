package com.sv.mc.repository;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.PlaceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 场地Dao
 */
@Repository
public interface PlaceRepository extends BaseRepository<PlaceEntity, Long>, PagingAndSortingRepository<PlaceEntity, Long> {

    /**
     * 根据场地Id 查询场地信息
     * @param id
     * @return 场地信息
     */
    @Query("from PlaceEntity as d   where d.id = :id")
    PlaceEntity findPlaceById(@Param("id") int id);

    /**
     * 根据场地id查询 行业市的场地
     * @param id 场地Id
     * @return 行业市下的场地
     */
    @Query(value="select " +
            "p.*,b.name,c.name " +
            "from mc.mc_place p, mc.mc_business b, mc.mc_city c " +
            "where b.id = p.business_id  and p.id = :id  " ,
            nativeQuery = true)
    List findPlaceListById(@Param("id") int id);

    /**
     * 根据市id查询场地
     * @param cityId 市id
     * @return 场地信息
     * @auther liaozhiqiang
     * @date 2018//7/11
     */
    @Query(value="select p.* from mc_city c,mc_place p where c.Id=p.city_id and c.Id=:cityId",nativeQuery = true)
    List<PlaceEntity> queryPlaceEntitiesByCityId(@Param("cityId") int cityId);

    /**
     * 分页查询场地方信息
     * @param offset 起始个数
     * @param pageSize 截至个数
     * @return 场地方集合
     */
    @Query(value="select * from mc_place as b where b.discard_status=1 and b.p_id is null LIMIT :offset,:pageSize",nativeQuery = true)
    List<PlaceEntity> findAllPlaceByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询主场地数量
     * @return  主场地数量
     */
    @Query(value="select count(*) from mc_place as b where b.discard_status=1 and b.p_id is null",nativeQuery = true)
    int findPlaceTotal();

    /**
     * 不分页查询场地方信息
     * @return 场地信息集合
     */
    @Query(value="select * from mc_place as b where b.discard_status=1",nativeQuery = true)
    List<PlaceEntity> findAllPlace();

    /**
     * 不分页查询场地方信息(pId为0，第一级)
     * @return 一级场地信息
     */
    @Query("from PlaceEntity as b where b.discardStatus=1 and b.pId is null")
    List<PlaceEntity> findAllPlaces2();


    /**
     *  不分页查询分公司场地方信息(pId为0，第一级)
     * @param superiorId  分公司Id
     * @return 分公司场地信息
     */
    @Query("from PlaceEntity as b where b.discardStatus=1 and b.pId is null and b.levelFlag=2 and b.superiorId=:superiorId")
    List<PlaceEntity> findAllPlaces3(@Param("superiorId") int superiorId);


    /**
     * 不分页查询代理商场地方信息(pId为0，第一级)
     * @param superiorId 代理商Id
     * @return  代理商场地信息
     */
    @Query("from PlaceEntity as b where b.discardStatus=1 and b.pId is null and b.levelFlag=3 and b.superiorId=:superiorId")
    List<PlaceEntity> findAllPlaces4(@Param("superiorId") int superiorId);


    /**
     * 根据场地Id 查询一级场地信息
     * @param placeId
     * @return 场地集合
     */
    @Query("from PlaceEntity as b where b.discardStatus=1 and b.pId is null and id=:placeId")
    List<PlaceEntity> findAllPlaces5(@Param("placeId") int placeId);

    /**
     *  根据场地 负责人 查询场地信息
     * @param placeId 场地ID
     * @param userId 负责人
     * @return 场地集合
     */
    @Query("from PlaceEntity where pId=:placeId and discardStatus=1 and userId=:userId")
    List<PlaceEntity> findPlaceByParentId(@Param("placeId") int placeId, @Param("userId") int userId);


    /**
     * 根据场地id查询他下一级的所有信息
     * @param placeId 场地Id
     * @return 场地信息
     */
    @Query("from PlaceEntity where pId=:placeId and discardStatus=1")
    List<PlaceEntity> findPlaceByParentId2(@Param("placeId") int placeId);



    /**
     * 根据场地Id 查询子场地id
     * @param placeId 场地Id
     * @return  子场地id
     */
    @Query(value="select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:placeId))",nativeQuery = true)
    List<Integer> findAllPlaceChildById(@Param("placeId") int placeId);

    /**
     * 查询场地下所有设备
     * @param placeId 场地Id
     * @return  场地下所有设备
     */
    @Query(value="select d.id,d.place_id,d.mc_type from mc_place p,mc_device d where p.id=d.place_id and p.id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:placeId)) )",nativeQuery = true)
    List<Object[]> findAllChildById(@Param("placeId") int placeId);

    /**
     * 查询场地下所有设备总数
     * @param placeId 场地Id
     * @return 设备数
     */
    @Query(value="select count(d.id) from mc_place p,mc_device d where p.id=d.place_id and p.id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:placeId)) )",nativeQuery = true)
    int findAllChildByIdCount(@Param("placeId") int placeId);

    /**
     * 分页查询场地下所有设备
     * @param placeId 场地Id
     * @return 场地下设备信息
     */
    @Query(value="select d.id,d.place_id,d.mc_type from mc_place p,mc_device d where p.id=d.place_id and p.id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:placeId)) )",nativeQuery = true)
    List<Object[]> findAllDeviceBypId(@Param("placeId") int placeId);

//    /**
//     * 根据场地id查所有设备
//     * @return
//     */
//    @Query("select p.deviceEntities from PlaceEntity p where p.id=:placeId")
//    List<DeviceEntity> findAllDeviceByPlaceId(@Param("placeId") int placeId);

    /**
     * 根据场地名称查询场地
     * @param name 场地名称
     * @return 场地信息
     */
    @Query("from PlaceEntity p where p.name = :name")
    PlaceEntity findPByName(@Param("name") String name);


    /**
     *  不分页查询当前用户管理的场地
     * @param pId 上级用户Id
     * @param level 用户等级
     * @return 场地集合
     */
    @Query(value="select * from mc_place as p where p.discard_status=1 AND p.p_id is null AND p.level_flag = :plevel and p.superior_id = :pId",nativeQuery = true)
    List<PlaceEntity> findAllPlaceById(@Param("pId") int pId, @Param("plevel") int level);


    /**
     *  根据场地名称查询场地
     * @param name 场地名称
     * @return 场地
     */
    @Query(value = "from PlaceEntity  as p where p.name=:name")
    PlaceEntity getPlaceName(@Param("name") String name);


    /**
     * 根据上级id查询场地
     * @param pid 上级Id
     * @return 场地集合
     */
    @Query(value = "select * from mc_place as p where p.superior_id=:pid",nativeQuery = true)
    List<PlaceEntity>getPlaceByP_ID(@Param("pid") int pid);



    /**
     *  根据上级场地id查询
     * @param pid 上级场地id
     * @return 场地集合
     */
    @Query(value = "select * from mc_place as p where p.id=:pid",nativeQuery = true)
    List<PlaceEntity>getPlaceBy_ID(@Param("pid") int pid);

}
