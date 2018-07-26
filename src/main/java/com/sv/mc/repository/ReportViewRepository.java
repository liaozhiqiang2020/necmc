package com.sv.mc.repository;


import com.sv.mc.pojo.AreaEntity;
import com.sv.mc.pojo.ReportViewEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReportViewRepository extends
        BaseRepository<AreaEntity, Long>, PagingAndSortingRepository<AreaEntity, Long> {

    /**
     * 根据省报表数据
     * @return
     */

@Query(value = "" +
        "SELECT" +
        " mc_place.name AS site," +
        " mc_device_model.name AS dm_name," +
        " mc_device_model.model AS chairType," +
        " count(DISTINCT mc_wx_user_info.Id) AS userNumber," +
        " count(case when mc_order. STATUS = 0 then mc_order. STATUS end) AS 'payment', " +
        " count(case when mc_order. STATUS = 1 then mc_order. STATUS end) AS 'accountpaid',  " +
        "  count(case when mc_order. STATUS = 2 then mc_order. STATUS end) AS 'offthestocks',  " +
        "  count(case when mc_order. STATUS = 3 then mc_order. STATUS end) AS 'canceled',  " +
        " count(mc_order.id) AS orderTotal," +
        " sum(mc_account.general_income) AS income," +
        " sum(mc_account.total_expenditure) AS expend," +
        " sum(mc_account.profit) AS profit," +
        "  count( DISTINCT mc_device_status.status)as unitException" +
        " FROM" +
        " mc_account," +
        " mc_place," +
        " mc_device_model," +
        " mc_wx_user_info," +
        " mc_order," +
        " mc_device_status," +
        " mc_device," +
        "        mc_province," +
        "        mc_city" +

        " where" +
        "  mc_order.pay_date_time BETWEEN :s and :e  " +
        " AND mc_place.id = mc_device.place_id" +
        " AND mc_device_status.device_id = mc_device.id" +
        " AND mc_order.device_Id = mc_device.Id" +
        " AND mc_order.wx_user_info_id=mc_wx_user_info.Id" +
        " AND mc_device.mc_type=mc_device_model.Id" +
        " AND mc_city.province_id=mc_province.Id" +
        " AND mc_place.city_id=mc_city.Id" +
        " AND  mc_place.id = mc_account.place_id "+
        " AND mc_province.id=:id"+
        " GROUP BY" +
        " site," +
        " dm_name," +
        " chairType",nativeQuery = true)
List<Object[]> fillReport(@Param("s") Date s, @Param("e") Date e, @Param("id") int id);

    /**
     * 根据市来查询报表
     */


    @Query(value = "" +
            "SELECT" +
            " mc_place.name AS site," +
            " mc_device_model.name AS dm_name," +
            " mc_device_model.model AS chairType," +
            " count(DISTINCT mc_wx_user_info.Id) AS userNumber," +
            " count(case when mc_order. STATUS = 0 then mc_order. STATUS end) AS 'payment', " +
            " count(case when mc_order. STATUS = 1 then mc_order. STATUS end) AS 'accountpaid',  " +
            "  count(case when mc_order. STATUS = 2 then mc_order. STATUS end) AS 'offthestocks',  " +
            "  count(case when mc_order. STATUS = 3 then mc_order. STATUS end) AS 'canceled',  " +
            " count(mc_order.id) AS orderTotal," +
            " sum(mc_account.general_income) AS income," +
            " sum(mc_account.total_expenditure) AS expend," +
            " sum(mc_account.profit) AS profit," +
            "  count( DISTINCT mc_device_status.status)as unitException" +
            " FROM" +
            " mc_account," +
            " mc_place," +
            " mc_device_model," +
            " mc_wx_user_info," +
            " mc_order," +
            " mc_device_status," +
            " mc_device," +
            "        mc_province," +
            "        mc_city" +

            " where" +
            "  mc_order.pay_date_time BETWEEN :s and :e  " +
            " AND mc_place.id = mc_device.place_id" +
            " AND mc_device_status.device_id = mc_device.id" +
            " AND mc_order.device_Id = mc_device.Id" +
            " AND mc_order.wx_user_info_id=mc_wx_user_info.Id" +
            " AND mc_device.mc_type=mc_device_model.Id" +
            " AND mc_city.province_id=mc_province.Id" +
            " AND mc_place.city_id=mc_city.Id" +
            " AND  mc_place.id = mc_account.place_id "+
            " AND mc_city.id=:id"+
            " GROUP BY" +
            " site," +
            " dm_name," +
            " chairType",nativeQuery = true)
    List<Object[]> fillcityReport(@Param("s") Date s, @Param("e") Date e, @Param("id") int id);


/**
 * 根据场地查询报表
 */
@Query(value = "" +
        "SELECT" +
        " mc_place.name AS site," +
        " mc_device_model.name AS dm_name," +
        " mc_device_model.model AS chairType," +
        " count(DISTINCT mc_wx_user_info.Id) AS userNumber," +
        " count(case when mc_order. STATUS = 0 then mc_order. STATUS end) AS 'payment', " +
        " count(case when mc_order. STATUS = 1 then mc_order. STATUS end) AS 'accountpaid',  " +
        "  count(case when mc_order. STATUS = 2 then mc_order. STATUS end) AS 'offthestocks',  " +
        "  count(case when mc_order. STATUS = 3 then mc_order. STATUS end) AS 'canceled',  " +
        " count(mc_order.id) AS orderTotal," +
        " sum(mc_account.general_income) AS income," +
        " sum(mc_account.total_expenditure) AS expend," +
        " sum(mc_account.profit) AS profit," +
        "  count( DISTINCT mc_device_status.status)as unitException" +
        " FROM" +
        " mc_account," +
        " mc_place," +
        " mc_device_model," +
        " mc_wx_user_info," +
        " mc_order," +
        " mc_device_status," +
        " mc_device," +
        "        mc_province," +
        "        mc_city" +

        " where" +
        "  mc_order.pay_date_time BETWEEN :s and :e  " +
        " AND mc_place.id = mc_device.place_id" +
        " AND mc_device_status.device_id = mc_device.id" +
        " AND mc_order.device_Id = mc_device.Id" +
        " AND mc_order.wx_user_info_id=mc_wx_user_info.Id" +
        " AND mc_device.mc_type=mc_device_model.Id" +
        " AND mc_city.province_id=mc_province.Id" +
        " AND mc_place.city_id=mc_city.Id" +
        " AND  mc_place.id = mc_account.place_id "+
        " AND mc_place.id=:id"+
        " GROUP BY" +
        " site," +
        " dm_name," +
        " chairType",nativeQuery = true)
List<Object[]> fillplaceReport(@Param("s") Date s, @Param("e") Date e, @Param("id") int id);







}
