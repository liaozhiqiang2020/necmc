package com.sv.mc.pojo;



import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Objects;


public class ReportViewEntity {

    private String site; //地点
    private String dm_name;// 型号
    private String chairType;// 类型
    private int userNumber;//用户量
    private int payment;//未付款
    private int accountpaid;//已付款
    private int offthestocks;//已完成
    private int canceled;//已取消
    private int orderTotal;//订单总数
    private BigDecimal income;//收入
    private BigDecimal expend;//支出
    private BigDecimal profit;//利润
    private int unitException;//异常

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDm_name() {
        return dm_name;
    }

    public void setDm_name(String dm_name) {
        this.dm_name = dm_name;
    }

    public String getChairType() {
        return chairType;
    }

    public void setChairType(String chairType) {
        this.chairType = chairType;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getAccountpaid() {
        return accountpaid;
    }

    public void setAccountpaid(int accountpaid) {
        this.accountpaid = accountpaid;
    }

    public int getOffthestocks() {
        return offthestocks;
    }

    public void setOffthestocks(int offthestocks) {
        this.offthestocks = offthestocks;
    }

    public int getCanceled() {
        return canceled;
    }

    public void setCanceled(int canceled) {
        this.canceled = canceled;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpend() {
        return expend;
    }

    public void setExpend(BigDecimal expend) {
        this.expend = expend;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public int getUnitException() {
        return unitException;
    }

    public void setUnitException(int unitException) {
        this.unitException = unitException;
    }

    @Override
    public String toString() {
        return "ReportViewEntity{" +
                "site='" + site + '\'' +
                ", dm_name='" + dm_name + '\'' +
                ", chairType='" + chairType + '\'' +
                ", userNumber=" + userNumber +
                ", payment=" + payment +
                ", accountpaid=" + accountpaid +
                ", offthestocks=" + offthestocks +
                ", canceled=" + canceled +
                ", orderTotal=" + orderTotal +
                ", income=" + income +
                ", expend=" + expend +
                ", profit=" + profit +
                ", unitException=" + unitException +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportViewEntity that = (ReportViewEntity) o;
        return Objects.equals(site, that.site) &&
                Objects.equals(dm_name, that.dm_name) &&
                Objects.equals(chairType, that.chairType) &&
                Objects.equals(userNumber, that.userNumber) &&
                Objects.equals(payment, that.payment) &&
                Objects.equals(accountpaid, that.accountpaid) &&
                Objects.equals(offthestocks, that.offthestocks) &&
                Objects.equals(canceled, that.canceled) &&
                Objects.equals(orderTotal, that.orderTotal) &&
                Objects.equals(income, that.income) &&
                Objects.equals(expend, that.expend) &&
                Objects.equals(profit, that.profit) &&
                Objects.equals(unitException, that.unitException);
    }

    @Override
    public int hashCode() {

        return Objects.hash(site, dm_name, chairType, userNumber, payment, accountpaid, offthestocks, canceled, orderTotal, income, expend, profit, unitException);
    }
}