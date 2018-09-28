package com.sv.mc.service;

import com.sv.mc.pojo.PriceHistoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 价格历史
 */
@Repository
public interface PriceHistoryService {

    /**
     * 价格历史查询
     * @return 历史价格信息
     */
    List<PriceHistoryEntity> findAllPrice();

    /**
     * 增加历史价格
     * @param priceEntity 价格
     * @return 新增的历史价格
     */
    PriceHistoryEntity addPrice(PriceHistoryEntity priceEntity);

}
