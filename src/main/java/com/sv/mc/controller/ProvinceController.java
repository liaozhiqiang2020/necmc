package com.sv.mc.controller;

import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 省数据控制层
 */
@RestController
public class ProvinceController {
    @Autowired
    private ProvinceService ProvinceService;

    /**
     * 全部查询
     * @return 返回所有省内容
     */
    @GetMapping(value = "/allProvince")
    public @ResponseBody
    List<ProvinceEntity> getAll() {
        return this.ProvinceService.findAllEntities();
    }
    /**
     * 根据id查询省
     * @param id 省Id
     * @return 返回所有省内容
     */
    @RequestMapping(value = "/Province",method=RequestMethod.GET)
    public @ResponseBody
    ProvinceEntity getProvinceById(@PathParam("id") int id ) {
        return this.ProvinceService.findProvinceById(id);
    }

}
