package com.sv.mc.service;


import com.sv.mc.pojo.PriceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PriceService{

    Page<PriceEntity> findAllPagePrice(Pageable pageable);

    List<PriceEntity> findAllPrice();

    PriceEntity updatePrice(PriceEntity priceEntity);

    void deletePrice(int priceId);

    PriceEntity findPriceById(int Id);

     PriceEntity addPrice(PriceEntity priceEntity);

     List<Object[]> findPriceByDeviceId(int deviceId);
}
