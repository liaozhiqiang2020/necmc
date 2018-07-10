package com.sv.mc.service.impl;

import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.repository.PlaceRepository;
import com.sv.mc.service.PlaceService;
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
        /**4
         * 插入一条场地数据
         * @param place
         */
        @Override
        public PlaceEntity insertPlace(PlaceEntity place) {
                return  this.placeRepository.save(place);
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

}
