package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mc_place", schema = "mc", catalog = "")
public class PlaceEntity {
    private int id;
    private String placeSn;
    private int levelFlag;
    private int discardStatus;
    private String principal;
    private String placeAddress;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp startDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp endDateTime;
    private Integer pId;
    private Integer placeMapId;
    private String placeRank;
    private int superiorId;


    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "superior_id")
    public int getSuperiorId() {
        return superiorId;
    }
    public void setSuperiorId(int superiorId) {
        this.superiorId = superiorId;
    }

    @Basic
    @Column(name = "place_sn")
    public String getPlaceSn() {
        return placeSn;
    }

    public void setPlaceSn(String placeSn) {
        this.placeSn = placeSn;
    }

    @Basic
    @Column(name = "level_flag")
    public int getLevelFlag() {
        return levelFlag;
    }

    public void setLevelFlag(int levelFlag) {
        this.levelFlag = levelFlag;
    }

    @Basic
    @Column(name = "discard_status")
    public int getDiscardStatus() {
        return discardStatus;
    }

    public void setDiscardStatus(int discardStatus) {
        this.discardStatus = discardStatus;
    }

    @Basic
    @Column(name = "principal")
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Basic
    @Column(name = "place_address")
    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
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
    @Column(name = "latitude")
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "start_date_time")
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Basic
    @Column(name = "end_date_time")
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Basic
    @Column(name = "p_id")
    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    @Basic
    @Column(name = "place_map_id")
    public Integer getPlaceMapId() {
        return placeMapId;
    }

    public void setPlaceMapId(Integer placeMapId) {
        this.placeMapId = placeMapId;
    }

    @Basic
    @Column(name = "place_rank")
    public String getPlaceRank() {
        return placeRank;
    }

    public void setPlaceRank(String placeRank) {
        this.placeRank = placeRank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceEntity that = (PlaceEntity) o;
        return id == that.id &&
                levelFlag == that.levelFlag &&
                discardStatus == that.discardStatus &&
                Objects.equals(placeSn, that.placeSn) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(placeAddress, that.placeAddress) &&
                Objects.equals(name, that.name) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime) &&
                Objects.equals(pId, that.pId) &&
                Objects.equals(placeMapId, that.placeMapId) &&
                Objects.equals(placeRank, that.placeRank);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, placeSn, levelFlag, discardStatus, principal, placeAddress, name, latitude, longitude, startDateTime, endDateTime, pId, placeMapId, placeRank);
    }
}
