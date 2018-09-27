package com.sv.mc.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *  账单实体类
 */
@Entity
@Table(name = "mc_account", schema = "mc", catalog = "")
public class AccountEntity {
    /**
     * 主键Id
     */
    private int id;
    /**
     * 场地Id
     */
    private int placeId;
    /**
     * 账单状态 0废弃  1在用
     */
    private Integer accountStatus;
    /**
     * 账单单位 （支付宝。微信。刷卡）
     */
    private String name;
    /**
     * 账单总收入
     */
    private BigDecimal generalIncome;
    /**
     * 账单总支出
     */
    private BigDecimal totalExpenditure;
    /**
     * 账单总利润
     */
    private BigDecimal profit;
    /**
     * 上级Id
     */
    private int superiorId;//上级id
    /**
     * 判断该账单单位(1总部,2分公司,3代理商)
     */
    private int typeFlag;

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
    @Column(name = "place_id")
    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Basic
    @Column(name = "account_status")
    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "general_income")
    public BigDecimal getGeneralIncome() {
        return generalIncome;
    }

    public void setGeneralIncome(BigDecimal generalIncome) {
        this.generalIncome = generalIncome;
    }

    @Basic
    @Column(name = "total_expenditure")
    public BigDecimal getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(BigDecimal totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    @Basic
    @Column(name = "profit")
    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    @Basic
    @Column(name = "superior_id")
    public int getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(int superiorId) {
        this.superiorId = superiorId;
    }

    @Basic
    @Column(name = "type_flag")
    public int getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(int typeFlag) {
        this.typeFlag = typeFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return id == that.id &&
                placeId == that.placeId &&
                superiorId == that.superiorId &&
                typeFlag == that.typeFlag &&
                Objects.equals(accountStatus, that.accountStatus) &&
                Objects.equals(name, that.name) &&
                Objects.equals(generalIncome, that.generalIncome) &&
                Objects.equals(totalExpenditure, that.totalExpenditure) &&
                Objects.equals(profit, that.profit);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, placeId, accountStatus, name, generalIncome, totalExpenditure, profit, superiorId, typeFlag);
    }
}
