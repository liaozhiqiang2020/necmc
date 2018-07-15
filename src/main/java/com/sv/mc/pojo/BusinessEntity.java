package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mc_business", schema = "mc", catalog = "")
public class BusinessEntity {
    private int id;
    private String name;
    private int level;
    private Integer discardStatus;

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
    @Column(name = "level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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
