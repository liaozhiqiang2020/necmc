package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

/**
 * 权限表实体类
 */
@Entity
@Table(name = "mc_permission", schema = "mc", catalog = "")
public class PermissionEntity {
    /**
     * 主键Id
     */
    @Id
    private int id;
    /**
     * 权限名称
     */
    private String permissionsName;
    /**
     * 当前权限可以访问的url
     */
    private String url;
    /**
     * 具体权限描述
     */
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "mc_role_permission",                       //指定第三张表
            joinColumns = {@JoinColumn(name = "permission_id")},             //本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "role_id")})  //另一张表与第三张表的外键的对应关系
    private List<RoleEntity> roleEntities = new ArrayList<>();      //角色集合

    @Basic
    @GeneratedValue
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "permissions_name",unique =true, nullable =false)
    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }

    @Basic
    @Column(name = "url", nullable =false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public List<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public void setRoleEntities(List<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    @Basic
    @Column(name = "description", nullable =false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionEntity that = (PermissionEntity) o;
        return id == that.id &&
                Objects.equals(permissionsName, that.permissionsName) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, permissionsName, url);
    }
}
