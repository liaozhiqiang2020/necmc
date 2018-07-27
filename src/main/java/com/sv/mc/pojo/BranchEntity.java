package com.sv.mc.pojo;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mc_branch", schema = "mc", catalog = "")
public class BranchEntity {
    private int id;
    private int levelFlag;      //判断1代理商和分公司平级,2分公司管理代理商
    private int discardStatus;  //事物删除(1是显示此数据,0是事物删除数据)
    private String name;  //分公司名称
    private String branchAddress;//分公司地址
    private String principal;   //分公司负责人
    private String telephone;   //分公司联系电话
    private String email;       //分公司邮箱
    private int headQuartersId;  //总公司Id

    @Basic
    @Column(name = "head_quarters_id")
    public int getHeadQuartersId() {
        return headQuartersId;
    }

    public void setHeadQuartersId(int headQuartersId) {
        this.headQuartersId = headQuartersId;
    }

//    private HeadQuartersEntity headQuartersEntity;//总公司信息

//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name="head_quarters_id")
//    public HeadQuartersEntity getHeadQuartersEntity() {
//        return headQuartersEntity;
//    }
//
//    public void setHeadQuartersEntity(HeadQuartersEntity headQuartersEntity) {
//        this.headQuartersEntity = headQuartersEntity;
//    }

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "branch_address")
    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
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
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchEntity that = (BranchEntity) o;
        return id == that.id &&
                levelFlag == that.levelFlag &&
                discardStatus == that.discardStatus &&
                Objects.equals(name, that.name) &&
                Objects.equals(branchAddress, that.branchAddress) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, levelFlag, discardStatus, name, branchAddress, principal, telephone, email);
    }
}
