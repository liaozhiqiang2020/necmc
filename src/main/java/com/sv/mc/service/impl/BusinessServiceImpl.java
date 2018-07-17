package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.BusinessEntity;
import com.sv.mc.repository.BusinessRepository;
import com.sv.mc.service.BusinessService;
import com.sv.mc.util.DataSourceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private BusinessRepository businessRepository;


    /**
     * 保存数据
     * @param business 保存行业分类数据
     * @return BusinessEntity
     */
    @Override
    public BusinessEntity save(BusinessEntity business) {
        return businessRepository.save(business);
    }

    /**
     * 根据pid查询数据L
     * @param id
     * 判断一二级
     * @return BusinessEntity
     */
    @Override
    public BusinessEntity fianBusinessById(int id) {
        return businessRepository.findBusinessById(id);
    }

    /**
     * 查询所有数据
     * @return  List BusinessEntity
     */
    @Override
    public List<BusinessEntity> findAllEntities() {
        return businessRepository.findAll();
    }


    @Override
    public BusinessEntity insertBusiness(BusinessEntity businessEntity) {
        businessEntity.setDiscardStatus(1);
        return this.businessRepository.save(businessEntity);
    }

    @Override
    public BusinessEntity updateBussiness(BusinessEntity businessEntity) {
        return this.businessRepository.save(businessEntity);
    }

    @Override
    public void deleteBussiness(int businessId) {
        BusinessEntity businessEntity = fianBusinessById(businessId);
        businessEntity.setDiscardStatus(0);
        this.businessRepository.save(businessEntity);
    }

    @Override
    public String findAllBusinessByPage(int page, int pageSize) {
        Gson gson = new Gson();
        DataSourceResult<BusinessEntity> branchEntityDataSourceResult = new DataSourceResult<>();
        int offset = ((page-1)*pageSize);
        List<BusinessEntity> branchEntityList = this.businessRepository.findAllBussByPage(offset,pageSize);
        int total = this.businessRepository.findBusinessTotal();
        branchEntityDataSourceResult.setData(branchEntityList);
        branchEntityDataSourceResult.setTotal(total);
        return gson.toJson(branchEntityDataSourceResult);
    }
/*
   查询第一级分类
 */
    @Override
    public List<BusinessEntity> selectBussinessByLevel(Integer id) {
        return this.businessRepository.findBusinessByParentId(id);
    }
/*
 查询二级分类
 */
    @Override
    public List<BusinessEntity> selectBussinessByLevel2(int id) {
        return this.businessRepository.findBusinessByParentId2(id);
    }
//增加一级分类
    @Override
    public BusinessEntity insertBussiness1(BusinessEntity business) {
        business.setDiscardStatus(1);
        business.setLevel(1);
        business.setParentId(null);
        this.businessRepository.save(business);
        return business;
    }
//增加二级分类
    @Override
    public BusinessEntity insertBussiness2(BusinessEntity business) {
        business.setDiscardStatus(1);
        business.setLevel(2);

        this.businessRepository.save(business);
        return business;
    }
//删除一级分类
    @Override
    public void deleteBussiness1(BusinessEntity buiness) {
     buiness.setDiscardStatus(0);
     this.businessRepository.save(buiness);

    }
//删除二级分类
    @Override
    public void deleteBussiness2(BusinessEntity buiness) {
        buiness.setDiscardStatus(0);

        this.businessRepository.save(buiness);
    }
//修改一级分类
    @Override
    public BusinessEntity upadateBussiness1(BusinessEntity business) {
        this.businessRepository.save(business);
        return business;
    }
//修改二级分类
    @Override
    public BusinessEntity upadateBussiness2(BusinessEntity business) {
        if(business.getLevel()==1){
            business.setParentId(0);
        }
        this.businessRepository.save(business);
        return business;
    }










}
