package com.sv.mc.service.impl;

import com.sv.mc.pojo.qo.ProvinceQo;
import com.sv.mc.repository.countRepository;
import com.sv.mc.service.CountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CountServiceImpl implements CountService {

    @Resource
    private countRepository repository;

    @Override
    public ProvinceQo findProvinceById(int pId, Date start , Date end) {

        List<Object[]> list = this.repository.findCountById(pId,start,end);
        Object[] array = list.get(0);
        String name = (String)array[0];
        int orderCount = Integer.valueOf(array[1].toString());
        int userCount = Integer.valueOf(array[2].toString());
        int income = Integer.valueOf(array[3].toString());
        ProvinceQo ProvinceQo = new ProvinceQo();
        ProvinceQo.setName(name);
        ProvinceQo.setIncome(income);
        ProvinceQo.setOrderCount(orderCount);
        ProvinceQo.setUserCount(userCount);
        return ProvinceQo;
    }
}
