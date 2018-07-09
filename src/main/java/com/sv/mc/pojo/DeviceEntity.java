package com.sv.mc.pojo;

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

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "maintain_date_time")
    @JsonIgnore
    public Timestamp getMaintainDateTime() {
        return maintainDateTime;
    }

    public void setMaintainDateTime(Timestamp maintainDateTime) {
        this.maintainDateTime = maintainDateTime;
    }

    @Basic
    @Column(name = "latitude")
    @JsonIgnore
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    @JsonIgnore
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


    @Basic
    @Column(name = "mc_status")
    @JsonIgnore
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
    @JsonIgnore
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
                mcStatus == that.mcStatus &&
                Objects.equals(maintainDateTime, that.maintainDateTime) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(mcType, that.mcType) &&
                Objects.equals(mcSn, that.mcSn) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, maintainDateTime, latitude, longitude, mcType,  mcStatus, mcSn, note);
    }
}
