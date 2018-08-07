package com.sv.mc.controller;

import com.sv.mc.pojo.*;
import com.sv.mc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
   @Autowired
   private  CountService countService;
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
    public List<ProvinceEntity> getProvince(HttpServletRequest request){
     List<ProvinceEntity> list= new ArrayList<>();
        UserEntity user= (UserEntity) request.getSession().getAttribute("user");
        int pId=user.getpId();
        if (user.getGradeId()==1) {
            list=ps.selectProvince();
        }else{
            list=this.countService.getProvinceByP_ID(pId);
        }



        return list;
    }

    /**
     * 查询所有市
     */

    @GetMapping(value = "/city1")
    public List<CityEntity> getCity2(HttpServletRequest request,@Param("id") int id){
        UserEntity user= (UserEntity) request.getSession().getAttribute("user");
       List<CityEntity>list=new ArrayList<>();
        int pId=user.getpId();
        if (user.getGradeId()==1) {
            list= rs.getRegionityCity(id);
        }else{
            list=this.countService.getCityByP_ID(pId);
        }
        return  list;
    }

    /**
     * 查询所有地区
     */
    @GetMapping(value = "/place")
    public List<PlaceEntity> getPlace(HttpServletRequest request,@Param("id") int id){
        UserEntity user= (UserEntity) request.getSession().getAttribute("user");
        int pId=user.getpId();
       List<PlaceEntity> list= new ArrayList<>();
        if (user.getGradeId()==1){
        list=  pls.getPlace(id);
       }else{
            list=this.countService.getPlaceByP_ID(pId);
        }
        return list;
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
