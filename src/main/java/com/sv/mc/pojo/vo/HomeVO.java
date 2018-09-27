package com.sv.mc.pojo.vo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 主页面 数据展示类
 */
public class HomeVO {

    /**
     * 正常设备数
     */
    private int nmoDevice;
    /**
     * 故障设备数
     */
    private int expDevice;
    /**
     * 昨日收入
     */
    private BigDecimal yedInc;
    /**
     * 昨日订单
     */
    private int yedOrder;

    public int getNmoDevice() {
        return nmoDevice;
    }

    public void setNmoDevice(int nmoDevice) {
        this.nmoDevice = nmoDevice;
    }

    public int getExpDevice() {
        return expDevice;
    }

    public void setExpDevice(int expDevice) {
        this.expDevice = expDevice;
    }

    public BigDecimal getYedInc() {
        return yedInc;
    }

    public void setYedInc(BigDecimal yedInc) {
        this.yedInc = yedInc;
    }

    public int getYedOrder() {
        return yedOrder;
    }

    public void setYedOrder(int yedOrder) {
        this.yedOrder = yedOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeVO homeVO = (HomeVO) o;
        return nmoDevice == homeVO.nmoDevice &&
                expDevice == homeVO.expDevice &&
                yedOrder == homeVO.yedOrder &&
                Objects.equals(yedInc, homeVO.yedInc);
    }
    @Override
    public int hashCode() {
        return Objects.hash(nmoDevice, expDevice, yedInc, yedOrder);
    }
}
