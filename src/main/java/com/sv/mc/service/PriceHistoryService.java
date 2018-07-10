package com.sv.mc.service;

import com.sv.mc.pojo.PriceHistoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceHistoryService {

    List<PriceHistoryEntity> findAllPrice();


    PriceHistoryEntity addPrice(PriceHistoryEntity priceEntity);

}
