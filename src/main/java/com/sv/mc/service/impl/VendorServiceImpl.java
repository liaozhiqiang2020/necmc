package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.pojo.VendorEntity;
import com.sv.mc.repository.VendorRepository;
import com.sv.mc.service.VendorService;
import com.sv.mc.util.DataSourceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:赵政博
 * 业务层
 */
@Service
public class VendorServiceImpl implements VendorService {
        @Autowired
        private VendorRepository vendorRepository;

        /**
         * 提交数据
         * @param vendor 代理商数据
         * @return VendorEntity
         */
        @Override
        public VendorEntity save(VendorEntity vendor) {
                return vendorRepository.save(vendor);
        }

        /**
         * 根据id查询代理商
         * @param id  代理商id
         * @return VendorEntity
         */
        @Override
        public VendorEntity findVendorById(int id) {
                return vendorRepository.findVendorById(id);
        }

        /**
         * 根据代理商id更改数据
         * @param id  代理商id
         * @param vendor 代理商数据
         * @return VendorEntity
         */
        @Override
        public VendorEntity updateVendorDataById(int id, VendorEntity vendor) {
                return vendorRepository.save(vendor);
        }

        /**
         * 查询所有代理商数据
         * @return List<VendorEntity>
         */
        @Override
        public List<VendorEntity> findAllEntities() {
                return vendorRepository.findAll();
        }


        /**
         * 插入一条代理商数据
         * @param vendor
         * @return VendorEntity
         */
        @Override
        public VendorEntity insertVendor(VendorEntity vendor) {
                vendor.setDiscardStatus(1);
                return vendorRepository.save(vendor);
        }



        @Override
        public String findAllVendorByPage(int page, int pageSize) {
                Gson gson = new Gson();
                DataSourceResult<VendorEntity> vendorEntityDataSourceResult = new DataSourceResult<>();
                int offset = ((page-1)*pageSize);
                List<VendorEntity> vendorEntities = this.vendorRepository.findAllVendorByPage(offset,pageSize);
                int total = this.vendorRepository.findVendorTotal();
                vendorEntityDataSourceResult.setData(vendorEntities);
                vendorEntityDataSourceResult.setTotal(total);
                return gson.toJson(vendorEntityDataSourceResult);
        }

        @Override
        public VendorEntity updateVendorDataById(VendorEntity vendorEntity) {
               return this.vendorRepository.save(vendorEntity);
        }

        @Override
        public void deleteVendor(int headId) {
                VendorEntity vendorEntity = findVendorById(headId);
                vendorEntity.setDiscardStatus(0);
                this.vendorRepository.save(vendorEntity);
        }
}
