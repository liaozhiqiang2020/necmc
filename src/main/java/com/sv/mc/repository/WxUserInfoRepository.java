package com.sv.mc.repository;

import com.sv.mc.pojo.WxUserInfoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 微信用户信息
 **@author: lzq
 * @date: 2018年7月6日
 */
@Repository
public interface WxUserInfoRepository extends BaseRepository<WxUserInfoEntity, Long>, PagingAndSortingRepository<WxUserInfoEntity, Long> {
    /**
     * 根据用户唯一编码查询用户信息
     * @param openId
     * @return 微信用户信息
     * @author: lzq
     * @date: 2018年7月6日
     */
    @Query("from WxUserInfoEntity as u where u.openCode = :openId")
    WxUserInfoEntity findWxUserInfoEntityByOpenCode(@Param("openId") String openId);
}
