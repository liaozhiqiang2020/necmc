package com.sv.mc.service;

import com.sv.mc.pojo.ReportViewEntity;

import java.util.Date;
import java.util.List;

public interface ReportViewService extends  BaseService{



    /**
     * 查询特定省的报表信息
     * @return
     */

    public List<ReportViewEntity> fillDayReport(Date start, Date end, int provinceid);


    /**
     * 查询特定市的报表信息
     * @return
     */

    public List<ReportViewEntity> fillcityReport(Date start, Date end, int cityid);



    /**
     * 查询特定地方的报表信息
     * @return
     */

    public List<ReportViewEntity> fillplaceReport(Date start, Date end, int placeid);


}
