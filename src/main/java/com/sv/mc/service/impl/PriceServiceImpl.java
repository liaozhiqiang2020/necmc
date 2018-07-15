package com.sv.mc.service.impl;

import com.sv.mc.pojo.PriceEntity;
import com.sv.mc.pojo.PriceHistoryEntity;
import com.sv.mc.repository.PriceHistoryRepository;
import com.sv.mc.repository.PriceRepository;
import com.sv.mc.service.PriceHistoryService;
import com.sv.mc.service.PriceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.print.PrinterException;
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
    private PriceHistoryService priceHistoryService;

    @Resource
    private PriceHistoryRepository priceHistoryRepository;

    /**
     * 批量新增、更新价格数据
     * @param priceEntityList 价格对象列表
     * @return 已经新增或更新的价格对象列表，准备返回前台画面进行展示
     */
    @Override
    public List<PriceEntity> batchSaveOrUpdatePrice(List<PriceEntity> priceEntityList) {
        List<PriceHistoryEntity> priceHistoryEntityList = new ArrayList<>(); //创建价格变化历史对象
        for (PriceEntity priceEntity : priceEntityList) {
            //给必要的成员变量赋值
            PriceHistoryEntity priceHistoryEntity = new PriceHistoryEntity();
            priceHistoryEntity.setUseTime(priceEntity.getUseTime());
            priceHistoryEntity.setCreateDateTime(priceEntity.getCreateDateTime());
            priceHistoryEntity.setEndDateTime(priceEntity.getEndDateTime());
            priceHistoryEntity.setLatestDateTime(new Timestamp(System.currentTimeMillis()));
            priceHistoryEntity.setPrice(priceEntity.getPrice());
            priceHistoryEntity.setStartDateTime(priceEntity.getStartDateTime());
            priceHistoryEntity.setStatus(priceEntity.getStatus());
            priceHistoryEntity.setUser(priceEntity.getUser());
//            priceHistoryEntity.setDeviceEntities(priceEntity.getDeviceEntities());
            priceHistoryEntityList.add(priceHistoryEntity);
        }
        //批量插入价格变化历史
        this.priceHistoryRepository.saveAll(priceHistoryEntityList);
        //批量插入或更新价格
        List<PriceEntity> persistPriceList = this.priceRepository.saveAll(priceEntityList);

        return persistPriceList;
    }

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


    /**
     * 更新价格
     * @param priceEntity 价格对象（价格对象必须包含主键ID）
     * @return 消息
     */
    @Override
    @Transactional
    public PriceEntity updatePrice(PriceEntity priceEntity) {
       return priceRepository.save(priceEntity);


    }

    /**
     * 删除价格
     * @param priceId 价格Id
     * @return 消息
     */
    @Transactional
    @Override
    public void deletePrice(int priceId) {
        PriceEntity price = priceRepository.findPriceEntitiesById(priceId);
        this.priceRepository.delete(price);
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
      return   this.priceRepository.save(priceEntity);

    }

    /**
     * 根据设备id的价格进行查询
     * @param deviceId 设备Id
     * @return 当前机器的价格集合
     */
    @Override
    public List<Object[]> findPriceByDeviceId(int deviceId) {
        return this.priceRepository.findPriceEntitiesByDeviceID(deviceId);
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
