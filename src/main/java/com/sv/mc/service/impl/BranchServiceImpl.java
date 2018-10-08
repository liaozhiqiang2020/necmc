package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sv.mc.pojo.*;
import com.sv.mc.repository.*;
import com.sv.mc.service.BranchService;
import com.sv.mc.service.ContractService;
import com.sv.mc.util.DataSourceResult;
import com.sv.mc.util.DateJsonValueProcessor;
import com.sv.mc.util.WxUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.groovy.GJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        @Autowired
        private ContractRepository contractRepository;

        /**1
         * 保存缓存数据
         * @param branch 分公司数据
         * @return 分公司数据
         */
        @Transactional
        @Override
        public BranchEntity save(BranchEntity branch) {
                return this.branchRepository.save(branch);
        }

        /**2
         * 查询所有分公司列表
         * @return List<BranchEntity> 查询到的分公司列表
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
         * @param branch 插入的内容
         */
        @Override
        @Transactional
        public BranchEntity insertBranch(BranchEntity branch) {
                branch.setDiscardStatus(1);
//                int headId = branch.getHeadQuartersEntity().getId();
//                HeadQuartersEntity headQuartersEntity = this.headQuartersRepository.findHeadQuartersById(branch.getHeadQuartersId());
//                branch.setHeadQuartersEntity(headQuartersEntity);
                return  this.branchRepository.save(branch);
        }

        /**
         * 根据分公司id更改分公司数据
         * @param branch 分公司实体类
         * @return branch 分公司实体类
         */
        @Override
        @Transactional
        public BranchEntity updateBranchDataById(BranchEntity branch) {
//                int headId = branch.getHeadQuartersEntity().getId();
//                HeadQuartersEntity headQuartersEntity = this.headQuartersRepository.findHeadQuartersById(branch.getHeadQuartersId());
//                branch.setHeadQuartersEntity(headQuartersEntity);
                return  this.branchRepository.save(branch);

        }

        /**
         * 根据id删除（逻辑删除，更改状态）
         * @param branchId 分公司Id
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
         * @param page 起始个数
         * @param pageSize 截至个数
         * @return 分公司数据
         */
        @Override
        @Transactional
        public String findAllBranchByPage(int page, int pageSize,HttpSession session) {
                UserEntity userEntity = (UserEntity) session.getAttribute("user");
                int superId = userEntity.getGradeId();
                List<BranchEntity> branchEntityList;
                int total;

                int offset = ((page - 1) * pageSize);
                if(superId==1){
                        branchEntityList = this.branchRepository.findAllBranchByPage2(offset, pageSize);//记录
                        total = this.branchRepository.findBranchTotal2();//数量
                }else{
                        branchEntityList = this.branchRepository.findAllBranchByPage(offset, pageSize,userEntity.getId());//记录
                        total = this.branchRepository.findBranchTotal(userEntity.getId());//数量
                }

                JSONArray jsonArray1 = new JSONArray();//新建json数组
                JSONObject jsonObject1 = new JSONObject();

                JSONArray jsonArray = JSONArray.fromObject(branchEntityList);
                String headQuartersName;
                for (int i = 0; i < jsonArray.size(); i++) {
                        String userName="";
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int headQuartersId = Integer.parseInt(jsonObject.get("headQuartersId").toString());
                        String userStr = jsonObject.get("userId").toString();
                        if(userStr!="" && !userStr.equals("0")){
                                int userId = Integer.parseInt(userStr);
                                userName = this.userRepository.findUserById(userId).getName();
                        }
                        headQuartersName = this.headQuartersRepository.findHeadQuartersById(headQuartersId).getName();//查出总部名称

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
         * @return 总公司分公司内容
         */
        @Override
        @Transactional
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
         * @return 总公司分公司代理商结果
         */
        @Override
        @Transactional
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
         * @return 状态为1的用户结果集
         */
        @Override
        @Transactional
        public List<UserEntity> findAllByStatus() {
                return this.userRepository.findAllByStatus();
        }

        /**
         * 根据分公司id查询下面的合同
         * @param branchId 分公司Id
         */
        @Override
        @Transactional
        public String findContractsByBranchId(int branchId) {
                List<ContractEntity> list = this.contractRepository.findContractsByBranchId(branchId);
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

                JSONArray jsonArray = JSONArray.fromObject(list,config);
                JSONArray jsonArray1 = new JSONArray();
                String placeName = "";
                String fileUrl = "";
                String fileName = "";
                for (int i = 0; i <jsonArray.size() ; i++) {
                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        int placeId = Integer.parseInt(jsonObject.get("placeId").toString());

                        PlaceEntity placeEntity = this.placeRepository.findPlaceById(placeId);
                        placeName=placeEntity.getName();
                        fileUrl = placeEntity.getFile();
                        fileName=placeEntity.getFileName();
                        jsonObject.put("placeName",placeName);
                        jsonObject.put("fileUrl",fileUrl);
                        jsonObject.put("fileName",fileName);
                        jsonArray1.add(jsonObject);
                }

                return jsonArray1.toString();
        }


        /**
         * 根据分公司id查询历史合同
         * @param branchId 分公司Id
         * @return 历史合同
         */
        @Override
        @Transactional
        public String findHistoryContractByBranchId(int branchId) {
                List<ContractEntity> list = this.contractRepository.findHistoryContractByBranchId(branchId);
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

                JSONArray jsonArray = JSONArray.fromObject(list,config);
                JSONArray jsonArray1 = new JSONArray();
                String placeName = "";
                String fileUrl = "";
                String fileName = "";
                for (int i = 0; i <jsonArray.size() ; i++) {
                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        int placeId = Integer.parseInt(jsonObject.get("placeId").toString());

                        PlaceEntity placeEntity = this.placeRepository.findPlaceById(placeId);
                        placeName=placeEntity.getName();
                        fileUrl = placeEntity.getFile();
                        fileName=placeEntity.getFileName();
                        jsonObject.put("placeName",placeName);
                        jsonObject.put("fileUrl",fileUrl);
                        jsonObject.put("fileName",fileName);
                        jsonArray1.add(jsonObject);
                }

                return jsonArray1.toString();
        }

        /**
         * 根据分公司id查询下面的场地
         * @param branchId 分公司Id
         * @return 场地结果集合
         */
        @Override
        @Transactional
        public List<PlaceEntity> findAllPlaceByBranchId(int branchId) {
                return this.branchRepository.findAllPlaceByBranchId(branchId);
        }

        /**
         * 分公司绑定场地
         * @param branchId 分公司Id
         * @param placeId  场地Id
         */
        @Override
        @Transactional
        public void branchBoundPlace(int branchId, int placeId) {
                List<Integer> list = this.placeRepository.findAllPlaceChildById(placeId);//查出placeId下的所有场地id

                WxUtil wxUtil = new WxUtil();
                Timestamp ts = wxUtil.getNowDate();//获取当前时间(时间戳)
                DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String timer = sdf.format(ts);
                String contractCode = "合同_"+timer;

                ContractEntity contractEntity=new ContractEntity();
                contractEntity.setOwner(branchId);//甲方
                contractEntity.setSecond(placeId);//乙方
                contractEntity.setFlag(2);//分公司签约
                contractEntity.setUseFlag(1);//使用中
                contractEntity.setContractCode(contractCode);//合同编号
                contractEntity.setPlaceId(placeId);
                this.contractRepository.save(contractEntity);

                for (int i = 0; i <list.size() ; i++) {
                        Integer placeChildId = list.get(i);
                        PlaceEntity placeEntity = this.placeRepository.findPlaceById(placeChildId);
                        placeEntity.setLevelFlag(2);
                        placeEntity.setSuperiorId(branchId);
                        this.placeRepository.save(placeEntity);
                }
        }
}
