package com.sv.mc.repository;

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

}
