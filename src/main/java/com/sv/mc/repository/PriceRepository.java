package com.sv.mc.repository;

import com.sv.mc.pojo.PriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PriceRepository extends BaseRepository<PriceEntity, Long>, PagingAndSortingRepository<PriceEntity, Long> {

    //根据钱数查询价格
    @Query("from PriceEntity as p where p.price = :price")
    List<PriceEntity> findPriceEntitiesByPrice(@Param("price") String price);

    //根据id查询价格
    @Query("from PriceEntity as p where p.id = :id")
    PriceEntity findPriceEntitiesById(@Param("id") int id);

    //根据使用时长查询价格
    @Query("from PriceEntity as p where p.useTime = :userTime")
    List<PriceEntity> findPriceEntitiesByUseTime(@Param("userTime") int useTime);

    @Query("from PriceEntity as p where p.status = :status")
    List<PriceEntity> findPriceEntitiesByStatus(@Param("status") int status);

    @Query("from PriceEntity as p where p.startDateTime > :startDateTime and p.endDateTime < :endDateTime")
    List<PriceEntity> findPriceByStartAndEndDate(@Param("startDateTime") Timestamp startTime, @Param("endDateTime") Timestamp endTime);

    //根据机器ID查询价格
    @Query(value = "select price,use_time FROM mc_price as p join mc_price_device on p.id = price_id  join mc_device as d on d.id=device_id where d.id = :dId",nativeQuery = true)
    List<Object[]> findPriceEntitiesByDeviceID(@Param("dId") int deviceId);

    //根据设备id查询价格和时间
    @Query(value = "select p.* from mc_price p,mc_device d,mc_price_device pd where p.id=pd.price_id and d.id=pd.device_id and d.id=:deviceId",nativeQuery = true)
    List<PriceEntity> queryPriceAndTime(@Param("deviceId") int deviceId);
}
