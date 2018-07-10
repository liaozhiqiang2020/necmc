package com.sv.mc.controller;

import com.sv.mc.pojo.CityEntity;
import com.sv.mc.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class CityController {
    @Autowired
    private CityService cotyService;

    /**
     * 全部查询
     * @return 返回所有省内容
     */
    @GetMapping(value = "/allCity")
    public @ResponseBody
    List<CityEntity> getAll() {
        return this.cotyService.findAllEntities();
    }
    /**
     * 根据id查询省
     * @return 返回所有省内容
     */
    @RequestMapping(value = "/city",method=RequestMethod.GET)
    public @ResponseBody
    CityEntity getProvinceById(@PathParam("id") int id ) {
        return this.cotyService.findCityById(id);
    }

}
