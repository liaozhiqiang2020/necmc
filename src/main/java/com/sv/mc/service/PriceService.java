package com.sv.mc.service;


import com.sv.mc.pojo.PriceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PriceService{

    Page<PriceEntity> findAllPagePrice(Pageable pageable);

    List<PriceEntity> findAllPrice();

    PriceEntity updatePrice(PriceEntity priceEntity);

    void deletePrice(int priceId);

    PriceEntity findPriceById(int Id);

     PriceEntity addPrice(PriceEntity priceEntity);

     List<Object[]> findPriceByDeviceId(int deviceId);

     List<Map<String,Object>> queryPriceAndTime(int deviceId);

    /**
     * 批量新增、更新价格数据
     * @param priceEntityList 价格对象列表
     * @return 已经新增或更新的价格对象列表，准备返回前台画面进行展示
     */
     List<PriceEntity> batchSaveOrUpdatePrice(List<PriceEntity> priceEntityList);
}
