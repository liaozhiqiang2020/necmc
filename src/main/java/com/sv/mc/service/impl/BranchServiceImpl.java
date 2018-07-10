package com.sv.mc.service.impl;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.repository.BranchRepository;
import com.sv.mc.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class BranchServiceImpl implements BranchService {
        @Autowired
        private BranchRepository branchRepository;

        /**1
         * 保存缓存数据
         * @param branch 分公司数据
         * @return
         */
        @Override
        public BranchEntity save(BranchEntity branch) {
                return this.branchRepository.save(branch);
        }

        /**2
         * 查询所有分公司列表
         * @return List<BranchEntity>
         */
        @Override
        @Transactional
        public List<BranchEntity> findAllEntities() {
            return  this.branchRepository.findAll();
        }

        /**3
         * 根据分公司id查询对应分公司数据
         * @param id  分公司id
         * @return  BranchEntity
         */
        @Override
        @Transactional
        public BranchEntity findBranchById(int id) {
            return this.branchRepository.findBranchById(id);
        }

        /**4
         * 插入一条分公司数据
         * @param branch
         */
        @Override
        public BranchEntity insertBranch(BranchEntity branch) {
                return  this.branchRepository.save(branch);
        }

        /**
         * 根据分公司id更改分公司数据
         * @param id 分公司id
         * @param branch 分公司实体类
         * @return branch
         */
        @Override
        public BranchEntity updateBranchDataById(int id,BranchEntity branch) {
                return  this.branchRepository.save(branch);

        }

}
