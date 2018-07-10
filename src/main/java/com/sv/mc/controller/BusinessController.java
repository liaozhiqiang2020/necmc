package com.sv.mc.controller;

import com.sv.mc.pojo.BusinessEntity;
import com.sv.mc.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    /**
     * 全部查询
     * @return 返回所有行业内容
     */
    @GetMapping(value = "/allBusiness")
    public @ResponseBody
    List<BusinessEntity> getAll() {
        return this.businessService.findAllEntities();
    }
    /**
     * 根据id查询行业
     * @return 返回所有行业内容p
     */
    @RequestMapping(value = "/business",method=RequestMethod.GET)
    public @ResponseBody
    BusinessEntity getBusinessById(@PathParam("id") int id ) {
        return this.businessService.fianBusinessById(id);
    }

    /**
     * 根据id修改数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/update/business",method=RequestMethod.GET)
    public @ResponseBody
    BusinessEntity updateBusinessById(@PathParam("id") int id,BusinessEntity business ) {
        return this.businessService.save(business);
    }

}
