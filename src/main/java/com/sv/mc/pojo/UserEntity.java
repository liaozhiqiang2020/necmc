package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 用户表
 *
 * @author 魏帅志
 */
@Entity
@Table(name = "mc_user", schema = "mc", catalog = "")
public class UserEntity {
    /**
     * 主键Id
     */
    private int id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户真实姓名
     */
    private String name;
    /**
     * 密码
     */
    private String authenticationString;
    /**
     * 创建日期
     */
    private Timestamp createDatetime;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String cellphoneNumber;
    /**
     * 固话
     */
    private String fixedPhoneNumber;
    /**
     * 上次登陆时间
     */
    private Timestamp latestLoginDatetime;
    /**
     * 上次登陆id
     */
    private String latestLoginIp;
    /**
     * 状态
     */
    private int status;
    /**
     * 隶属公司Id
     */
    private int pId;
    /**
     * 判断所属公司是那个级别 1总公司,2分公司,3代理商,4场地方
     */
    private int gradeId;

    private Set<RoleEntity> roleEntitySet = new HashSet<>();

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", unique = true, nullable = false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    @Column(name = "authentication_string")
    public String getAuthenticationString() {
        return authenticationString;
    }

    public void setAuthenticationString(String authenticationString) {
        this.authenticationString = authenticationString;
    }

    @Basic
    @Column(name = "create_datetime")
    public Timestamp getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Timestamp createDatetime) {
        this.createDatetime = createDatetime;
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
    @Column(name = "cellphone_number")
    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }


    @Basic
    @Column(name = "fixed_phone_number")
    public String getFixedPhoneNumber() {
        return fixedPhoneNumber;
    }

    public void setFixedPhoneNumber(String fixedPhoneNumber) {
        this.fixedPhoneNumber = fixedPhoneNumber;
    }


    @Basic
    @Column(name = "latest_login_datetime")
    public Timestamp getLatestLoginDatetime() {
        return latestLoginDatetime;
    }

    public void setLatestLoginDatetime(Timestamp latestLoginDatetime) {
        this.latestLoginDatetime = latestLoginDatetime;
    }


    @Basic
    @Column(name = "latest_login_ip")
    public String getLatestLoginIp() {
        return latestLoginIp;
    }

    public void setLatestLoginIp(String latestLoginIp) {
        this.latestLoginIp = latestLoginIp;
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
    @Column(name = "p_id")
    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    @Basic
    @Column(name = "grade_id")
    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "mc_user_role",                       //指定第三张表
            joinColumns = {@JoinColumn(name = "user_id")},             //本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "role_id")})  //另一张表与第三张表的外键的对应关系
    public Set<RoleEntity> getRoleEntitySet() {
        return roleEntitySet;
    }

    public void setRoleEntitySet(Set<RoleEntity> roleEntitySet) {
        this.roleEntitySet = roleEntitySet;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                status == that.status &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(name, that.name) &&
                Objects.equals(authenticationString, that.authenticationString) &&
                Objects.equals(createDatetime, that.createDatetime) &&
                Objects.equals(email, that.email) &&
                Objects.equals(cellphoneNumber, that.cellphoneNumber) &&
                Objects.equals(fixedPhoneNumber, that.fixedPhoneNumber) &&
                Objects.equals(latestLoginDatetime, that.latestLoginDatetime) &&
                Objects.equals(latestLoginIp, that.latestLoginIp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, name, authenticationString, createDatetime, email, cellphoneNumber, fixedPhoneNumber, latestLoginDatetime, latestLoginIp, status);
    }
}
