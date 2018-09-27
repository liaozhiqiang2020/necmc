package com.sv.mc.pojo.qo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 登陆页面展示类对象
 */
public class ProvinceQo {
    /**
     * 地区名称
     */
    private String name;
    /**
     * 用户数
     */
    private int userCount;
    /**
     * 订单数
     */
    private int orderCount;
    /**
     * 收入
     */
    private BigDecimal income;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvinceQo that = (ProvinceQo) o;
        return userCount == that.userCount &&
                orderCount == that.orderCount &&
                income == that.income &&
                Objects.equals(name, that.name) ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, userCount, orderCount, income);
    }
}
