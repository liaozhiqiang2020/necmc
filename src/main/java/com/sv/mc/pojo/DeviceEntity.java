package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mc_device", schema = "mc", catalog = "")
public class DeviceEntity {
    private int id;
    private int placeId;                    //场地id
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp maintainDateTime;     //维修时间
    private BigDecimal latitude;            //按摩椅纬度
    private BigDecimal longitude;           //按摩椅经度
    private int mcType;                     //按摩椅型号
    private int mcStatus;                   //按摩椅状态(0可用,1使用中,2维修中)
    private String mcSn;                    //按摩椅编号
    private String note;                    //备注
    private Integer discardStatus;

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "place_id")
    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
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
    @Column(name = "mc_type")
    public int getMcType() {
        return mcType;
    }

    public void setMcType(int mcType) {
        this.mcType = mcType;
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
                placeId == that.placeId &&
                mcType == that.mcType &&
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

        return Objects.hash(id, placeId, maintainDateTime, latitude, longitude, mcType, mcStatus, mcSn, note, discardStatus);
    }
}
