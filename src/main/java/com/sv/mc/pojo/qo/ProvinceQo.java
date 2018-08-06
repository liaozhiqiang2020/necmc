package com.sv.mc.pojo.qo;

import java.util.Objects;

public class ProvinceQo {
    private String name;
    private int userCount;
    private int orderCount;
    private int income;


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

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
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
