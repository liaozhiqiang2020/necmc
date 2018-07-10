package com.sv.mc.service.impl;

import com.sv.mc.pojo.VendorEntity;
import com.sv.mc.repository.VendorRepository;
import com.sv.mc.service.VendorService;
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
         * 插入一条代理商数据
         * @param vendor
         * @return VendorEntity
         */
        @Override
        public VendorEntity insertVendor(VendorEntity vendor) {
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
}
