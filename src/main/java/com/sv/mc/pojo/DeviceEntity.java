package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mc_device", schema = "mc", catalog = "")
public class DeviceEntity {
    private int id;//资产编码

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp maintainDateTime; //维修时间
    private BigDecimal latitude;//按摩椅纬度
    private BigDecimal longitude; //按摩椅经度
    private int mcStatus;//按摩椅状态(0可用,1使用中,2维修中)
    private String mcSn;//按摩椅SN
    private String loraId;//按摩椅模块编号
    private String note;//备注
    private int strength;//按摩强度(0弱，1中，2强)
    private Integer discardStatus;
    private DeviceModelEntity deviceModelEntity; //按摩椅类型
    private PlaceEntity placeEntity;            //场地
    private SupplierEntity supplierEntity;  //供应商
    private List<PriceEntity> priceEntities = new ArrayList<>();     //价格集合

    @Id
    @GeneratedValue
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(name = "mc_price_device",                       //指定第三张表
            joinColumns = {@JoinColumn(name = "device_id")},             //本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "price_id")})  //另一张表与第三张表的外键的对应关系
    @JsonIgnore
    public List<PriceEntity> getPriceEntities() {
        return priceEntities;
    }

    public void setPriceEntities(List<PriceEntity> priceEntities) {
        this.priceEntities = priceEntities;
    }


    @Basic
    @Column(name = "strength")
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @ManyToOne
    @JoinColumn(name="mc_type")
    public DeviceModelEntity getDeviceModelEntity() {
        return deviceModelEntity;
    }

    public void setDeviceModelEntity(DeviceModelEntity deviceModelEntity) {
        this.deviceModelEntity = deviceModelEntity;
    }

    @ManyToOne
    @JoinColumn(name="supplier_id")
    public SupplierEntity getSupplierEntity() {
        return supplierEntity;
    }

    public void setSupplierEntity(SupplierEntity supplierEntity) {
        this.supplierEntity = supplierEntity;
    }

    @ManyToOne
    @JoinColumn(name="place_id")
    public PlaceEntity getPlaceEntity() {
        return placeEntity;
    }

    public void setPlaceEntity(PlaceEntity placeEntity) {
        this.placeEntity = placeEntity;
    }

    @Basic
    @Column(name = "loraId")
    public String getLoraId() {
        return loraId;
    }

    public void setLoraId(String loraId) {
        this.loraId = loraId;
    }

    @Basic
    @Column(name = "maintain_date_time")
    public Timestamp getMaintainDateTime() {
        return maintainDateTime;
    }

    public void setMaintainDateTime(Timestamp maintainDateTime) {
        this.maintainDateTime = maintainDateTime;
    }

    @Basic
    @Column(name = "latitude")
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }


    @Basic
    @Column(name = "mc_status")
    public int getMcStatus() {
        return mcStatus;
    }

    public void setMcStatus(int mcStatus) {
        this.mcStatus = mcStatus;
    }

    @Basic
    @Column(name = "mc_sn")
    public String getMcSn() {
        return mcSn;
    }

    public void setMcSn(String mcSn) {
        this.mcSn = mcSn;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "discard_status")
    public Integer getDiscardStatus() {
        return discardStatus;
    }

    public void setDiscardStatus(Integer discardStatus) {
        this.discardStatus = discardStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceEntity that = (DeviceEntity) o;
        return id == that.id &&
                mcStatus == that.mcStatus &&
                Objects.equals(maintainDateTime, that.maintainDateTime) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(mcSn, that.mcSn) &&
                Objects.equals(note, that.note) &&
                Objects.equals(discardStatus, that.discardStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, maintainDateTime, latitude, longitude, mcStatus, mcSn, note, discardStatus);
    }


}
