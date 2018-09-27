package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * 优惠码实体类
 * @author: lzq
 * @date: 2018年7月3日
 */
@Entity
@Table(name = "mc_promo_code", schema = "mc", catalog = "")
public class PromoCodeEntity {
    /**
     * 优惠码Id
     */
    private int id;
    /**
     * 优惠码状态(0:已使用,1未使用)
     */
    private Integer status;  //优惠码状态(0:已使用，1未使用)
    private String content;  //优惠码内容
    private Integer origin;  //优惠码来源(1线上，0线下)
    private Integer type;  //优惠码类型()

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
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "origin")
    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoCodeEntity that = (PromoCodeEntity) o;
        return id == that.id &&
                Objects.equals(status, that.status) &&
                Objects.equals(content, that.content) &&
                Objects.equals(origin, that.origin) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, status, content, origin, type);
    }
}
