package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 价格实体类
 * @author 魏帅志
 */
@Entity
@Table(name = "mc_price", schema = "mc", catalog = "")
public class PriceEntity {
    /**
     * 主键Id
     */
    private int id;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 使用时间
     */
    private int useTime;
//  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * 创建时间
     */
    private Timestamp createDateTime;
    /**
     * 状态
     */
    private int status;         //状态
//  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * 价格开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Timestamp startDateTime;
//  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * 价格结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Timestamp endDateTime;
    /**
     * 价格名称
     */
    private String priceName;
    /**
     * 价格备注
     */
    private String description;
    /**
     * 价格对应机器
     */
    private DeviceModelEntity deviceModelEntity;
    /**
     * 一对多用户
     */
    private UserEntity user;
    /**
     * 设备多对多集合
     */
    private List<DeviceEntity> deviceEntities = new ArrayList<>();             //设备多对多集合


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "mc_price_device",                       //指定第三张表
            joinColumns = {@JoinColumn(name = "price_id")},             //本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "device_id")})  //另一张表与第三张表的外键的对应关系
    public List<DeviceEntity> getDeviceEntities() {
        return deviceEntities;
    }

    public void setDeviceEntities(List<DeviceEntity> deviceEntities) {
        this.deviceEntities = deviceEntities;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "use_time")
    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }


    @ManyToOne(cascade = CascadeType.ALL)// 指定多对一关系
    @JoinColumn(name="user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    @ManyToOne(fetch = FetchType.EAGER)// 指定多对一关系
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name="mc_type")
    public DeviceModelEntity getDeviceModelEntity() {
        return deviceModelEntity;
    }

    public void setDeviceModelEntity(DeviceModelEntity deviceModelEntity) {
        this.deviceModelEntity = deviceModelEntity;
    }

    @Basic
    @Column(name = "create_date_time")
    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "price_name")
    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    @Basic
    @Column(name = "start_date_time")
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Basic
    @Column(name = "end_date_time")
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceEntity that = (PriceEntity) o;
        return id == that.id &&
                useTime == that.useTime &&
                status == that.status &&
                Objects.equals(price, that.price) &&
                Objects.equals(createDateTime, that.createDateTime) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime) &&
                Objects.equals(priceName, that.priceName) &&
                Objects.equals(deviceModelEntity, that.deviceModelEntity) &&
                Objects.equals(user, that.user) &&
                Objects.equals(deviceEntities, that.deviceEntities);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, price, useTime, createDateTime, status, startDateTime, endDateTime, priceName, deviceModelEntity, user, deviceEntities);
    }
}
