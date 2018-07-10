package com.sv.mc.pojo;

<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonIgnore;

>>>>>>> origin/master
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

<<<<<<< HEAD
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
=======
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
>>>>>>> origin/master

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
<<<<<<< HEAD
=======
    @JsonIgnore
>>>>>>> origin/master
    public String getPlaceSn() {
        return placeSn;
    }

    public void setPlaceSn(String placeSn) {
        this.placeSn = placeSn;
    }

    @Basic
<<<<<<< HEAD
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
=======
    @Column(name = "principal")
    @JsonIgnore
>>>>>>> origin/master
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Basic
    @Column(name = "place_address")
<<<<<<< HEAD
=======
    @JsonIgnore
>>>>>>> origin/master
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
<<<<<<< HEAD
    @Column(name = "place_level_id")
    public int getPlaceLevelName() {
        return placeLevelId;
    }

    public void setPlaceLevelName(int placeLevelName) {
        this.placeLevelId = placeLevelName;
    }



    @Basic
    @Column(name = "latitude")
=======
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
>>>>>>> origin/master
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
<<<<<<< HEAD
=======
    @JsonIgnore
>>>>>>> origin/master
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "start_date_time")
<<<<<<< HEAD
=======
    @JsonIgnore
>>>>>>> origin/master
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Basic
    @Column(name = "end_date_time")
<<<<<<< HEAD
=======
    @JsonIgnore
>>>>>>> origin/master
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

<<<<<<< HEAD

=======
>>>>>>> origin/master
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceEntity that = (PlaceEntity) o;
        return id == that.id &&
<<<<<<< HEAD
                levelFlag == that.levelFlag &&
                businessId == that.businessId &&
                cityId == that.cityId &&
                discardStatus == that.discardStatus &&
=======
>>>>>>> origin/master
                Objects.equals(placeSn, that.placeSn) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(placeAddress, that.placeAddress) &&
                Objects.equals(placeName, that.placeName) &&
<<<<<<< HEAD
                Objects.equals(placeLevelId, that.placeLevelId) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime) ;
=======
                Objects.equals(placeRank, that.placeRank) &&
                Objects.equals(pId, that.pId) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime);
>>>>>>> origin/master
    }

    @Override
    public int hashCode() {

<<<<<<< HEAD
        return Objects.hash(id, placeSn, levelFlag, businessId, cityId, discardStatus, principal, placeAddress, placeName, placeLevelId,  latitude, longitude, startDateTime, endDateTime);
=======
        return Objects.hash(id, placeSn, principal, placeAddress, placeName, placeRank, pId, latitude, longitude, startDateTime, endDateTime);
    }

    @Basic
    @Column(name = "place_map_id")
    public int getPlaceMapId() {
        return placeMapId;
    }

    public void setPlaceMapId(int placeMapId) {
        this.placeMapId = placeMapId;
>>>>>>> origin/master
    }
}
