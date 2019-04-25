package com.sv.mc.service;

import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.pojo.qo.ProvinceQo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public interface CountService {

     /**
      * 查询省数据
      * @param pId 省Id
      * @param start 起始时间
      * @param end 截止时间
      * @return 省数据
      */

     ProvinceQo findProvinceById(int pId, String start, String end);


     /**
      * 根据省ID查询所有市区
      * @param id 省Id
      * @param start 起始时间
      * @param end 截止时间
      * @return 市区数据
      */
     List<ProvinceQo> findCityByProvinceID(int id, String start, String end);



     /**
      *  根据市查询所有场地数据
      * @param id 市Id
      * @param start 起始时间
      * @param end 截至时间
      * @return 场地数据
      */
     List<ProvinceQo> findPlaceByCityID(int id, String start, String end);



     /**
      * 查询市总数据
      * @param cId 市id
      * @param start 起始时间
      * @param end 截止时间
      * @return 市总数据
      */
     ProvinceQo findCityById(int cId, String start, String end);





     /**
      * 查询省总数据
      * @param start 起始时间
      * @param end 截止时间
      * @return 省总数据
      */
     ProvinceQo findprovince(String start, String end);



     /**
      *  根据场地ID查询一个场地
      * @param pId 场地Id
      * @param start 起始时间
      * @param end 截止时间
      * @return 场地数据
      */
     ProvinceQo getONEPlaceById(int pId, String start, String end);



     /**
      * 查询所有省
      * @param start 起始时间
      * @param end 截止时间
      * @return 所有省的数据
      */
     List<ProvinceQo>getALLProvince(String start, String end);



     /**
      *  根据pid查询省
      * @param pid 隶属单位Id
      * @return 省信息
      */
     List<ProvinceEntity>getProvinceByP_ID(int pid);


     /**
      *  根据pid查询市
      * @param pid 隶属单位
      * @return 市信息
      */
     List<CityEntity> getCityByP_ID(int pid);


     /**
      *   根据隶属单位查询场地
      * @param pid 隶属单位Id
      * @return 场地信息
      */
     List<PlaceEntity>getPlaceByP_ID(int pid);

//==================================================================================================================================================================
//=========================================2 ,3 级权限增加============================================================================================================
//==================================================================================================================================================================
//==================================================================================================================================================================

     /**
      * 无条件查询所有所在省数据
      * @param level 权限等级
      * @param pid 隶属单位
      * @param start 起始时间
      * @param end 截止时间
      * @return  所在省数据
      */


     List<ProvinceQo> getProvinceBypId(int level, int pid, String start, String end);

     /**
      *  根据省ID查询所在省
      * @param level 权限等级
      * @param pid 隶属单位
      * @param start 起始时间
      * @param end 截止时间
      * @param provinceId 省Id
      * @return 所在省报表数据
      */
     List<ProvinceQo> getProvinceBypIdANDprovinceID(int level, int pid, String start, String end, int provinceId);



     /**
      * 根据省ID查询所有所在市数据
      * @param level 权限等级
      * @param pid 隶属单位
      * @param start 起始时间
      * @param end 截止时间
      * @param provinceId 省id
      * @return 市报表数据
      */
     List<ProvinceQo> getCBypIdANDprovinceID(int level, int pid, String start, String end, int provinceId);



     /**
      * 根据市ID查询所有所在市数据
      * @param level 权限等级
      * @param pid  隶属单位
      * @param start 起始时间
      * @param end 截止时间
      * @param cityId 市Id
      * @return 所在市数据
      */

     List<ProvinceQo> getCityBypIdANDcityID(int level, int pid, String start, String end, int cityId);



     /**
      *  根据市Id 查询所有所在场地数据
      * @param level 权限等级
      * @param pid 隶属单位
      * @param start 起始时间
      * @param end 截止时间
      * @param cityId 市Id
      * @return 所在场地报表数据
      */
     List<ProvinceQo> getPlacyBypIdANDcityID(int level, int pid, String start, String end, int cityId);


     /**
      *  根据场地Id 查询所有所在场地数据
      * @param level 权限等级
      * @param pid 隶属单位
      * @param start 起始时间
      * @param end 截止时间
      * @param placeId 场地Id
      * @return 所在场地报表数据
      */
     List<ProvinceQo> getPlacyBypIdANDplaceID(int level, int pid, String start, String end, int placeId);



     /**
      * 最低级权限查询场地
      * @param start 起始时间
      * @param end 截止时间
      * @param placeId 场地Id
      * @return 所在场地报表数据
      */
     List<ProvinceQo> getPlacyByANDplaceID(String start, String end, int placeId);

}
