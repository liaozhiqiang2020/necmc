package com.sv.mc.repository;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单
 * @author: lzq
 * @date: 2018年7月6日
 */
@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, Long>, PagingAndSortingRepository<OrderEntity, Long> {
    /**
     * 根据订单号查询订单信息
     *
     * @param paidOrderId
     * @return 指定订单信息
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("from OrderEntity as u where u.id =:paidOrderId")
    OrderEntity findPaidOrderByOrderId(@Param("paidOrderId") int paidOrderId);

    /**
     * 根据用户编号查询用户已支付订单
     * @param openId
     * @param state
     * @return  订单信息集合
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query(value = "select p.* from mc_order p,mc_wx_user_info u where p.wx_user_info_id=u.id and u.open_code=:openId and p.status=:state order by p.create_date_time DESC", nativeQuery = true)
    List<OrderEntity> findListByWxUserId(@Param("openId") String openId, @Param("state") int state);

    /**
     * 根据用户编号查询用户已支付订单(分页查询)
     * @param openId
     * @param state
     * @return 订单信息集合
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query(value = "select p.* from mc_order p,mc_wx_user_info u where p.wx_user_info_id=u.id and u.open_code=:openId and p.status=:state order by p.create_date_time DESC LIMIT :offset,:pageSize", nativeQuery = true)
    List<OrderEntity> findListByWxUserIdByPage(@Param("openId") String openId, @Param("state") int state,@Param("offset") int offset,@Param("pageSize") int pageSize);

    /**
     * 根据订单code查询订单id
     * @param paidOrderCode
     * @return  订单信息
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("from OrderEntity as u where u.code =:paidOrderCode")
    OrderEntity findPaidOrderIdByOrderCode(@Param("paidOrderCode") String paidOrderCode);

    /**
     * 查询所有已支付订单
     * @return 订单信息集合
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("from OrderEntity as u where u.status=1 or u.status=2")
    List<OrderEntity> findAllOrderList();

    /**
     * 查询某个时间段的总收入
     * @param startTime
     * @param endTime
     * @return 收入钱数
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query(value="select sum(money) from mc_order where status=1 or status=2 and  pay_date_time BETWEEN cast(:startTime as datetime ) and cast(:endTime as datetime)",nativeQuery = true)
    BigDecimal findTotalIncomeByTime(@Param("startTime")String startTime,@Param("endTime")String endTime);

//    /**
//     * 根据openId查询服务中订单
//     * @param openId
//     * @return
//     */
//    @Query("from OrderEntity as u where u.code =:openId")
//    List<OrderEntity> getOrderListServing(@Param("openId")String openId);

    /**
     * 根据orderId获取按摩椅code
     * @param orderId 订单id
     * @return 按摩椅code 集合
     */
    @Query(value = "select d.mc_sn,o.strength from mc_order o,mc_device d where o.device_id = d.id and o.id=:orderId",nativeQuery = true)
    List<Object[]> getMcCodeForList(@Param("orderId")int orderId);

    /**
     * 根据orderId获取按摩椅code
     * @param orderId 订单编号
     * @return  按摩椅code
     */
    @Query(value = "select d.mc_sn from mc_order o,mc_device d where o.device_id = d.id and o.id=:orderId",nativeQuery = true)
    String getMcCode(@Param("orderId")int orderId);


    /**
     * 分页查询订单信息(不选场地)
     * @param offset 起始个数
     * @param pageSize  截至个数
     * @param startTime  起始时间
     * @param endTime  截止时间
     * @return 订单信息集合
     */
    @Query(value="select * from mc_order as b where create_date_time BETWEEN :startTime and :endTime order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 分页查询分公司订单信息
     * @param offset  起始个数
     * @param pageSize  截至个数
     * @param branchId  分公司id
     * @param startTime 起始时间
     * @param endTime  截止时间
     * @return 订单信息集合
     */
    @Query(value="select * from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=2 and p.superior_id=:branchId and create_date_time BETWEEN :startTime and :endTime order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPage2(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("branchId") int branchId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 分页查询代理商订单信息
     * @param offset 起始个数
     * @param pageSize  截至个数
     * @param vendorId 代理商Id
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return 订单信息集合
     */
    @Query(value="select * from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=3 and p.superior_id=:vendorId and create_date_time BETWEEN :startTime and :endTime order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPage3(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("vendorId") int vendorId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 分页查询场地订单信息
     * @param offset  起始个数
     * @param pageSize  截至个数
     * @param placeId  场地Id
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return 订单信息集合
     */
    @Query(value="select * from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.Id in (select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:placeId))) and create_date_time BETWEEN :startTime and :endTime order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPage4(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("placeId") int placeId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 查询一定时间订单数
     * @param startTime 起始时间
     * @param endTime  截止时间
     * @return 订单数
     */
    @Query(value="select count(*) from mc_order as b where create_date_time BETWEEN :startTime and :endTime",nativeQuery = true)
    int findOrderTotal(@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 查询指定时间段分公司订单数
     * @param branchId 分公司Id
     * @param startTime 起始时间
     * @param endTime  截止时间
     * @return 订单数
     */
    @Query(value="select count(*) from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=2 and p.superior_id=:branchId and create_date_time BETWEEN :startTime and :endTime",nativeQuery = true)
    int findOrderTotal2(@Param("branchId") int branchId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 查询指定时间段代理商订单数
     * @param vendorId 代理商Id
     * @param startTime 起始时间
     * @param endTime  截止时间
     * @return 订单数
     */
    @Query(value="select count(*) from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=3 and p.superior_id=:vendorId and create_date_time BETWEEN :startTime and :endTime",nativeQuery = true)
    int findOrderTotal3(@Param("vendorId") int vendorId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     *  查询指定时间段场地的订单数
     * @param placeId  场地Id
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return 订单数
     */
    @Query(value="select count(*) from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.Id in (select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:placeId))) and create_date_time BETWEEN :startTime and :endTime",nativeQuery = true)
    int findOrderTotal4(@Param("placeId") int placeId,@Param("startTime") String startTime,@Param("endTime") String endTime);




    /**
     *  分页查询指定时间场地订单信息
     * @param offset  起始个数
     * @param pageSize    截至个数
     * @param startTime 起始时间
     * @param endTime  截止时间
     * @param placeId 场地Id
     * @return 订单集合
     */
    @Query(value="select * from mc_order as b,mc_device as d where b.device_Id=d.Id and d.place_id=:placeId and mc_start_date_time BETWEEN :startTime and :endTime order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPlace(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("placeId") int placeId);

    /**
     * 分页查询指定时间段分公司场地订单信息
     * @param offset 起始个数
     * @param pageSize 截至个数
     * @param branchId 分公司id
     * @param startTime 起始时间
     * @param endTime  截止时间
     * @param placeId 场地Id
     * @return 订单信息
     */
    @Query(value="select * from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=2 and p.superior_id=:branchId and d.place_id=:placeId and mc_start_date_time BETWEEN :startTime and :endTime order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPlace2(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("branchId") int branchId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("placeId") int placeId);

    /**
     * 分页查询指定时间段代理商场地订单信息
     * @param offset  起始个数
     * @param pageSize 截至个数
     * @param vendorId 代理商Id
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @param placeId 场地Id
     * @return 订单信息
     */
    @Query(value="select * from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=3 and p.superior_id=:vendorId and d.place_id=:placeId and mc_start_date_time BETWEEN :startTime and :endTime order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPlace3(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("vendorId") int vendorId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("placeId") int placeId);

    /**
     * 分页查询指定时间段场地的订单信息
     * @param offset 起始个数
     * @param pageSize 截至个数
     * @param placeId 场地Id
     * @param startTime 起始时间
     * @param endTime  截止时间
     * @return 订单信息
     */
    @Query(value="select * from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.Id=:placeId and mc_start_date_time BETWEEN :startTime and :endTime order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPlace4(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("placeId") int placeId,@Param("startTime") String startTime,@Param("endTime") String endTime);


    /**
     * 查询指定时间段场地的订单数量
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @param placeId 场地Id
     * @return 订单数量
     */
    @Query(value="select count(*) from mc_order as b,mc_device as d where b.device_Id=d.Id and d.place_id=:placeId and mc_start_date_time BETWEEN :startTime and :endTime",nativeQuery = true)
    int findOrderTotalByPlace(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("placeId") int placeId);

    /**
     * 查询指定时间段分公司场地的订单数量
     * @param branchId 分公司Id
     * @param startTime  起始时间
     * @param endTime 截止时间
     * @param placeId 场地ID
     * @return 订单数量
     */
    @Query(value="select count(*) from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=2 and p.superior_id=:branchId and d.place_id=:placeId and mc_start_date_time BETWEEN :startTime and :endTime",nativeQuery = true)
    int findOrderTotalByPlace2(@Param("branchId") int branchId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("placeId") int placeId);

    /**
     * 查询指定时间代理商的场地订单数量
     * @param vendorId 代理商Id
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @param placeId 场地Id
     * @return 订单数量
     */
    @Query(value="select count(*) from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=3 and p.superior_id=:vendorId and d.place_id=:placeId and mc_start_date_time BETWEEN :startTime and :endTime",nativeQuery = true)
    int findOrderTotalByPlace3(@Param("vendorId") int vendorId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("placeId") int placeId);

    /**
     * 查询指定时间场地的订单数
     * @param placeId 场地Id
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return 订单数量
     */
    @Query(value="select count(*) from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.Id=:placeId and mc_start_date_time BETWEEN :startTime and :endTime",nativeQuery = true)
    int findOrderTotalByPlace4(@Param("placeId") int placeId,@Param("startTime") String startTime,@Param("endTime") String endTime);


    /**
     * 查询指定时间段 支付的订单数量
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return 订单数
     */
    @Query(value="select count(id) from mc_order where pay_date_time BETWEEN cast(:startTime as datetime ) and cast(:endTime as datetime)",nativeQuery = true)
    int findOrderByPeriod(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 分页查询指定时间段的支付订单信息
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @param offset 起始个数
     * @param pageSize 截至个数
     * @return 订单信息集合
     */
    @Query(value="select * from mc_order where pay_date_time BETWEEN cast(:startTime as datetime ) and cast(:endTime as datetime) order by create_date_time DESC LIMIT :offset,:pageSize",nativeQuery = true)
    List<OrderEntity> findOrdersInfo(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询所有订单信息
     * @return 订单信息
     */
    @Query(value = "select * from mc_order where create_date_time>= :startTime and create_date_time< :endTime ",nativeQuery = true)
    List<OrderEntity>getExcelOrder(@Param("startTime") String startTime,@Param("endTime") String endTime);



    /**
     * 最高权限查询昨天的订单数
     * @return 昨天订单数量
     */
    @Query(value = "select count(mc_order.id) from mc_order where to_days(create_date_time) = to_days(now())",nativeQuery = true)
    int getYeOrder();



    /**
     *  代理商查询昨天的订单数量
     * @param pid 代理商id
     * @return 昨天订单数
     */
    @Query(value = "select count(mc_order.id) from mc_order where to_days(create_date_time) = to_days(now()) and " +
            "mc_order.device_id in " +
            "(select mc_device.id from mc_device where mc_status<>4 and discard_status=1 and mc_device.place_id in " +
            "( select mc_place.id from mc_place where discard_status=1 and level_flag=3 and superior_id=:pid))",nativeQuery = true)
    int getYeOrderThree(@Param("pid")int pid);


    /**
     * 分公司查询 昨天的订单数量
     * @param pid 分公司id
     * @return  分公司订单数量
     */
    @Query(value = "select count(mc_order.id) from mc_order where to_days(create_date_time) = to_days(now()) and " +
            "mc_order.device_id in " +
            "(select mc_device.id from mc_device where mc_status<>4 and discard_status=1 and mc_device.place_id in " +
            "( select mc_place.id from mc_place where discard_status=1 and level_flag=2 and superior_id=:pid))",nativeQuery = true)
    int getYeOrderTwo(@Param("pid")int pid);


    /**
     * 场地管理员查询昨天的订单数
     * @param pid 场地Id
     * @return 订单数量
     */
    @Query(value =  "" +"select count(mc_order.id) from mc_order where to_days(create_date_time) = to_days(now()) and mc_order.device_id in"+
            " (select mc_device.id from mc_device where mc_status<>4 and discard_status=1 and mc_device.place_id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:pid))))",

            nativeQuery = true)
    int getYeOrderFour(@Param("pid")int pid);




}
