package com.sv.mc.controller;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.OrderEntity;
import com.sv.mc.service.OrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 订单管理controller
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;


    /**
     * 跳转到订单管理页面
     */
    @GetMapping(value="/turnToOrderMgr")
    public ModelAndView turnToOrderMgr(){
        return new ModelAndView("./order/orderMgr");
    }


    /**
     * 全部查询
     * @return 返回所有订单内容
     */
    @GetMapping(value = "/orderMgr/allOrder")
    public @ResponseBody
    String getAll(@Param("page") String page, @Param("pageSize") String pageSize, HttpSession session) {
        return this.orderService.findAllOrdersByPage(Integer.parseInt(page),Integer.parseInt(pageSize),session);
    }

    /**
     * 后台添加订单描述
     */
    @PostMapping(value = "/orderMgr/addOrderDescription")
    public @ResponseBody
    void addOrderDescription(int orderId,String description){
        this.orderService.addOrderDescription(orderId,description);
    }

    /**
     * 跳转到昨日订单信息页面
     */
    @GetMapping(value="/turnToOrderMgrByYesterday")
    public ModelAndView turnToOrderMgrByYesterday(){
        return new ModelAndView("./order/orderMgrYesterday");
    }

    /**
     * 全部查询
     * @return 返回昨日订单内容
     */
    @GetMapping(value = "/orderMgr/allOrderYesterday")
    public @ResponseBody
    String allOrderYesterday(@Param("page") String page, @Param("pageSize") String pageSize) {
        return this.orderService.findYesterDayOrderInfo(Integer.parseInt(page),Integer.parseInt(pageSize));
    }

}
