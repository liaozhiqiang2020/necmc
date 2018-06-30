package com.sv.triangle.pojo;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "sys_user", schema = "triangle")
public class UserEntity implements Serializable{
    private int id;
    private String userName;
    private String authenticationString;
    private Timestamp createDatetime;
    private String email;
    private String cellphoneNumber;
    private String fixedPhoneNumber;
    private Timestamp latestLoginDatetime;
    private Timestamp updateDatetime;
    private String qq;
    private String latestLoginIp;

    private Collection<RoleEntity> roleEntities = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "sys_user_role",                       //指定第三张表
            joinColumns = {@JoinColumn(name = "user_id")},             //本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "role_id")})  //另一张表与第三张表的外键的对应关系
    public Collection<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public void setRoleEntities(Collection<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 30)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "authentication_string", nullable = false, length = 64)
    public String getAuthenticationString() {
        return authenticationString;
    }

    public void setAuthenticationString(String authenticationString) {
        this.authenticationString = authenticationString;
    }

    @Basic
    @Column(name = "create_datetime", nullable = false)
    public Timestamp getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Timestamp createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "cellphone_number", nullable = true, length = 50)
    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    @Basic
    @Column(name = "fixed_phone_number", nullable = true, length = 45)
    public String getFixedPhoneNumber() {
        return fixedPhoneNumber;
    }

    public void setFixedPhoneNumber(String fixedPhoneNumber) {
        this.fixedPhoneNumber = fixedPhoneNumber;
    }

    @Basic
    @Column(name = "latest_login_datetime", nullable = false)
    public Timestamp getLatestLoginDatetime() {
        return latestLoginDatetime;
    }

    public void setLatestLoginDatetime(Timestamp latestLoginDatetime) {
        this.latestLoginDatetime = latestLoginDatetime;
    }

    @Basic
    @Column(name = "update_datetime", nullable = false)
    public Timestamp getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    @Basic
    @Column(name = "qq", nullable = true, length = 20)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Basic
    @Column(name = "latest_login_ip", nullable = true, length = 30)
    public String getLatestLoginIp() {
        return latestLoginIp;
    }

    public void setLatestLoginIp(String latestLoginIp) {
        this.latestLoginIp = latestLoginIp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(authenticationString, that.authenticationString) &&
                Objects.equals(createDatetime, that.createDatetime) &&
                Objects.equals(email, that.email) &&
                Objects.equals(cellphoneNumber, that.cellphoneNumber) &&
                Objects.equals(fixedPhoneNumber, that.fixedPhoneNumber) &&
                Objects.equals(latestLoginDatetime, that.latestLoginDatetime) &&
                Objects.equals(updateDatetime, that.updateDatetime) &&
                Objects.equals(qq, that.qq) &&
                Objects.equals(latestLoginIp, that.latestLoginIp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, authenticationString, createDatetime, email, cellphoneNumber, fixedPhoneNumber, latestLoginDatetime, updateDatetime, qq, latestLoginIp);
    }

}
