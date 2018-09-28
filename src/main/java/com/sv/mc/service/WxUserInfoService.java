package com.sv.mc.service;

import com.sv.mc.pojo.WxUserInfoEntity;

import java.util.Map;

/**
 * 微信用户信息
 * @author: lzq
 * @date: 2018年7月6日
 */
public interface WxUserInfoService<T> extends BaseService<T> {
    /**
     * 插入用户数据
     * @param wxUserInfoEntity 用户信息
     * @author: lzq
     * @date: 2018年7月6日
     */
    void addWxUserInfo(WxUserInfoEntity wxUserInfoEntity);

    /**
     * 删除用户数据
     * @param openId 用户唯一Id
     * @author: lzq
     * @date: 2018年7月6日
     */
    void deleteWxUserInfo(String openId);

    /**
     * 修改用户信息
     * @param wxUserInfoEntity 用户信息对象
     * @author: lzq
     * @date: 2018年7月6日
     */
    void updateWxUserInfo(WxUserInfoEntity wxUserInfoEntity);

    /**
     * 根据openId查询是否存在用户信息
     * @param openId 用户唯一id
     * @return 用户信息
     * @author: lzq
     * @date: 2018年7月6日
     */
    WxUserInfoEntity findWxUserInfoByOpenId(String openId);

    /**
     * 插入用户信息，手机号，openId
     * @param openId 用户唯一Id
     * @param userInfo  用户信息
     */
    void saveUserInfoAndPhoneAndOpenId(String openId, String userInfo);
}
