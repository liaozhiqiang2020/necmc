package com.sv.mc.service;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.GatewayEntity;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据网关sn查询网关信息
     * @param sn
     * @return
     */
    GatewayEntity selectGateBySn(String sn);

    /**
     * 查询所有网关信息(不分页,json数据)
     */
    String selectAllGatewayEnties();

    /**
     * 根据网关sn查询网关下所有设备
     * @return
     */
    List<String> findAllDeviceByGatewayCode(String sn);

    /**
     * 插入一条网关数据
     * @param map
     * @return
     */
    GatewayEntity insertGateway(Map<String,Object> map);

    /**
     * 修改网关数据
     * @param map
     * @return
     */
    GatewayEntity updateGateway(Map<String,Object> map);

}
