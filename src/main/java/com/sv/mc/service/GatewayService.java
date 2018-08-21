package com.sv.mc.service;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.GatewayEntity;

import java.util.List;

/**
 * 网管管理接口
 * @param <T>
 */
public interface GatewayService<T> extends BaseService<T>{
    /**
     * 保存数据
     * @param gatewayEntity
     * @return
     */
    GatewayEntity save(GatewayEntity gatewayEntity);

    /**
     * 根据id查询网关信息
     * @param id
     * @return
     */
    GatewayEntity findGatewayInfoById(int id);

    /**
     * 修改网关信息
     * @param gatewayEntity
     * @return
     *//**
     * 重启网关
     */
    GatewayEntity updateGatewayInfo(GatewayEntity gatewayEntity);

    /**
     * 修改网关域名端口
     */
    void updateGatewayPort(String domainName,String port)throws Exception;

    /**
     * 修改网关频道
     */
    void updateGatewayChannel(String channel)throws Exception;


    void restartGateway()throws Exception;
}
