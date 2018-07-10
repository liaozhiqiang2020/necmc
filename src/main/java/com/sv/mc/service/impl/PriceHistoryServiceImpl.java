package com.sv.mc.service.impl;

import com.sv.mc.pojo.PriceHistoryEntity;
import com.sv.mc.repository.PriceHistoryRepository;
import com.sv.mc.service.PriceHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PriceHistoryServiceImpl implements PriceHistoryService {

    @Resource
    private PriceHistoryRepository priceHistoryRepository;
    /**
     * 不分页查询所有价格
     * @return 所有价格
     */

    @Transactional(readOnly = true)
    @Override
    public List<PriceHistoryEntity> findAllPrice() {
        return this.priceHistoryRepository.findAll();
    }


    /**
     * 添加价格
     * @param PriceHistoryEntity 价格历史对象
     * @return 消息
     */
    @Override
    @Transactional
    public PriceHistoryEntity addPrice(PriceHistoryEntity PriceHistoryEntity) {
        return   this.priceHistoryRepository.save(PriceHistoryEntity);

    }

}
