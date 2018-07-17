package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.repository.PlaceRepository;
import com.sv.mc.repository.VendorRepository;
import com.sv.mc.service.PlaceService;
import com.sv.mc.util.BaseUtil;
import com.sv.mc.util.DataSourceResult;
import com.sv.mc.util.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
        @Autowired
        private PlaceRepository placeRepository;
        @Autowired
        private VendorRepository vendorRepository;

        /**1
         * 保存缓存数据
         * @param place 场地数据
         * @return
         */
        @Override
        public PlaceEntity save(PlaceEntity place) {
                return this.placeRepository.save(place);
        }
        /**2
         * 查询所有场地数据列表
         * @return List<PranchEntity>
         */
        @Override
        @Transactional
        public List<PlaceEntity> findAllEntities() {
            return  this.placeRepository.findAll();
        }

        /**3
         * 根据场地id查询对应场地数据
         * @param id  分公司id
         * @return  PranchEntity
         */
        @Override
        @Transactional
        public PlaceEntity findPlaceById(int id) {
            return this.placeRepository.findPlaceById(id);
        }

        /**5
         * 根据场地id更改场地数据
         * @param id 场地
         * @param place 场地实体类
         * @return PranchEntity
         */
        @Override
        public PlaceEntity updatePlaceById(int id, PlaceEntity place) {
                return  this.placeRepository.save(place);

        }


        @Override
        public List findPlace(int id) {
                return null;
        }


        @Override
        public String findAllPlaceByPage(int page, int pageSize) {
                Gson gson = new Gson();
                DataSourceResult<PlaceEntity> placeEntityDataSourceResult = new DataSourceResult<>();
                int offset = ((page-1)*pageSize);
                List<PlaceEntity> placeEntityList = this.placeRepository.findAllPlaceByPage(offset,pageSize);
                int total = this.placeRepository.findPlaceTotal();
                placeEntityDataSourceResult.setData(placeEntityList);
                placeEntityDataSourceResult.setTotal(total);


                String result = gson.toJson(placeEntityDataSourceResult);
//                System.out.println(result);
                JSONObject jsonObject = JSONObject.fromObject(result);
                JSONArray jsonArray =jsonObject.getJSONArray("data");
                JSONArray jsonArray1 = new JSONArray();
                String superiorName="";
                String levelFlagName="";
                for (int i = 0; i <jsonArray.size() ; i++) {
                        JSONObject jsonObject12 =jsonArray.getJSONObject(i);
//                        System.out.println(jsonObject12);
                        int superiorId =Integer.parseInt(jsonObject12.get("superiorId").toString());
                        int levelFlag =Integer.parseInt(jsonObject12.get("levelFlag").toString());
                        if(levelFlag==1){
                                levelFlagName = "总部";
                                superiorName = this.vendorRepository.findBranchNameById(superiorId);
                        }else if(levelFlag==2){
                                levelFlagName = "分公司";
                                superiorName = this.vendorRepository.findHeadNameById(superiorId);
                        } else if(levelFlag==3){
                                levelFlagName = "代理商";
                                superiorName = this.vendorRepository.findVendorById(superiorId).getName();
                        }
                        jsonObject12.put("superiorName",superiorName);
                        jsonObject12.put("levelFlagName",levelFlagName);
                        jsonArray1.add(jsonObject12);
                }

//                System.out.println(jsonArray1.toString());

                jsonObject.put("data",jsonArray1);
                return jsonObject.toString();

//                return gson.toJson(placeEntityDataSourceResult);
        }

        @Override
        public String findAllPlace() {
                List<PlaceEntity> placeEntityList = this.placeRepository.findAllPlaces();//查询所有pid为0的
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
                config.setExcludes(new String[] { "deviceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray
                JSONArray jsonArray = JSONArray.fromObject(placeEntityList,config);//转化为jsonArray
                JSONArray jsonArray1 = new JSONArray();//新建json数组

                String superiorName="";
                String levelFlagName="";
                for (int y = 0; y <jsonArray.size() ; y++) {
                        JSONObject jsonObject2 =jsonArray.getJSONObject(y);
                        int superiorId =Integer.parseInt(jsonObject2.get("superiorId").toString());
                        int levelFlag =Integer.parseInt(jsonObject2.get("levelFlag").toString());
                        if(levelFlag==1){
                                levelFlagName = "总部";
                                superiorName = vendorRepository.findHeadNameById(superiorId);
                        }else if(levelFlag==2){
                                levelFlagName = "分公司";
                                superiorName = vendorRepository.findBranchNameById(superiorId);
                        } else if(levelFlag==3){
                                levelFlagName = "代理商";
                                superiorName = vendorRepository.findVendorById(superiorId).getName();
                        }
                        jsonObject2.put("superiorName",superiorName);
                        jsonObject2.put("levelFlagName",levelFlagName);
                        jsonArray1.add(jsonObject2);
                }
                return jsonArray1.toString();
        }

        /**4
         * 插入一条场地数据
         * @param place
         */
        @Override
        public PlaceEntity insertPlace(PlaceEntity place) {
                place.setpId(0);
                place.setPlaceLevelId(1);
                place.setDiscardStatus(1);
                return  this.placeRepository.save(place);
        }

        @Override
        public PlaceEntity updatePlace(PlaceEntity placeEntity) {
                return this.placeRepository.save(placeEntity);
        }

        @Override
        public void deletePlace(int placeId) {
                PlaceEntity placeEntity = findPlaceById(placeId);
                placeEntity.setDiscardStatus(0);
                this.placeRepository.save(placeEntity);

        }

        @Override
        public List<DeviceEntity> findDeviceByPlace(int placeId) {
                List<DeviceEntity> deviceEntities = this.placeRepository.findAllDeviceByPlaceId(placeId);
                return deviceEntities;
        }


        @Override
        public String findPlaceByParentId(int placeId) {
                List<PlaceEntity> placeEntityList = this.placeRepository.findPlaceByParentId(placeId);
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
                config.setExcludes(new String[] { "deviceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray
                JSONArray jsonArray = JSONArray.fromObject(placeEntityList,config);//转化为jsonArray
                JSONArray jsonArray1 = new JSONArray();//新建json数组

                String superiorName="";
                String levelFlagName="";
                for (int i = 0; i <jsonArray.size() ; i++) {
                        JSONObject jsonObject12 =jsonArray.getJSONObject(i);
//                        System.out.println(jsonObject12);
                        int superiorId =Integer.parseInt(jsonObject12.get("superiorId").toString());
                        int levelFlag =Integer.parseInt(jsonObject12.get("levelFlag").toString());
                        if(levelFlag==1){

                                levelFlagName = "总部";
                                superiorName = this.vendorRepository.findHeadNameById(superiorId);
                        }else if(levelFlag==2){
                                levelFlagName = "分公司";
                                superiorName = this.vendorRepository.findBranchNameById(superiorId);
                        } else if(levelFlag==3){
                                levelFlagName = "代理商";
                                superiorName = this.vendorRepository.findVendorById(superiorId).getName();
                        }
                        jsonObject12.put("superiorName",superiorName);
                        jsonObject12.put("levelFlagName",levelFlagName);
                        jsonArray1.add(jsonObject12);
                }
                return jsonArray1.toString();
        }


        @Override
        public List<PlaceEntity> findAllPlaces() {
                return this.placeRepository.findAllPlaces();
        }
}
