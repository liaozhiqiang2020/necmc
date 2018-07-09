package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mc_place", schema = "mc")
public class PlaceEntity {
    private int id;
    private String placeSn;
    private String principal;
    private String placeAddress;
    private String placeName;
    private String placeRank;
    private Integer pId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int placeMapId;

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "place_sn")
    @JsonIgnore
    public String getPlaceSn() {
        return placeSn;
    }

    public void setPlaceSn(String placeSn) {
        this.placeSn = placeSn;
    }

    @Basic
    @Column(name = "principal")
    @JsonIgnore
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Basic
    @Column(name = "place_address")
    @JsonIgnore
    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    @Basic
    @Column(name = "place_name")
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Basic
    @Column(name = "place_rank")
    @JsonIgnore
    public String getPlaceRank() {
        return placeRank;
    }

    public void setPlaceRank(String placeRank) {
        this.placeRank = placeRank;
    }

    @Basic
    @Column(name = "p_id")
    @JsonIgnore
    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    @Basic
    @Column(name = "latitude")
    @JsonIgnore
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    @JsonIgnore
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "start_date_time")
    @JsonIgnore
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Basic
    @Column(name = "end_date_time")
    @JsonIgnore
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceEntity that = (PlaceEntity) o;
        return id == that.id &&
                Objects.equals(placeSn, that.placeSn) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(placeAddress, that.placeAddress) &&
                Objects.equals(placeName, that.placeName) &&
                Objects.equals(placeRank, that.placeRank) &&
                Objects.equals(pId, that.pId) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, placeSn, principal, placeAddress, placeName, placeRank, pId, latitude, longitude, startDateTime, endDateTime);
    }

    @Basic
    @Column(name = "place_map_id")
    public int getPlaceMapId() {
        return placeMapId;
    }

    public void setPlaceMapId(int placeMapId) {
        this.placeMapId = placeMapId;
    }
}
