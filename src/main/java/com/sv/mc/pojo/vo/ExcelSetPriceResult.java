package com.sv.mc.pojo.vo;

import java.math.BigDecimal;

/**
 * 价格导入结果对象
 */
public class ExcelSetPriceResult {
    /**
     * 用户输入的设备Id
     */
  private String id;
    /**
     * 用户输入的价格
     */
  private BigDecimal price;
    /**
     * 用户输入的价格名称
     */
    private String priceName;
    /**
     * 绑定的时长
     */
  private int time;
    /**
     * 绑定的结果
     */
    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
