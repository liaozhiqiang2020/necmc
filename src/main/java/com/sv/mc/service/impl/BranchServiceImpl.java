package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sv.mc.pojo.*;
import com.sv.mc.repository.*;
import com.sv.mc.service.BranchService;
import com.sv.mc.util.DataSourceResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.groovy.GJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class BranchServiceImpl implements BranchService {
        @Autowired
        private BranchRepository branchRepository;
        @Autowired
        private HeadQuartersRepository headQuartersRepository;
        @Autowired
        private VendorRepository vendorRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private PlaceRepository placeRepository;

        /**1
         * 保存缓存数据
         * @param branch 分公司数据
         * @return
         */
        @Transactional
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
        @Transactional
        public BranchEntity insertBranch(BranchEntity branch) {
                branch.setDiscardStatus(1);
//                int headId = branch.getHeadQuartersEntity().getId();
                HeadQuartersEntity headQuartersEntity = this.headQuartersRepository.findHeadQuartersById(branch.getHeadQuartersId());
//                branch.setHeadQuartersEntity(headQuartersEntity);
                return  this.branchRepository.save(branch);
        }

        /**
         * 根据分公司id更改分公司数据
         * @param branch 分公司实体类
         * @return branch
         */
        @Override
        @Transactional
        public BranchEntity updateBranchDataById(BranchEntity branch) {
//                int headId = branch.getHeadQuartersEntity().getId();
                HeadQuartersEntity headQuartersEntity = this.headQuartersRepository.findHeadQuartersById(branch.getHeadQuartersId());
//                branch.setHeadQuartersEntity(headQuartersEntity);
                return  this.branchRepository.save(branch);

        }

        /**
         * 根据id删除（逻辑删除，更改状态）
         */
        @Override
        @Transactional
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
        @Transactional
        public String findAllBranchByPage(int page, int pageSize) {
                int offset = ((page - 1) * pageSize);
                List<BranchEntity> branchEntityList = this.branchRepository.findAllBranchByPage(offset, pageSize);//记录
                int total = this.branchRepository.findBranchTotal();//数量

                JSONArray jsonArray1 = new JSONArray();//新建json数组
                JSONObject jsonObject1 = new JSONObject();

                JSONArray jsonArray = JSONArray.fromObject(branchEntityList);
                String headQuartersName;
                String userName;
                for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int headQuartersId = Integer.parseInt(jsonObject.get("headQuartersId").toString());
                        int userId = Integer.parseInt(jsonObject.get("userId").toString());
                        headQuartersName = this.headQuartersRepository.findHeadQuartersById(headQuartersId).getName();//查出总部名称
                        userName = this.userRepository.findUserById(userId).getName();
                        jsonObject.put("headQuartersName", headQuartersName);
                        jsonObject.put("userName", userName);
                        jsonArray1.add(jsonObject);
                }
                jsonObject1.put("data",jsonArray1);
                jsonObject1.put("total",total);

                return jsonObject1.toString();
        }


        /**
         * 查询所有总公司和分公司内容
         */
        @Override
        public String allBranchAndHead() {
                JSONArray jsonArray3 = new JSONArray();

                List<HeadQuartersEntity> headQuartersEntities = this.headQuartersRepository.findAll();
                JSONArray jsonArray1 = JSONArray.fromObject(headQuartersEntities);

                for (int i = 0; i < jsonArray1.size(); i++) {
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                        JSONObject jsonObject3 = new JSONObject();
                        int id = Integer.parseInt(jsonObject1.get("id").toString());
                        String name = jsonObject1.get("name").toString();
                        jsonObject3.put("id",id+"_"+name);
                        jsonObject3.put("name",name);
                        jsonArray3.add(jsonObject3);
                }

                List<BranchEntity> branchEntities = this.branchRepository.findAll();
                JSONArray jsonArray2 = JSONArray.fromObject(branchEntities);
                for (int y = 0; y < jsonArray2.size(); y++) {
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(y);
                        JSONObject jsonObject4 = new JSONObject();
                        int id = Integer.parseInt(jsonObject2.get("id").toString());
                        String name = jsonObject2.get("name").toString();
                        jsonObject4.put("id",id+"_"+name);
                        jsonObject4.put("name",name);
                        jsonArray3.add(jsonObject4);
                }

                return  jsonArray3.toString();
        }



        /**
         * 查询所有总公司和分公司和代理商内容
         */
        @Override
        public String allBranchAndHeadAndVendor() {
//                List<HeadQuartersEntity> headQuartersEntities = this.headQuartersRepository.findAll();
//                List<BranchEntity> branchEntities = this.branchRepository.findAll();
//                List<VendorEntity> vendorEntities = this.vendorRepository.findAll();
//
//                List list = new ArrayList();
//                list.addAll(headQuartersEntities);
//                list.addAll(branchEntities);
//                list.addAll(vendorEntities);
//
//                JSONArray jsonArray = JSONArray.fromObject(list);




                JSONArray jsonArray3 = new JSONArray();

                List<HeadQuartersEntity> headQuartersEntities = this.headQuartersRepository.findAll();
                JSONArray jsonArray1 = JSONArray.fromObject(headQuartersEntities);

                for (int i = 0; i < jsonArray1.size(); i++) {
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                        JSONObject jsonObject3 = new JSONObject();
                        int id = Integer.parseInt(jsonObject1.get("id").toString());
                        String name = jsonObject1.get("name").toString();
                        jsonObject3.put("id",id+"_"+name);
                        jsonObject3.put("name",name);
                        jsonArray3.add(jsonObject3);
                }

                List<BranchEntity> branchEntities = this.branchRepository.findAll();
                JSONArray jsonArray2 = JSONArray.fromObject(branchEntities);
                for (int y = 0; y < jsonArray2.size(); y++) {
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(y);
                        JSONObject jsonObject4 = new JSONObject();
                        int id = Integer.parseInt(jsonObject2.get("id").toString());
                        String name = jsonObject2.get("name").toString();
                        jsonObject4.put("id",id+"_"+name);
                        jsonObject4.put("name",name);
                        jsonArray3.add(jsonObject4);
                }

                List<VendorEntity> vendorEntities = this.vendorRepository.findAll();
                JSONArray jsonArray4 = JSONArray.fromObject(vendorEntities);
                for (int x = 0; x < jsonArray4.size(); x++) {
                        JSONObject jsonObject2 = jsonArray4.getJSONObject(x);
                        JSONObject jsonObject4 = new JSONObject();
                        int id = Integer.parseInt(jsonObject2.get("id").toString());
                        String name = jsonObject2.get("name").toString();
                        jsonObject4.put("id",id+"_"+name);
                        jsonObject4.put("name",name);
                        jsonArray3.add(jsonObject4);
                }

                return  jsonArray3.toString();
        }


        /**
         * 查询所有状态为1的用户
         * @return
         */
        @Override
        public List<UserEntity> findAllByStatus() {
                return this.userRepository.findAllByStatus();
        }

        /**
         * 根据分公司id查询下面的场地
         */
        @Override
        public List<PlaceEntity> findAllPlaceByBranchId(int branchId) {
                return this.branchRepository.findAllPlaceByBranchId(branchId);
        }

        /**
         * 分公司绑定场地
         */
        @Override
        public void branchBoundPlace(int branchId, int placeId) {
                PlaceEntity placeEntity = this.placeRepository.findPlaceById(placeId);
                placeEntity.setLevelFlag(2);
                placeEntity.setSuperiorId(branchId);
                this.placeRepository.save(placeEntity);
        }
}
