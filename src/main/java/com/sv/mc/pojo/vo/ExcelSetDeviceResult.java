package com.sv.mc.pojo.vo;

/**
 * 设备导入结果
 */
public class ExcelSetDeviceResult {
    /**
     * 场地名称
     */
  private  String name;
    /**
     * 设备坐标纬度
     */
  private  String weidu;
    /**
     * 设备坐标经度
     */
  private  String jingdu;
    /**
     * 设备型号
     */
  private  String type;
    /**
     * 设备sn 编号
     */
  private  String  sn;
    /**
     * 备注
     */
  private  String beizhu;
    /**
     * 上级
     */
  private  String supplier;
    /**
     * 设备类型
     */
  private  String deviceType;
    /**
     * 导入的sn
     */
  private  String gatSn;
    /**
     * 结果
     */
  private  String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getWeidu() {
        return weidu;
    }

    public void setWeidu(String weidu) {
        this.weidu = weidu;
    }

    public String getJingdu() {
        return jingdu;
    }

    public void setJingdu(String jingdu) {
        this.jingdu = jingdu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getGatSn() {
        return gatSn;
    }

    public void setGatSn(String gatSn) {
        this.gatSn = gatSn;
    }
}
