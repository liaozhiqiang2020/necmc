package com.sv.mc.service;

import com.sv.mc.pojo.ReportViewEntity;

import java.util.Date;
import java.util.List;

/**
 * 报表service
 */
public interface ReportViewService extends  BaseService{

    /**
     * 查询特定省的报表信息
     * @param start 起始时间
     * @param end 截止时间
     * @param provinceid 省Id
     * @return 报表信息
     */
    List<ReportViewEntity> fillDayReport(Date start, Date end, int provinceid);


    /**
     * 查询特定市的报表信息
     * @param start 起始时间
     * @param end 截止时间
     * @param cityid 市id
     * @return 市报表信息
     */
    List<ReportViewEntity> fillcityReport(Date start, Date end, int cityid);




    /**
     * 查询特定地方的报表信息
     * @param start 起始时间
     * @param end 截止时间
     * @param placeid 场地Id
     * @return 报表信息
     */
    List<ReportViewEntity> fillplaceReport(Date start, Date end, int placeid);


}
