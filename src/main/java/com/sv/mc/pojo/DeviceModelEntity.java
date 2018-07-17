package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 实体类层 按摩椅类型
 * Author:赵政博
 */
@Entity
@Table(name = "mc_device_model", schema = "mc", catalog = "")
public class DeviceModelEntity {
    private int id;
    private String name;     //按摩椅名称
    private String model;    //按摩椅型号
    private int deviceType;     //按摩椅型号分类
    private List<PriceEntity> priceEntities = new ArrayList<>();

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "device_type")
    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceModelEntity")
    public List<PriceEntity> getPriceEntities() {
        return priceEntities;
    }

    public void setPriceEntities(List<PriceEntity> priceEntities) {
        this.priceEntities = priceEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceModelEntity that = (DeviceModelEntity) o;
        return id == that.id &&
                deviceType == that.deviceType &&
                Objects.equals(name, that.name) &&
                Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, model, deviceType);
    }
}
