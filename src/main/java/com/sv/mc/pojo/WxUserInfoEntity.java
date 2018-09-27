package com.sv.mc.pojo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 微信用户信息实体类
 * @author: lzq
 * @date: 2018年7月3日
 */
@Entity
@Table(name = "mc_wx_user_info", schema = "mc", catalog = "")
public class WxUserInfoEntity {
    /**
     * 微信用户Id
     */
    private int id;
    /**
     * 微信用户唯一编号
     */
    private String openCode;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户头像地址
     */
    private String avatarUrl;
    /**
     * 性别
     */
    private String gender;
    /**
     * 用户所在城市
     */
    private String city;
    /**
     * 用户所在省份
     */
    private String province;
    /**
     * 用户所在国家
     */
    private String country;
    /**
     * 用户的语言,简体中文为zn_CN
     */
    private String language;
    /**
     * 用户绑定的手机号(国外手机号会有区号)
     */
    private String phoneNumber;
    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;
    /**
     * 区号
     */
    private String countryCode;
    /**
     * 修改时间
     */
    private Timestamp updateDateTime;  //修改时间

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
    @Column(name = "open_code")
    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    @Basic
    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Basic
    @Column(name = "avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "pure_phone_number")
    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    @Basic
    @Column(name = "country_code")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Basic
    @Column(name = "update_date_time")
    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Timestamp updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WxUserInfoEntity that = (WxUserInfoEntity) o;
        return id == that.id &&
                Objects.equals(openCode, that.openCode) &&
                Objects.equals(nickName, that.nickName) &&
                Objects.equals(avatarUrl, that.avatarUrl) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(city, that.city) &&
                Objects.equals(province, that.province) &&
                Objects.equals(country, that.country) &&
                Objects.equals(language, that.language) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(purePhoneNumber, that.purePhoneNumber) &&
                Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(updateDateTime, that.updateDateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, openCode, nickName, avatarUrl, gender, city, province, country, language, phoneNumber, purePhoneNumber, countryCode, updateDateTime);
    }
}
