package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.*;
import com.sv.mc.repository.*;
import com.sv.mc.service.PriceService;
import com.sv.mc.weixinpay.vo.Json;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 价格逻辑层
 * @author 魏帅志
 */
@Service
public class PriceServiceImpl implements PriceService {

    @Resource
    private PriceRepository priceRepository;

    @Resource
    private PriceHistoryServiceImpl priceHistoryService;

    @Resource
    private DeviceModelRepository deviceModelRepository;

    @Resource
    private DeviceRepository deviceRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private PlaceRepository placeRepository;


    /**
     * 分页查询所有价格
     * @param pageable 分页需要的条件
     * @return 所有价格
     */

    @Override
    @Transactional(readOnly = true)
    public Page<PriceEntity> findAllPagePrice(Pageable pageable) {

        return this.priceRepository.findAll(pageable);
    }


    /**
     * 不分页查询所有价格
     * @return 所有价格
     */

    @Transactional(readOnly = true)
    @Override
    public List<PriceEntity> findAllPrice() {
        return this.priceRepository.findAll();
    }

    @Override
    public List<PriceEntity> findStatusPrice() {
        return this.priceRepository.findPriceEntitiesByStatus();
    }


    /**
     * 更新价格
     * @param priceEntity 价格对象（价格对象必须包含主键ID）
     * @return 消息
     */
    @Override
    @Transactional
    public PriceEntity updatePrice(PriceEntity priceEntity) {
        int oldTime = priceEntity.getUseTime();
        int useTime = oldTime*60;
        priceEntity.setUseTime(useTime);
        return priceRepository.save(priceEntity);


    }

    /**
     * 删除价格
     * @param priceEntity 价格Id
     * @return 消息
     */
    @Transactional
    @Override
    public void deletePrice(PriceEntity priceEntity) {
        priceEntity.setStatus(0);
        this.priceRepository.save(priceEntity);
    }

    /**
     * 根据id查询价格
     * @param Id    价格ID
     * @return
     */
    @Override
    public PriceEntity findPriceById(int Id) {
        return this.priceRepository.findPriceEntitiesById(Id);
    }

    /**
     * 添加价格
     * @param priceEntity 价格对象
     * @return 消息
     */
    @Override
    @Transactional
    public PriceEntity addPrice(PriceEntity priceEntity) {
        int oldTime = priceEntity.getUseTime();
        int useTime = oldTime*60;
        priceEntity.setUseTime(useTime);
        priceEntity.setStatus(1);
        priceEntity.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        UserEntity userEntity =userRepository.findUserById(1);
        priceEntity.setUser(userEntity);
        DeviceModelEntity deviceModelEntity = deviceModelRepository.findById(1);
        priceEntity.setDeviceModelEntity(deviceModelEntity);
        return   this.priceRepository.save(priceEntity);

    }

    /**
     * 批量删除或者保存价格数据
     * @param priceEntityList
     * @return 页面需要回显新的价格数据
     */
    @Override
    @Transactional
    public List<PriceEntity> batchSaveOrUpdatePrice(List<PriceEntity> priceEntityList) {
        for (PriceEntity priceEntity:priceEntityList) {
            priceEntity.setUseTime(priceEntity.getUseTime()*60);
        }
        List<PriceEntity> priceEntities = this.priceRepository.saveAll(priceEntityList);
        return priceEntities;
    }

    @Transactional
    @Override
    public List<PriceEntity> batchDeletePrice(List<PriceEntity> priceEntityList) {
        for (PriceEntity priceEntity:priceEntityList){
            priceEntity.setStatus(0);
        }
        List<PriceEntity> priceEntities = this.priceRepository.saveAll(priceEntityList);
        return priceEntities;
    }

    /**
     * 根据设备id的价格进行查询
     * @param deviceId 设备Id
     * @return 当前机器的价格集合
     */
    @Transactional(readOnly = true)
    @Override
    public List<Object[]> findPriceByDeviceId(int deviceId) {
        return this.priceRepository.findPriceEntitiesByDeviceID(deviceId);
    }

    @Transactional
    @Override
    public List<PriceEntity> findDevicePrice(int deviceId) {

        return priceRepository.findDevicePrice(deviceId);
    }

    //查询该设备当前未绑定的价格
    @Override
    @Transactional
    public List<PriceEntity> findUnDevicePrice(int deviceId) {
        List<PriceEntity> priceAll = this.priceRepository.findPriceEntitiesByMcType(deviceId);
        List<PriceEntity> priceEntityList = this.priceRepository.findDevicePrice(deviceId);
        List<PriceEntity> priceList = new ArrayList<>();
        for (PriceEntity price: priceAll
             ) {

        }

        return priceAll;
    }

    @Transactional
    @Override
    public List<PriceEntity> deviceSavePrice(Map<String,Object> listMap) {
        Object deviceId = listMap.get("deviceId");
        Object price = listMap.get("price");
        DeviceEntity device = this.deviceRepository.findDeviceById((int)deviceId);
        for (int priceId: (ArrayList<Integer>)price
                ) {
            device.getPriceEntities().add(this.priceRepository.findPriceEntitiesById(priceId));
        }
        return device.getPriceEntities();
    }

    @Transactional
    @Override
    public List<PriceEntity> deviceDeletePrice(Map<String,Object> listMap) {
        Object deviceId = listMap.get("deviceId");
        Object price = listMap.get("price");
        DeviceEntity device = this.deviceRepository.findDeviceById((int)deviceId);
        for (int priceId: (ArrayList<Integer>)price
                ) {
            device.getPriceEntities().remove(this.priceRepository.findPriceEntitiesById(priceId));
        }
        return device.getPriceEntities();
    }

    //为场地上某种类型的机器添加价格
    @Override
    public List<PriceEntity> placeAddPrice(Map<String,Object> listMap) {

        Object placeId = listMap.get("placeId");
        Object price = listMap.get("price");
        PlaceEntity place = this.placeRepository.findPlaceById((int)placeId);
        for (DeviceEntity device: place.getDeviceEntities()
                ) {
            for (int priceId: (ArrayList<Integer>)price
                    ) {
                PriceEntity priceEntity = this.priceRepository.findPriceEntitiesById(priceId);
                if (device.getDeviceModelEntity() == priceEntity.getDeviceModelEntity()){
                    device.getPriceEntities().add(priceEntity);
                    List<PriceEntity> priceEntityList = device.getPriceEntities();
                        for  ( int  i  =   0 ; i  <  priceEntityList.size()  -   1 ; i ++ )  {
                            for  ( int  j  =  priceEntityList.size()  -   1 ; j  >  i; j -- )  {
                                if  (priceEntityList.get(j).equals(priceEntityList.get(i)))  {
                                    priceEntityList.remove(j);
                                }
                            }
                        }
            }
            }
        }
        this.placeRepository.save(place);
        return this.priceRepository.findPriceEntitiesByStatus();
    }


    /**
     * 根据设备id查询价格和时间
     * @param deviceId
     * @return
     */
    @Override
    public List<Map<String, Object>> queryPriceAndTime(int deviceId) {
        List<Map<String, Object>> listmap = new ArrayList<>();
        List<PriceEntity> priceEntityList = this.priceRepository.queryPriceAndTime(deviceId);
        for (int i = 0; i <priceEntityList.size() ; i++) {
            Map<String,Object> map = new HashMap<>();
            PriceEntity priceEntity = priceEntityList.get(i);
            String priceName = priceEntity.getPriceName();
            BigDecimal price = priceEntity.getPrice();
            int useTime = priceEntity.getUseTime();

            map.put("priceName",priceName);
            map.put("price",price);
            map.put("useTime",useTime/60);
            listmap.add(map);
        }
        return listmap;
    }
}
