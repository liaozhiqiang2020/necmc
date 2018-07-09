package com.sv.mc.repository;

import com.sv.mc.pojo.PromoCodeEntity;
import com.sv.mc.pojo.WxUserInfoEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 小程序优惠码
 * @author: lzq
 * @date: 2018年7月6日
 */
@Repository
public interface PromoCodeRepository extends BaseRepository<PromoCodeEntity, Long>, PagingAndSortingRepository<PromoCodeEntity, Long> {
    
}
