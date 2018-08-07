package com.sv.mc.controller;

import com.sv.mc.pojo.qo.ProvinceQo;
import com.sv.mc.service.CountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class CountController {
    @Resource
    CountService countService;

    /**
     *   根据省ID查询一个省
     * @param pId
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "/findProvinceByPid")
    private ProvinceQo findProvinceById(@RequestParam("pId") int pId, @RequestParam("start") Date start ,@RequestParam("end") Date end){
        ProvinceQo p=   this.countService.findProvinceById(pId,start,end);
        return p;
    }

    /**
     * 根据省ID查询所有市
     */
    @GetMapping(value = "/findCityByPid")
    private List<ProvinceQo> findCTByPId(@RequestParam("pId") int pId, @RequestParam("start") Date start ,@RequestParam("end") Date end){
        List<ProvinceQo> p=   this.countService.findCityByProvinceID(pId,start,end);
        return p;
    }

    /**
     * 查询一个市区
     */
    @GetMapping(value = "/findPlaceByCity")
    private ProvinceQo findCityBycityID(@RequestParam("cId") int cId, @RequestParam("start") Date start ,@RequestParam("end") Date end){
        ProvinceQo p=   this.countService.findCityById(cId,start,end);
        return p;
    }


    /**
     * 根据市ID查询所有场地
     */
    @GetMapping(value = "/findPlaceBycid")
    private List<ProvinceQo> findByplacecId(@RequestParam("cId") int cId, @RequestParam("start") Date start ,@RequestParam("end") Date end){
        List<ProvinceQo> p=   this.countService.findPlaceByCityID(cId, start, end);
        return p;
    }

    /**
     * 查询一个场地 根据场地ID
     */

    @GetMapping(value = "/findonePlace")
    private ProvinceQo getplaceByplaceID(@RequestParam("pId") int pId, @RequestParam("start") Date start ,@RequestParam("end") Date end){
        ProvinceQo p=   this.countService.getONEPlaceById(pId, start, end);
        return p;
    }


    /**
     * 查询所有省
     */
    @GetMapping(value = "/findoneProvince")
    private List<ProvinceQo> getprovinceByplaceID( @RequestParam("start") Date start ,@RequestParam("end") Date end){
        List<ProvinceQo> p=   this.countService.getALLProvince(start, end);
        return p;
    }

}
