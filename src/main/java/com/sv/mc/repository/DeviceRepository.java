package com.sv.mc.repository;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.DeviceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends BaseRepository<DeviceEntity, Long>, PagingAndSortingRepository<DeviceEntity, Long> {
    @Query("from DeviceEntity as d   where d.id = :id")
    DeviceEntity findDeviceById(@Param("id") int id);

    /**
     * 根据设备id查询
     * @param id
     * @return
     */
    @Query("from DeviceEntity as d where d.id = :id")
    List<DeviceEntity> findDevicesById(@Param("id") int id);

    /**
     * 根据设备编号查询设备id
     */
    @Query("select d.id from DeviceEntity d where d.mcSn=:deviceCode")
    int queryDeviceIdByDeviceCode(@Param("deviceCode")String deviceCode);


    /**
     * 查询按摩椅正常数量
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("select count(id) from DeviceEntity d where d.mcStatus=0 or d.mcStatus=1")
    int findNormalDeviceTotalCount();

    /**
     * 查询按摩椅故障数量
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("select count(id) from DeviceEntity d where d.mcStatus=2")
    int findFaultDeviceTotalCount();

    /**
     * 查询异常信息数量
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("select count(id) from DeviceStatusEntity where status=1")
    int findFaultInfoCount();

    /**
     * 一段时间内的设备投放量
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query(value="select count(id) from mc_device_date d where d.start_date BETWEEN cast(:startTime as datetime ) and cast(:endTime as datetime)",nativeQuery = true)
    int findLaunchChairCount(@Param("startTime") String startTime,@Param("endTime")String endTime);


    /**
     * 分页查询设备信息
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_device as b where b.discard_status=1 LIMIT :offset,:pageSize",nativeQuery=true)
    List<DeviceEntity> findAllDeviceByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询所有设备
     * @return
     */
    @Query("from DeviceEntity as b where b.discardStatus=1")
    List<DeviceEntity> findAllDevice();

    /**
     * 查询数量
     * @return
     */
    @Query(value="select count(*) from mc_device as b where b.discard_status=1",nativeQuery = true)
    int findDeviceTotal();

    /**
     * 根据场地ID 查询 设备SN
     * @param id 场地ID
     * @return  设备信息
     */

    @Query(value="select d.mc_sn from mc_place p,mc_device d where p.id=d.place_id and p.id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:id)))",nativeQuery = true)
    List<String> findAllByPlaceId(@Param("id") int id);
}
