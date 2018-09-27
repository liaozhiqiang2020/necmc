package com.sv.mc.pojo;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

/**
 * 代理商实体类
 */
@Entity
@Table(name = "mc_vendor", schema = "mc", catalog = "")
public class VendorEntity {
    /**
     * 主键Id
     */
    private int id;
    /**
     * 删除状态
     */
    private int discardStatus;
    /**
     * 代理商名称
     */
    private String name;
    /**
     * 代理商地址
     */
    private String vendorAddress;
    /**
     * 负责人
     */
    private String principal;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 隶属单位1总部,2分公司
     */
    private int levelFlag;
    /**
     * 上级主键
     */
    private int superiorId;
    /**
     * 用户id
     */
    private Integer userId;

    @Basic
    @Column(name = "superior_id")
    public int getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(int superiorId) {
        this.superiorId = superiorId;
    }

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
    @Column(name="user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
    @Column(name = "discard_status")
    public int getDiscardStatus() {
        return discardStatus;
    }

    public void setDiscardStatus(int discardStatus) {
        this.discardStatus = discardStatus;
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
                discardStatus == that.discardStatus &&
                Objects.equals(name, that.name) &&
                Objects.equals(vendorAddress, that.vendorAddress) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, levelFlag, discardStatus, name, vendorAddress, principal, telephone, email);
    }
}
