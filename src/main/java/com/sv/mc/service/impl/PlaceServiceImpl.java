package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.*;
import com.sv.mc.repository.*;
import com.sv.mc.service.BusinessService;
import com.sv.mc.service.PlaceService;
import com.sv.mc.util.DataSourceResult;
import com.sv.mc.util.DateJsonValueProcessor;
import com.sv.mc.util.WxUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PlaceServiceImpl implements PlaceService {
        @Autowired
        private PlaceRepository placeRepository;
        @Autowired
        private VendorRepository vendorRepository;
        @Resource
        private DeviceRepository deviceRepository;
        @Autowired
        private BusinessRepository businessRepository;
        @Autowired
        private CityRepository cityRepository;

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
                int offset = ((page-1)*pageSize);
                List<PlaceEntity> placeEntityList = this.placeRepository.findAllPlaceByPage(offset,pageSize);
                int total = this.placeRepository.findPlaceTotal();

                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
                config.setExcludes(new String[] { "deviceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray

                JSONArray jsonArray = JSONArray.fromObject(placeEntityList,config);
                JSONArray jsonArray1 = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();
                String superiorName="";
                String levelFlagName="";
                for (int i = 0; i <jsonArray.size() ; i++) {
                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        Integer superiorId =Integer.parseInt(jsonObject.get("superiorId").toString());
                        int levelFlag =Integer.parseInt(jsonObject.get("levelFlag").toString());
                        int businessId = Integer.parseInt(jsonObject.get("businessId").toString());
                        int cityId = Integer.parseInt(jsonObject.get("cityId").toString());
                        if(superiorId!=null){
                                if(levelFlag==1){
                                        levelFlagName = "总部";
                                        superiorName = this.vendorRepository.findHeadNameById(superiorId).getName();
                                }else if(levelFlag==2){
                                        levelFlagName = "分公司";
                                        superiorName = this.vendorRepository.findBranchNameById(superiorId).getName();
                                } else if(levelFlag==3){
                                        levelFlagName = "代理商";
                                        superiorName = this.vendorRepository.findVendorById(superiorId).getName();
                                }
                        }
                        String businessName=this.businessRepository.findBusinessById(businessId).getName();
                        String cityName = this.cityRepository.findCityById(cityId).getName();

                        jsonObject.put("superiorName",superiorName);
                        jsonObject.put("levelFlagName",levelFlagName);
                        jsonObject.put("businessName",businessName);
                        jsonObject.put("cityName",cityName);
                        jsonArray1.add(jsonObject);
                }

                jsonObject2.put("data",jsonArray1);
                jsonObject2.put("total",total);
                return jsonObject2.toString();
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
                                superiorName = vendorRepository.findHeadNameById(superiorId).getName();
                        }else if(levelFlag==2){
                                levelFlagName = "分公司";
                                superiorName = vendorRepository.findBranchNameById(superiorId).getName();
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
         * @param
         */
        @Override
        public PlaceEntity insertPlace(Map map) {
                PlaceEntity placeEntity = new PlaceEntity();
                Object id = map.get("id");
                Object businessId = map.get("businessId");
                Object cityId = map.get("cityId");
                Object endDateTime = map.get("endDateTime");
                Object startDateTime = map.get("startDateTime");
                Object name = map.get("name");
                Object placeAddress = map.get("placeAddress");
                Object placeSn = map.get("placeSn");
                Object principal = map.get("principal");
                int superiorId = Integer.parseInt(map.get("superiorId").toString().split("_")[0]);//上级公司id
                String superiorName = map.get("superiorId").toString().split("_")[1];//上级公司name

                placeEntity.setDiscardStatus(1);
                if(id!=null){
                        placeEntity.setId(Integer.parseInt(id.toString()));
                }
                placeEntity.setName(name.toString());
                placeEntity.setPrincipal(principal.toString());
                placeEntity.setPlaceAddress(placeAddress.toString());
                placeEntity.setBusinessId(Integer.parseInt(businessId.toString()));
                placeEntity.setCityId(Integer.parseInt(cityId.toString()));
                placeEntity.setPlaceSn(placeSn.toString());
                placeEntity.setEndDateTime(Timestamp.valueOf(endDateTime.toString()));
                placeEntity.setStartDateTime(Timestamp.valueOf(startDateTime.toString()));
                placeEntity.setpId(null);

                HeadQuartersEntity headQuartersEntity = this.vendorRepository.findHeadNameByIdAndName(superiorId,superiorName);//根据id查询总部信息
                if(headQuartersEntity==null){  //如果分公司表中没有查到数据，就查总部表
                        BranchEntity branchEntity = this.vendorRepository.findBranchNameByIdAndName(superiorId,superiorName);//根据id查询分公司信息
                        if(branchEntity==null){
                                VendorEntity vendorEntity = this.vendorRepository.findVendorById(superiorId);//根据id查询代理商信息
                                placeEntity.setLevelFlag(3);
                                placeEntity.setSuperiorId(vendorEntity.getId());
                        }else{
                                placeEntity.setLevelFlag(2);
                                placeEntity.setSuperiorId(branchEntity.getId());
                        }
                }else{
                        placeEntity.setLevelFlag(1);
                        placeEntity.setSuperiorId(headQuartersEntity.getId());
                }

                return this.placeRepository.save(placeEntity);
        }


        /**
         * 修改场地数据
         * @param
         * @return
         */
        @Override
        public PlaceEntity updatePlace(Map map) {
                PlaceEntity placeEntity = new PlaceEntity();
                Object id = map.get("id");
                Object businessId = map.get("businessId");
                Object cityId = map.get("cityId");
                Object endDateTime = map.get("endDateTime");
                Object startDateTime = map.get("startDateTime");
                Object name = map.get("name");
                Object placeAddress = map.get("placeAddress");
                Object placeSn = map.get("placeSn");
                Object principal = map.get("principal");
                int superiorId = Integer.parseInt(map.get("superiorId").toString().split("_")[0]);//上级公司id
                String superiorName = map.get("superiorId").toString().split("_")[1];//上级公司name

                placeEntity.setDiscardStatus(1);
                if(id!=null){
                        placeEntity.setId(Integer.parseInt(id.toString()));
                }
                placeEntity.setpId(null);
                placeEntity.setName(name.toString());
                placeEntity.setPrincipal(principal.toString());
                placeEntity.setPlaceAddress(placeAddress.toString());
                placeEntity.setBusinessId(Integer.parseInt(businessId.toString()));
                placeEntity.setCityId(Integer.parseInt(cityId.toString()));
                placeEntity.setPlaceSn(placeSn.toString());
                placeEntity.setEndDateTime(Timestamp.valueOf(endDateTime.toString()));
                placeEntity.setStartDateTime(Timestamp.valueOf(startDateTime.toString()));


                HeadQuartersEntity headQuartersEntity = this.vendorRepository.findHeadNameByIdAndName(superiorId,superiorName);//根据id查询总部信息
                if(headQuartersEntity==null){  //如果分公司表中没有查到数据，就查总部表
                        BranchEntity branchEntity = this.vendorRepository.findBranchNameByIdAndName(superiorId,superiorName);//根据id查询分公司信息
                        if(branchEntity==null){
                                VendorEntity vendorEntity = this.vendorRepository.findVendorById(superiorId);//根据id查询代理商信息
                                placeEntity.setLevelFlag(3);
                                placeEntity.setSuperiorId(vendorEntity.getId());
                        }else{
                                placeEntity.setLevelFlag(2);
                                placeEntity.setSuperiorId(branchEntity.getId());
                        }
                }else{
                        placeEntity.setLevelFlag(1);
                        placeEntity.setSuperiorId(headQuartersEntity.getId());
                }

                return this.placeRepository.save(placeEntity);
        }

        /**4
         * 插入一条场地数据
         * @param place
         */
        @Override
        public PlaceEntity insertPlaceChild(PlaceEntity place) {
                place.setDiscardStatus(1);
                return  this.placeRepository.save(place);
        }

        @Override
        public PlaceEntity updatePlaceChild(PlaceEntity placeEntity) {
                return this.placeRepository.save(placeEntity);
        }

        @Override
        public void deletePlace(int placeId) {
                PlaceEntity placeEntity = findPlaceById(placeId);
                placeEntity.setDiscardStatus(0);
                this.placeRepository.save(placeEntity);

        }

        @Override
        public List<DeviceEntity> findDeviceByPlace(int pId) {
                List<Object[]> deviceEntities = this.placeRepository.findAllChildById(pId);
//                List<Map<String,Object>> mapList = new ArrayList<>();
                List<DeviceEntity> deviceList = new ArrayList<>();
                for (int i = 0; i <deviceEntities.size() ; i++) {
                        Object[] object =deviceEntities.get(i);
                        int id = Integer.parseInt(object[0].toString());
                        deviceList.add(this.deviceRepository.findDeviceById(id));
                }

                return deviceList;

        }

        @Override
        public List<DeviceEntity> findDeviceByPlaceId(int placeId) {
                return this.placeRepository.findAllDeviceByPlaceId(placeId);
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
                                superiorName = this.vendorRepository.findHeadNameById(superiorId).getName();
                        }else if(levelFlag==2){
                                levelFlagName = "分公司";
                                superiorName = this.vendorRepository.findBranchNameById(superiorId).getName();
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
                return this.placeRepository.findAllPlace();
        }


        @Override
        public List<PlaceEntity> getPlace(int cityId) {
                return this.placeRepository.queryPlaceEntitiesByCityId(cityId);
        }


        /**
         * 不分页查询第一级场地数据
         */
        @Override
        public List<PlaceEntity> findAllPlaceFirst() {
                return this.placeRepository.findAllPlaces();
        }
}
