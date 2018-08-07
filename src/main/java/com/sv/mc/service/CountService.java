package com.sv.mc.service;

import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.pojo.qo.ProvinceQo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface CountService {

     /**
      * 查询省数据
      * @param pId
      * @param start
      * @param end
      * @return
      */

     ProvinceQo findProvinceById(int pId, Date start, Date end);

     /**
      * 根据省ID查询所有市区
      */

     List<ProvinceQo> findCityByProvinceID(int id, Date start, Date end);


     /**
      *  根据市查询所有场地数据
      */

     List<ProvinceQo> findPlaceByCityID(int id, Date start, Date end);


     /**
      * 查询市总数据
      */

     ProvinceQo findCityById(int cId, Date start, Date end);




     /**
      * 查询省总数据
      */
     ProvinceQo findprovince(Date start, Date end);


     /**
      * 根据场地ID查询一个场地
      */
     ProvinceQo getONEPlaceById(int pId, Date start, Date end);


     /**
      * 查询所有省
      */
     List<ProvinceQo>getALLProvince(Date start, Date end);


     /**
      * 根据pid查询省
      */

     List<ProvinceEntity>getProvinceByP_ID(int pid);

     /**
      * 根据pid查询市
      */
     List<CityEntity> getCityByP_ID(int pid);

     /**
      * 根据pid查询场地
      */
     List<PlaceEntity>getPlaceByP_ID(int pid);





}
