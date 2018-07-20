package com.sv.mc.service.impl;

import com.sv.mc.pojo.OrderEntity;
import com.sv.mc.pojo.WxUserInfoEntity;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.repository.OrderRepository;
import com.sv.mc.repository.WxUserInfoRepository;
import com.sv.mc.service.DeviceService;
import com.sv.mc.service.JMSProducer;
import com.sv.mc.service.OrderService;
import com.sv.mc.util.WxUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 已支付订单服务
 * @author: lzq
 * @date: 2018年7月6日
 */
@Service
public class OrderServiceImpl implements OrderService<OrderEntity> {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WxUserInfoRepository wxUserInfoRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Resource
    private JMSProducer jmsProducer;




    /**
     * 查询所有订单
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public List<OrderEntity> findAllEntities() {
        PageRequest pageRequest = new PageRequest(0,5);
        Page<OrderEntity> wxUserInfoEntityPage = this.orderRepository.findAll(pageRequest);
        return wxUserInfoEntityPage.getContent();
    }

    /**
     * 创建订单
     * @param orderEntity
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void createPaidOrder(OrderEntity orderEntity) {
        this.orderRepository.save(orderEntity);
    }

    /**
     * 查询当前用户订单列表
     * @param openId
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public List<OrderEntity> findListByWxUserOpenId(String openId, int state) {
        List<OrderEntity> orderEntityList = this.orderRepository.findListByWxUserId(openId,state);
        return orderEntityList;
    }

    /**
     * 根据订单号查询订单
     * @param paidOrderId
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public OrderEntity findPaidOrderByOrderId(int paidOrderId) {
        OrderEntity orderEntity = this.orderRepository.findPaidOrderByOrderId(paidOrderId);
        return orderEntity;
    }

    /**
     * 根据订单code查询订单信息
     * @param paidOrderCode
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public int findPaidOrderIdByOrderCode(String paidOrderCode) {
        OrderEntity orderEntity = this.orderRepository.findPaidOrderIdByOrderCode(paidOrderCode);
        return orderEntity.getId();
    }

    /**
     * 修改已支付订单
     * @param
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void updatePaidOrder(OrderEntity orderEntity) {
        this.orderRepository.save(orderEntity);
    }

    /**
     * 根据订单code修改订单状态
     * @param paidOrderCode,state
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void updateOrderByCode(String paidOrderCode,int state) {
        OrderEntity orderEntity = this.orderRepository.findPaidOrderIdByOrderCode(paidOrderCode);//查询订单信息
        orderEntity.setStatus(state);//写入订单状态
        this.orderRepository.save(orderEntity);

    }

    /**
     * 根据订单id修改订单状态
     * @param orderId,state
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void updateOrderById(int orderId, int state) {
        OrderEntity orderEntity = this.orderRepository.findPaidOrderByOrderId(orderId); //查询订单信息
        orderEntity.setStatus(state);//写入订单状态
        this.orderRepository.save(orderEntity);
    }

    /**
     * 获取按摩剩余时间
     * @param orderId
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public long getMcRemainingTime(int orderId) {
        long differTime = 0;
        OrderEntity orderEntity = this.findPaidOrderByOrderId(orderId);//查询订单信息
        Timestamp orderTime = orderEntity.getMcEndDateTime();//按摩结束时间
        WxUtil wxUtil = new WxUtil();
        Timestamp nowTime = wxUtil.getNowDate();//获取当前时间

        if (nowTime.getTime() <= orderTime.getTime()) {//判断当前时间是否小于等于按摩结束时间，如果为true，获得时间差
            differTime = wxUtil.getDifference(nowTime, orderTime, 0);//获取时间差值
        } else {//如果按摩时间结束，修改订单状态为已结束
            differTime = differTime;
        }

        return differTime;
    }

    /**
     * 创建订单
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public int createPaidOrder(String openid, int mcTime, String deviceCode, String promoCode, BigDecimal money, String unPaidOrderCode, int state) {
        OrderEntity orderEntity = new OrderEntity();
        WxUtil wxUtil = new WxUtil();
        String paidOrderCode = wxUtil.createPaidOrderCode(openid, deviceCode); //生成订单号
        int deviceId = this.deviceRepository.queryDeviceIdByDeviceCode(deviceCode);//设备id

        Timestamp ts = wxUtil.getNowDate();//获取当前时间(时间戳)

        WxUserInfoEntity wxUserInfoEntity = this.wxUserInfoRepository.findWxUserInfoEntityByOpenCode(openid); //根据openId查询wxUser表
        if (wxUserInfoEntity != null) {
            orderEntity.setWxUserInfoId(wxUserInfoEntity.getId());
        } else {
            wxUserInfoEntity = new WxUserInfoEntity();
            wxUserInfoEntity.setOpenCode(openid);
            wxUserInfoEntity.setUpdateDateTime(wxUtil.getNowDate());
            this.wxUserInfoRepository.save(wxUserInfoEntity);
        }

        orderEntity.setCode(paidOrderCode);//生成订单号
        orderEntity.setCreateDateTime(ts);//订单创建时间
        orderEntity.setPayDateTime(ts);//支付时间
        orderEntity.setMcStartDateTime(ts);//开始计时时间
        orderEntity.setCodeWx("66666666666");//微信订单号
        orderEntity.setDeviceId(deviceId);//设备id

        Timestamp afterTs = wxUtil.getAfterDate(mcTime);//计算按摩结束时间

        orderEntity.setMcEndDateTime(afterTs);//结束计时时间
        orderEntity.setStatus(state);//使用状态(未支付)
        orderEntity.setMcTime(mcTime);//按摩时长
        orderEntity.setMoney(money);//金额
        createPaidOrder(orderEntity);

        return findPaidOrderIdByOrderCode(paidOrderCode);
    }

    /**
     * 修改订单按摩开始时间，付款时间和结束时间
     * @param orderId
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void updateOrderDetail(int orderId,int state,int mcTime){
        WxUtil wxUtil = new WxUtil();
        OrderEntity orderEntity = this.findPaidOrderByOrderId(orderId);//查询订单信息
        Timestamp ts = wxUtil.getNowDate();//获取当前时间(时间戳)
        orderEntity.setPayDateTime(ts);//支付时间

        orderEntity.setMcStartDateTime(ts);//开始计时时间
        orderEntity.setCodeWx("66666666666");//微信订单号

        Timestamp afterTs = wxUtil.getAfterDate(mcTime);//计算按摩结束时间
        orderEntity.setMcEndDateTime(afterTs);//结束计时时间
        orderEntity.setStatus(state);//使用状态1  已支付，使用中

        this.orderRepository.save(orderEntity);//保存订单信息


        String devideCode = getMcCode(orderId);
        String chairCode = wxUtil.convertStringToHex(devideCode);

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        //参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        service.scheduleAtFixedRate(()-> {
            try {
                findOrderStateByTime(service,afterTs,orderEntity,chairCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);//开始计时
    }

    /**
     * 每秒查询一次订单状态，如果时间到了，修改状态为[已完成订单]state=2
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void findOrderStateByTime(ScheduledExecutorService service,Timestamp afterTs,OrderEntity orderEntity,String chairCode){
        WxUtil wxUtil = new WxUtil();
        if(afterTs.getTime()<= wxUtil.getNowDate().getTime()){//获取当前时间(时间戳)//如果现在的时间大于等于结束时间
            orderEntity.setStatus(2);//修改状态为已完成
            this.orderRepository.save(orderEntity);
            service.shutdownNow();//停止当前计时器
            try {
                jmsProducer.sendMessage("faaf0e10"+chairCode+"0000");//按摩椅20000002  停止按摩椅
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据订单号查询订单
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public String findPaidOrderById(int orderId) {
        OrderEntity orderEntity = findPaidOrderByOrderId(orderId);//查询订单信息
        JSONObject jsonObject = JSONObject.fromObject(orderEntity);//转为json对象
        String json = jsonObject.toString();//json字符串
        return json;
    }

    /**
     * 查询订单
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public String findPaidOrderList(String openCode, int state) {
        List<OrderEntity> orderEntityList = findListByWxUserOpenId(openCode,state);//用openCode查询所有status=1的已支付订单
        JSONArray jsonArray = JSONArray.fromObject(orderEntityList);//把list转成JSONArray
        String json = jsonArray.toString();//json字符串
        return json;
    }

    /**
     * 查看服务中列表中订单状态，如果时间结束状态为1，改为2
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void servingOrderState(int orderId) {
        WxUtil wxUtil = new WxUtil();
        OrderEntity orderEntity = this.orderRepository.findPaidOrderByOrderId(orderId);//查询的订单信息
        if(orderEntity.getMcEndDateTime().getTime()<= wxUtil.getNowDate().getTime()){//获取当前时间(时间戳)//如果现在的时间大于等于结束时间
            orderEntity.setStatus(2);//修改状态为已完成
            this.orderRepository.save(orderEntity);
        }
    }

    /**
     * 根据orderId获取按摩椅code
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public String getMcCode(int orderId) {
        return this.orderRepository.getMcCode(orderId);
    }
}
