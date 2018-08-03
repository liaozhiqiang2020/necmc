package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.ContractEntity;
import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.repository.ContractRepository;
import com.sv.mc.repository.HeadQuartersRepository;
import com.sv.mc.repository.PlaceRepository;
import com.sv.mc.service.HeadQuartersService;
import com.sv.mc.util.DataSourceResult;
import com.sv.mc.util.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * author:赵政博
 * 业务层
 */
@Service
public class HeadQuartersServiceImpl implements HeadQuartersService {
        @Autowired
        private HeadQuartersRepository headQuartersRepository;
        @Autowired
        private PlaceRepository placeRepository;
        @Autowired
        private ContractRepository contractRepository;
    /**
     * 保存
     * @param headQuarters 总公司数据
     * @return HeadQuartersEntity
     */
    @Override
    @Transactional
    public HeadQuartersEntity save(HeadQuartersEntity headQuarters) {
        return headQuartersRepository.save(headQuarters);
    }
    /**
     * 根据总公司id更改总公司数据
     * @param id  总公司id
     * @return HeadQuartersEntity
     */
    @Override
    @Transactional
    public HeadQuartersEntity findHeadQuartersById(int id) {
        return headQuartersRepository.findHeadQuartersById(id);
    }


    @Override
    @Transactional
    public List findAllEntities() {
        return headQuartersRepository.findAll();
    }


    /**
     * 分页查询总公司数据
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @Transactional
    public String findAllHeadByPage(int page, int pageSize) {
        Gson gson = new Gson();
        DataSourceResult<HeadQuartersEntity> branchEntityDataSourceResult = new DataSourceResult<>();
        int offset = ((page-1)*pageSize);
        List<HeadQuartersEntity> headQuartersEntities = this.headQuartersRepository.findAllHeadByPage(offset,pageSize);
        int total = this.headQuartersRepository.findHeadTotal();
        branchEntityDataSourceResult.setData(headQuartersEntities);
        branchEntityDataSourceResult.setTotal(total);
        return gson.toJson(branchEntityDataSourceResult);
    }

    /**
     * 修改数据
     * @param headQuartersEntity
     * @return
     */
    @Override
    @Transactional
    public HeadQuartersEntity updateHeadDataById(HeadQuartersEntity headQuartersEntity) {
        return this.headQuartersRepository.save(headQuartersEntity);
    }

    /**
     * 新增数据
     * @param headQuartersEntity
     * @return
     */
    @Override
    @Transactional
    public HeadQuartersEntity insertHead(HeadQuartersEntity headQuartersEntity) {
        headQuartersEntity.setDeleteFlag(1);
        return this.headQuartersRepository.save(headQuartersEntity);
    }

    /**
     * 逻辑删除数据
     * @param headId
     */
    @Override
    @Transactional
    public void deleteHead(int headId) {
        HeadQuartersEntity headQuartersEntity = findHeadQuartersById(headId);
        headQuartersEntity.setDeleteFlag(0);
        this.headQuartersRepository.save(headQuartersEntity);
    }


    @Override
    public List<HeadQuartersEntity> findAllHead() {
        return this.headQuartersRepository.findAllHead();
    }

    @Override
    public HeadQuartersEntity findHeadByBranchId(int branchId) {
        return this.headQuartersRepository.findHeadByBranchId(branchId);
    }

    /**
     * 根据总公司id查询下面的场地
     */
    @Override
    public List<PlaceEntity> findAllPlaceByHeadId(int headId) {
        return this.headQuartersRepository.findAllPlaceByHeadId(headId);
    }

    /**
     * 查询所有未绑定的场地
     */
    @Override
    public List<PlaceEntity> findAllUnboundPlace() {
        return this.headQuartersRepository.findAllUnboundPlace();
    }

    /**
     * 总公司绑定场地(包括所有子场地)
     */
    @Override
    public void headBoundPlace(int headId, int placeId) {
        List<Integer> list = this.placeRepository.findAllPlaceChildById(placeId);//查出placeId下的所有场地id

        ContractEntity contractEntity=new ContractEntity();
        contractEntity.setOwner(headId);//甲方
        contractEntity.setSecond(placeId);//乙方
        contractEntity.setFlag(1);//分公司签约
        contractEntity.setUseFlag(1);//使用中
        contractEntity.setPlaceId(placeId);
        this.contractRepository.save(contractEntity);

        for (int i = 0; i <list.size() ; i++) {
            Integer placeChildId = list.get(i);
            PlaceEntity placeEntity = this.placeRepository.findPlaceById(placeChildId);
            placeEntity.setLevelFlag(1);
            placeEntity.setSuperiorId(headId);
            this.placeRepository.save(placeEntity);
        }
    }

    /**
     * 解绑上级与场地解绑
     * @param placeId
     */
    @Override
    public void unboundPlace(int placeId,int supId,int flagId) {
        ContractEntity contractEntity = this.contractRepository.findContractEntityBeforeUnBound(placeId,supId,flagId);
        contractEntity.setUseFlag(0);
        this.contractRepository.save(contractEntity);

        List<Integer> list = this.placeRepository.findAllPlaceChildById(placeId);
        for (int i = 0; i <list.size() ; i++) {
            Integer placeChildId = list.get(i);
            PlaceEntity placeEntity = this.placeRepository.findPlaceById(placeChildId);
            placeEntity.setLevelFlag(null);
            placeEntity.setSuperiorId(null);
            this.placeRepository.save(placeEntity);
        }
    }


    /**
     * 根据总公司id查询下面的合同
     */
    @Override
    public String findContractByHeadId(int headId) {
        List<ContractEntity> list = this.contractRepository.findContractsByHeadId(headId);
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
     * 根据总公司id查询合同历史
     */
    @Override
    public String findHistoryContractByHeadId(int headId) {
        List<ContractEntity> list = this.contractRepository.findHistoryContractByHeadId(headId);
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
}
