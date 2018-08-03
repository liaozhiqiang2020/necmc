package com.sv.mc.service.impl;

import com.sv.mc.pojo.*;
import com.sv.mc.pojo.vo.DevicePriceImport;
import com.sv.mc.repository.*;
import com.sv.mc.service.PriceService;
import com.sv.mc.util.DateJsonValueProcessor;
import com.sv.mc.util.intUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static javax.xml.bind.JAXBIntrospector.getValue;

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
            int useTime = (int)jsonObject2.get("useTime");
            int newUseTime = useTime/60;
            jsonObject2.replace("useTime",newUseTime);
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
        int total = this.priceRepository.findPriceTotal();
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
            int useTime = (int)jsonObject2.get("useTime");
            int newUseTime = useTime/60;
            jsonObject2.replace("useTime",newUseTime);
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
     * 更新价格
     * @param map 价格对象（价格对象必须包含主键ID）
     * @return 消息
     */
    @Override
    @Transactional
    public PriceEntity updatePrice(Map<String,Object> map) {
        PriceEntity priceEntity = this.priceRepository.findPriceEntitiesById((int)map.get("id"));
        int useTime = (int)map.get("useTime")*60;
        String end = (String) map.get("endDateTime");
        String start = (String) map.get("startDateTime");
        String endDate1 = new DateJsonValueProcessor().dateString(end);
        String startDate1 = new DateJsonValueProcessor().dateString(start);
        if (com.sv.mc.util.intUtil.isInteger((String) map.get("deviceModel"))){
            int deviceModelId =Integer.parseInt((String) map.get("deviceModel"));
            DeviceModelEntity deviceModelEntity = this.deviceModelRepository.findById(deviceModelId);
            priceEntity.setDeviceModelEntity(deviceModelEntity);
        }
        BigDecimal price= intUtil.getBigDecimal(map.get("price"));
//        price=price.setScale(4, BigDecimal.ROUND_HALF_UP);
//        BigDecimal price = new BigDecimal((int)map.get("price"));
        priceEntity.setUseTime(useTime);
        priceEntity.setStatus(1);
        priceEntity.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        priceEntity.setPrice(price);
        priceEntity.setEndDateTime(Timestamp.valueOf(endDate1));
        priceEntity.setStartDateTime(Timestamp.valueOf(startDate1));
        priceEntity.setPriceName((String)map.get("priceName"));

        PriceHistoryEntity history = new PriceHistoryEntity();
        history.setPriceName((String)map.get("priceName"));
        history.setCreateDateTime(priceEntity.getCreateDateTime());
        history.setEndDateTime(Timestamp.valueOf(endDate1));
        history.setStartDateTime(Timestamp.valueOf(startDate1));
        history.setStatus(1);
        history.setUser(priceEntity.getUser());
        history.setEditTime(new Timestamp(System.currentTimeMillis()));
        history.setNewPrice(price);
        history.setNewUseTime(useTime);
        history.setOldPrice(priceEntity.getPrice());
        history.setOldUseTime(priceEntity.getUseTime());
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
        int id = priceEntity.getId();
       PriceEntity price = this.priceRepository.findPriceEntitiesById(id);
       price.setStatus(0);
        PriceHistoryEntity history = new PriceHistoryEntity();
        history.setPriceName(price.getPriceName());
        history.setCreateDateTime(price.getCreateDateTime());
        history.setEndDateTime(price.getEndDateTime());
        history.setStartDateTime(price.getStartDateTime());
        history.setStatus(0);
        history.setUser(price.getUser());
        history.setEditTime(new Timestamp(System.currentTimeMillis()));
        history.setNewPrice(price.getPrice());
        history.setNewUseTime(price.getUseTime());
        history.setOldPrice(price.getPrice());
        history.setOldUseTime(price.getUseTime());
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
    public PriceEntity addPrice( Map<String,Object> map, HttpServletRequest request)  {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        UserEntity userEntity = this.userRepository.findUserById(user.getId());
        PriceEntity priceEntity = new PriceEntity();
        int useTime = (int)map.get("useTime")*60;
        String end = (String) map.get("endDateTime");
        String start = (String) map.get("startDateTime");
        String endDate1 = new DateJsonValueProcessor().dateString(end);
        String startDate1 = new DateJsonValueProcessor().dateString(start);
        int deviceModelId =Integer.parseInt((String) map.get("deviceModel"));
        DeviceModelEntity deviceModelEntity = this.deviceModelRepository.findById(deviceModelId);
        BigDecimal price= intUtil.getBigDecimal(map.get("price"));
        priceEntity.setUseTime(useTime);
        priceEntity.setStatus(1);
        priceEntity.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        priceEntity.setUser(userEntity);
        priceEntity.setDeviceModelEntity(deviceModelEntity);
        priceEntity.setPrice(price);
        priceEntity.setEndDateTime(Timestamp.valueOf(endDate1));
        priceEntity.setStartDateTime(Timestamp.valueOf(startDate1));
        priceEntity.setPriceName((String)map.get("priceName"));
//        priceEntity.setDeviceModelEntity();
        PriceHistoryEntity history = new PriceHistoryEntity();
        history.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        history.setEndDateTime(Timestamp.valueOf(endDate1));
        history.setStartDateTime(Timestamp.valueOf(startDate1));
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
    public Set<PriceEntity> findDevicePrice(int deviceId) {
        Set<PriceEntity> priceSet = new HashSet<>();
        List<PriceEntity> priceList1 =  priceRepository.findDevicePrice(deviceId);
        priceSet.addAll(priceList1);
        Timestamp date = new Timestamp(System.currentTimeMillis());
        for (PriceEntity p:priceSet
             ) {
            if(p.getEndDateTime().getTime()<date.getTime() && p.getEndDateTime()!=null){
                priceSet.remove(p);
            }
        }
        return priceSet;
    }

    //查询该设备当前未绑定的价格
    @Override
    @Transactional
    public List<PriceEntity> findUnDevicePrice(int deviceId) {
        List<PriceEntity> priceAll = this.priceRepository.findPriceEntitiesByMcType(deviceId,new Timestamp(System.currentTimeMillis()));
        System.out.println(priceAll);
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
        List<DeviceEntity> deviceList = this.deviceRepository.findDevicesByPlaceId((int)placeId);
        PriceEntity priceEntity = this.priceRepository.findPriceEntitiesById((int)priceId);
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

    @Override
    public List<PriceEntity> findPriceEntitiesByEnd() {
        return this.priceRepository.findPriceEntitiesByEnd();
    }

    /**
     * 查询所有导出数据
     * @return
     */
    @Override
    public List<PriceEntity> findAllPagePrice() {

        return this.priceRepository.findAllPrice();
    }



    /**
     * 导入价格绑定数据
     * @param file
     * @throws IOException
     */
    @Override
    public void getExcel(MultipartFile file) throws IOException {
        String name=file.getOriginalFilename();
        String excelName=name.substring(name.indexOf("."));
        //System.out.println(excelName);获取文件名
        if(excelName.toLowerCase().equals(".xls")){//判断文件版本
            //System.out.println(name.toLowerCase());
            HSSFWorkbook hssfWorkbook=  new HSSFWorkbook(file.getInputStream());
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            List<DevicePriceImport> devicePriceImport= new ArrayList<DevicePriceImport>();
            DevicePriceImport dp = new DevicePriceImport();
            if(hssfSheet == null){
                return;
            }
            //遍历行row
            for(int rowNum = 0; rowNum<=hssfSheet.getLastRowNum();rowNum++){
                //获取每一行
                HSSFRow row = hssfSheet.getRow(rowNum+1);
                if(row == null){
                    continue;
                }
                //遍历列cell
                for(int cellNum = 0; cellNum<=row.getLastCellNum();cellNum++){
                    //获取每一列
                    HSSFCell cell = row.getCell(cellNum);
                    if(cell == null){
                        continue;
                    }
                    Object sn = getValue(row.getCell(0));
                    Object price = getValue(row.getCell(1));
                    Object useTime =getValue( row.getCell(2));
                    int userTime=(Integer.parseInt(useTime.toString()))*60;
                    //判断sn 是否存在
                    DeviceEntity deviceEntity=   this.deviceRepository.getDeviceBySN((String) sn);
                    PriceEntity priceEntity=this.priceRepository.findAllFlag(userTime,Integer.parseInt(price.toString()),deviceEntity.getDeviceModelEntity());
                    if (deviceEntity != null && priceEntity!=null){
                            if (priceEntity.getEndDateTime()==null || priceEntity.getEndDateTime().getTime()>new Date().getTime()){
                                if(!deviceEntity.getPriceEntities().contains(priceEntity)){
                                    deviceEntity.getPriceEntities().add(priceEntity);
                                    Set<PriceEntity> priceSet = new HashSet<>();
                                    priceSet.addAll(deviceEntity.getPriceEntities());
                                    deviceEntity.getPriceEntities().clear();
                                    deviceEntity.getPriceEntities().addAll(priceSet);
                                    this.deviceRepository.save(deviceEntity);
                                }
                            }
                    }else {
                        continue;
                    }


                }
            }

        }
        if(excelName.toLowerCase().equals(".xlsx")){
            XSSFWorkbook xssfWorkbook=  new XSSFWorkbook(file.getInputStream());
        }








    }







}
