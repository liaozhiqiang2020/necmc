package com.sv.mc.pojo;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 价格历史实体类
 * @author 魏帅志
 */
@Entity
@Table(name = "mc_price_history", schema = "mc", catalog = "")
public class PriceHistoryEntity {
    private int id;                     //主键id
    private BigDecimal price;           //价格
    private int useTime;                //使用时间
    private Timestamp createDateTime;   //创建时间
    private Timestamp latestDateTime;   //最后操作时间
    private int status;                 //状态
    private Timestamp startDateTime;    //价格开始时间
    private Timestamp endDateTime;      //价格结束时间

    private UserEntity user;            //一对多用户
//    private Set<DeviceEntity> deviceEntities = new HashSet<>();             //设备多对多集合


//    @ManyToMany(fetch = FetchType.EAGER)
//    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
//    @JoinTable(name = "mc_device_price_history",                       //指定第三张表
//            joinColumns = {@JoinColumn(name = "price_id")},             //本表与中间表的外键对应
//            inverseJoinColumns = {@JoinColumn(name = "device_id")})  //另一张表与第三张表的外键的对应关系
//    public Set<DeviceEntity> getDeviceEntities() {
//        return deviceEntities;
//    }

//    public void setDeviceEntities(Set<DeviceEntity> deviceEntities) {
//        this.deviceEntities = deviceEntities;
//    }

    @ManyToOne// 指定多对一关系
    @JoinColumn(name="user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

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

    @Basic
    @Column(name = "create_date_time")
    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Basic
    @Column(name = "latest_date_time")
    public Timestamp getLatestDateTime() {
        return latestDateTime;
    }

    public void setLatestDateTime(Timestamp latestDateTime) {
        this.latestDateTime = latestDateTime;
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
        PriceHistoryEntity that = (PriceHistoryEntity) o;
        return id == that.id &&
                useTime == that.useTime &&
                status == that.status &&
                Objects.equals(price, that.price) &&
                Objects.equals(createDateTime, that.createDateTime) &&
                Objects.equals(latestDateTime, that.latestDateTime) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, price, useTime, createDateTime, latestDateTime, status, startDateTime, endDateTime);
    }
}
