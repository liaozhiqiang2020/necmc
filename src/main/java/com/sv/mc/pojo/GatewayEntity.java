package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 网关管理类
 */
@Entity
@Table(name = "mc_gateway", schema = "mc", catalog = "")
public class GatewayEntity {
    /**
     * 主键Id
     */
    private int id;
    /**
     * 网关通讯ip
     */
    private String ip;
    /**
     * 网关通讯端口
     */
    private String port;
    /**
     * 网关通讯频道
     */
    private String channel;
    /**
     * 网关Sn
     */
    private String gatewaySn;
    /**
     * 设备数
     */
    private Integer deviceCount;
    /**
     * 域名
     */
    private String domainName;
    /**
     * 场地
     */
    private int placeId;
    /**
     * 最后通信时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp lastCorrespondTime;
    /**
     * 当前状态 0不在线 1在线
     */
    private int status; //当前状态
    /**
     * 协议类型 1 老协议 2 新协议
     */
    private int protocolType;

    @Id
    @GeneratedValue
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name="place_id")
    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Basic
    @Column(name="status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name="last_correspond_time")
    public Timestamp getLastCorrespondTime() {
        return lastCorrespondTime;
    }

    public void setLastCorrespondTime(Timestamp lastCorrespondTime) {
        this.lastCorrespondTime = lastCorrespondTime;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "domain_name")
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Basic
    @Column(name = "port")
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Basic
    @Column(name = "channel")
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Basic
    @Column(name = "gateway_sn")
    public String getGatewaySn() {
        return gatewaySn;
    }

    public void setGatewaySn(String gatewaySn) {
        this.gatewaySn = gatewaySn;
    }

    @Basic
    @Column(name = "device_count")
    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }




    @Basic
    @Column(name = "protocol_type")
    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GatewayEntity that = (GatewayEntity) o;
        return id == that.id &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(port, that.port) &&
                Objects.equals(channel, that.channel) &&
                Objects.equals(gatewaySn, that.gatewaySn) &&
                Objects.equals(deviceCount, that.deviceCount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ip, port, channel, gatewaySn, deviceCount);
    }
}
