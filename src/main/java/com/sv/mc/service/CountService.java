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

//==================================================================================================================================================================
//=========================================2 ,3 级权限增加============================================================================================================
//==================================================================================================================================================================
//==================================================================================================================================================================

     /**
      * 无条件查询所有所在省数据
      * @param level
      * @param pid
      * @param start
      * @param end
      */

     List<ProvinceQo> getProvinceBypId( int level, int pid, Date start, Date end);


     /**
      * 根据省ID查询所在省
      * @param level
      * @param pid
      * @return
      */

     List<ProvinceQo> getProvinceBypIdANDprovinceID( int level, int pid,Date start, Date end,int provinceId);


     /**
      * 根据省ID查询所有所在市数据
      */
     List<ProvinceQo> getCBypIdANDprovinceID( int level, int pid, Date start, Date end,int provinceId);


     /**
      * 根据市ID查询所有所在市数据
      */

     List<ProvinceQo> getCityBypIdANDcityID(int level, int pid, Date start, Date end,int cityId);



     /**
      * 根据市Id 查询所有所在场地数据
      */
     List<ProvinceQo> getPlacyBypIdANDcityID( int level, int pid, Date start, Date end,int cityId);


     /**
      * 根据场地Id 查询所有所在场地数据
      */
     List<ProvinceQo> getPlacyBypIdANDplaceID( int level, int pid, Date start, Date end,int placeId);


     /**
      * 最低级权限查询场地
      */
     List<ProvinceQo> getPlacyByANDplaceID( Date start, Date end,int placeId);

}
