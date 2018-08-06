package com.sv.mc.repository;

import com.sv.mc.pojo.AreaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface countRepository extends BaseRepository<AreaEntity, Long>, PagingAndSortingRepository<AreaEntity, Long> {


    @Query(value = "select s.name,count(distinct o.id),count(distinct o.wx_user_info_id),count(ad.capital) from mc_order o left join mc_device d on d.id = o.device_Id left join mc_place p on d.place_id = p.id left join mc_city c on c.id = p.city_id left join mc_province s on c.province_id = s.id left join mc_account_detail ad on ad.from_id = o.id where s.id = :provinceId and ad.capital_flag= 1 and ad.from_level = 0 and o.create_date_time BETWEEN :startDate AND :endDate",nativeQuery = true)
    List<Object[]> findCountById(@Param("provinceId") int pId,@Param("startDate") Date start ,@Param("endDate") Date end);

}
