package com.sv.mc.pojo;

/**
 * 报表接收页面参数实体
 */
public class ReportParamEntity {
    /**
     * 起始时间
     */
    private String startTime;
    /**
     * 截止时间
     */
    private String endTime;
    /**
     * 省Id
     */
    private int provinceId;
    /**
     * 市id
     */
    private int cityId;
    /**
     * 场地Id
     */
    private int placeId;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
}
