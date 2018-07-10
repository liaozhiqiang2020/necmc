package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * 实体类层 省
 * Author:赵政博
 */
@Entity
@Table(name = "mc_province", schema = "mc", catalog = "")
public class ProvinceEntity {
    private int id;
    private String name; //省名
    private int areaId; //大区id

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
    @Column(name = "area_id")
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvinceEntity that = (ProvinceEntity) o;
        return id == that.id &&
                areaId == that.areaId &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, areaId);
    }
}
