package com.sv.mc.service.impl;

import com.sv.mc.pojo.WxUserInfoEntity;
import com.sv.mc.repository.WxUserInfoRepository;
import com.sv.mc.service.WxUserInfoService;
import com.sv.mc.util.WxUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 微信用户信息
 * @author: lzq
 * @date: 2018年7月6日
 */
@Service
public class WxUserInfoServiceImpl implements WxUserInfoService<WxUserInfoEntity> {

    @Autowired
    private WxUserInfoRepository wxUserInfoRepository;

    /**
     * 查询所有用户信息
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    @Cacheable
    public List<WxUserInfoEntity> findAllEntities() {
        PageRequest pageRequest = new PageRequest(0,5);
        Page<WxUserInfoEntity> wxUserInfoEntityPage = this.wxUserInfoRepository.findAll(pageRequest);
        return wxUserInfoEntityPage.getContent();
    }

    /**
     * 插入用户信息
     * @param wxUserInfoEntity
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void addWxUserInfo(WxUserInfoEntity wxUserInfoEntity) {
        this.wxUserInfoRepository.save(wxUserInfoEntity);
    }

    /**
     * 删除用户信息
     * @param openId
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void deleteWxUserInfo(String openId) {
        WxUserInfoEntity wxUserInfoEntity = this.wxUserInfoRepository.findWxUserInfoEntityByOpenCode(openId);
        this.wxUserInfoRepository.delete(wxUserInfoEntity);
    }

    /**
     * 修改用户信息
     * @param wxUserInfoEntity
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    @Transactional
    public void updateWxUserInfo(WxUserInfoEntity wxUserInfoEntity) {
        this.wxUserInfoRepository.save(wxUserInfoEntity);
    }

    /**
     * 根据openId查询是否存在用户信息
     * @param openId
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Override
    public WxUserInfoEntity findWxUserInfoByOpenId(String openId) {
        WxUserInfoEntity wxUserInfoEntity = this.wxUserInfoRepository.findWxUserInfoEntityByOpenCode(openId);
        return wxUserInfoEntity;
    }

    /**
     * 插入用户信息，手机号，openId
     */
    @Override
    public void saveUserInfoAndPhoneAndOpenId(String openId, String userInfo) {
        WxUserInfoEntity wxUserInfoEntity;

        WxUtil wxUtil = new WxUtil();

        //用户基本信息
        JSONObject jsonObject1 = JSONObject.fromObject(userInfo.toString());
        String nickName = jsonObject1.get("nickName").toString();

//        try {
//            nickName = new String(nickName.getBytes("ISO-8859-1"), "UTF-8");
//            System.out.println("打印微信个人用户数据******************* nickname = " + nickName);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("获取WX用户个人信息******************* 解析失败 Exception e = " + e.toString());
//        }

        String gender = jsonObject1.get("gender").toString();
        String language = jsonObject1.get("language").toString();
        String city = jsonObject1.get("city").toString();
        String province = jsonObject1.get("province").toString();
        String country = jsonObject1.get("country").toString();
        String avatarUrl = jsonObject1.get("avatarUrl").toString();

        //存入微信用户基本信息
        wxUserInfoEntity = this.findWxUserInfoByOpenId(openId);
        //如果当前用户信息不为空，修改原来的信息
        if (wxUserInfoEntity != null) {
            wxUserInfoEntity.setId(wxUserInfoEntity.getId());
            wxUserInfoEntity.setNickName(nickName);
            wxUserInfoEntity.setGender(gender);
            wxUserInfoEntity.setLanguage(language);
            wxUserInfoEntity.setCity(city);
            wxUserInfoEntity.setProvince(province);
            wxUserInfoEntity.setCountry(country);
            wxUserInfoEntity.setAvatarUrl(avatarUrl);
            wxUserInfoEntity.setUpdateDateTime(wxUtil.getNowDate());
            this.updateWxUserInfo(wxUserInfoEntity);
        } else{//如果当前用户信息为空，插入新的信息
            wxUserInfoEntity = new WxUserInfoEntity();
            wxUserInfoEntity.setOpenCode(openId);
            wxUserInfoEntity.setNickName(nickName);
            wxUserInfoEntity.setGender(gender);
            wxUserInfoEntity.setLanguage(language);
            wxUserInfoEntity.setCity(city);
            wxUserInfoEntity.setProvince(province);
            wxUserInfoEntity.setCountry(country);
            wxUserInfoEntity.setAvatarUrl(avatarUrl);//头像地址
            wxUserInfoEntity.setUpdateDateTime(wxUtil.getNowDate());
            this.addWxUserInfo(wxUserInfoEntity);
        }
    }
}