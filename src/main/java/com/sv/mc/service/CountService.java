package com.sv.mc.service;

import com.sv.mc.pojo.qo.ProvinceQo;
import org.springframework.stereotype.Service;

import java.util.Date;


public interface CountService {

     ProvinceQo findProvinceById(int pId, Date start , Date end);
}
