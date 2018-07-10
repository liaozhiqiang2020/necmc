package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * 实体类层 代理商
 * Author:赵政博
 */
@Entity
@Table(name = "mc_vendor", schema = "mc", catalog = "")
public class VendorEntity {
    private int id;
    private int levelFlag; //判断隶属的单位(1总部,2分公司)
    private int superiorId;//存储上级主键
    private int discardStatus;//事物删除(1事物显示,0事物删除)
    private String vendorName; //代理商名字
    private String vendorAddress;//代理商地址
    private String principal;//负责人
    private String telephone;//电话
    private String email;//邮箱

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "level_flag")
    public int getLevelFlag() {
        return levelFlag;
    }

    public void setLevelFlag(int levelFlag) {
        this.levelFlag = levelFlag;
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
    @Column(name = "discard_status")
    public int getDiscardStatus() {
        return discardStatus;
    }

    public void setDiscardStatus(int discardStatus) {
        this.discardStatus = discardStatus;
    }

    @Basic
    @Column(name = "vendor_name")
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Basic
    @Column(name = "vendor_address")
    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    @Basic
    @Column(name = "principal")
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendorEntity that = (VendorEntity) o;
        return id == that.id &&
                levelFlag == that.levelFlag &&
                superiorId == that.superiorId &&
                discardStatus == that.discardStatus &&
                Objects.equals(vendorName, that.vendorName) &&
                Objects.equals(vendorAddress, that.vendorAddress) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, levelFlag, superiorId, discardStatus, vendorName, vendorAddress, principal, telephone, email);
    }
}
