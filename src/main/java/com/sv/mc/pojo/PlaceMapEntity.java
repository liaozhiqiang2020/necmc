package com.sv.mc.pojo;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * 实体类层 场地地图
 * Author: 赵政博
 */
@Entity
@Table(name = "mc_place_map", schema = "mc", catalog = "")
public class PlaceMapEntity {
    private int id;
    /**
     * 场地方图片
     */
    private byte[] placeImage;
    /**
     * 场地方id
     */
    private int placeId;
    /**
     * 场地名称
     */
    private String name;

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
    @Column(name = "place_image")
    public byte[] getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(byte[] placeImage) {
        this.placeImage = placeImage;
    }

    @Basic
    @Column(name = "place_id")
    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceMapEntity that = (PlaceMapEntity) o;
        return id == that.id &&
                placeId == that.placeId &&
                Arrays.equals(placeImage, that.placeImage) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, placeId, name);
        result = 31 * result + Arrays.hashCode(placeImage);
        return result;
    }
}
