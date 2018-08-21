package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mc_gateway", schema = "mc", catalog = "")
public class GatewayEntity {
    private int id;
    private String ip;
    private String port;
    private String channel;
    private String gatewaySn;
    private Integer deviceCount;
    private String domainName;//域名

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
