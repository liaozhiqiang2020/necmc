package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.repository.PlaceRepository;
import com.sv.mc.repository.VendorRepository;
import com.sv.mc.service.PlaceService;
import com.sv.mc.util.BaseUtil;
import com.sv.mc.util.DataSourceResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
                Gson gson = new Gson();
                DataSourceResult<PlaceEntity> placeEntityDataSourceResult = new DataSourceResult<>();
                List<PlaceEntity> placeEntityList = this.placeRepository.findAllPlace();
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

//                System.out.println(jsonArray1.toString());

                jsonObject.put("data",jsonArray1);
                return jsonObject.toString();

//                return gson.toJson(placeEntityDataSourceResult);
        }

        /**4
         * 插入一条场地数据
         * @param place
         */
        @Override
        public PlaceEntity insertPlace(PlaceEntity place,String startDateTiame,String endDateTime) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");//小写的mm表示的是分钟
                ParsePosition pos = new ParsePosition(0);
                BaseUtil baseUtil = new BaseUtil();


                String nReviseDate = baseUtil.parseTime(startDateTiame);    //时间格式转换
                Date nReviseDate2 = sdf.parse(nReviseDate,pos);
                Timestamp ts1 = new Timestamp(nReviseDate2.getTime());

                String reviseDate = baseUtil.parseTime(endDateTime);    //时间格式转换
                Date reviseDate2 = sdf.parse(reviseDate,pos);
                Timestamp ts2 = new Timestamp(reviseDate2.getTime());


                place.setStartDateTime(ts1);
                place.setStartDateTime(ts2);


                place.setDiscardStatus(1);
                return  this.placeRepository.save(place);
        }

        @Override
        public PlaceEntity updatePlace(PlaceEntity placeEntity,String startDateTiame,String endDateTime) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");//小写的mm表示的是分钟
                ParsePosition pos = new ParsePosition(0);
                BaseUtil baseUtil = new BaseUtil();


                String nReviseDate = baseUtil.parseTime(startDateTiame);    //时间格式转换
                Date nReviseDate2 = sdf.parse(nReviseDate,pos);
                Timestamp ts1 = new Timestamp(nReviseDate2.getTime());

                String reviseDate = baseUtil.parseTime(endDateTime);    //时间格式转换
                Date reviseDate2 = sdf.parse(reviseDate,pos);
                Timestamp ts2 = new Timestamp(reviseDate2.getTime());


                placeEntity.setStartDateTime(ts1);
                placeEntity.setStartDateTime(ts2);

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
                PlaceEntity place = this.placeRepository.findPlaceById(placeId);
                List<DeviceEntity> devices = place.getDeviceEntities();
                return devices;
        }
}
