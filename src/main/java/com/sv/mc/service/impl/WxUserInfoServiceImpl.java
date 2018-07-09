package com.sv.mc.service.impl;

import com.sv.mc.pojo.WxUserInfoEntity;
import com.sv.mc.repository.WxUserInfoRepository;
import com.sv.mc.service.WxUserInfoService;
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
}