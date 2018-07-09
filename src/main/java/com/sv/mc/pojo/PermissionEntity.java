package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mc_permission", schema = "mc", catalog = "")
public class PermissionEntity {
    @Id
    private int id;
    private String permissionsName;
    private String url;

    @Basic
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "permissions_name")
    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
