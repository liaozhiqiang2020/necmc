package com.sv.mc.service;

import com.sv.mc.pojo.WxUserInfoEntity;

/**
 * 微信用户信息
 * @author: lzq
 * @date: 2018年7月6日
 */
public interface WxUserInfoService<T> extends BaseService<T> {
    /**
     * 插入用户数据
     * @author: lzq
     * @date: 2018年7月6日
     */
    void addWxUserInfo(WxUserInfoEntity wxUserInfoEntity);

    /**
     * 删除用户数据
     * @author: lzq
     * @date: 2018年7月6日
     */
    void deleteWxUserInfo(String openId);

    /**
     * 修改用户信息
     * @param wxUserInfoEntity
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    void updateWxUserInfo(WxUserInfoEntity wxUserInfoEntity);

    /**
     * 根据openId查询是否存在用户信息
     * @param openId
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    WxUserInfoEntity findWxUserInfoByOpenId(String openId);
}
