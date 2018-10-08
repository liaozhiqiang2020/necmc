package com.sv.mc.controller;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.GatewayEntity;
import com.sv.mc.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 网关控制层
 */
@RestController
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

    /**
     * 跳转到网关管理页面
     * @return 网关页面
     */
    @GetMapping(value = "/gatewayMgr/turnToGatewayMgr")
    public ModelAndView turnToGatewayMgr() {
        return new ModelAndView("./baseInfo/gatewayMgr");
    }


    /**
     * 查询所有网关信息(不分页,json数据)
     * @return 所有网关信息
     */
    @GetMapping(value = "/gatewayMgr/allGateway")
    public @ResponseBody
    String getAllGateway() {
        return this.gatewayService.selectAllGatewayEnties();
    }


    /**
     * 查询所有网关信息(不分页,list)
     * @return 网关信息
     */
    @GetMapping(value = "/gatewayMgr/allGatewayList")
    public @ResponseBody
    List<GatewayEntity> allGatewayList() {
        return this.gatewayService.findAllEntities();
    }

    /**
     * 添加网关
     * @return 添加的网关内容
     */
    @PostMapping(value = "/gatewayMgr/insertGateway")
    public @ResponseBody
    GatewayEntity insertGateway(@RequestBody  GatewayEntity gatewayEntity) {
        gatewayEntity.setStatus(0);
        return this.gatewayService.save(gatewayEntity);
    }

    /**
     * 修改网关信息
     * @param gatewayEntity 修改的网关信息
     * @return 网关信息
     */
    @PostMapping(value = "/gatewayMgr/updateGateway")
    public @ResponseBody
    GatewayEntity updateGateway(@RequestBody GatewayEntity gatewayEntity) {
        return this.gatewayService.updateGatewayInfo(gatewayEntity);
    }

    /**
     * 修改域名端口
     * @param domainName 域名名称
     * @param port 端口
     * @param gatewaySn 编号sn
     */
    @PostMapping(value = "/gatewayMgr/updateGatewayPort")
    public @ResponseBody
    void updateGatewayPort(String domainName, String port,String gatewaySn) {
        try {
            this.gatewayService.updateGatewayPort(domainName, port,gatewaySn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改频道
     * @param channel 频道信息
     * @param gatewaySn 网关sn
     */
    @PostMapping(value = "/gatewayMgr/updateGatewayChannel")
    public @ResponseBody
    void updateGatewayChannel(String channel,String gatewaySn) {
        try {
            this.gatewayService.updateGatewayChannel(channel,gatewaySn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启网关
     * @param gatewaySn sn
     */
    @PostMapping(value = "/gatewayMgr/restartGateway")
    public @ResponseBody
    void restartGateway(String gatewaySn) {
        try {
            this.gatewayService.restartGateway(gatewaySn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
