package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "mc_role", schema = "mc", catalog = "")
public class RoleEntity {
    private int id;                             //角色id
    private Timestamp createDateTime;           //创建时间
    private String roleName;                    //角色名
    private String description;                 //角色权限描述
    private Set<UserEntity> userEntitySet = new HashSet<>();
    private Set<PermissionEntity> permissionEntityHashSet = new HashSet<>();

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
    @Column(name = "create_date_time")
    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Basic
    @Column(name = "role_name",unique =true, nullable =false)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    @Basic
    @Column(name = "description", nullable =false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "mc_user_role",                       //指定第三张表
            joinColumns = {@JoinColumn(name = "role_id")},             //本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "user_id")})  //另一张表与第三张表的外键的对应关系
    public Set<UserEntity> getUserEntitySet() {
        return userEntitySet;
    }

    public void setUserEntitySet(Set<UserEntity> userEntitySet) {
        this.userEntitySet = userEntitySet;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "mc_role_permission",                       //指定第三张表
            joinColumns = {@JoinColumn(name = "role_id")},             //本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})  //另一张表与第三张表的外键的对应关系
    public Set<PermissionEntity> getPermissionEntityHashSet() {
        return permissionEntityHashSet;
    }

    public void setPermissionEntityHashSet(Set<PermissionEntity> permissionEntityHashSet) {
        this.permissionEntityHashSet = permissionEntityHashSet;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return id == that.id &&
                Objects.equals(createDateTime, that.createDateTime) &&
                Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, createDateTime, roleName);
    }
}
