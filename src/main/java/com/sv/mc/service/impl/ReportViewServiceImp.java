
package com.sv.mc.service.impl;

import com.sv.mc.pojo.ReportViewEntity;
import com.sv.mc.repository.ReportViewRepository;
import com.sv.mc.service.ReportViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ReportViewServiceImp implements ReportViewService {
   @Autowired
    private ReportViewRepository rvr;

    /**
     * 省报表
     * @param start 起始时间
     * @param end 截止时间
     * @param id 省Id
     * @return 报表
     */
    @Override
    public List<ReportViewEntity> fillDayReport(Date start, Date end,int id) {
        ReportViewEntity reportViewEntity=new ReportViewEntity();
        List<Object[]> o=   rvr.fillReport(start, end,id);

        List<ReportViewEntity> listreport= new ArrayList<ReportViewEntity>();
      for (int i=0;i<o.size();i++){
            Object[] o1 = o.get(i);
             String str = (String) o1[0];

          reportViewEntity.setSite(str);
          reportViewEntity.setDm_name((String) o1[1]);
          reportViewEntity.setChairType((String) o1[2]);
          reportViewEntity.setUserNumber(Integer.parseInt(o1[3].toString()));
          reportViewEntity.setPayment(Integer.parseInt(o1[4].toString()));
          reportViewEntity.setAccountpaid(Integer.parseInt(o1[5].toString()));
          reportViewEntity.setOffthestocks(Integer.parseInt(o1[6].toString()));
          reportViewEntity.setCanceled(Integer.parseInt(o1[7].toString()));
          reportViewEntity.setOrderTotal(Integer.parseInt(o1[8].toString()));
          reportViewEntity.setIncome((BigDecimal) o1[9]);
          reportViewEntity.setExpend((BigDecimal) o1[10]);
          reportViewEntity.setProfit((BigDecimal) o1[11]);
          reportViewEntity.setUnitException(Integer.parseInt(o1[12].toString()));

          listreport.add(reportViewEntity);

      }

      return listreport;
    }












    @Override
    public List findAllEntities() {
        return null;
    }


    /**
     * 市报表
     * @param start 起始时间
     * @param end 截止时间
     * @param cityid 市id
     * @return 市报表
     */
    @Override
    public List<ReportViewEntity> fillcityReport(Date start, Date end, int cityid) {
        ReportViewEntity reportViewEntity=new ReportViewEntity();
        List<Object[]> o=   rvr.fillcityReport(start, end,cityid);

        List<ReportViewEntity> listreport= new ArrayList<ReportViewEntity>();
        for (int i=0;i<o.size();i++){
            Object[] o1 = o.get(i);
            String str = (String) o1[0];

            reportViewEntity.setSite(str);
            reportViewEntity.setDm_name((String) o1[1]);
            reportViewEntity.setChairType((String) o1[2]);
            reportViewEntity.setUserNumber(Integer.parseInt(o1[3].toString()));
            reportViewEntity.setPayment(Integer.parseInt(o1[4].toString()));
            reportViewEntity.setAccountpaid(Integer.parseInt(o1[5].toString()));
            reportViewEntity.setOffthestocks(Integer.parseInt(o1[6].toString()));
            reportViewEntity.setCanceled(Integer.parseInt(o1[7].toString()));
            reportViewEntity.setOrderTotal(Integer.parseInt(o1[8].toString()));
            reportViewEntity.setIncome((BigDecimal) o1[9]);
            reportViewEntity.setExpend((BigDecimal) o1[10]);
            reportViewEntity.setProfit((BigDecimal) o1[11]);
            reportViewEntity.setUnitException(Integer.parseInt(o1[12].toString()));

            listreport.add(reportViewEntity);

        }

        return listreport;

    }

    @Override
    public List<ReportViewEntity> fillplaceReport(Date start, Date end, int placeid) {
        ReportViewEntity reportViewEntity=new ReportViewEntity();
        List<Object[]> o=   rvr.fillplaceReport(start, end, placeid);

        List<ReportViewEntity> listreport= new ArrayList<ReportViewEntity>();
        for (int i=0;i<o.size();i++){
            Object[] o1 = o.get(i);
            String str = (String) o1[0];

            reportViewEntity.setSite(str);
            reportViewEntity.setDm_name((String) o1[1]);
            reportViewEntity.setChairType((String) o1[2]);
            reportViewEntity.setUserNumber(Integer.parseInt(o1[3].toString()));
            reportViewEntity.setPayment(Integer.parseInt(o1[4].toString()));
            reportViewEntity.setAccountpaid(Integer.parseInt(o1[5].toString()));
            reportViewEntity.setOffthestocks(Integer.parseInt(o1[6].toString()));
            reportViewEntity.setCanceled(Integer.parseInt(o1[7].toString()));
            reportViewEntity.setOrderTotal(Integer.parseInt(o1[8].toString()));
            reportViewEntity.setIncome((BigDecimal) o1[9]);
            reportViewEntity.setExpend((BigDecimal) o1[10]);
            reportViewEntity.setProfit((BigDecimal) o1[11]);
            reportViewEntity.setUnitException(Integer.parseInt(o1[12].toString()));

            listreport.add(reportViewEntity);

        }

        return listreport;
    }
}

