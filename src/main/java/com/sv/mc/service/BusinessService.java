package com.sv.mc.service;

import com.sv.mc.pojo.BusinessEntity;

import java.util.List;

/**
 * 接口行业
 * author:赵政博
 */
public interface BusinessService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param business 保存行业分类数据
     * @return       AreaEntity 保存的行业数据
     */
    BusinessEntity save(BusinessEntity business);

    /**
     * 查询行业分类详细数据
     * @param id 判断一二级
     * @return BusinessEntity 行业详细数据
     */
    BusinessEntity fianBusinessById(int id);

    /**
     * 插入数据
     * @param businessEntity 插入的行业信息
     * @return 插入的行业信息
     */
    BusinessEntity insertBusiness(BusinessEntity businessEntity);

    /**
     * 修改数据
     * @param businessEntity 要修改的行业数据
     * @return  修改后的行业数据
     */
    BusinessEntity updateBussiness(BusinessEntity businessEntity);

    /**
     * 删除数据
     * @param businessId 要删除的数据
     */
    void deleteBussiness(int businessId);

    /**
     * 分页查询行业分类信息
     * @param page 起始个数
     * @param pageSize 截至个数
     * @return 行业分类信息
     */
    String findAllBusinessByPage(int page, int pageSize);

    /**
     * 使用中的行业
     * @return 查询结果使用中的行业数据
     */
    List<BusinessEntity> allUseBusiness();





    /**
     *  查询一级行业
     * @param id 行业Id
     * @return 查询结果 行业集合
     */
    List<BusinessEntity> selectBussinessByLevel(Integer id);



    /**
     * 查询二级行业信息
     * @param id 行业Id
     * @return 查询到的二级行业信息
     */
   List<BusinessEntity> selectBussinessByLevel2(int id);



    /**
     * 增加一级行业
     * @param business  需要增加的行业数据
     * @return 增加了的行业数据
     */
  BusinessEntity insertBussiness1(BusinessEntity business);





    /**
     * 增加二级行业
     * @param business 行业信息
     * @return 增加了的行业信息
     */
  BusinessEntity insertBussiness2(BusinessEntity business);


    /**
     * 删除一级行业
     * @param buiness 需要删除的行业信息
     */
   void deleteBussiness1(BusinessEntity buiness);


    /**
     * 删除二级行业 需要删除的二级行业
     * @param buiness 删除的行业对象
     */
  void deleteBussiness2(BusinessEntity buiness);


    /**
     *  修改一级行业
     * @param buiness 需要修改的一级行业信息
     * @return 修改的行业信息
     */
    BusinessEntity upadateBussiness1(BusinessEntity buiness);


    /**
     * 修改二级行业
     * @param buiness 行业信息
     * @return 修改后的行业信息
     */
    BusinessEntity upadateBussiness2(BusinessEntity buiness);

}
