package com.sv.mc.pojo;

<<<<<<< HEAD
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 实体类层 设备
 * Author:赵政博
 */
@Entity
@Table(name = "mc_device", schema = "mc", catalog = "")
public class DeviceEntity {
    private int id;
    private int placeId;                    //场地id
    private Timestamp maintainDateTime;     //维修时间
    private BigDecimal latitude;            //按摩椅纬度
    private BigDecimal longitude;           //按摩椅经度
    private int mcType;                     //按摩椅型号
    private int mcStatus;                   //按摩椅状态(0可用,1使用中,2维修中)
    private String mcSn;                    //按摩椅编号
    private String note;                    //备注
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "mc_device", schema = "mc")
public class DeviceEntity {
    private int id;
    private Timestamp maintainDateTime;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int mcType;
    private int mcStatus;
    private String mcSn;
    private String note;


    private Set<PriceEntity> priceEntities = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "mc_price_device",                       //指定第三张表
            joinColumns = {@JoinColumn(name = "device_id")},             //本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "price_id")})  //另一张表与第三张表的外键的对应关系
    public Set<PriceEntity> getPriceEntities() {
        return priceEntities;
    }

    public void setPriceEntities(Set<PriceEntity> priceEntities) {
        this.priceEntities = priceEntities;
    }
>>>>>>> origin/master

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
<<<<<<< HEAD
    @Column(name = "place_id")
    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Basic
    @Column(name = "maintain_date_time")
=======
    @Column(name = "maintain_date_time")
    @JsonIgnore
>>>>>>> origin/master
    public Timestamp getMaintainDateTime() {
        return maintainDateTime;
    }

    public void setMaintainDateTime(Timestamp maintainDateTime) {
        this.maintainDateTime = maintainDateTime;
    }

    @Basic
    @Column(name = "latitude")
<<<<<<< HEAD
=======
    @JsonIgnore
>>>>>>> origin/master
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
<<<<<<< HEAD
=======
    @JsonIgnore
>>>>>>> origin/master
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "mc_type")
    public int getMcType() {
        return mcType;
    }

    public void setMcType(int mcType) {
        this.mcType = mcType;
    }

<<<<<<< HEAD
    @Basic
    @Column(name = "mc_status")
=======

    @Basic
    @Column(name = "mc_status")
    @JsonIgnore
>>>>>>> origin/master
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
<<<<<<< HEAD
=======
    @JsonIgnore
>>>>>>> origin/master
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceEntity that = (DeviceEntity) o;
        return id == that.id &&
<<<<<<< HEAD
                placeId == that.placeId &&
                mcType == that.mcType &&
=======
>>>>>>> origin/master
                mcStatus == that.mcStatus &&
                Objects.equals(maintainDateTime, that.maintainDateTime) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
<<<<<<< HEAD
=======
                Objects.equals(mcType, that.mcType) &&
>>>>>>> origin/master
                Objects.equals(mcSn, that.mcSn) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {

<<<<<<< HEAD
        return Objects.hash(id, placeId, maintainDateTime, latitude, longitude, mcType, mcStatus, mcSn, note);
=======
        return Objects.hash(id, maintainDateTime, latitude, longitude, mcType,  mcStatus, mcSn, note);
>>>>>>> origin/master
    }
}
