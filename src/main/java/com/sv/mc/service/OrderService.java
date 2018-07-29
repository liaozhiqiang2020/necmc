package com.sv.mc.service;

import com.sv.mc.pojo.OrderEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 已支付订单服务
 * @author: lzq
 * @date: 2018年7月6日
 */
public interface OrderService<T> extends BaseService<T>{
    /**
     * 创建已支付订单
     */
    void createPaidOrder(OrderEntity orderEntity);

    /**
     * 根据用户编号查询当前用户已支付订单
     * @param openId
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    List<OrderEntity> findListByWxUserOpenId(String openId, int state);

    /**
     * 根据用户编号查询当前用户已支付订单（分页）
     * @param openId
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
     List<OrderEntity> findListByWxUserOpenIdByPage(String openId, int state,int offset,int pageSize);

    /**
     * 根据订单号查询订单信息
     * @author: lzq
     * @date: 2018年7月6日
     */
    OrderEntity findPaidOrderByOrderId(int paidOrderId);

    /**
     * 根据订单code修改订单状态
     * @author: lzq
     * @date: 2018年7月6日
     */
    void updateOrderByCode(String paidOrderCode,int state);

    /**
     * 根据订单id修改订单状态
     * @author: lzq
     * @date: 2018年7月6日
     */
    void updateOrderById(int orderId,int state,String description);

    /**
     * 修改订单信息
     * @author: lzq
     * @date: 2018年7月6日
     */
    void updatePaidOrder(OrderEntity orderEntity);

    /**
     * 根据订单code查询订单信息
     * @author: lzq
     * @date: 2018年7月6日
     */
    int findPaidOrderIdByOrderCode(String paidOrderCode);

    /**
     * 获取按摩剩余时间
     * @param orderId
     * @return
     */
     long getMcRemainingTime(int orderId);

    /**
     * 修改订单按摩开始时间，付款时间和结束时间
     * @param orderId
     */
     void updateOrderDetail(int orderId,int state,int mcTime);

    /**
     * 创建订单
     * @return
     */
    int createPaidOrder(String openid, int mcTime, String deviceCode, String promoCode, BigDecimal money, String unPaidOrderCode, int state,int strength);

    /**
     * 根据openId定时查询订单状态
     */
    void findOrderStateByTime(ScheduledExecutorService service, Timestamp afterTs, OrderEntity orderEntity,String chairCode);

    /**
     * 根据订单号查询订单
     * @author: lzq
     * @date: 2018年7月6日
     */
    String findPaidOrderById(int orderId);

    /**
     * 查询订单
     * @author: lzq
     * @date: 2018年7月6日
     */
    String findPaidOrderList(String openCode, int state);

    /**
     * 分页查询订单
     * @author: lzq
     * @date: 2018年7月6日
     */
    String findPaidOrderListByPage(String openCode, int state,int pageNumber,int pageSize);

    /**
     * 查看服务中列表中订单状态，如果时间结束状态为1，改为2
     * @author: lzq
     * @date: 2018年7月6日
     */
    void servingOrderState(int orderId);

    /**
     * 根据订单号获取按摩椅code
     * @param orderId
     * @return
     */
    Map<String,Object> getMcCodeForMap(int orderId);

    /**
     * 根据订单号获取按摩椅code
     * @param orderId
     * @return
     */
    String getMcCode(int orderId);

    /**
     * 分页查询所有订单(后台查询)
     * @return
     */
    String findAllOrdersByPage(int page, int pageSize);

    /**
     * 后台添加订单描述
     * @param
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    void addOrderDescription(int orderId, String description);
}
