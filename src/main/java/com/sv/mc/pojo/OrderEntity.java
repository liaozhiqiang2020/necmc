package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 订单实体类
 * @author: lzq
 * @date: 2018年7月3日
 */
@Entity
@Table(name = "mc_order", schema = "mc", catalog = "")
public class OrderEntity {
    private int id;   //订单id
    private String code;  //订单编号(自己编写后台代码生成规则)
    private String codeWx;  //微信生成的订单编号
    private int status;  //订单状态(0未付款，1已付款(服务中)，2已付款(已完成)，3已取消)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDateTime;  //订单创建时间(自己的订单)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp payDateTime;  //订单支付时间
    private BigDecimal money; //订单金额
    private String description;  //订单描述
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp mcStartDateTime;  //按摩开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp mcEndDateTime;  //按摩结束时间
    private Integer mcStatus;  //按摩椅状态(0为异常，1为正常)
    private Integer deviceId;  //椅子编号
    private Integer promoCodeId;  //优惠码编号
    private Integer wxUserInfoId;  //微信用户编号
    private Integer mcTime;  //按摩时长

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
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "code_wx")
    public String getCodeWx() {
        return codeWx;
    }

    public void setCodeWx(String codeWx) {
        this.codeWx = codeWx;
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
    @Column(name = "create_date_time")
    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Basic
    @Column(name = "pay_date_time")
    public Timestamp getPayDateTime() {
        return payDateTime;
    }

    public void setPayDateTime(Timestamp payDateTime) {
        this.payDateTime = payDateTime;
    }

    @Basic
    @Column(name = "money")
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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
    @Column(name = "mc_start_date_time")
    public Timestamp getMcStartDateTime() {
        return mcStartDateTime;
    }

    public void setMcStartDateTime(Timestamp mcStartDateTime) {
        this.mcStartDateTime = mcStartDateTime;
    }

    @Basic
    @Column(name = "mc_end_date_time")
    public Timestamp getMcEndDateTime() {
        return mcEndDateTime;
    }

    public void setMcEndDateTime(Timestamp mcEndDateTime) {
        this.mcEndDateTime = mcEndDateTime;
    }

    @Basic
    @Column(name = "mc_status")
    public Integer getMcStatus() {
        return mcStatus;
    }

    public void setMcStatus(Integer mcStatus) {
        this.mcStatus = mcStatus;
    }

    @Basic
    @Column(name = "device_Id")
    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "promo_code_id")
    public Integer getPromoCodeId() {
        return promoCodeId;
    }

    public void setPromoCodeId(Integer promoCodeId) {
        this.promoCodeId = promoCodeId;
    }

    @Basic
    @Column(name = "wx_user_info_id")
    public Integer getWxUserInfoId() {
        return wxUserInfoId;
    }

    public void setWxUserInfoId(Integer wxUserInfoId) {
        this.wxUserInfoId = wxUserInfoId;
    }

    @Basic
    @Column(name = "mc_time")
    public Integer getMcTime() {
        return mcTime;
    }

    public void setMcTime(Integer mcTime) {
        this.mcTime = mcTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id == that.id &&
                status == that.status &&
                Objects.equals(code, that.code) &&
                Objects.equals(codeWx, that.codeWx) &&
                Objects.equals(createDateTime, that.createDateTime) &&
                Objects.equals(payDateTime, that.payDateTime) &&
                Objects.equals(money, that.money) &&
                Objects.equals(description, that.description) &&
                Objects.equals(mcStartDateTime, that.mcStartDateTime) &&
                Objects.equals(mcEndDateTime, that.mcEndDateTime) &&
                Objects.equals(mcStatus, that.mcStatus) &&
                Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(promoCodeId, that.promoCodeId) &&
                Objects.equals(wxUserInfoId, that.wxUserInfoId) &&
                Objects.equals(mcTime, that.mcTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code, codeWx, status, createDateTime, payDateTime, money, description, mcStartDateTime, mcEndDateTime, mcStatus, deviceId, promoCodeId, wxUserInfoId, mcTime);
    }
}
