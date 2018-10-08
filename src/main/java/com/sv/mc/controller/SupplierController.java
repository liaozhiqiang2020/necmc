package com.sv.mc.controller;

import com.sv.mc.pojo.SupplierEntity;
import com.sv.mc.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 供应商 控制层
 */
@RestController
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    /**
     * 全部查询
     * @return 返回所有供应商
     */
    @GetMapping(value = "/supplierMgr/allSupplier")
    public @ResponseBody
    List<SupplierEntity> getAll() {
        return this.supplierService.findAllEntities();
    }
    /**
     * 根据id查询单个合同内容
     * @param id 合同id
     * @return 单个分公司合同
     */
    @RequestMapping(value = "/supplierMgr/supplier",method=RequestMethod.GET)
    public @ResponseBody
    SupplierEntity getbranchById(@PathParam("id") int id ) {
        return this.supplierService.findSupplierById(id);
    }

    /**
     * 插入一条新合同数据
     * @param supplier 供应商信息
     * @return 合同信息
     */
    @RequestMapping(value = "/supplierMgr/supplier/insert",method = RequestMethod.POST)
    public @ResponseBody
    SupplierEntity insertBranch(@RequestBody SupplierEntity supplier){
        return  supplierService.insertSupplier(supplier);
    }

}
