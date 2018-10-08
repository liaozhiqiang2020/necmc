package com.sv.mc.controller;

import com.sv.mc.pojo.AreaEntity;
import com.sv.mc.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 大区控制层
 */
@RestController
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * 全部查询
     * @return 返回所有大区域内容
     */
    @GetMapping(value = "/allArea")
    public @ResponseBody
    List<AreaEntity> getAll() {
        return this.areaService.findAllEntities();
    }
}
