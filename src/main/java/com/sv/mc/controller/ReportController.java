package com.sv.mc.controller;

import com.sv.mc.pojo.*;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.repository.OrderRepository;
import com.sv.mc.service.DeviceService;
import com.sv.mc.service.OrderService;
import com.sv.mc.service.ReportService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 销售报表统计查询controller
 * @auther liaozhiqiang
 * @date 2018/7/11
 */
@Controller
@RequestMapping("/saleReport")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * 跳转到报表查询条件页面
     * @return View销售报表
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @GetMapping(value="/reportCondition")
    public ModelAndView turnToReportCondition(){
        return new ModelAndView("./report/reportCondition");
    }

    /**
     * 查询所有省份
     * @return 所有省份数据
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @RequestMapping(value="/queryAllProvince")
    @ResponseBody
    public List<ProvinceEntity> queryAllProvince(){
        return this.reportService.queryAllProvince();
    }

    /**
     * 根据省份id查询市
     * @param provinceId 省id
     * @return 市信息
     * @auther liaozhiqiang
     * @date 2018//7/11
     */
    @RequestMapping(value="/queryAllCityByProId")
    @ResponseBody
    public List<CityEntity> queryAllCityByProId(int provinceId){
        return this.reportService.queryAllCityByProId(provinceId);
    }

    /**
     * 根据市id查询场地
     * @param cityId 市Id
     * @return 场地信息集合
     * @auther liaozhiqiang
     * @date 2018//7/11
     */
    @RequestMapping(value="/queryAllPlaceByCityId")
    @ResponseBody
    public List<PlaceEntity> queryAllPlaceByCityId(int cityId){
        return this.reportService.queryAllPlaceByCityId(cityId);
    }


    /**
     * 点击[生成报表]按钮后，获取页面数据
     * @param reportParamEntity 报表对象
     * @return 订单信息集合
     */
    @RequestMapping(value="/getReportCondition")
    @ResponseBody
    public List<OrderEntity> getReportCondition(ReportParamEntity reportParamEntity){
        List<OrderEntity> orderEntityList = new ArrayList<>();
        System.out.println(reportParamEntity.getProvinceId());
//        System.out.println(reportParamEntity.getEndTime());

        BigDecimal totalIncome = this.orderRepository.findTotalIncomeByTime(reportParamEntity.getStartTime(),reportParamEntity.getEndTime());//查询时间段内的总收入
//        System.out.println(totalIncome);

        int totalNormalChairCount = this.deviceRepository.findNormalDeviceTotalCount();//按摩椅正常数量
//        System.out.println(totalNormalChairCount);

        int totalFaultChairCount = this.deviceRepository.findFaultDeviceTotalCount(); //按摩椅异常数量
//        System.out.println(totalFaultChairCount);

        int totalLaunchChairCount = this.deviceRepository.findLaunchChairCount(reportParamEntity.getStartTime(),reportParamEntity.getEndTime());//设备投放量
//        System.out.println(totalLaunchChairCount);

        int totalFaultInfoCount = this.deviceRepository.findFaultInfoCount();//异常信息数量
//        System.out.println(totalFaultInfoCount);

        String[] timeData = reportParamEntity.getStartTime().split("/");
        String reportMonth = timeData[1];
        String reportDay = timeData[2].split(" ")[0];
        System.out.println(reportDay);

        String dailyReportDate = reportParamEntity.getEndTime().substring(0,9);
        String dailyReportDate2="";
        if(Integer.parseInt(reportMonth)>=1 && Integer.parseInt(reportMonth)<10){ //如果月份在1-10
            if(Integer.parseInt(reportDay)>=1 && Integer.parseInt(reportDay)<10){//如果日期在1-10
                dailyReportDate2 = reportParamEntity.getStartTime().substring(0,8);
            }else{
                dailyReportDate2 = reportParamEntity.getStartTime().substring(0,10);
            }

        }else if(Integer.parseInt(reportMonth)>=10 && Integer.parseInt(reportMonth)<=12){//如果月份在11-12
            if(Integer.parseInt(reportDay)>=10 && Integer.parseInt(reportDay)<=31){//10-31
                dailyReportDate2 = reportParamEntity.getStartTime().substring(0,10);
            }else{
                dailyReportDate2 = reportParamEntity.getStartTime().substring(0,9);
            }

        }


        System.out.println(dailyReportDate);
        System.out.println(dailyReportDate2);

        

        return orderEntityList;
    }
}
