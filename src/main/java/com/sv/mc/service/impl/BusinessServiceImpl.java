package com.sv.mc.service.impl;

import com.sv.mc.pojo.BusinessEntity;
import com.sv.mc.repository.BusinessRepository;
import com.sv.mc.service.BusinessService;
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
}
