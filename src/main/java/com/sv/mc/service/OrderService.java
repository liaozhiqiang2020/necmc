package com.sv.mc.service;

import com.sv.mc.pojo.OrderEntity;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
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
     * @param orderEntity 订单信息
     */
    void createPaidOrder(OrderEntity orderEntity);

    /**
     * 根据用户编号查询当前用户已支付订单
     * @param openId 用户唯一id
     * @param state 订单状态
     * @return 订单集合
     * @author: lzq
     * @date: 2018年7月6日
     */
    List<OrderEntity> findListByWxUserOpenId(String openId, int state);

    /**
     * 根据用户编号查询当前用户已支付订单（分页）
     * @param openId 用户唯一id
     * @param state 订单状态
     * @param offset  起始个数
     * @param pageSize 截至个数
     * @return 订单集合
     * @author: lzq
     * @date: 2018年7月6日
     */
     List<OrderEntity> findListByWxUserOpenIdByPage(String openId, int state, int offset, int pageSize);

    /**
     * 根据订单号查询订单信息
     * @param paidOrderId 订单号
     * @author: lzq
     * @date: 2018年7月6日
     */
    OrderEntity findPaidOrderByOrderId(int paidOrderId);

    /**
     * 根据订单code修改微信订单号
     * @param codeWx 微信订单号
     * @param orderId  订单Id
     * @author: lzq
     * @date: 2018年7月6日
     */
    void updateOrderByCode(String orderId, String codeWx);

    /**
     * 根据订单code修改订单状态
     * @param paidOrderCode 订单编号
     * @param state 订单状态
     * @author: lzq
     * @date: 2018年7月6日
     */
    void updateOrderByCodeState(String paidOrderCode, int state);

    /**
     * 根据订单id修改订单状态
     * @param state  订单状态
     * @param description 订单描述
     * @param orderId 订单Id
     * @author: lzq
     * @date: 2018年7月6日
     */
    void updateOrderById(int orderId, int state, String description) throws Exception;

    /**
     * 修改订单信息
     * @param orderEntity 修改的订单信息
     * @author: lzq
     * @date: 2018年7月6日
     */
    void updatePaidOrder(OrderEntity orderEntity);

    /**
     * 根据订单code查询订单信息
     * @param paidOrderCode 订单code
     * @author: lzq
     * @date: 2018年7月6日
     */
    int findPaidOrderIdByOrderCode(String paidOrderCode);

    /**
     * 根据订单code查询订单信息
     * @param paidOrderCode 订单code
     * @author: lzq
     * @date: 2018年7月6日
     */
    OrderEntity findOrderEntityByOrderCode(String paidOrderCode);

    /**
     * 获取按摩剩余时间
     * @param orderId 订单Id
     * @return 剩余按摩时间
     */
     long getMcRemainingTime(int orderId);

    /**
     * 修改订单按摩开始时间，付款时间和结束时间
     * @param orderId 订单Id
     * @param state 状态
     * @param  mcTime  时间
     */
     void updateOrderDetail(int orderId, int state, int mcTime);


    /**
     * 创建订单
     * @param openid 用户唯一Id
     * @param mcTime  按摩时长
     * @param deviceCode 设备编号
     * @param promoCode 优惠码
     * @param money 钱
     * @param unPaidOrderCode 未付款订单号
     * @param state 订单状态
     * @param strength 按摩力度
     * @return 创建的订单
     */
    int createPaidOrder(String openid, int mcTime, String deviceCode, String promoCode, BigDecimal money, String unPaidOrderCode, int state, int strength);


    int createPaidOrderAlipay(String openid, int mcTime, String deviceCode, String promoCode, BigDecimal money, String unPaidOrderCode, int state, int strength);


    /**
     * 根据openId定时查询订单状态
     * @param service 定时器服务
     * @param afterTs 按摩结束时间
     * @param orderEntity  订单信息
     * @param chairCode 按摩椅编号
     */
    void findOrderStateByTime(ScheduledExecutorService service, Timestamp afterTs, OrderEntity orderEntity, String chairCode);

    /**
     * 根据订单号查询订单
     * @author: lzq
     * @date: 2018年7月6日
     * @param orderId  订单信息
     */
    String findPaidOrderById(int orderId);

    /**
     * 查询订单
     * @author: lzq
     * @date: 2018年7月6日
     * @param state 订单状态
     * @param openCode  用户唯一Id
     */
    String findPaidOrderList(String openCode, int state);


    /**
     * 分页查询订单
     * @param openCode 用户唯一Id
     * @param state 订单状态
     * @param pageNumber 起始个数
     * @param pageSize 截至个数
     * @return 订单信息
     */
    String findPaidOrderListByPage(String openCode, int state, int pageNumber, int pageSize);

    /**
     * 查看服务中列表中订单状态，如果时间结束状态为1，改为2
     * @author: lzq
     * @date: 2018年7月6日
     * @param orderId 订单Id
     */
    void servingOrderState(int orderId);

    /**
     * 根据订单号获取按摩椅code
     * @param orderId 订单号
     * @return  按摩椅code
     */
    Map<String,Object> getMcCodeForMap(int orderId);

    /**
     * 根据订单号获取按摩椅code
     * @param orderId 订单号
     * @return 按摩椅code
     */
    String getMcCode(int orderId);

    /**
     * 分页查询所有订单(后台查询)
     * @param page 起始个数
     * @param pageSize 截至个数
     * @param session 用户信息
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return 订单信息
     */
    String findAllOrdersByPage(int page, int pageSize, HttpSession session, String startTime, String endTime);

    /**
     * 后台添加订单描述
     * @param orderId 订单Id
     * @param description 订单描述
     * @author: lzq
     * @date: 2018年7月6日
     */
    void addOrderDescription(int orderId, String description);

    /**
     * 根据时间段查询订单数量，例如：[2018/7/1 00:00:00, 2018/8/1 00:00:00)
     * @return 订单数量
     */
    int findYesterDayOrderCount();

    /**
     * 根据时间段查询订单数量，例如：[2018/7/1 00:00:00, 2018/8/1 00:00:00)
     * @param pageSize 截至个数
     * @param session 用户信息
     * @param page  起始个数
     * @return 订单信息
     */
    String findYesterDayOrderInfo(int page, int pageSize, HttpSession session);



    /**
     * 查询所有订单信息
     * @param startTime  起始时间
     * @param endTime  截止时间
     * @return 订单信息
     */

    List<OrderEntity> findAllExcelOrder(String startTime, String endTime);
}
