package com.sv.mc.controller;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.OrderEntity;
import com.sv.mc.service.OrderService;
import com.sv.mc.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * 订单管理controller
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;


    /**
     * 跳转到订单查询(管理员)页面
     * @return  订单查询管理员 view 对象
     */
    @GetMapping(value="/orderMgr/turnToOrderMgr")
    public ModelAndView turnToOrderMgr(){
        return new ModelAndView("./order/orderMgr");
    }

    /**
     * 跳转到订单查询(代理、场地)页面
     * @return  订单查询view 对象
     */
    @GetMapping(value="/orderMgr/turnToOrderPlaceMgr")
    public ModelAndView turnToOrderPlaceMgr(){
        return new ModelAndView("./order/orderPlaceMgr");
    }


    /**
     * 分页全部查询
     * @param session 用户信息
     * @param pageSize  截至个数
     * @param page 起始个数
     * @param startTime  起始时间
     * @param endTime  截止时间
     * @return 返回所有订单内容
     */
    @GetMapping(value = "/orderMgr/allOrder")
    public @ResponseBody
    String getAll(@Param("page") String page, @Param("pageSize") String pageSize, HttpSession session,@Param("startTime") String startTime,@Param("endTime") String endTime) {
        return this.orderService.findAllOrdersByPage(Integer.parseInt(page),Integer.parseInt(pageSize),session,startTime,endTime);
    }

    /**
     * 后台添加订单描述
     * @param orderId 订单Id
     * @param description 订单信息
     */
    @PostMapping(value = "/orderMgr/addOrderDescription")
    public @ResponseBody
    void addOrderDescription(int orderId,String description){
        this.orderService.addOrderDescription(orderId,description);
    }

    /**
     * 跳转到昨日订单信息页面
     */
    @GetMapping(value="/orderMgr/turnToOrderMgrByYesterday")
    public ModelAndView turnToOrderMgrByYesterday(){
        return new ModelAndView("./order/orderMgrYesterday");
    }

    /**
     * 分页全部查询
     * @return 返回昨日订单内容（改为今日 2018/9/20）
     * @param page 起始个数
     * @param pageSize 截至个数
     * @param session 用户信息
     */
    @GetMapping(value = "/orderMgr/allOrderYesterday")
    public @ResponseBody
    String allOrderYesterday(@Param("page") String page, @Param("pageSize") String pageSize,HttpSession session) {
        return this.orderService.findYesterDayOrderInfo(Integer.parseInt(page),Integer.parseInt(pageSize),session);
    }

    /**
     * excel 导出订单记录
     * @param response 响应下载
     * @param startTime 起始时间
     * @param endTime 截止时间
     */
    @GetMapping(value = "/orderMgr/getExcelOrder")
    public void getExcelOrder(HttpServletResponse response,@Param("startTime") String startTime,@Param("endTime") String endTime){

        List<OrderEntity>list =this.orderService.findAllExcelOrder(startTime,endTime);
        System.out.println("执行了");
        //标题
        String[] title = {"订单id","来源", "订单编号","订单号","订单状态","订单创建时间","订单支付时间","订单金额($)","开始按摩时间","结束按摩时间","按摩时长(分钟)","取消订单原因","备注"};
        //文件名
        Date d = new Date();
        String time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
        String fileName = "订单统计表" + time + ".xls";
        //sheet 名
        String sheetName = "订单统计表";
        String[][] content = new String[list.size()][0];
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            OrderEntity orderEntity=list.get(i);

            String obj = orderEntity.getOrderSource();
            String obj1 = orderEntity.getCode();
            String obj2 =orderEntity.getCodeWx();
            String obj3=null;
            int status = orderEntity.getStatus();
            if (status==0){
                obj3="未付款";
            }else if (status==1){
                obj3="服务中";
            }else if(status==2){
                obj3="已完成";
            }else{
                obj3="已取消";
            }
            Timestamp createDateTime = orderEntity.getCreateDateTime();
            String obj4=createDateTime.toString();
            String obj5=null;
            if (orderEntity.getPayDateTime()!=null){
             obj5 = orderEntity.getPayDateTime().toString();
            }
            BigDecimal money=orderEntity.getMoney();
            String obj6=money.stripTrailingZeros().toPlainString();//金额
            String obj7=null;
            String obj8=null;
            String obj9=null;
            String obj10=null;
            String obj11=null;
            if (orderEntity.getMcStartDateTime()!=null){
                obj7=orderEntity.getMcStartDateTime().toString();
            }
            if (orderEntity.getMcEndDateTime()!=null){
                obj8=orderEntity.getMcEndDateTime().toString();
            }
            if(orderEntity.getMcTime()!=null){
                obj9=orderEntity.getMcTime().toString();
            }
            if(orderEntity.getCancelReason()!=null){
                obj10=orderEntity.getCancelReason();
            }if(orderEntity.getDescription()!=null){
                obj11=orderEntity.getDescription();
            }
            String obj12=String.valueOf(orderEntity.getId());
            content[i][1] = obj;
            content[i][2] = obj1;
            content[i][3] = obj2;
            content[i][4] = obj3;
            content[i][5] = obj4;
            content[i][6] = obj5;
            content[i][7] = obj6;
            content[i][8] = obj7;
            content[i][9] = obj8;
            content[i][10] = obj9;
            content[i][11] = obj10;
            content[i][12] = obj11;
            content[i][0]=obj12;
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    /**
     * 文件下载
     * @param response 响应下载
     * @param fileName 文件名
     */
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\""+ fileName+"\"");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
