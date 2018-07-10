package com.sv.mc.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 实体类层 场地
 * Author:赵政博
 */
@Entity
@Table(name = "mc_place", schema = "mc", catalog = "")
public class PlaceEntity {
    private int id;
    private String placeSn;   //场地编码
    private int levelFlag;      //判断隶属单位(1总部,2分公司,3代理商)
    private int businessId;     //行业二级
    private int cityId;         //市id
    private int placeLevelId; //场地级别id
    private int discardStatus;  //事物删除(0显示,1删除)
    private String principal; ///负责人
    private String placeAddress; //场地地址
    private String placeName;   //场地名称
    private BigDecimal latitude; //纬度
    private BigDecimal longitude;//经度
    private Timestamp startDateTime;//协议开始时间
    private Timestamp endDateTime;  //协议结束期间

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
    @Column(name = "business_id")
    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    @Basic
    @Column(name = "city_id")
    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
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
    @Column(name = "place_name")
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Basic
    @Column(name = "place_level_id")
    public int getPlaceLevelName() {
        return placeLevelId;
    }

    public void setPlaceLevelName(int placeLevelName) {
        this.placeLevelId = placeLevelName;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceEntity that = (PlaceEntity) o;
        return id == that.id &&
                levelFlag == that.levelFlag &&
                businessId == that.businessId &&
                cityId == that.cityId &&
                discardStatus == that.discardStatus &&
                Objects.equals(placeSn, that.placeSn) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(placeAddress, that.placeAddress) &&
                Objects.equals(placeName, that.placeName) &&
                Objects.equals(placeLevelId, that.placeLevelId) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime) ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, placeSn, levelFlag, businessId, cityId, discardStatus, principal, placeAddress, placeName, placeLevelId,  latitude, longitude, startDateTime, endDateTime);
    }
}
