package com.sv.mc.controller;

import com.sv.mc.pojo.AreaEntity;
import com.sv.mc.pojo.BusinessEntity;
import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.service.AreaService;
import com.sv.mc.service.BusinessService;
import com.sv.mc.service.ProvinceService;
import com.sv.mc.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 行业分类管理控制层
 */
@RestController
public class RegionController {
    @Autowired
    private RegionService regionService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ProvinceService provinceService;

    /**
     * 跳转到行业分类管理页面
     *
     * @return 行业分类view对象
     * @auther wangyuchen
     * @date 2018/7/17
     */
    @GetMapping(value = "/turnToregion")
    public ModelAndView turnToBussinessMgr() {
        return new ModelAndView("./baseInfo/region");
    }

    /**
     * 响应大区数据
     * @return 大区数据集合
     */
    @GetMapping(value = "/bussinessMgr/getRegion")
    public @ResponseBody
    List<AreaEntity> getArea() {
        return regionService.getRegionOne();
    }

    /**
     * 响应省级数据
     *
     * @param id 省id
     * @return 省数据
     */
    @GetMapping(value = "/bussinessMgr/getProvince")
    public @ResponseBody
    List<ProvinceEntity> getProvince(@Param("id") int id) {
        return regionService.getRegionPrivince(id);
    }

    /**
     * 响应市级数据
     * @param id 市Id
     * @return 市数据
     */
    @GetMapping(value = "/bussinessMgr/getCity")
    public @ResponseBody
    List<CityEntity> getCity(@Param("id") int id) {
        return regionService.getRegionityCity(id);
    }

    /**
     * 修改大区
     * @param area 大区信息
     * @return 修改大区信息
     */
    @PostMapping(value = "/bussinessMgr/updateArea")
    public @ResponseBody
    AreaEntity getArea(@RequestBody AreaEntity area) {
        return this.areaService.updateArea(area);
    }

    /**
     * 增加大区
     * @param area 大区增加的数据
     * @return 大区结果对象
     */
    @PostMapping(value = "/bussinessMgr/createArea")
    public @ResponseBody
    AreaEntity createArea(@RequestBody AreaEntity area) {
        return this.areaService.createArea(area);
    }


    /**
     * 删除大区
     * @param area 删除的大区信息
     */
    @PostMapping(value = "/bussinessMgr/deleteArea")
    public @ResponseBody
    void Area(@RequestBody AreaEntity area) {
        this.areaService.removeArea(area);
    }


/**
 * 显示所有省
 */

  @GetMapping("/bussinessMgr/allProvince")
 public @ResponseBody
    List<ProvinceEntity> fillProvince(){
      return this.provinceService.selectProvince();
  }

    /**
     * 删除省
     * @param provinceEntity
     */
    @PostMapping("/bussinessMgr/removeProvince")
   public  @ResponseBody void  removeProvince(@RequestBody ProvinceEntity provinceEntity){
    this.provinceService.removeProvince(provinceEntity);
   }

    /**
     * 增加省
     * @param provinceEntity
     * @return
     */
    @PostMapping("/bussinessMgr/createProvince")
    public  @ResponseBody ProvinceEntity  createProvince(@RequestBody ProvinceEntity provinceEntity){
      return  this.provinceService.createProvince(provinceEntity);
    }






}
