package com.sv.mc.controller;

import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.pojo.ReportViewEntity;
import com.sv.mc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@RestController
public class ReportController1 {
   @Autowired
    private ProvinceService ps ;

   @Autowired
  private RegionService rs;
   @Autowired
   private PlaceService pls;
   @Autowired
   private ReportViewService rvs;
    /**
     * 跳转到report页面
     *
     * @return
     */
    @GetMapping(value = "/turnToreport")
    public ModelAndView turnToBussinessMgr() {
        return new ModelAndView("./baseInfo/report");
    }


    /**
     * 查询所有省
     */

    @GetMapping(value = "/province")
    public List<ProvinceEntity> getProvince(){
  return  ps.selectProvince();
    }

    /**
     * 查询所有市
     */

    @GetMapping(value = "/city1")
    public List<CityEntity> getCity2(@Param("id") int id){
        return  rs.getRegionityCity(id);
    }

    /**
     * 查询所有地区
     */
    @GetMapping(value = "/place")
    public List<PlaceEntity> getPlace(@Param("id") int id){
        return  pls.getPlace(id);
    }


    /**
     * 根据时间省id 查询报表
     */

    @GetMapping(value = "/fillday")
    public List<ReportViewEntity> getProvince1(@Param("s")Date s,@Param("e")Date e,@Param("id") int id){
      return  rvs.fillDayReport(s, e, id);
    }


    /**
     *  查询市报表
     * @param s
     * @param e
     * @param id
     * @return
     */
    @GetMapping(value = "/fillcity")
    public List<ReportViewEntity> getCity1(@Param("s")Date s,@Param("e")Date e,@Param("id") int id){
        return  rvs.fillcityReport(s, e, id);


    }

    /**
     *  查询市报表
     * @param s
     * @param e
     * @param id
     * @return
     */
    @GetMapping(value = "/fillplace")
    public List<ReportViewEntity> getPlace1(@Param("s")Date s,@Param("e")Date e,@Param("id") int id){
        return  rvs.fillplaceReport(s, e, id);
    }







}
