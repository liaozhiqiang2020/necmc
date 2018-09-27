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
    /**
     * 主键Id
     */
    private int id;
    /**
     * 价格名称
     */
    private String priceName;
    /**
     * 新价格
     */
    private BigDecimal newPrice;
    /**
     * 旧价格
     */
    private BigDecimal oldPrice;
    /**
     * 新使用时间
     */
    private int newUseTime;
    /**
     * 旧使用时间
     */
    private int oldUseTime;
    /**
     * 创建时间
     */
    private Timestamp createDateTime;
    /**
     * 最后操作时间
     */
    private Timestamp editTime;
    /**
     * 状态
     */
    private int status;
    /**
     * 价格开始时间
     */
    private Timestamp startDateTime;
    /**
     * 价格结束时间
     */
    private Timestamp endDateTime;
    /**
     * 一对多用户
     */
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
    @Column(name = "price_name")
    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
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

    @Basic
    @Column(name = "new_price")
    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    @Basic
    @Column(name = "old_price")
    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }


    @Basic
    @Column(name = "old_use_time")
    public int getOldUseTime() {
        return oldUseTime;
    }

    public void setOldUseTime(int oldUseTime) {
        this.oldUseTime = oldUseTime;
    }

    @Basic
    @Column(name = "edit_time")
    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    @Basic
    @Column(name = "new_use_time")
    public int getNewUseTime() {
        return newUseTime;
    }

    public void setNewUseTime(int newUseTime) {
        this.newUseTime = newUseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceHistoryEntity that = (PriceHistoryEntity) o;
        return id == that.id &&
                newUseTime == that.newUseTime &&
                oldUseTime == that.oldUseTime &&
                status == that.status &&
                Objects.equals(newPrice, that.newPrice) &&
                Objects.equals(oldPrice, that.oldPrice) &&
                Objects.equals(createDateTime, that.createDateTime) &&
                Objects.equals(editTime, that.editTime) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, newPrice, oldPrice, newUseTime, oldUseTime, createDateTime, editTime, status, startDateTime, endDateTime, user);
    }
}
