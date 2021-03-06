package com.sv.mc.pojo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mc_device_status", schema = "mc", catalog = "")
/**
 * 设备状态实体类
 */
public class DeviceStatusEntity {
    /**
     * 主键Id
     */
    private int id;
    /**
     * 状态 0正常 1异常
     */
    private int status;
    /**
     * 创建时间
     */
    private Timestamp createDateTime;
    /**
     * 备注
     */
    private String note;

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
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "create_date_time")
    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceStatusEntity that = (DeviceStatusEntity) o;
        return id == that.id &&
                status == that.status &&
                Objects.equals(createDateTime, that.createDateTime) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, status, createDateTime, note);
    }
}
