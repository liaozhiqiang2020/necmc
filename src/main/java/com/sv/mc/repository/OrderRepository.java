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
 * 已支付订单
 * @author: lzq
 * @date: 2018年7月6日
 */
@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, Long>, PagingAndSortingRepository<OrderEntity, Long> {
    /**
     * 根据订单号查询订单信息
     *
     * @param paidOrderId
     * @return OrderEntity
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("from OrderEntity as u where u.id =:paidOrderId")
    OrderEntity findPaidOrderByOrderId(@Param("paidOrderId") int paidOrderId);

    /**
     * 根据用户编号查询用户已支付订单
     * @param openId
     * @param state
     * @return List<OrderEntity>
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query(value = "select p.* from mc_order p,mc_wx_user_info u where p.wx_user_info_id=u.id and u.open_code=:openId and p.status=:state order by p.create_date_time DESC", nativeQuery = true)
    List<OrderEntity> findListByWxUserId(@Param("openId") String openId, @Param("state") int state);

    /**
     * 根据用户编号查询用户已支付订单(分页查询)
     * @param openId
     * @param state
     * @return List<OrderEntity>
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query(value = "select p.* from mc_order p,mc_wx_user_info u where p.wx_user_info_id=u.id and u.open_code=:openId and p.status=:state order by p.create_date_time DESC LIMIT :offset,:pageSize", nativeQuery = true)
    List<OrderEntity> findListByWxUserIdByPage(@Param("openId") String openId, @Param("state") int state,@Param("offset") int offset,@Param("pageSize") int pageSize);

    /**
     * 根据订单code查询订单id
     * @param paidOrderCode
     * @return OrderEntity
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("from OrderEntity as u where u.code =:paidOrderCode")
    OrderEntity findPaidOrderIdByOrderCode(@Param("paidOrderCode") String paidOrderCode);

    /**
     * 查询所有已支付订单
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("from OrderEntity as u where u.status=1 or u.status=2")
    List<OrderEntity> findAllOrderList();

    /**
     * 查询某个时间段的总收入
     * @param startTime
     * @param endTime
     * @return
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
     * @param orderId
     * @return
     */
    @Query(value = "select d.mc_sn,o.strength from mc_order o,mc_device d where o.device_id = d.id and o.id=:orderId",nativeQuery = true)
    List<Object[]> getMcCodeForList(@Param("orderId")int orderId);

    /**
     * 根据orderId获取按摩椅code
     * @param orderId
     * @return
     */
    @Query(value = "select d.mc_sn from mc_order o,mc_device d where o.device_id = d.id and o.id=:orderId",nativeQuery = true)
    String getMcCode(@Param("orderId")int orderId);



    /**
     * 分页查询订单信息
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_order as b order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 分页查询订单信息
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=2 and p.superior_id=:branchId order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPage2(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("branchId") int branchId);

    /**
     * 分页查询订单信息
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=3 and p.superior_id=:vendorId order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPage3(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("vendorId") int vendorId);

    /**
     * 分页查询订单信息
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.Id=:placeId order by b.create_date_time DESC LIMIT :offset,:pageSize",nativeQuery=true)
    List<OrderEntity> findAllOrdersByPage4(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("placeId") int placeId);

    /**
     * 查询订单数量
     * @return
     */
    @Query(value="select count(*) from mc_order as b",nativeQuery = true)
    int findOrderTotal();

    @Query(value="select count(*) from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=2 and p.superior_id=:branchId",nativeQuery = true)
    int findOrderTotal2(@Param("branchId") int branchId);

    @Query(value="select count(*) from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.level_flag=3 and p.superior_id=:vendorId",nativeQuery = true)
    int findOrderTotal3(@Param("vendorId") int vendorId);

    @Query(value="select count(*) from mc_order b,mc_place p,mc_device d where b.device_Id=d.Id and d.place_id=p.Id and p.Id=:placeId",nativeQuery = true)
    int findOrderTotal4(@Param("placeId") int placeId);

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value="select count(id) from mc_order where pay_date_time BETWEEN cast(:startTime as datetime ) and cast(:endTime as datetime)",nativeQuery = true)
    int findOrderByPeriod(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value="select * from mc_order where pay_date_time BETWEEN cast(:startTime as datetime ) and cast(:endTime as datetime) order by create_date_time DESC LIMIT :offset,:pageSize",nativeQuery = true)
    List<OrderEntity> findOrdersInfo(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
}
