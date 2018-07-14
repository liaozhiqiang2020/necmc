package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.repository.PlaceRepository;
import com.sv.mc.service.PlaceService;
import com.sv.mc.util.DataSourceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
        @Autowired
        private PlaceRepository placeRepository;

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


        /**4
         * 插入一条场地数据
         * @param place
         */
        @Override
        public PlaceEntity insertPlace(PlaceEntity place) {
                place.setDiscardStatus(1);
                return  this.placeRepository.save(place);
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
                return gson.toJson(placeEntityDataSourceResult);
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
}
