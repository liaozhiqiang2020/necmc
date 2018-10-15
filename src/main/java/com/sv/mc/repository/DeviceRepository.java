package com.sv.mc.repository;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.DeviceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * 设备dao
 */
@Repository
public interface DeviceRepository extends BaseRepository<DeviceEntity, Long>, PagingAndSortingRepository<DeviceEntity, Long> {
    /**
     * 根据设备Id 查询设备
     * @param id 设备Id
     * @return 设备结果对象
     */
    @Query("from DeviceEntity as d   where d.id = :id")
    DeviceEntity findDeviceById(@Param("id") int id);

    /**
     * 查询所有未删除设备
     * @return 设备结果集
     */
    @Query(value = "select * from mc_device where mc_status<>0", nativeQuery = true)
    List<DeviceEntity> findDeviceEntities();

    /**
     * 根据设备id查询
     * @param id
     * @return 设备对象
     */
    @Query("from DeviceEntity as d where d.id = :id")
    List<DeviceEntity> findDevicesById(@Param("id") int id);

    /**
     * 根据设备id 查询 设备数
     * @param id 设备Id
     * @return 设备数量
     */
    @Query("select count(*) from DeviceEntity as d where d.id = :id")
    int findDevicesByIdCount(@Param("id") int id);

    /**
     *  根据设备Id 分公司id 查询设备信息
     * @param id 设备Id
     * @param branchId 分公司id
     * @return 设备集合对象
     */
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id=p.Id and p.level_flag=2 and p.superior_id=:branchId and d.Id=:id", nativeQuery = true)
    List<DeviceEntity> findDevicesById2(@Param("id") int id, @Param("branchId") int branchId);

    /**
     * 根据设备Id 分公司id 查询设备数
     * @param id 设备Id
     * @param branchId 分公司Id
     * @return 设备个数
     */
    @Query(value = "select count(*) from mc_device d,mc_place p where d.place_id=p.Id and p.level_flag=2 and p.superior_id=:branchId and d.Id=:id", nativeQuery = true)
    int findDevicesById2Count(@Param("id") int id, @Param("branchId") int branchId);

    /**
     * 根据代理商Id 查询 代理商设备
     * @param id 设备id
     * @param vendorId 上级Id
     * @return 代理商设备信息
     */
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id=p.Id and p.level_flag=3 and p.superior_id=:vendorId and d.Id=:id", nativeQuery = true)
    List<DeviceEntity> findDevicesById3(@Param("id") int id, @Param("vendorId") int vendorId);

    /**
     * 根据代理商Id 查询 代理商设备数
     * @param id 设备Id
     * @param vendorId 上级Id
     * @return 代理商设备数
     */
    @Query(value = "select count(* )from mc_device d,mc_place p where d.place_id=p.Id and p.level_flag=3 and p.superior_id=:vendorId and d.Id=:id", nativeQuery = true)
    int findDevicesById3Count(@Param("id") int id, @Param("vendorId") int vendorId);

    /**
     *  根据设备Id 场地Id 查询设备
     * @param id 设备Id
     * @param placeId 场地Id
     * @return 设备信息集合
     */
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id=p.Id and p.Id=:placeId and d.Id=:id", nativeQuery = true)
    List<DeviceEntity> findDevicesById4(@Param("id") int id, @Param("placeId") int placeId);

    /**
     *   根据设备Id 场地Id 查询设备数
     * @param id 设备Id
     * @param placeId 场地Id
     * @return 设备数量
     */
    @Query(value = "select count(* ) from mc_device d,mc_place p where d.place_id=p.Id and p.Id=:placeId and d.Id=:id", nativeQuery = true)
    int findDevicesById4Count(@Param("id") int id, @Param("placeId") int placeId);

    /**
     *  根据设备编号 查询设备Id
     * @param deviceCode 设备标号
     * @return 设备Id
     */
    @Query("select d.id from DeviceEntity d where d.mcSn=:deviceCode")
    Integer queryDeviceIdByDeviceCode(@Param("deviceCode") String deviceCode);

    /**
     * 根据模块Id 查询 设备Id
     * @param loraId 模块id
     * @return 设备Id
     */
    @Query("select d.id from DeviceEntity d where d.loraId=:loraId")
    int queryDeviceIdByLoraId(@Param("loraId") String loraId);


    /**
     * 查询按摩椅正常数量
     *
     * @return 正常按摩椅数量
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("select count(id) from DeviceEntity d where d.mcStatus=0 or d.mcStatus=1")
    int findNormalDeviceTotalCount();

    /**
     * 查询按摩椅故障数量
     *
     * @return 故障按摩椅数量
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("select count(id) from DeviceEntity d where d.mcStatus=2")
    int findFaultDeviceTotalCount();

    /**
     * 查询异常信息数量
     *
     * @return 异常信息数量
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("select count(id) from DeviceStatusEntity where status=1")
    int findFaultInfoCount();


    /**
     * 根据起始截至时间查询设备投放
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return 设备投放数量
     */
    @Query(value = "select count(id) from mc_device_date d where d.start_date BETWEEN cast(:startTime as datetime ) and cast(:endTime as datetime)", nativeQuery = true)
    int findLaunchChairCount(@Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 分页查询设备信息
     *
     * @param offset 起始个数
     * @param pageSize 截至个数
     * @return  设备集合
     */
    @Query(value = "select * from mc_device as b where b.discard_status=1 LIMIT :offset,:pageSize", nativeQuery = true)
    List<DeviceEntity> findAllDeviceByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);


    /**
     * 根据上级id 查询分公司设备信息
     * @param superId 上级Id
     * @return 设备集合
     */
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1 and p.level_flag=2 and p.superior_id=:superId", nativeQuery = true)
    List<DeviceEntity> findAllDevice3(@Param("superId") Integer superId);

    /**
     * 根据上级Id 查询分公司设备数量
     * @param superId 上级Id
     * @return 设备数量
     */
    @Query(value = "select count(d.Id) from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1 and p.level_flag=2 and p.superior_id=:superId", nativeQuery = true)
    int findAllDeviceTotal3(@Param("superId") Integer superId);

    /**
     * 根据上级id查询代理商设备
     * @param superId 上级Id
     * @return 设备集合
     */
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1 and p.level_flag=3 and p.superior_id=:superId", nativeQuery = true)
    List<DeviceEntity> findAllDevice4(@Param("superId") Integer superId);

    /**
     * 根据上级ID查询代理商设备数
     * @param superId 上级Id
     * @return 设备数量
     */
    @Query(value = "select count(d.Id) from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1 and p.level_flag=3 and p.superior_id=:superId", nativeQuery = true)
    int findAllDeviceTotal4(@Param("superId") Integer superId);

    /**
     * 根据场地id查询设备
     * @param placeId 场地ID
     * @return 设备信息集合
     */
    @Query(value = "select d.* from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1 and place_id=:placeId", nativeQuery = true)
    List<DeviceEntity> findAllDevice5(@Param("placeId") Integer placeId);

    /**
     * 根据场地id查询设备数量
     * @param placeId 场地ID
     * @return 设备数量
     */
    @Query(value = "select count(d.Id) from mc_device d,mc_place p where d.place_id = p.Id and d.discard_status=1 and place_id=:placeId", nativeQuery = true)
    int findAllDeviceTotal5(@Param("placeId") Integer placeId);


    /**
     * 查询所有设备
     * @return 设备集合
     */
    @Query(value = "select d.* from mc_device d where d.discard_status=1", nativeQuery = true)
    List<DeviceEntity> findAllDevice2();

    /**
     * 查所所有设备数量
     * @return 设备数量
     */
    @Query(value = "select count(*) from mc_device as b where b.discard_status=1", nativeQuery = true)
    int findDeviceTotal();

    /**
     * 根据场地ID 查询 设备SN
     *
     * @param id 场地ID
     * @return 设备信息
     */

    @Query(value = "select d.mc_sn from mc_place p,mc_device d where p.id=d.place_id and p.id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:id)))", nativeQuery = true)
    List<String> findAllByPlaceId(@Param("id") int id);

    /**
     * 查询场地下包含的所有设备
     * @param id 场地id
     * @return 设备集合
     */
    @Query(value = "select d.* from mc_place p,mc_device d where p.id=d.place_id and p.id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:id)))", nativeQuery = true)
    List<DeviceEntity> findDevicesByPlaceId(@Param("id") int id);

    /**
     *  根据场地Id 查询指定类型的设备
     * @param id 场地iD
     * @param type 类型
     * @return 设备集合
     */
    @Query(value = "select d.* from mc_place p,mc_device d where p.id=d.place_id and d.mc_type = :type1 and p.id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:id)))", nativeQuery = true)
    List<DeviceEntity> findDeviceByPlace(@Param("id") int id, @Param("type1") int type);

    /**
     * 查询设备是否存在
     * @param sn  设备sn
     * @return 设备信息
     */
    @Query("from DeviceEntity as d where d.mcSn=:sn")
    DeviceEntity getDeviceBySN(@Param("sn") String sn);


    /**
     * 根据权限等级查询设备信息
     * @param id 权限等级Id
     * @return  设备集合
     */
    @Query(value = "SELECT d.* from mc_device AS d,mc_place as p ,mc_user as u where d.place_id=p.id and u.grade_id=p.level_flag and d.place_id=p.Id and u.grade_id=:pid and d.discard_status=1", nativeQuery = true)
    List<DeviceEntity> getDeviceByPid(@Param("pid") int id);

    /**
     * 根据场地id查询设备
     * @param id 场地Id
     * @return 设备集合
     */
    @Query(value = "select d.* from mc_device as d where d.p_id=:pid and d.discard_status=1", nativeQuery = true)
    List<DeviceEntity> getDeviceByPlaceId(@Param("pid") int id);

    /**
     * 查询所有设备
     * @return 所有的设备数据
     */
    @Query(value = "SELECT d.* from mc_device as d where d.discard_status=1", nativeQuery = true)
    List<DeviceEntity> getAllDevice();


    /**
     * 根据sn查询设备
     * @param sn 设备编号Sn
     * @return 设备信息
     */
    @Query(value = "select * from mc_device where mc_sn=:sn",nativeQuery = true)
    DeviceEntity getDeviceBySn2(@Param("sn") String sn);

    /**
     * 1级权限返回所有的正常设备数
     *
     * @return 正常设备数量
     */
    @Query(value = "select count(mc_device.id) from mc_device where  last_correspond_time > DATE_SUB(NOW(), INTERVAL 60 MINUTE); ", nativeQuery = true)
    int getDeviceCount();

    /**
     * 1级权限返回所有的异常设备数
     *
     * @return 异常设备数量
     */
    @Query(value = "select count(mc_device.id) from mc_device where last_correspond_time < DATE_SUB(NOW(), INTERVAL 60 MINUTE); ", nativeQuery = true)
    int getDeviceErrorCount();

    /**
     *  3级权限查询正常设备数量
     * @param pid 上级Id
     * @return  正常设备数量
     */
    @Query(value = "select count(mc_device.id) from mc_device where last_correspond_time > DATE_SUB(NOW(), INTERVAL 60 MINUTE) and mc_device.place_id in " +
            "( select mc_place.id from mc_place where discard_status=1 and level_flag=3 and superior_id=:pid ) ", nativeQuery = true)
    int getDevCotBySuperiorId(@Param("pid") int pid);



    /**
     * 3级权限返回异常设备数
     * @param pid 上级Id
     * @return 异常设备数量
     */
    @Query(value = "select count(mc_device.id) from mc_device where last_correspond_time < DATE_SUB(NOW(), INTERVAL 60 MINUTE) and mc_device.place_id in " +
            "( select mc_place.id from mc_place where discard_status=1 and level_flag=3 and superior_id=:pid ) ", nativeQuery = true)
    int getDevErrorCotBySuperiorId(@Param("pid") int pid);

    /**
     * 2级权限返回正常设备数
     * @param pid 上级Id
     * @return 正常设备数量
     */
    @Query(value = "select count(mc_device.id) from mc_device where last_correspond_time > DATE_SUB(NOW(), INTERVAL 60 MINUTE) and mc_device.place_id in " +
            "( select mc_place.id from mc_place where discard_status=1 and level_flag=2 and superior_id=:pid ) ", nativeQuery = true)
    int getDevCotBySuperiorId1(@Param("pid") int pid);

    /**
     *  2级权限返回异常设备数
     * @param pid 上级Id
     * @return 异常设备数量
     */
    @Query(value = "select count(mc_device.id) from mc_device where last_correspond_time < DATE_SUB(NOW(), INTERVAL 60 MINUTE) and mc_device.place_id in " +
            "( select mc_place.id from mc_place where discard_status=1 and level_flag=2 and superior_id=:pid) ", nativeQuery = true)
    int getDevErrorCotBySuperiorId1(@Param("pid") int pid);


    /**
     * 场地管理员正常设备数
     * @param pid 场地id
     * @return 设备数量
     */
    @Query(value = "" +
            "select count(mc_device.id) from mc_device where last_correspond_time > DATE_SUB(NOW(), INTERVAL 60 MINUTE) and mc_device.place_id " +
            "in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:pid))) "
            , nativeQuery = true)
    int getcDeviceByPid(@Param("pid") int pid);



    /**
     *  场地管理员异常设备数
     * @param pid 场地id
     * @return 场地管理员异常设备数
     */
    @Query(value =  "select count(mc_device.id) from mc_device where last_correspond_time < DATE_SUB(NOW(), INTERVAL 60 MINUTE) and mc_device.place_id " +
            "in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:pid))) "
            , nativeQuery = true)
    int geteDeviceByPid(@Param("pid") int pid);


    /**
     * 根据网关Sn查询网关下 所有的设备lora_id
     * @param sn 网关Sn
     * @return lora_id
     */
    @Query(value = "select d.lora_id from mc_gateway g,mc_device d where g.Id = d.gateway_id and g.gateway_sn=:sn order by lora_id", nativeQuery = true)
    List<String> findAllDeviceByGatewayCode(@Param("sn") String sn);

    /**
     * 修改设备最后通信时间
     * @param loraId  模块id
     * @param status  是否在线
     * @param timeStr  设备最后在线时间
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update mc_device set isnot_online=:status,last_correspond_time=:timeStr where lora_id=:loraId", nativeQuery = true)
    void updateDeviceTimeByLoraId(@Param("loraId") String loraId, @Param("status") int status, @Param("timeStr")String timeStr);

    /**
     * 修改设备在线状态
     * @param loraId 模块Id
     * @param status 是否在线
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update mc_device set isnot_online=:status where lora_id=:loraId", nativeQuery = true)
    void updateDeviceStatusByLoraId(@Param("loraId") String loraId, @Param("status") int status);

    /**
     * 根据模块Id 查询设备
     * @param loraId 模块iD
     * @return 设备对象
     */
    @Query(value = "select * from mc_device where lora_id=:loraId", nativeQuery = true)
    DeviceEntity findDeviceEntityByLoraId(@Param("loraId") String loraId);
}
