package com.sv.mc.repository;

import com.sv.mc.pojo.DeviceEntity;
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

    //根据机器id查询机器已绑定价格
    @Query("select d.priceEntities from DeviceEntity d  where d.id = :did")
    List<PriceEntity> findDevicePrice(@Param("did") int deviceId);


    //状态为可用的所有价格
    @Query("from PriceEntity as p where p.status = 1")
    List<PriceEntity> findPriceEntitiesByStatus();

    //根据价格里的机器类型查询出所有该设备可绑定价格
    @Query("select p from PriceEntity as p,DeviceEntity as d where p.deviceModelEntity = d.deviceModelEntity and p.status = 1 and d.id = :deviceId")
    List<PriceEntity> findPriceEntitiesByMcType(@Param("deviceId") int deviceId);


    //根据机器ID查询价格
    @Query(value = "select price,use_time FROM mc_price as p join mc_price_device on p.id = price_id  join mc_device as d on d.id=device_id where d.id = :dId",nativeQuery = true)
    List<Object[]> findPriceEntitiesByDeviceID(@Param("dId") int deviceId);

    //根据设备id查询价格和时间
    @Query(value = "select p.* from mc_price p,mc_device d,mc_price_device pd where p.id=pd.price_id and d.id=pd.device_id and p.status=1 and d.id=:deviceId ORDER BY p.price DESC",nativeQuery = true)
    List<PriceEntity> queryPriceAndTime(@Param("deviceId") int deviceId);


    /**
     * 分页查询所有可用价格
     * @param page
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_price as p where p.status = 1 LIMIT :offset,:pageSize",nativeQuery=true)
    List<PriceEntity> findAllPriceByPage(@Param("offset") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * 查询数量
     * @return
     */
    @Query(value="select count(*) from mc_price as p where p.status= 1",nativeQuery = true)
    int findPriceTotal();



    /**
     * 查询所有可用价格

     * @return
     */
    @Query(value="select * from mc_price as p where p.status = 1 ",nativeQuery=true)
    List<PriceEntity> findAllPrice();










}
