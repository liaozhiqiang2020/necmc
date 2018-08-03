package com.sv.mc.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 实体类层 合同
 * Author:赵政博
 */
@Entity
@Table(name = "mc_contract", schema = "mc", catalog = "")
public class ContractEntity {
    private int id;
    private int owner;           //甲方
    private int second;          //乙方
    private Timestamp effectDateTime;//签约时间
    private Timestamp startDateTime; //开始时间
    private Timestamp endDeteTime;   //结束时间
    private String contractCode;     //合同编号
    private int earningsRatio;       //收益比例
    private BigDecimal minimum;     //保底（最低是多少钱，低于这个这个标准没收益）
    private BigDecimal adminiFee;   //管理费（基础收益扣除合同额的百分之多少，相当于管理费）
    private int flag;
    private int useFlag;
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
//
//    @Basic
//    @Column(name = "owner")
//    public String getOwner() {
//        return owner;
//    }
//
//    public void setOwner(String owner) {
//        this.owner = owner;
//    }
//
//    @Basic
//    @Column(name = "second")
//    public String getSecond() {
//        return second;
//    }
//
//    public void setSecond(String second) {
//        this.second = second;
//    }

    @Basic
    @Column(name = "owner")
    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "second")
    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
    @Basic
    @Column(name = "flag")
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
    @Basic
    @Column(name = "use_flag")
    public int getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(int useFlag) {
        this.useFlag = useFlag;
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
    @Column(name = "effect_date_time")
    public Timestamp getEffectDateTime() {
        return effectDateTime;
    }

    public void setEffectDateTime(Timestamp effectDateTime) {
        this.effectDateTime = effectDateTime;
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
    @Column(name = "end_dete_time")
    public Timestamp getEndDeteTime() {
        return endDeteTime;
    }

    public void setEndDeteTime(Timestamp endDeteTime) {
        this.endDeteTime = endDeteTime;
    }

    @Basic
    @Column(name = "contract_code")
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    @Basic
    @Column(name = "earnings_ratio")
    public int getEarningsRatio() {
        return earningsRatio;
    }

    public void setEarningsRatio(int earningsRatio) {
        this.earningsRatio = earningsRatio;
    }

    @Basic
    @Column(name = "minimum")
    public BigDecimal getMinimum() {
        return minimum;
    }

    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    @Basic
    @Column(name = "admini_fee")
    public BigDecimal getAdminiFee() {
        return adminiFee;
    }

    public void setAdminiFee(BigDecimal adminiFee) {
        this.adminiFee = adminiFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractEntity that = (ContractEntity) o;
        return id == that.id &&
                earningsRatio == that.earningsRatio &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(second, that.second) &&
                Objects.equals(effectDateTime, that.effectDateTime) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDeteTime, that.endDeteTime) &&
                Objects.equals(contractCode, that.contractCode) &&
                Objects.equals(minimum, that.minimum) &&
                Objects.equals(adminiFee, that.adminiFee);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, owner, second, effectDateTime, startDateTime, endDeteTime, contractCode, earningsRatio, minimum, adminiFee);
    }
}
