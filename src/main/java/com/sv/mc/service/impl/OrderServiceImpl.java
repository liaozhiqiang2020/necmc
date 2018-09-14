package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.*;
import com.sv.mc.repository.AccountRepository;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.repository.OrderRepository;
import com.sv.mc.repository.WxUserInfoRepository;
import com.sv.mc.service.*;
import com.sv.mc.util.DataSourceResult;
import com.sv.mc.util.DateJsonValueProcessor;
import com.sv.mc.util.SingletonHungary;
import com.sv.mc.util.WxUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 已支付订单服务
 *
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
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountDetailService accountDetailService;

    /**
     * 分页查询所有订单(后台查询)
     *
     * @return
     */
    @Override
    @Transactional
    public String findAllOrdersByPage(int page, int pageSize,HttpSession session) {
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        int superId = userEntity.getGradeId();//1.2.3.4
        int flag = userEntity.getpId();//上级id
        List<OrderEntity> branchEntityList;
        int offset = ((page - 1) * pageSize);
        int total = 0;

        if(superId==1){
            branchEntityList=this.orderRepository.findAllOrdersByPage(offset, pageSize);//记录
            total = this.orderRepository.findOrderTotal();//数量
        }else if(superId==2){
            branchEntityList= this.orderRepository.findAllOrdersByPage2(offset, pageSize,flag);
            total = this.orderRepository.findOrderTotal2(flag);//数量
        }else if(superId==3){
            branchEntityList= this.orderRepository.findAllOrdersByPage3(offset, pageSize,flag);
            total = this.orderRepository.findOrderTotal3(flag);//数量
        }else{
            branchEntityList= this.orderRepository.findAllOrdersByPage4(offset, pageSize,flag);
            total = this.orderRepository.findOrderTotal4(flag);//数量
        }

        DataSourceResult<OrderEntity> branchEntityDataSourceResult = new DataSourceResult<>();

        branchEntityDataSourceResult.setData(branchEntityList);
        branchEntityDataSourceResult.setTotal(total);
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject jsonObject = JSONObject.fromObject(branchEntityDataSourceResult, config);//转化为jsonArray
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();//新建json数组

        String statusName = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject12 = jsonArray.getJSONObject(i);
            int status = Integer.parseInt(jsonObject12.get("status").toString());

            Object object = jsonObject12.get("deviceId").toString();

            if(object.equals("0")){
                jsonObject12.put("deviceSn", "");
            }else{
                int deviceId = Integer.parseInt(object.toString());
                String deviceSn = this.deviceRepository.findDeviceById(deviceId).getMcSn();
                jsonObject12.put("deviceSn", deviceSn);
            }

            if (status == 0) {
                statusName = "未付款";
            } else if (status == 1) {
                statusName = "服务中";
            } else if (status == 2) {
                statusName = "已完成";
            } else if (status == 3) {
                statusName = "已取消";
            }
            jsonObject12.put("statusName", statusName);
            jsonArray1.add(jsonObject12);
        }
        jsonObject1.put("data", jsonArray1);
        jsonObject1.put("total", total);


        return jsonObject1.toString();
    }


    /**
     * 查询所有订单
     *
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public List<OrderEntity> findAllEntities() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<OrderEntity> wxUserInfoEntityPage = this.orderRepository.findAll(pageRequest);
        return wxUserInfoEntityPage.getContent();
    }

    /**
     * 创建订单
     *
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
     *
     * @param openId
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public List<OrderEntity> findListByWxUserOpenId(String openId, int state) {
        List<OrderEntity> orderEntityList = this.orderRepository.findListByWxUserId(openId, state);
        return orderEntityList;
    }

    /**
     * 分页查询当前用户订单列表
     *
     * @param openId
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public List<OrderEntity> findListByWxUserOpenIdByPage(String openId, int state, int offset, int pageSize) {
        List<OrderEntity> orderEntityList = this.orderRepository.findListByWxUserIdByPage(openId, state, offset, pageSize);
        return orderEntityList;
    }

    /**
     * 根据订单号查询订单
     *
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
     *
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
     *
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
     *
     * @param paidOrderCode,state
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void updateOrderByCode(String paidOrderCode, int state) {
        OrderEntity orderEntity = this.orderRepository.findPaidOrderIdByOrderCode(paidOrderCode);//查询订单信息
        orderEntity.setStatus(state);//写入订单状态
        this.orderRepository.save(orderEntity);

    }



    /**
     * 根据订单id修改订单状态
     *
     * @param orderId,state
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void updateOrderById(int orderId, int state, String description) {
        OrderEntity orderEntity = this.orderRepository.findPaidOrderByOrderId(orderId); //查询订单信息
        orderEntity.setStatus(state);//写入订单状态
        orderEntity.setDescription(description);
        this.orderRepository.save(orderEntity);
    }

    /**
     * 获取按摩剩余时间
     *
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
     *
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public int createPaidOrder(String openid, int mcTime, String deviceCode, String promoCode, BigDecimal money, String unPaidOrderCode, int state, int strength) {
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
        orderEntity.setCodeWx("");//微信/支付宝/银联订单号
        orderEntity.setOrderSource("微信");
        orderEntity.setDeviceId(deviceId);//设备id

        Timestamp afterTs = wxUtil.getAfterDate(mcTime);//计算按摩结束时间

        orderEntity.setMcEndDateTime(afterTs);//结束计时时间
        orderEntity.setStatus(state);//使用状态(未支付)
        orderEntity.setMcTime(mcTime);//按摩时长
        orderEntity.setMoney(money);//金额
        orderEntity.setStrength(strength);//按摩强度
        createPaidOrder(orderEntity);

        return findPaidOrderIdByOrderCode(paidOrderCode);
    }

    /**
     * 修改订单按摩开始时间，付款时间和结束时间
     *
     * @param orderId
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void updateOrderDetail(int orderId, int state, int mcTime) {
        WxUtil wxUtil = new WxUtil();
        OrderEntity orderEntity = this.findPaidOrderByOrderId(orderId);//查询订单信息
        Timestamp ts = wxUtil.getNowDate();//获取当前时间(时间戳)
        orderEntity.setPayDateTime(ts);//支付时间

        orderEntity.setMcStartDateTime(ts);//开始计时时间

        Timestamp afterTs = wxUtil.getAfterDate(mcTime);//计算按摩结束时间
        orderEntity.setMcEndDateTime(afterTs);//结束计时时间
        orderEntity.setStatus(state);//使用状态1  已支付，使用中

        this.orderRepository.save(orderEntity);//保存订单信息

        if (state == 1) {//插入账单信息
            AccountDetailEntity accountDetailEntity = new AccountDetailEntity();
//            int deviceId = orderEntity.getDeviceId();//按摩椅编号
//            DeviceEntity deviceEntity = this.deviceRepository.findDeviceById(deviceId);//设备信息
//            int placeId = deviceEntity.getPlaceEntity().getId();//场地id
//            int superiorId = deviceEntity.getPlaceEntity().getSuperiorId();//上级单位id
//            int typeFlag = deviceEntity.getPlaceEntity().getLevelFlag();//上级单位
//            BigDecimal bigDecimal = new BigDecimal(0);
            BigDecimal money = orderEntity.getMoney();//收入
            accountDetailEntity.setAccountId(1);
            accountDetailEntity.setDetailCode("accountDetail_" + ts.toString());
            accountDetailEntity.setDetailName("accountDetail_" + ts.toString());
            accountDetailEntity.setCapital(money);
            accountDetailEntity.setCapitalFlag(1);
            accountDetailEntity.setDetailDateTime(ts);
            this.accountDetailService.createAccountDetail(accountDetailEntity);

//
//            accountEntity.setPlaceId(placeId);
//            accountEntity.setAccountStatus(1);
//            accountEntity.setName("微信");
//            accountEntity.setGeneralIncome(money);
//            accountEntity.setTotalExpenditure(bigDecimal);
//            accountEntity.setProfit(money);
//            accountEntity.setSuperiorId(superiorId);
//            accountEntity.setTypeFlag(typeFlag);
//            this.accountService.createAccount(accountEntity);
        }


        String devideCode = getMcCode(orderId);
        String chairCode = wxUtil.convertStringToHex(devideCode);

        SingletonHungary.getSingleTon().remove(devideCode+"runing");
        SingletonHungary.getSingleTon().remove(devideCode+"status");
//        SingletonHungary.getSingleTon().put(devideCode+"status",devideCode+"_"+2);

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        //参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        service.scheduleAtFixedRate(() -> {
            try {
                findOrderStateByTime(service, afterTs, orderEntity, chairCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);//开始计时

    }

    /**
     * 每秒查询一次订单状态，如果时间到了，修改状态为[已完成订单]state=2
     *
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void findOrderStateByTime(ScheduledExecutorService service, Timestamp afterTs, OrderEntity orderEntity, String chairCode) {
        WxUtil wxUtil = new WxUtil();
        if (afterTs.getTime() <= wxUtil.getNowDate().getTime()) {//获取当前时间(时间戳)//如果现在的时间大于等于结束时间
            orderEntity.setStatus(2);//修改状态为已完成
            this.orderRepository.save(orderEntity);
            service.shutdownNow();//停止当前计时器
            try {
                jmsProducer.sendMessage("faaf0e10" + chairCode + "0000");//按摩椅20000002  停止按摩椅
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据订单号查询订单
     *
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
     *
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public String findPaidOrderList(String openCode, int state) {
        List<OrderEntity> orderEntityList = findListByWxUserOpenId(openCode, state);//用openCode查询所有status=1的已支付订单
        JSONArray jsonArray = JSONArray.fromObject(orderEntityList);//把list转成JSONArray
        String json = jsonArray.toString();//json字符串
        return json;
    }

    /**
     * 分页查询订单
     *
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public String findPaidOrderListByPage(String openCode, int state, int pageNumber, int pageSize) {
        int offset = ((pageNumber - 1) * pageSize);
        List<OrderEntity> orderEntityList = findListByWxUserOpenIdByPage(openCode, state, offset, pageSize);//用openCode查询所有status=1的已支付订单
        JSONArray jsonArray = JSONArray.fromObject(orderEntityList);//把list转成JSONArray
        String json = jsonArray.toString();//json字符串
        return json;
    }


    /**
     * 查看服务中列表中订单状态，如果时间结束状态为1，改为2
     *
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void servingOrderState(int orderId) {
        WxUtil wxUtil = new WxUtil();
        OrderEntity orderEntity = this.orderRepository.findPaidOrderByOrderId(orderId);//查询的订单信息
        if (orderEntity.getMcEndDateTime().getTime() <= wxUtil.getNowDate().getTime()) {//获取当前时间(时间戳)//如果现在的时间大于等于结束时间
            orderEntity.setStatus(2);//修改状态为已完成
            this.orderRepository.save(orderEntity);
        }
    }

    /**
     * 根据orderId获取按摩椅code
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> getMcCodeForMap(int orderId) {
        List<Object[]> list = this.orderRepository.getMcCodeForList(orderId);
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = list.get(i);
            String chairId = object[0].toString();
            int strength = Integer.parseInt(object[1].toString());
            map.put("chairId", chairId);
            map.put("strength", strength);
        }

        return map;
    }


    /**
     * 根据orderId获取按摩椅code
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public String getMcCode(int orderId) {
        return this.orderRepository.getMcCode(orderId);
    }


    /**
     * 后台添加订单描述
     *
     * @param
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void addOrderDescription(int orderId, String description) {
        OrderEntity orderEntity = this.orderRepository.findPaidOrderByOrderId(orderId); //查询订单信息
        orderEntity.setCancelReason(description);
        this.orderRepository.save(orderEntity);
    }

    @Override
    public int findYesterDayOrderCount() {
        Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, -1);    //得到前一天

        String yestedayDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime());
        String today = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date());
//        System.out.println(yestedayDate);
//        System.out.println(today);

//        return 56;
        return this.orderRepository.findOrderByPeriod(yestedayDate, today);
    }


    @Override
    public String findYesterDayOrderInfo(int page,int pageSize) {
        Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, -1);    //得到前一天

        String yestedayDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime());
        String today = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date());

        int offset = ((page - 1) * pageSize);
        List<OrderEntity> orderEntityList = this.orderRepository.findOrdersInfo(yestedayDate, today,offset,pageSize);
        int total = this.orderRepository.findOrderByPeriod(yestedayDate, today);//数量

        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONArray jsonArray= JSONArray.fromObject(orderEntityList, config);//转化为jsonArray

        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();//新建json数组

        String statusName = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject12 = jsonArray.getJSONObject(i);
            int status = Integer.parseInt(jsonObject12.get("status").toString());

            Object object = jsonObject12.get("deviceId").toString();

            if(object.equals("0")){
                jsonObject12.put("deviceSn", "");
            }else{
                int deviceId = Integer.parseInt(object.toString());
                String deviceSn = this.deviceRepository.findDeviceById(deviceId).getMcSn();
                jsonObject12.put("deviceSn", deviceSn);
            }

            if (status == 0) {
                statusName = "未付款";
            } else if (status == 1) {
                statusName = "服务中";
            } else if (status == 2) {
                statusName = "已完成";
            } else if (status == 3) {
                statusName = "已取消";
            }
            jsonObject12.put("statusName", statusName);
            jsonArray1.add(jsonObject12);
        }
        jsonObject1.put("data", jsonArray1);
        jsonObject1.put("total", total);


        return jsonObject1.toString();
    }

    /**
     * 不分页查询所有订单信息
     * @return
     */
    @Override
    public List<OrderEntity> findAllExcelOrder() {
        return this.orderRepository.getExcelOrder();
    }


    /**
     * 修改微信订单号
     * @param orderId
     * @param codeWx
     */
    @Override
    public void updateOrderByCode(String orderId,String codeWx) {
        OrderEntity orderEntity = this.orderRepository.findPaidOrderByOrderId(Integer.parseInt(orderId));//查询订单信息
        orderEntity.setCodeWx(codeWx);
        this.orderRepository.save(orderEntity);
    }
}
