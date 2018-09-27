package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 实体类层 大区
 * Author:赵政博
 */
@Entity
@Table(name = "mc_area", schema = "mc", catalog = "")
public class AreaEntity {
    private int id;
    /**
     * 大区名称
     */
    private String name;
    /**
     * 大区状态 0隐藏 1显示
     */
    private int areaState;

    private List<ProvinceEntity> province=new ArrayList<ProvinceEntity>();//有多少个省
    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="area_id")
    public List<ProvinceEntity> getProvince() {
        return province;
    }

    public void setProvince(List<ProvinceEntity> provicce) {
        this.province = provicce;
    }
*/
    @Id
    @GeneratedValue()//自增长
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
    @Column(name = "area_state")
    public int getAreaState() {
        return areaState;
    }

    public void setAreaState(int areaState) {
        this.areaState = areaState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaEntity that = (AreaEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
