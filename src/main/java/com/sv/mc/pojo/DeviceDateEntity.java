package com.sv.mc.pojo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 实体类层 设备入库
 * Author:赵政博
 */
@Entity
@Table(name = "mc_device_date", schema = "mc", catalog = "")
public class
DeviceDateEntity {
    /**
     * 主键Id
     */
    private int id;
    /**
     * 设备写入系统时间
     */
    private Timestamp startDate;
    /**
     * 设备最后一次使用时间
     */
    private Timestamp endDate;
    /**
     * 设备进入场地时间
     */
    private Timestamp entrancePlaceDate;
    /**
     * 设备撤出场地时间
     */
    private Timestamp withdrawPlaceDate;
    /**
     * 绑定设备Id
     */
    private int deviceId;
    /**
     * 绑定场地id
     */
    private int placeId;

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
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "entrance_place_date")
    public Timestamp getEntrancePlaceDate() {
        return entrancePlaceDate;
    }

    public void setEntrancePlaceDate(Timestamp entrancePlaceDate) {
        this.entrancePlaceDate = entrancePlaceDate;
    }

    @Basic
    @Column(name = "withdraw_place_date")
    public Timestamp getWithdrawPlaceDate() {
        return withdrawPlaceDate;
    }

    public void setWithdrawPlaceDate(Timestamp withdrawPlaceDate) {
        this.withdrawPlaceDate = withdrawPlaceDate;
    }

    @Basic
    @Column(name = "device_id")
    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "place_id")
    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDateEntity that = (DeviceDateEntity) o;
        return id == that.id &&
                deviceId == that.deviceId &&
                placeId == that.placeId &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(entrancePlaceDate, that.entrancePlaceDate) &&
                Objects.equals(withdrawPlaceDate, that.withdrawPlaceDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, startDate, endDate, entrancePlaceDate, withdrawPlaceDate, deviceId, placeId);
    }
}
