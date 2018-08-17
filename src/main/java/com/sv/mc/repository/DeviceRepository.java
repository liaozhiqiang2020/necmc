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

    @Query(value="select d.* from mc_device d,mc_place p where d.place_id=p.Id and p.level_flag=2 and p.superior_id=:branchId and d.Id=:id",nativeQuery = true)
    List<DeviceEntity> findDevicesById2(@Param("id") int id,@Param("branchId") int branchId);

    @Query(value="select d.* from mc_device d,mc_place p where d.place_id=p.Id and p.level_flag=2 and p.superior_id=:vendorId and d.Id=:id",nativeQuery = true)
    List<DeviceEntity> findDevicesById3(@Param("id") int id,@Param("vendorId") int vendorId);

    @Query(value="select d.* from mc_device d,mc_place p where d.place_id=p.Id and p.Id=:placeId and d.Id=:id",nativeQuery = true)
    List<DeviceEntity> findDevicesById4(@Param("id") int id,@Param("placeId") int placeId);

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
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1 and p.user_id=:userId",nativeQuery = true)
    List<DeviceEntity> findAllDevice(@Param("userId") Integer userId);

    /**
     * 查询所有设备
     * @return
     */
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1 and p.level_flag=2 and p.superior_id=:superId",nativeQuery = true)
    List<DeviceEntity> findAllDevice3(@Param("superId") Integer superId);

    /**
     * 查询所有设备
     * @return
     */
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1 and p.level_flag=3 and p.superior_id=:superId",nativeQuery = true)
    List<DeviceEntity> findAllDevice4(@Param("superId") Integer superId);

    /**
     * 查询所有设备
     * @return
     */
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1 and place_id=:placeId",nativeQuery = true)
    List<DeviceEntity> findAllDevice5(@Param("placeId") Integer placeId);



    /**
     * 查询所有设备
     * @return
     */
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1",nativeQuery = true)
    List<DeviceEntity> findAllDevice2();

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






    @Query(value="select d.* from mc_place p,mc_device d where p.id=d.place_id and p.id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:id)))",nativeQuery = true)
    List<DeviceEntity> findDevicesByPlaceId(@Param("id") int id);

    @Query(value="select d.* from mc_place p,mc_device d where p.id=d.place_id and d.mc_type = :type1 and p.id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:id)))",nativeQuery = true)
    List<DeviceEntity> findDeviceByPlace(@Param("id") int id , @Param("type1") int type);
    /**
     * 查询设备是否存在
     * @param sn
     * @return
     */
    @Query(value="from DeviceEntity as d where d.mcSn=:sn")
    DeviceEntity getDeviceBySN(@Param("sn") String sn);


    /**
     * 根据权限查询设备
     */
@Query(value = "SELECT d.* from mc_device AS d,mc_place as p ,mc_user as u where d.place_id=p.id and u.grade_id=p.level_flag and d.place_id=p.Id and u.grade_id=:pid and d.discard_status=1",nativeQuery = true)
    List<DeviceEntity> getDeviceByPid(@Param("pid")int id);

    /**
     * 根据场地查询设备
     */
    @Query(value = "select d.* from mc_device as d where d.p_id=:pid and d.discard_status=1",nativeQuery = true)
    List<DeviceEntity> getDeviceByPlaceId(@Param("pid")int id );

    /**
     *
     */
    @Query(value = "SELECT d.* from mc_device as d where d.discard_status=1",nativeQuery = true)
  List<DeviceEntity> getAllDevice();


    /**
     * 根据sn查询设备
     */
    @Query(value = "from DeviceEntity as d where d.mcSn=:sn")
    DeviceEntity getDeviceBySn(@Param("sn") String sn);



}
