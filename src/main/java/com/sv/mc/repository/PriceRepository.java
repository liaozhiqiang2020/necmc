package com.sv.mc.repository;

import com.sv.mc.pojo.PriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
/**
 * 价格表
 */
public interface PriceRepository extends BaseRepository<PriceEntity, Long>, PagingAndSortingRepository<PriceEntity, Long> {

    /**
     * 根据钱数查询价格
     * @param price 价格
     * @return 价格数据
     */
    @Query("from PriceEntity as p where p.price = :price")
    List<PriceEntity> findPriceEntitiesByPrice(@Param("price") String price);

    /**
     * 根据Id 查询价格
     * @param id 主键Id
     * @return 价格表
     */
    @Query("from PriceEntity as p where p.id = :id")
    PriceEntity findPriceEntitiesById(@Param("id") int id);


    /**
     *  根据机器Id查询已绑定价格
     * @param deviceId 设备Id
     * @return 绑定的价格
     */
    @Query("select d.priceEntities from DeviceEntity d  where d.id = :did")
    List<PriceEntity> findDevicePrice(@Param("did") int deviceId);

    /**
     * 查询机器绑定的价格（排序去重）
     * @param deviceId 设备Id
     * @return 排序去重后的价格
     */
    @Query(value="select DISTINCT p.use_time,p.Id,p.price_name,p.price,p.create_date_time,p.user_id,p.status,p.start_date_time,p.end_date_time,p.mc_type,p.description from mc_price p,mc_price_device d where p.Id=d.price_id and d.device_id=:did and p.status=1 order by p.price,p.use_time",nativeQuery = true)
    List<PriceEntity> findDevicePriceSort(@Param("did") int deviceId);

    /**
     * 状态为可用的所有价格
     * @return 价格集合
     */
    @Query("from PriceEntity as p where p.status = 1 ")
    List<PriceEntity> findPriceEntitiesByStatus();



    /**
     * 状态为可用的所有价格
     * @return 状态为可用的所有价格
     */
    @Query("from PriceEntity as p where p.status = 1 and (p.endDateTime > now() or p.endDateTime = null)")
    List<PriceEntity> findPriceEntitiesByEnd();



    /**
     * 根据价格里的机器类型查询出所有该设备可绑定价格
     * @param deviceId 设备ID
     * @param date 结束时间
     * @return 可绑定的价格
     */
    @Query("select p from PriceEntity as p,DeviceEntity as d where p.deviceModelEntity = d.deviceModelEntity and p.status = 1 and d.id = :deviceId and (p.endDateTime > :date  or p.endDateTime = null)")
    List<PriceEntity> findPriceEntitiesByMcType(@Param("deviceId") int deviceId, @Param("date") Timestamp date);


    /**
     *  根据机器ID查询价格
     * @param deviceId
     * @return  价格集合
     */
    @Query(value = "select price,use_time FROM mc_price as p join mc_price_device on p.id = price_id  join mc_device as d on d.id=device_id where d.id = :dId", nativeQuery = true)
    List<Object[]> findPriceEntitiesByDeviceID(@Param("dId") int deviceId);



    /**
     *   根据设备Id查询价格和时间
     * @param deviceId
     * @return 价格集合
     */
    @Query(value = "select p.* from mc_price p,mc_device d,mc_price_device pd where p.id=pd.price_id and d.id=pd.device_id and p.status=1 and d.id=:deviceId ORDER BY p.price DESC", nativeQuery = true)
    List<PriceEntity> queryPriceAndTime(@Param("deviceId") int deviceId);


    /**
     * 分页查询所有可用价格
     *
     * @param page 起始个数
     * @param pageSize 截至个数
     * @return 价格集合
     */
    @Query(value = "select * from mc_price as p where p.status = 1 and p.end_date_time > now() or p.end_date_time is null LIMIT :offset,:pageSize", nativeQuery = true)
    List<PriceEntity> findAllPriceByPage(@Param("offset") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * 查询可绑定的数量
     * @return 查询数量
     */
    @Query(value = "select count(*) from mc_price as p where p.status= 1 and p.end_date_time > now() or p.end_date_time is null", nativeQuery = true)
    int findPriceTotal();


    /**
     * 查询所有可用价格
     * 可用的价格数据
     * @return 价格集合
     */
    @Query(value = "select * from mc_price as p where p.status = 1 ", nativeQuery = true)
    List<PriceEntity> findAllPrice();



    /**
     *  查询价格是否存在
     * @param userTime 使用时间
     * @param price 价格
     * @param mc 设备编号
     * @param name 价格名称
     * @return
     */
    @Query(value = "select p.* from mc_price as p , mc_device AS d where p.use_time=:userTime and p.price=:price and p.mc_type = d.mc_type  and p.status= 1 and d.mc_sn=:mc and p.price_name=:name",
            nativeQuery = true)
    PriceEntity findAllFlag(@Param("userTime") int userTime, @Param("price") BigDecimal price, @Param("mc") String mc, @Param("name") String name);


}
