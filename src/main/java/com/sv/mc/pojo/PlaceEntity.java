package com.sv.mc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 场地实体类
 */
@Entity
@Table(name = "mc_place", schema = "mc", catalog = "")
public class PlaceEntity {
    private int id;
    private String placeSn;
    private Integer levelFlag;
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
    private Integer level;
    private String placeLevelName;
    private Integer superiorId;
    private Integer placeLevelId;

    private Integer businessId;
    private Integer cityId;
    private int userId;//用户id
    private String file;
    private String fileName;
;
    @Basic
    @JoinColumn(name="business_id")
    public Integer getBusinessId() {
        return businessId;
    }

    @Basic
    @JoinColumn(name="city_id")
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Basic
    @JoinColumn(name="file")
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Basic
    @JoinColumn(name="file_Name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    //    private BusinessEntity businessEntity;//行业信息
//
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name="business_id")
//    public BusinessEntity getBusinessEntity() {
//        return businessEntity;
//    }
//
//    public void setBusinessEntity(BusinessEntity businessEntity) {
//        this.businessEntity = businessEntity;
//    }
//
//    private CityEntity cityEntity;//城市信息
//
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name="city_id")
//    public CityEntity getCityEntity() {
//        return cityEntity;
//    }
//
//    public void setCityEntity(CityEntity cityEntity) {
//        this.cityEntity = cityEntity;
//    }

    private List<DeviceEntity> deviceEntities = new ArrayList<>();//设备信息

    @JsonIgnore
    @OneToMany( mappedBy = "placeEntity")  //指定一对多关系
    public List<DeviceEntity> getDeviceEntities() {
        return deviceEntities;
    }

    public void setDeviceEntities(List<DeviceEntity> deviceEntities) {
        this.deviceEntities = deviceEntities;
    }

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
    @Column(name="user_id")
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
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
    public Integer getLevelFlag() {
        return levelFlag;
    }

    public void setLevelFlag(Integer levelFlag) {
        this.levelFlag = levelFlag;
    }

//    @Basic
//    @Column(name = "business_id")
//    public Integer getBusinessId() {
//        return businessId;
//    }
//
//    public void setBusinessId(Integer businessId) {
//        this.businessId = businessId;
//    }

//    @Basic
//    @Column(name = "city_id")
//    public Integer getCityId() {
//        return cityId;
//    }
//
//    public void setCityId(Integer cityId) {
//        this.cityId = cityId;
//    }

    @Basic
    @Column(name = "place_level_id")
    public Integer getPlaceLevelId() {
        return placeLevelId;
    }

    public void setPlaceLevelId(Integer placeLevelId) {
        this.placeLevelId = placeLevelId;
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

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "place_level_name")
    public String getPlaceLevelName() {
        return placeLevelName;
    }

    public void setPlaceLevelName(String placeLevelName) {
        this.placeLevelName = placeLevelName;
    }

    @Basic
    @Column(name = "superior_id")
    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceEntity that = (PlaceEntity) o;
        return id == that.id &&
                discardStatus == that.discardStatus &&
                Objects.equals(placeSn, that.placeSn) &&
                Objects.equals(levelFlag, that.levelFlag) &&
                Objects.equals(placeLevelId, that.placeLevelId) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(placeAddress, that.placeAddress) &&
                Objects.equals(name, that.name) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime) &&
                Objects.equals(pId, that.pId) &&
                Objects.equals(placeMapId, that.placeMapId) &&
                Objects.equals(placeRank, that.placeRank) &&
                Objects.equals(level, that.level) &&
                Objects.equals(placeLevelName, that.placeLevelName) &&
                Objects.equals(superiorId, that.superiorId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, placeSn, levelFlag, placeLevelId, discardStatus, principal, placeAddress, name, latitude, longitude, startDateTime, endDateTime, pId, placeMapId, placeRank, level, placeLevelName, superiorId);
    }
}
