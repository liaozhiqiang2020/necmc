package com.sv.mc.controller;

import com.sv.mc.pojo.UserEntity;
import com.sv.mc.pojo.qo.ProvinceQo;
import com.sv.mc.service.CountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报表信息控制层
 */
@RestController
public class CountController {
    @Resource
    CountService countService;

    /**
     *   根据省ID查询一个省
     * @param pId 省id
     * @param start 起始时间
     * @param end 截止时间
     * @return 省报表数据
     */
    @GetMapping(value = "/findProvinceByPid")
    private List<ProvinceQo> findProvinceById(@RequestParam("pId") int pId, @RequestParam("start") String start , @RequestParam("end") String end, HttpServletRequest request){
        UserEntity user=(UserEntity) request.getSession().getAttribute("user");
         int p_Id= user.getpId();
         int gid=user.getGradeId();
         List<ProvinceQo> p1=  new ArrayList();
        ProvinceQo p=null;
       if(gid==1){
           p=   this.countService.findProvinceById(pId,start,end);
           p1.add(p);
       }else if(gid==4){
           p=null;
           p1.add(p);
       }else{
        List<ProvinceQo>   plist=this.countService.getProvinceBypIdANDprovinceID(gid,p_Id,start,end,pId);
        if (plist.size()!=0){
            p=plist.get(0);
            p1.add(p);

        }else {
            p=null;
        }
       }

        return p1;
    }


    /**
     * 根据省ID查询所有市
     * @param pId 省id
     * @param start 起始时间
     * @param end  截止时间
     * @param request 请求信息
     * @return 市报表
     */
    @GetMapping(value = "/findCityByPid")
    private List<ProvinceQo> findCTByPId(@RequestParam("pId") int pId, @RequestParam("start") String start ,@RequestParam("end") String end,HttpServletRequest request){
        UserEntity user=(UserEntity) request.getSession().getAttribute("user");
        int p_Id= user.getpId();
        int gid=user.getGradeId();
        List<ProvinceQo> p=   null;
        if(gid==1){
            p=   this.countService.findCityByProvinceID(pId,start,end);
        }else if (gid==4){
            p=null;
        }else {
            p=this.countService.getCBypIdANDprovinceID(gid,p_Id,start,end,pId);
        }
        return p;
    }

    /**
     * 查询一个市区
     * @param cId 市Id
     * @param start 起始时间
     * @param end 截止时间
     * @param request 请求信息
     * @return 市报表数据
     */
    @GetMapping(value = "/findPlaceByCity")
    private List<ProvinceQo> findCityBycityID(@RequestParam("cId") int cId, @RequestParam("start") String start ,@RequestParam("end") String end,HttpServletRequest request){
        UserEntity user=(UserEntity) request.getSession().getAttribute("user");
        int p_Id= user.getpId();
        int gid=user.getGradeId();
        List<ProvinceQo> p1=  new ArrayList();
        ProvinceQo p =null;
        if(gid==1){
         p=  this.countService.findCityById(cId,start,end);
            p1.add(p);
         }else if (gid==4){
            p=null;
            p1.add(p);
        }else{
          List<ProvinceQo>  pist=this.countService.getCityBypIdANDcityID(gid,p_Id,start,end,cId);
          if (pist.size()!=0){
              p=pist.get(0);
              p1.add(p);
          }else{
              p=null;
              p1.add(p);
          }
        }

        return p1;
    }


    /**
     * 根据市ID查询所有场地
     * @param cId 市Id
     * @param start 起始时间
     * @param end 截止时间
     * @param request 请求信息
     * @return 市信息集合
     */
    @GetMapping(value = "/findPlaceBycid")
    private List<ProvinceQo> findByplacecId(@RequestParam("cId") int cId, @RequestParam("start") String start ,@RequestParam("end") String end,HttpServletRequest request){
        UserEntity user=(UserEntity) request.getSession().getAttribute("user");
        int p_Id= user.getpId();
        int gid=user.getGradeId();
        List<ProvinceQo> p=null;
        if(gid==1){
          p= this.countService.findPlaceByCityID(cId, start, end);
        }else if (gid==4){
            p=null;
        }else{
            p=this.countService.getPlacyBypIdANDcityID(gid,p_Id,start,end,cId);
        }
        return p;
    }


    /**
     * 查询一个场地 根据场地ID
     * @param pId 场地Id
     * @param start 起始时间
     * @param end 截止时间
     * @param request 用户信息
     * @return 场地报表
     */
    @GetMapping(value = "/findonePlace")
    private List<ProvinceQo> getplaceByplaceID(@RequestParam("pId") int pId, @RequestParam("start") String start ,@RequestParam("end") String end,HttpServletRequest request){
        UserEntity user=(UserEntity) request.getSession().getAttribute("user");
        int p_Id= user.getpId();
        int gid=user.getGradeId();
        List<ProvinceQo> p1=  new ArrayList();
        ProvinceQo p=null;
        if (gid==1){
         p=   this.countService.getONEPlaceById(pId, start, end);
            p1.add(p);
        }else if (gid==2||gid==3){
           List<ProvinceQo> plist=this.countService.getPlacyBypIdANDplaceID(gid,p_Id,start,end,pId);
            if (plist.size()!=0){
                p= plist.get(0);
                p1.add(p);
            }else {
                p=null;
                p1.add(p);
            }
        }else {

            List<ProvinceQo> plist=this.countService.getPlacyByANDplaceID(start,end,pId);
            if (plist.size()!=0){
                //p= plist.get(0);
                p1.addAll(plist);
            }else {
                p=null;
                p1.add(p);
            }
        }

        return p1;
    }


    /**
     * 查询所有省
     * @param start 起始时间
     * @param end 截止时间
     * @param request 用户请求
     * @return 省信息
     */
    @GetMapping(value = "/findoneProvince")
    private List<ProvinceQo> getprovinceByplaceID( @RequestParam("start") String start ,@RequestParam("end") String end,HttpServletRequest request){
        UserEntity user=(UserEntity) request.getSession().getAttribute("user");
        int p_Id= user.getpId();
        int gid=user.getGradeId();
        List<ProvinceQo> p=null;
        if (gid==1){
         p=   this.countService.getALLProvince(start, end);
        }else if (gid==4){
          p=null;
        }else {
            p=this.countService.getProvinceBypId(gid,p_Id,start,end);
        }

        return p;
    }

}
