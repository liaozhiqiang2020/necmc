package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * 实体类层 生产厂家
 * Author:赵政博
 */
@Entity
@Table(name = "mc_supplier", schema = "mc", catalog = "")
public class SupplierEntity {
    private int id;
    /**
     * 生产厂家名称
     */
    private String supplierName;
    /**
     * 生产厂家地址
     */
    private String supplierAddress;
    /**
     * 联系人
     */
    private String principal;
    /**
     * 联系地址
     */
    private Integer telephone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 按摩椅型号
     */
    private String model;
    /**
     * 按摩椅型号分类
     */
    private int modelType;//按摩椅型号分类

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
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Basic
    @Column(name = "supplier_address")
    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
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
    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
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

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "model_type")
    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplierEntity that = (SupplierEntity) o;
        return id == that.id &&
                modelType == that.modelType &&
                Objects.equals(supplierName, that.supplierName) &&
                Objects.equals(supplierAddress, that.supplierAddress) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(email, that.email) &&
                Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, supplierName, supplierAddress, principal, telephone, email, model, modelType);
    }
}
