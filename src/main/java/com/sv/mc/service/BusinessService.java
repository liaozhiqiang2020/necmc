package com.sv.mc.service;

import com.sv.mc.pojo.BusinessEntity;

import java.util.List;

/**
 * 接口
 * author:赵政博
 */
public interface BusinessService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param business 保存行业分类数据
     * @return       AreaEntity
     */
    BusinessEntity save(BusinessEntity business);

    /**
     * 查询行业分类详细数据
     * @param id 判断一二级
     * @return BusinessEntity
     */
    BusinessEntity fianBusinessById(int id);

    /**
     * 插入数据
     * @param businessEntity
     * @return
     */
    BusinessEntity insertBusiness(BusinessEntity businessEntity);

    /**
     * 修改数据
     * @param businessEntity
     * @return
     */
    BusinessEntity updateBussiness(BusinessEntity businessEntity);

    /**
     * 删除数据
     * @param businessId
     */
    void deleteBussiness(int businessId);

    /**
     * 分页查询行业分类信息
     */
    String findAllBusinessByPage(int page, int pageSize);


     /*
      查询一级行业
      */
    List<BusinessEntity> selectBussinessByLevel(Integer id);

   /*
     查询二级行业信息
    */
   List<BusinessEntity> selectBussinessByLevel2(int id);

  /*
    增加一级行业
  */
  BusinessEntity insertBussiness1(BusinessEntity business);



  /*
    增加二级行业
  */
  BusinessEntity insertBussiness2(BusinessEntity business);


  /*
    删除一级行业
   */
   void deleteBussiness1(BusinessEntity buiness);
  /*
   删除二级行业
   */
  void deleteBussiness2(BusinessEntity buiness);
   /*
   修改一级行业
    */
    BusinessEntity upadateBussiness1(BusinessEntity buiness);
 /*
   修改二级行业
  */
    BusinessEntity upadateBussiness2(BusinessEntity buiness);

}
