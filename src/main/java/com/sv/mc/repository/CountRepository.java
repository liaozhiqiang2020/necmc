package com.sv.mc.repository;

import com.sv.mc.pojo.AreaEntity;
import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.ProvinceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CountRepository extends BaseRepository<AreaEntity, Long>, PagingAndSortingRepository<AreaEntity, Long> {


    @Query(value = "select s.name,count(o.id),count(o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) from mc_order o left join mc_device d on d.id = o.device_Id left join mc_place p on d.place_id = p.id left join mc_city c on c.id = p.city_id left join mc_province s on c.province_id = s.id left join mc_account_detail ad on ad.from_id = o.id where s.id = :provinceId  and o.create_date_time BETWEEN :startDate AND :endDate",nativeQuery = true)
    List<Object[]> findCountById(@Param("provinceId") int pId,@Param("startDate") Date start ,@Param("endDate") Date end);


    /**
     * 根据省ID查询所有市信息
     * @param pId  省ID
     * @param start  起始时间
     * @param end   截止时间
     * @return
     */
    @Query(value = " "+
       "     select c.name ,count( o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) "+
       " from  mc_city c "+
            " left  join mc_place p on c.id = p.city_id "+
            "  left  join mc_device d on d.place_id = p.id "+
            " left  join mc_order o on o.device_id = d.id "+
            "  left  join mc_province s on c.province_id = s.id "+
            "  left  join mc_account_detail ad on ad.from_id = o.id and o.create_date_time >= :startDate and o.create_date_time < :endDate "+
            "  where c.province_id = :provinceId "+
            "   group by c.name "


            ,nativeQuery = true)
    List<Object[]>findCityByPid(@Param("provinceId") int pId,@Param("startDate") Date start ,@Param("endDate") Date end);


    /**
     * 根据市查询所有场地
     * @param cId
     * @param start
     * @param end
     * @return
     */

   @Query(value = " " +
           "   select p.name ,count( o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0))" +
            "  from  mc_city c " +
            " left  join mc_place p on c.id = p.city_id " +
            "  left  join mc_device d on d.place_id = p.id " +
            "  left  join mc_order o on o.device_id = d.id " +
           "  left  join mc_province s on c.province_id = s.id " +
           "  left  join mc_account_detail ad on ad.from_id = o.id and o.create_date_time >= :startDate and o.create_date_time < :endDate " +
           "  where p.city_id = :cityId " +
           "  group by p.name " +
           "",nativeQuery = true)
    List<Object[]>findPlaceByCityID(@Param("cityId") int cId,@Param("startDate") Date start ,@Param("endDate") Date end);


    /**
     * 根据市ID查询一个市所有数据
     */
    @Query(value = " " +
            "   select c.name ,count( o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
            "from  mc_city c " +
            " left  join mc_place p on c.id = p.city_id " +
            "   left  join mc_device d on d.place_id = p.id " +
            "   left  join mc_order o on o.device_id = d.id " +
            "  left  join mc_province s on c.province_id = s.id " +
            "   left  join mc_account_detail ad on ad.from_id = o.id and o.create_date_time >= :startDate and o.create_date_time < :endDate " +
            " where c.id = :cityId " +
            "",nativeQuery = true)
    List<Object[]>findOneCTByCityID(@Param("cityId") int cId,@Param("startDate") Date start ,@Param("endDate") Date end);


    /**
     * 根据场地ID查询一个场地
     */
    @Query(value = " " +
            "         select p.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0))" +
            "  from  mc_city c" +
            "  left  join mc_place p on c.id = p.city_id" +
            "  left  join mc_device d on d.place_id = p.id" +
            "  left  join mc_order o on o.device_id = d.id" +
            "  left  join mc_province s on c.province_id = s.id" +
            "   left  join mc_account_detail ad on ad.from_id = o.id and o.create_date_time >= :startDate and o.create_date_time < :endDate " +
            "  where p.id = :placeId " +
            "  group by p.name" +
            "",nativeQuery = true)
    List<Object[]>findOnePlaceByPlaceID(@Param("placeId") int cId,@Param("startDate") Date start ,@Param("endDate") Date end);


/**
 *   无条件查询所有省数据
 */
    @Query(value = " " +
            "      select s.name ,count(o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0)) " +
            " from  mc_city c  " +
            " left  join mc_place p on c.id = p.city_id  " +
            "  left  join mc_device d on d.place_id = p.id  " +
            "  left  join mc_order o on o.device_id = d.id  " +
            "  left  join mc_province s on c.province_id = s.id  " +
            "  left  join mc_account_detail ad on ad.from_id = o.id and o.create_date_time >= :startDate and o.create_date_time < :endDate and ad.from_level = 0  " +
            "  group by s.name  " +
            "  order by s.id  " +
            "",nativeQuery = true)
    List<Object[]>findProvince(@Param("startDate") Date start ,@Param("endDate") Date end);


    /**
     * 根据省查询所有市
     * @param start
     * @param end
     * @return
     */
    @Query(value = " " +
            "     select c.name ,count(distinct o.id),count(distinct o.wx_user_info_id),sum(if(ad.capital_flag=1,ad.capital,0))" +
            "  from  mc_city c " +
            "  left  join mc_place p on c.id = p.city_id " +
            "  left  join mc_device d on d.place_id = p.id " +
            "  left  join mc_order o on o.device_id = d.id " +
            "   left  join mc_province s on c.province_id = s.id " +
            "   left  join mc_account_detail ad on ad.from_id = o.id and o.create_date_time >= :startDate and o.create_date_time < :endDate " +
            "   where c.province_id = :provinceId " +
            "  group by c.name " +
            "",nativeQuery = true)
            List<Object[]>findcityByProvince(@Param("provinceId") int pId,@Param("startDate") Date start ,@Param("endDate") Date end);










}
