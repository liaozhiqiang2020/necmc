package com.sv.mc.pojo;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "sys_role", schema = "triangle")
public class sysRoleEntity implements Serializable{
    private int id;
    private Timestamp createDateTime;
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "sys_user_role",                       //指定第三张表
            joinColumns = {@JoinColumn(name = "role_id")},             //本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "user_id")})  //另一张表与第三张表的外键的对应关系
    public Set<sysUserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(Set<sysUserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "sys_permission_role",                       //指定第三张表
            joinColumns = {@JoinColumn(name = "role_id")},             //本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})  //另一张表与第三张表的外键的对应关系
//    @ManyToMany(mappedBy = "roleEntities")
    public Set<sysPermissionEntity> getPermissionEntities() {
        return permissionEntities;
    }

    public void setPermissionEntities(Set<sysPermissionEntity> permissionEntities) {
        this.permissionEntities = permissionEntities;
    }

    private Set<sysUserEntity> userEntities = new HashSet<>();
    private Set<sysPermissionEntity> permissionEntities = new HashSet<>();









    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        sysRoleEntity that = (sysRoleEntity) o;
        return id == that.id &&
                Objects.equals(createDateTime, that.createDateTime) &&
                Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, createDateTime, roleName);
    }
}
