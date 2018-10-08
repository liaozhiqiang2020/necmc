package com.sv.mc.service.impl;

import com.sv.mc.pojo.PlaceMapEntity;
import com.sv.mc.repository.PlaceMapRepository;
import com.sv.mc.service.PlaceMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceMapServiceImpl implements PlaceMapService {
        @Autowired
        private PlaceMapRepository placeMapRepository;

        /**
         * 保存数据
         * @param place 场地图片数据
         * @return PlaceMapEntity 地图信息
         */
        @Override
        public PlaceMapEntity save(PlaceMapEntity place) {
                return placeMapRepository.save(place);
        }

        /**
         * 根据id查询相对的数据
         * @param id  场地id
         * @return PlaceMapEntity 地图信息集合
         */
        @Override
        public PlaceMapEntity findPlaceMapById(int id) {
                return placeMapRepository.findDeviceById(id);
        }

        /**
         * 根据id更改数据
         * @param id  场地id
         * @param placeMap 场地图片
         * @return PlaceMapEntity 地图信息
         */
        @Override
        public PlaceMapEntity updatePlaceMapById(int id, PlaceMapEntity placeMap) {
                return placeMapRepository.save(placeMap);
        }

        /**
         * 插入一条新数据
         * @param placeMap
         * @return PlaceMapEntity 地图信息
         */
        @Override
        public PlaceMapEntity insertPlaceMap(PlaceMapEntity placeMap) {
                return placeMapRepository.save(placeMap);
        }

        /**
         * 查询所有地图数据
          * @return  List地图信息集合
         */
        @Override
        public List findAllEntities() {
                return placeMapRepository.findAll();
        }
}
