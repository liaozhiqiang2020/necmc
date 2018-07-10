package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * 实体类层 市
 * Author:赵政博
 */
@Entity
@Table(name = "mc_city", schema = "mc", catalog = "")
public class CityEntity {
    private int id;
    private int provinceId; //省id
    private String name;    //市名字

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "province_id")
    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity that = (CityEntity) o;
        return id == that.id &&
                provinceId == that.provinceId &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, provinceId, name);
    }
}
