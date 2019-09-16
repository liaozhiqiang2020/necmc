package com.sv.mc.service;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.GatewayEntity;

import java.util.List;
import java.util.Map;

/**
 * 网关管理接口
 * @param <T>
 */
public interface GatewayService<T> extends BaseService<T>{
    /**
     * 保存数据
     * @param gatewayEntity 要增加的网关信息
     * @return 增加的网关数据
     */
    GatewayEntity save(GatewayEntity gatewayEntity);

    /**
     * 根据id查询网关信息
     * @param id  主键Id
     * @return 网关信息
     */
    GatewayEntity findGatewayInfoById(int id);

    /**
     * 修改网关信息
     * @param gatewayEntity 修改的信息
     * @return  修改了的信息
     *//**
     * 重启网关
     */
    GatewayEntity updateGatewayInfo(GatewayEntity gatewayEntity);


    /**
     * 修改网关域名端口
     * @param domainName 域名
     * @param port 通讯端口
     * @param gatewaySn sn
     * @throws Exception
     */
    void updateGatewayPort(String domainName, String port, String gatewaySn)throws Exception;


    /**
     * 修改网关频道
     * @param channel 网关通讯频道
     * @param gatewaySn sn
     * @throws Exception
     */
    void updateGatewayChannel(String channel, String gatewaySn)throws Exception;

    /**
     * 重启网关
     * @param gatewaySn sn
     * @throws Exception
     */
    void restartGateway(String gatewaySn)throws Exception;

    /**
     * 根据网关sn查询网关信息
     * @param sn  网关Sn
     * @return 查询到的网关信息
     */
    GatewayEntity selectGateBySn(String sn);


    /**
     * 查询所有网关信息(不分页,json数据)
     * @return  json 格式网关
     */
    String selectAllGatewayEnties();

    /**
     * 根据网关sn查询网关下所有设备
     * @param sn 网关Sn
     * @return json 设备
     *
     */
    List<String> findAllDeviceByGatewayCode(String sn);

    /**
     * 插入一条网关数据
     * @param map json格式网关
     * @return  增加的网关对象
     */
    GatewayEntity insertGateway(Map<String, Object> map);

    /**
     * 修改网关数据
     * @param map  json格式要修改的信息
     * @return 网关数据
     */
    GatewayEntity updateGateway(Map<String, Object> map);

    /**
     * 删除网关
     */
    String deleteGateway(String sn);
}
