package com.sv.mc.pojo.vo;

import java.math.BigDecimal;

public class ExcelSetPriceResult {

  private String id;   //用户输入的设备ID
  private BigDecimal price; //用户输入的价格
  private int time;  //时长
  private String msg; //结果

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
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
