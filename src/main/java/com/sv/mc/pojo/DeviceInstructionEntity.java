package com.sv.mc.pojo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mc_device_instruction", schema = "mc", catalog = "")
public class DeviceInstructionEntity {
    private int id;
    private String instructionName;
    private String instruction;
    private Timestamp createDateTime;
    private int status;

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
    @Column(name = "instruction_name")
    public String getInstructionName() {
        return instructionName;
    }

    public void setInstructionName(String instructionName) {
        this.instructionName = instructionName;
    }

    @Basic
    @Column(name = "instruction")
    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
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
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceInstructionEntity that = (DeviceInstructionEntity) o;
        return id == that.id &&
                status == that.status &&
                Objects.equals(instructionName, that.instructionName) &&
                Objects.equals(instruction, that.instruction) &&
                Objects.equals(createDateTime, that.createDateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, instructionName, instruction, createDateTime, status);
    }
}
