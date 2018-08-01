package com.sv.mc.service.impl;

import com.sv.mc.pojo.*;
import com.sv.mc.repository.*;
import com.sv.mc.service.PriceService;
import com.sv.mc.util.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 价格逻辑层
 * @author 魏帅志
 */
@Service
public class PriceServiceImpl implements PriceService {

    @Resource
    private PriceRepository priceRepository;

    @Resource
    private PriceHistoryRepository priceHistoryRepository;

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
     * @return 所有价格
     */

    @Override
    @Transactional(readOnly = true)
    public String findAllPagePrice(int page, int pageSize) {
        int offset = ((page-1)*pageSize);
        List<PriceEntity> priceList = this.priceRepository.findAllPriceByPage(offset,pageSize);
        int total = this.priceRepository.findPriceTotal();
        for (PriceEntity price: priceList
                ) {
            price.setUseTime(price.getUseTime()/60);
        }
        String userName = "";
        String deviceModel = "";
        String deviceType = "";
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
//        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setExcludes(new String[] { "deviceModelEntity","user","deviceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray



//        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray jsonArray = JSONArray.fromObject(priceList,config);//转化为jsonArray
        JSONArray jsonArray1 = new JSONArray();//新建json数组
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i <jsonArray.size() ; i++) {
            JSONObject jsonObject2 =jsonArray.getJSONObject(i);
            userName = priceList.get(i).getUser().getName();
            deviceModel = priceList.get(i).getDeviceModelEntity().getName();
            deviceType = priceList.get(i).getDeviceModelEntity().getModel();
            jsonObject2.put("userName",userName);
            jsonObject2.put("deviceModel",deviceModel);
            jsonObject2.put("deviceType",deviceType);
            jsonArray1.add(jsonObject2);
        }
        jsonObject.put("data",jsonArray1);
        jsonObject.put("total",total);
        return jsonObject.toString();
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
     * 不分页查询所有可用价格
     * @return 所有价格
     */
    @Override
    @Transactional
    public String findStatusPrice() {
        List<PriceEntity> priceList = this.priceRepository.findPriceEntitiesByStatus();
        for (PriceEntity price: priceList
             ) {
            price.setUseTime(price.getUseTime()/60);
        }
        String userName = "";
        String deviceModel = "";
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
//        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setExcludes(new String[] { "deviceModelEntity","user","deviceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray



//        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray jsonArray = JSONArray.fromObject(priceList,config);//转化为jsonArray
        JSONArray jsonArray1 = new JSONArray();//新建json数组
        for (int i = 0; i <jsonArray.size() ; i++) {
            JSONObject jsonObject2 =jsonArray.getJSONObject(i);
          userName = priceList.get(i).getUser().getName();
          deviceModel = priceList.get(i).getDeviceModelEntity().getName();
            jsonObject2.put("userName",userName);
            jsonObject2.put("deviceModel",deviceModel);
            jsonArray1.add(jsonObject2);
        }
        return jsonArray1.toString();
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
        PriceEntity priceEntity1 = this.priceRepository.findPriceEntitiesById(priceEntity.getId());
        PriceHistoryEntity history = new PriceHistoryEntity();
        history.setPriceName(priceEntity.getPriceName());
        history.setCreateDateTime(priceEntity1.getCreateDateTime());
        history.setEndDateTime(priceEntity.getEndDateTime());
        history.setStartDateTime(priceEntity.getStartDateTime());
        history.setStatus(priceEntity.getStatus());
        history.setUser(priceEntity.getUser());
        history.setEditTime(new Timestamp(System.currentTimeMillis()));
        history.setNewPrice(priceEntity.getPrice());
        history.setNewUseTime(priceEntity.getUseTime());
        history.setOldPrice(priceEntity1.getPrice());
        history.setOldUseTime(priceEntity1.getUseTime());
        this.priceHistoryRepository.save(history);
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
        PriceHistoryEntity history = new PriceHistoryEntity();
        history.setPriceName(priceEntity.getPriceName());
        history.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        history.setEndDateTime(priceEntity.getEndDateTime());
        history.setStartDateTime(priceEntity.getStartDateTime());
        history.setStatus(0);
        history.setUser(priceEntity.getUser());
        history.setEditTime(new Timestamp(System.currentTimeMillis()));
        history.setNewPrice(priceEntity.getPrice());
        history.setNewUseTime(priceEntity.getUseTime());
        history.setOldPrice(priceEntity.getPrice());
        history.setOldUseTime(priceEntity.getUseTime());
        this.priceHistoryRepository.save(history);
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
     * @param map 价格对象
     * @return 消息
     */
    @Override
    @Transactional
    public PriceEntity addPrice( Map<String,Object> map, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        UserEntity userEntity = this.userRepository.findUserById(user.getId());
        PriceEntity priceEntity = new PriceEntity();
        int useTime = (int)map.get("useTime")*60;
        int deviceModelId =Integer.parseInt((String) map.get("deviceModel"));
        DeviceModelEntity deviceModelEntity = this.deviceModelRepository.findById(deviceModelId);
        BigDecimal price = new BigDecimal((int)map.get("price"));
        priceEntity.setUseTime(useTime);
        priceEntity.setStatus(1);
        priceEntity.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        priceEntity.setUser(userEntity);
        priceEntity.setDeviceModelEntity(deviceModelEntity);
        priceEntity.setPrice(price);
        priceEntity.setEndDateTime(Timestamp.valueOf((String) map.get("endDateTime")));
        priceEntity.setStartDateTime(Timestamp.valueOf(map.get("startDateTIme").toString()));
        priceEntity.setPriceName((String)map.get("priceName"));
//        priceEntity.setDeviceModelEntity();
        PriceHistoryEntity history = new PriceHistoryEntity();
        history.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        history.setEndDateTime(Timestamp.valueOf(map.get("endDateTime").toString()));
        history.setStartDateTime(Timestamp.valueOf(map.get("startDateTIme").toString()));
        history.setPriceName((String)map.get("priceName"));
        history.setStatus(1);
        history.setUser(userEntity);
        history.setEditTime(new Timestamp(System.currentTimeMillis()));
        history.setNewPrice(price);
        history.setNewUseTime(useTime);
        history.setOldPrice(price);
        history.setOldUseTime(useTime);
        this.priceHistoryRepository.save(history);
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
            if(!priceEntityList.contains(price)){
                priceList.add(price);
            }
        }

        return priceList;
    }

    @Transactional
    @Override
    public List<PriceEntity> deviceSavePrice(Map<String,Object> listMap) {
        Object deviceId = listMap.get("deviceId");
        Object priceId = listMap.get("price");
        DeviceEntity device = this.deviceRepository.findDeviceById((int)deviceId);

        PriceEntity price  = findPriceById((int) priceId);
        List<PriceEntity> devicePrice= device.getPriceEntities();
        for (int i= 0; i<devicePrice.size();i++){
            if (devicePrice.get(i).getUseTime() == price.getUseTime()){
                device.getPriceEntities().remove(devicePrice.get(i));
            }
        }

        device.getPriceEntities().add(price);
        this.deviceRepository.save(device);

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
    @Transactional
    public List<PriceEntity> placeAddPrice(Map<String,Object> listMap) {

        Object placeId = listMap.get("placeId");
        Object priceId = listMap.get("price");
        List<Object[]> deviceEntities = this.placeRepository.findAllChildById((int)placeId);
        List<DeviceEntity> deviceList = new ArrayList<>();
        PriceEntity priceEntity = this.priceRepository.findPriceEntitiesById((int)priceId);
        for (int i = 0; i <deviceEntities.size() ; i++) {
            Object[] object =deviceEntities.get(i);
            int id = Integer.parseInt(object[0].toString());
            deviceList.add(this.deviceRepository.findDeviceById(id));
        }
        System.out.println(deviceList);
        for (DeviceEntity device: deviceList
                ) {
                if (device.getDeviceModelEntity() == priceEntity.getDeviceModelEntity()){
                    List<PriceEntity> priceEntityList = device.getPriceEntities();
                    if (!priceEntityList.contains(priceEntity)){
                     for (int i=0;i<priceEntityList.size();i++){
                         if(priceEntityList.get(i).getUseTime() == priceEntity.getUseTime()){
                             device.getPriceEntities().remove(priceEntityList.get(i));
                         }
                     }
                        device.getPriceEntities().add(priceEntity);
                    }

//                    List<PriceEntity> priceEntityList = device.getPriceEntities();
//                        for  ( int  i  =   0 ; i  <  priceEntityList.size()  -   1 ; i ++ )  {
//                            for  ( int  j  =  priceEntityList.size()  -   1 ; j  >  i; j -- )  {
//                                if  (priceEntityList.get(j).equals(priceEntityList.get(i)))  {
//                                    priceEntityList.remove(j);
//                                }
//                            }
//                        }
            }
            }

        this.deviceRepository.saveAll(deviceList);
        return this.priceRepository.findPriceEntitiesByStatus();
    }


    /**
     * 根据设备id查询价格和时间
     * @param deviceCode
     * @return
     */
    @Override
    public List<Map<String, Object>> queryPriceAndTime(String deviceCode) {
        List<Map<String, Object>> listmap = new ArrayList<>();
        int deviceId = this.deviceRepository.queryDeviceIdByDeviceCode(deviceCode);
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

    /**
     * 查询所有导出数据
     * @return
     */
    @Override
    public List<PriceEntity> findAllPagePrice() {

        return this.priceRepository.findAllPrice();
    }
}
