package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mc_business", schema = "mc", catalog = "")
/**
 * 行业一级
 */
public class BusinessEntity {
    /**
     * 主键
     */
    private int id;
    /**
     * 级分类名称
     */
    private String name;
    /**
     * 级分类级别
     */
    private int level;
    /**
     * 逻辑删除 0删除 1使用
     */
    private Integer discardStatus;
    /**
     * 1级id
     */
    private Integer parentId;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "discard_status")
    public Integer getDiscardStatus() {
        return discardStatus;
    }
    public void setDiscardStatus(Integer discardStatus) {
        this.discardStatus = discardStatus;
    }



    @Basic
    @Column(name = "parent_id")
    public void setParentId(Integer parentId) { this.parentId = parentId; }
    public Integer getParentId() { return parentId; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessEntity that = (BusinessEntity) o;
        return id == that.id &&
                level == that.level &&
                Objects.equals(name, that.name) &&
                Objects.equals(discardStatus, that.discardStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, level, discardStatus);
    }
}
