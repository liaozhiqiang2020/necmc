package com.sv.mc.repository;

import com.sv.mc.pojo.AreaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报表数据查询 dao数据库访问
 */
@Repository
public interface CountRepository extends BaseRepository<AreaEntity, Long>, PagingAndSortingRepository<AreaEntity, Long> {
    /**
     * 查询一个省数据
     * @param pId 省id
     * @param start 查询起始时间
     * @param end   查询截至时间
     * @return  省报表数据
     */
    @Query(value = "" +
            "      select s.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
            " from  mc_city c " +
            " left  join mc_place p on c.id = p.city_id " +
            " left  join mc_device d on d.place_id = p.id " +
            " left  join mc_order o on o.device_id = d.id  and o.create_date_time >= :startDate and o.create_date_time < :endDate" +
            " left  join mc_province s on c.province_id = s.id " +
            " left  join mc_account_detail ad on ad.from_id = o.id  " +
            " where s.id=:provinceId " +
            " group by s.name " +
            "",nativeQuery = true)
    List<Object[]> findCountById(@Param("provinceId") int pId, @Param("startDate") String start, @Param("endDate") String end);


    /**
     * 根据省ID查询所有市信息
     * @param pId  省ID
     * @param start  起始时间
     * @param end   截止时间
     * @return  所有市信息
     */
    @Query(value = " "+
       "     select c.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) "+
       " from  mc_city c "+
            " left  join mc_place p on c.id = p.city_id "+
            "  left  join mc_device d on d.place_id = p.id "+
            " left  join mc_order o on o.device_id = d.id  and o.create_date_time >= :startDate and o.create_date_time < :endDate"+
            "  left  join mc_province s on c.province_id = s.id "+
            "  left  join mc_account_detail ad on ad.from_id = o.id  "+
            "  where c.province_id = :provinceId "+
            "   group by c.name "


            ,nativeQuery = true)
    List<Object[]>findCityByPid(@Param("provinceId") int pId, @Param("startDate") String start, @Param("endDate") String end);


    /**
     * 根据市查询所有场地
     * @param cId 市Id
     * @param start 起始时间
     * @param end 截至时间
     * @return 所有场地数据
     */

   @Query(value = " " +
           "   select p.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0))" +
            "  from  mc_city c " +
            " left  join mc_place p on c.id = p.city_id " +
            "  left  join mc_device d on d.place_id = p.id " +
            "  left  join mc_order o on o.device_id = d.id  and o.create_date_time >= :startDate and o.create_date_time < :endDate" +
           "  left  join mc_province s on c.province_id = s.id " +
           "  left  join mc_account_detail ad on ad.from_id = o.id  " +
           "  where p.city_id = :cityId " +
           "  group by p.name " +
           "",nativeQuery = true)
    List<Object[]>findPlaceByCityID(@Param("cityId") int cId, @Param("startDate") String start, @Param("endDate") String end);



    /**
     *  根据市ID查询一个市所有数据
     * @param cId 市id
     * @param start 起始时间
     * @param end  截止时间
     * @return 指定市报表数据
     */

    @Query(value = " " +
            "   select c.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
            "from  mc_city c " +
            " left  join mc_place p on c.id = p.city_id " +
            "   left  join mc_device d on d.place_id = p.id " +
            "   left  join mc_order o on o.device_id = d.id  and o.create_date_time >= :startDate and o.create_date_time < :endDate" +
            "  left  join mc_province s on c.province_id = s.id " +
            "   left  join mc_account_detail ad on ad.from_id = o.id  " +
            " where c.id = :cityId " +
            "",nativeQuery = true)
    List<Object[]>findOneCTByCityID(@Param("cityId") int cId, @Param("startDate") String start, @Param("endDate") String end);



    /**
     * 根据场地Id查询一个场地的信息
     * @param cId 场地Id
     * @param start 起始时间
     * @param end 截至时间
     * @return 一个场地的信息
     */
    @Query(value = " " +
            "         select p.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0))" +
            "  from  mc_city c" +
            "  left  join mc_place p on c.id = p.city_id" +
            "  left  join mc_device d on d.place_id = p.id" +
            "  left  join mc_order o on o.device_id = d.id and o.create_date_time >= :startDate and o.create_date_time < :endDate" +
            "  left  join mc_province s on c.province_id = s.id" +
            "   left  join mc_account_detail ad on ad.from_id = o.id  " +
            "  where p.id = :placeId " +
            "  group by p.name" +
            "",nativeQuery = true)
    List<Object[]>findOnePlaceByPlaceID(@Param("placeId") int cId, @Param("startDate") String start, @Param("endDate") String end);



    /**
     *  无条件查询所有省数据
     * @param start 起始时间
     * @param end 截止时间
     * @return 省报表数据
     */
    @Query(value = " " +
          "  select s.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
            "  from  mc_city c " +
            "  left  join mc_place p on c.id = p.city_id " +
            "  left  join mc_device d on d.place_id = p.id " +
            "  left  join mc_order o on o.device_id = d.id and o.create_date_time >= :startDate and o.create_date_time < :endDate " +
            " left  join mc_province s on c.province_id = s.id " +
            "  left  join mc_account_detail ad on ad.from_id = o.id and ad.from_level = 0 " +
            " group by s.name having count(o.id)>0 " +
            "   " +
            "",nativeQuery = true)
    List<Object[]>findProvince(@Param("startDate") String start, @Param("endDate") String end);


    /**
     * 根据省id查询所有市数据
     * @param pId 省id
     * @param start 起始时间
     * @param end 截止时间
     * @return 指定市数据
     */
    @Query(value = " " +
            "     select c.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0))" +
            "  from  mc_city c " +
            "  left  join mc_place p on c.id = p.city_id " +
            "  left  join mc_device d on d.place_id = p.id " +
            "  left  join mc_order o on o.device_id = d.id and o.create_date_time >= :startDate and o.create_date_time < :endDate " +
            "   left  join mc_province s on c.province_id = s.id " +
            "   left  join mc_account_detail ad on ad.from_id = o.id  " +
            "   where c.province_id = :provinceId " +
            "  group by c.name " +
            "",nativeQuery = true)
            List<Object[]>findcityByProvince(@Param("provinceId") int pId, @Param("startDate") String start, @Param("endDate") String end);


//==================================================================================================================================================================
//=========================================2 ,3 级权限增加============================================================================================================
//==================================================================================================================================================================
//==================================================================================================================================================================


    /**
     *  2,3 权限 无条件查询所有省数据
     * @param level 用户等级
     * @param pid 上级id
     * @param start 起始时间
     * @param end 截止时间
     * @return 所有省数据
     */

@Query(value = "" +
        "  select s.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
        " from  mc_city c " +
        "  left join mc_place p on c.id = p.city_id and p.level_flag = :groudId and p.superior_id = :p_Id " +
        " left  join mc_device d on d.place_id = p.id " +
        " left  join mc_order o on o.device_id = d.id  and o.create_date_time >= :start and o.create_date_time < :end1" +
        " left  join mc_province s on c.province_id = s.id " +
        " left  join mc_account_detail ad on ad.from_id = o.id  " +
        " group by s.name having count(o.id)>0" +
        "",nativeQuery = true)
    List<Object[]> getProvinceBypId(@Param("groudId") int level, @Param("p_Id") int pid, @Param("start") String start, @Param("end1") String end);



    /**
     * 根据省ID 查询所在省数据
     * @param level 用户等级
     * @param pid 上级Id
     * @param start 起始时间
     * @param end 截止时间
     * @param provinceId 省id
     * @return 所在省数据
     */

    @Query(value = "" +
            "  select s.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
            " from  mc_city c " +
            " left join mc_place p on c.id = p.city_id and p.level_flag = :groudId and p.superior_id = :p_Id " +
            " left  join mc_device d on d.place_id = p.id " +
            " left  join mc_order o on o.device_id = d.id and o.create_date_time >= :start and o.create_date_time < :end1" +
            " left  join mc_province s on c.province_id = s.id " +
            " left  join mc_account_detail ad on ad.from_id = o.id  " +
            " where s.id=:provinceId" +
            " group by s.name " +
            "",nativeQuery = true)
    List<Object[]> getProvinceBypIdANDprovinceID(@Param("groudId") int level, @Param("p_Id") int pid, @Param("start")
            String start, @Param("end1") String end, @Param("provinceId") int provinceId);


    /**
     *  根据省ID查询所有所在市数据
     * @param level 用户等级
     * @param pid 上级Id
     * @param start 起始时间
     * @param end 截止时间
     * @param provinceId 省Id
     * @return  省下所在市数据
     */
    @Query(value = "" +
            "  select c.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
            " from  mc_city c " +
            " left join mc_place p on c.id = p.city_id and p.level_flag = :groudId and p.superior_id = :p_Id " +
            " left  join mc_device d on d.place_id = p.id " +
            " left  join mc_order o on o.device_id = d.id and o.create_date_time >= :start and o.create_date_time < :end1 " +
            " left  join mc_province s on c.province_id = s.id " +
            " left  join mc_account_detail ad on ad.from_id = o.id  " +
            " where s.id=:provinceId" +
            " group by c.name " +
            "",nativeQuery = true)
    List<Object[]> getCBypIdANDprovinceID(@Param("groudId") int level, @Param("p_Id") int pid, @Param("start")
            String start, @Param("end1") String end, @Param("provinceId") int provinceId);





    /**
     * 根据市ID查询所有所在市数据
     * @param level 用户等级
     * @param pid 上级Id
     * @param start 起始时间
     * @param end 截至时间
     * @param cityId 市id
     * @return 指定市数据
     */
    @Query(value = "" +
            "  select c.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
            " from  mc_city c " +
            " left join mc_place p on c.id = p.city_id and p.level_flag = :groudId and p.superior_id = :p_Id " +
            " left  join mc_device d on d.place_id = p.id " +
            " left  join mc_order o on o.device_id = d.id and o.create_date_time >= :start and o.create_date_time < :end1  " +
            " left  join mc_province s on c.province_id = s.id " +
            " left  join mc_account_detail ad on ad.from_id = o.id " +
            " where c.id=:cityId" +
            " group by c.name " +
            "",nativeQuery = true)
    List<Object[]> getCityBypIdANDcityID(@Param("groudId") int level, @Param("p_Id") int pid, @Param("start")
            String start, @Param("end1") String end, @Param("cityId") int cityId);




    /**
     *  根据市Id 查询所有所在场地数据
     * @param level 用户等级
     * @param pid 上级Id
     * @param start 起始时间
     * @param end 截止时间
     * @param cityId 市id
     * @return 所在场地数据
     */
    @Query(value = "" +
            "  select p.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
            " from  mc_city c " +
            " left join mc_place p on c.id = p.city_id and p.level_flag = :groudId and p.superior_id = :p_Id " +
            " left  join mc_device d on d.place_id = p.id " +
            " left  join mc_order o on o.device_id = d.id  and o.create_date_time >= :start and o.create_date_time < :end1" +
            " left  join mc_province s on c.province_id = s.id " +
            " left  join mc_account_detail ad on ad.from_id = o.id  " +
            " where c.id=:cityId" +
            " group by p.name " +
            "",nativeQuery = true)
    List<Object[]> getPlacyBypIdANDcityID(@Param("groudId") int level, @Param("p_Id") int pid, @Param("start")
            String start, @Param("end1") String end, @Param("cityId") int cityId);






    /**
     * 根据场地Id 查询所有所在场地数据
     * @param level 用户等级
     * @param pid  上级Id
     * @param start 起始时间
     * @param end 截止时间
     * @param placeId 场地id
     * @return 所在场地数据
     */
    @Query(value = "" +
            "    select p.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
            "  from  mc_city c " +
            " left join mc_place p on c.id = p.city_id and p.level_flag = :groudId and p.superior_id = :p_Id " +
            "  left  join mc_device d on d.place_id = p.id " +
            " left  join mc_order o on o.device_id = d.id  and o.create_date_time >= :start and o.create_date_time <:end1 " +
            " left  join mc_province s on c.province_id = s.id " +
            "  left  join mc_account_detail ad on ad.from_id = o.id  " +
            "  where p.id=:placeId " +
            "  group by p.name " +
            "",nativeQuery = true)
    List<Object[]> getPlacyBypIdANDplaceID(@Param("groudId") int level, @Param("p_Id") int pid, @Param("start")
            String start, @Param("end1") String end, @Param("placeId") int placeId);


    /**
     * 最低级权限查询场地
     * @param start 起始时间
     * @param end 截止时间
     * @param placeId 场地Id
     * @return 场地信息
     */

    @Query(value = "" +
            "   select p.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0))"
            + "   from  mc_city c "
            + "  left join mc_place p on c.id = p.city_id "
            + "    left  join mc_device d on d.place_id = p.id "
            + "    left  join mc_order o on o.device_id = d.id and o.create_date_time >= :start and o.create_date_time < :end1 "
            + "    left  join mc_province s on c.province_id = s.id "
            + "    left  join mc_account_detail ad on ad.from_id = o.id  "
            + "    where p.id  in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:placeId))) "
            +"    group by p.name "+
     "",nativeQuery = true)
    List<Object[]> getPlacyByANDplaceID(@Param("start") String start, @Param("end1") String end, @Param("placeId") int placeId);





}
