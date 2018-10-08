package com.sv.mc.controller;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.ContractEntity;
import com.sv.mc.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 合同信息控制层
 */
@RestController
public class ContractController {
    @Autowired
    private ContractService contractService;

    /**
     * 全部查询
     * @return 返回所有合同内容
     */
    @GetMapping(value = "/allContract")
    public @ResponseBody
    List<ContractEntity> getAll() {
        return this.contractService.findAllEntities();
    }
    /**
     * 根据id查询单个合同内容
     * @param id 合同Id
     * @return 单个分公司合同
     */
    @RequestMapping(value = "/contract",method=RequestMethod.GET)
    public @ResponseBody
    ContractEntity getbranchById(@PathParam("id") int id ) {
        return this.contractService.findSignById(id);
    }

    /**
     * 插入一条新合同数据
     * @param sign 合同信息
     * @return 合同结果
     */
    @RequestMapping(value = "/contract/insert",method = RequestMethod.POST)
    public @ResponseBody
    ContractEntity insertBranch(@RequestBody ContractEntity sign){
        return  contractService.insertSign(sign);
    }

    /**
     * 修改签约信息
     * @param map 修改的签约信息
     * @return 修改的签约信息
     */
    @RequestMapping(value = "/contract/updateContract",method = RequestMethod.POST)
    public @ResponseBody
    void updateBranch(@RequestBody Map<String,Object> map){
         this.contractService.updateContract(map);
    }
}
