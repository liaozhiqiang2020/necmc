package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.pojo.UserEntity;
import com.sv.mc.repository.BranchRepository;
import com.sv.mc.repository.HeadQuartersRepository;
import com.sv.mc.service.BranchService;
import com.sv.mc.util.DataSourceResult;
import net.sf.json.groovy.GJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class BranchServiceImpl implements BranchService {
        @Autowired
        private BranchRepository branchRepository;
        @Autowired
        private HeadQuartersRepository headQuartersRepository;

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
                branch.setDiscardStatus(1);
                int headId = branch.getHeadQuartersEntity().getId();
                HeadQuartersEntity headQuartersEntity = this.headQuartersRepository.findHeadQuartersById(headId);
                branch.setHeadQuartersEntity(headQuartersEntity);
                return  this.branchRepository.save(branch);
        }

        /**
         * 根据分公司id更改分公司数据
         * @param branch 分公司实体类
         * @return branch
         */
        @Override
        public BranchEntity updateBranchDataById(BranchEntity branch) {
                int headId = branch.getHeadQuartersEntity().getId();
                HeadQuartersEntity headQuartersEntity = this.headQuartersRepository.findHeadQuartersById(headId);
                branch.setHeadQuartersEntity(headQuartersEntity);
                return  this.branchRepository.save(branch);

        }

        /**
         * 根据id删除（逻辑删除，更改状态）
         */
        @Override
        public void deleteBranch(int branchId){
                BranchEntity branchEntity = findBranchById(branchId);
                branchEntity.setDiscardStatus(0);
                this.branchRepository.save(branchEntity);
        }

        /**
         * 分页查询分公司数据
         * @param page
         * @param pageSize
         * @return
         */
        @Override
        public String findAllBranchByPage(int page, int pageSize) {
                Gson gson = new Gson();
                DataSourceResult<BranchEntity> branchEntityDataSourceResult = new DataSourceResult<>();
                int offset = ((page-1)*pageSize);
                List<BranchEntity> branchEntityList = this.branchRepository.findAllBranchByPage(offset,pageSize);//记录
                int total = this.branchRepository.findBranchTotal();//数量
                branchEntityDataSourceResult.setData(branchEntityList);
                branchEntityDataSourceResult.setTotal(total);
                return gson.toJson(branchEntityDataSourceResult);

        }
}
