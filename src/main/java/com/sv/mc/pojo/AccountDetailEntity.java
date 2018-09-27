package com.sv.mc.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *  账单详情实体类
 */
@Entity
@Table(name = "mc_account_detail", schema = "mc", catalog = "")
public class AccountDetailEntity {
    private int id;
    /**
     * 关联账目表
     */
    private int accountId;
    /**
     * 账单编号
     */
    private String detailCode;
    /**
     * 账单单据名称
     */
    private String detailName;
    /**
     * 资金
     */
    private BigDecimal capital;
    /**
     * 资金类型 1收入 2支出 3退款
     */
    private int capitalFlag;
    /**
     * 账目时间
     */
    private Timestamp detailDateTime;
    /**
     * 账目来源id
     */
    private int fromId;
    /**
     * 0 设备订单  1 商场订单
     */
    private int from_level;

    @Basic
    @Column(name = "from_id")
    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    @Basic
    @Column(name = "from_level")
    public int getFrom_level() {
        return from_level;
    }

    public void setFrom_level(int from_level) {
        this.from_level = from_level;
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
    @Column(name = "account_id")
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "detail_code")
    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    @Basic
    @Column(name = "detail_name")
    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    @Basic
    @Column(name = "capital")
    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    @Basic
    @Column(name = "capital_flag")
    public int getCapitalFlag() {
        return capitalFlag;
    }

    public void setCapitalFlag(int capitalFlag) {
        this.capitalFlag = capitalFlag;
    }

    @Basic
    @Column(name = "detail_date_time")
    public Timestamp getDetailDateTime() {
        return detailDateTime;
    }

    public void setDetailDateTime(Timestamp detailDateTime) {
        this.detailDateTime = detailDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDetailEntity that = (AccountDetailEntity) o;
        return id == that.id &&
                accountId == that.accountId &&
                capitalFlag == that.capitalFlag &&
                Objects.equals(detailCode, that.detailCode) &&
                Objects.equals(detailName, that.detailName) &&
                Objects.equals(capital, that.capital) &&
                Objects.equals(detailDateTime, that.detailDateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, accountId, detailCode, detailName, capital, capitalFlag, detailDateTime);
    }
}
