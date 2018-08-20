package com.sv.mc.service.impl;

import com.sv.mc.pojo.*;
import com.sv.mc.pojo.vo.ExcelSetPriceResult;
import com.sv.mc.repository.*;
import com.sv.mc.service.PriceService;
import com.sv.mc.util.DateJsonValueProcessor;
import com.sv.mc.util.intUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static javax.xml.bind.JAXBIntrospector.getValue;

/**
 * 价格逻辑层
 *
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
     *
     * @return 所有价格
     */

    @Override
    @Transactional(readOnly = true)
    public String findAllPagePrice(int page, int pageSize) {
        int offset = ((page - 1) * pageSize);
        List<PriceEntity> priceList = this.priceRepository.findAllPriceByPage(offset, pageSize);
        int total = this.priceRepository.findPriceTotal();
        String userName = "";
        String deviceModel = "";
        String deviceType = "";
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
//        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setExcludes(new String[]{"deviceModelEntity", "user", "deviceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray


//        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray jsonArray = JSONArray.fromObject(priceList, config);//转化为jsonArray
        JSONArray jsonArray1 = new JSONArray();//新建json数组
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            userName = priceList.get(i).getUser().getName();
            int useTime = (int) jsonObject2.get("useTime");
            int newUseTime = useTime / 60;
            jsonObject2.replace("useTime", newUseTime);
            deviceModel = priceList.get(i).getDeviceModelEntity().getName();
            deviceType = priceList.get(i).getDeviceModelEntity().getModel();
            jsonObject2.put("userName", userName);
            jsonObject2.put("deviceModel", deviceModel);
            jsonObject2.put("deviceType", deviceType);
            jsonArray1.add(jsonObject2);
        }
        jsonObject.put("data", jsonArray1);
        jsonObject.put("total", total);
        return jsonObject.toString();
    }


    /**
     * 不分页查询所有价格
     *
     * @return 所有价格
     */

    @Transactional(readOnly = true)
    @Override
    public List<PriceEntity> findAllPrice() {
        return this.priceRepository.findAll();
    }


    /**
     * 不分页查询所有可用价格
     *
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
        config.setExcludes(new String[]{"deviceModelEntity", "user", "deviceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray


//        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray jsonArray = JSONArray.fromObject(priceList, config);//转化为jsonArray
        JSONArray jsonArray1 = new JSONArray();//新建json数组
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            userName = priceList.get(i).getUser().getName();
            int useTime = (int) jsonObject2.get("useTime");
            int newUseTime = useTime / 60;
            jsonObject2.replace("useTime", newUseTime);
            deviceModel = priceList.get(i).getDeviceModelEntity().getName();
            deviceType = priceList.get(i).getDeviceModelEntity().getModel();
            jsonObject2.put("userName", userName);
            jsonObject2.put("deviceModel", deviceModel);
            jsonObject2.put("deviceType", deviceType);
            jsonArray1.add(jsonObject2);
        }
        jsonObject.put("data", jsonArray1);
        jsonObject.put("total", total);
        return jsonObject.toString();
    }


    /**
     * 更新价格
     *
     * @param map 价格对象（价格对象必须包含主键ID）
     * @return 消息
     */
    @Override
    @Transactional
    public PriceEntity updatePrice(Map<String, Object> map) {
        PriceEntity priceEntity = this.priceRepository.findPriceEntitiesById((int) map.get("id"));
        PriceHistoryEntity history = new PriceHistoryEntity();
        int useTime = (int) map.get("useTime") * 60;
        String end = (String) map.get("endDateTime");
        String start = (String) map.get("startDateTime");
        priceDate(history, priceEntity, end, start);
        if (com.sv.mc.util.intUtil.isInteger((String) map.get("deviceModel"))) {
            int deviceModelId = Integer.parseInt((String) map.get("deviceModel"));
            DeviceModelEntity deviceModelEntity = this.deviceModelRepository.findById(deviceModelId);
            priceEntity.setDeviceModelEntity(deviceModelEntity);
        }
        BigDecimal price = intUtil.getBigDecimal(map.get("price"));
        priceEntity.setUseTime(useTime);
        priceEntity.setStatus(1);
        priceEntity.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        priceEntity.setPrice(price);
        priceEntity.setPriceName((String) map.get("priceName"));
        history.setPriceName((String) map.get("priceName"));
        history.setCreateDateTime(priceEntity.getCreateDateTime());
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
     *
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
        this.priceRepository.save(price);
    }

    /**
     * 根据id查询价格
     *
     * @param Id 价格ID
     * @return
     */
    @Override
    public PriceEntity findPriceById(int Id) {
        return this.priceRepository.findPriceEntitiesById(Id);
    }

    /**
     * 添加价格
     *
     * @param map 价格对象
     * @return 消息
     */
    @Override
    @Transactional
    public PriceEntity addPrice(Map<String, Object> map, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        UserEntity userEntity = this.userRepository.findUserById(user.getId());
        PriceHistoryEntity history = new PriceHistoryEntity();
        PriceEntity priceEntity = new PriceEntity();
        int useTime = (int) map.get("useTime") * 60;
        String end = (String) map.get("endDateTime");
        String start = (String) map.get("startDateTime");
        priceDate(history, priceEntity, end, start);
        int deviceModelId = Integer.parseInt((String) map.get("deviceModel"));
        DeviceModelEntity deviceModelEntity = this.deviceModelRepository.findById(deviceModelId);
        BigDecimal price = intUtil.getBigDecimal(map.get("price"));
        priceEntity.setUseTime(useTime);
        priceEntity.setStatus(1);
        priceEntity.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        priceEntity.setUser(userEntity);
        priceEntity.setDeviceModelEntity(deviceModelEntity);
        priceEntity.setPrice(price);
        priceEntity.setPriceName((String) map.get("priceName"));
//        priceEntity.setDeviceModelEntity();

        history.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        history.setPriceName((String) map.get("priceName"));
        history.setStatus(1);
        history.setUser(userEntity);
        history.setEditTime(new Timestamp(System.currentTimeMillis()));
        history.setNewPrice(price);
        history.setNewUseTime(useTime);
        history.setOldPrice(price);
        history.setOldUseTime(useTime);
        this.priceHistoryRepository.save(history);
        return this.priceRepository.save(priceEntity);

    }

    /**
     * 价格方案起止结束时间判断
     *
     * @param history
     * @param priceEntity
     * @param end
     * @param start
     */
    private void priceDate(PriceHistoryEntity history, PriceEntity priceEntity, String end, String start) {
        if (end == null && start == null) {
            priceEntity.setEndDateTime(null);
            priceEntity.setStartDateTime(null);
            history.setEndDateTime(null);
            history.setStartDateTime(null);
        } else if (end.equals(start)) {
            priceEntity.setEndDateTime(null);
            priceEntity.setStartDateTime(null);
            history.setEndDateTime(null);
            history.setStartDateTime(null);
        } else if (end != null && start != null) {
            String endDate1 = new DateJsonValueProcessor().dateString(end);
            String startDate1 = new DateJsonValueProcessor().dateString(start);
            priceEntity.setEndDateTime(Timestamp.valueOf(endDate1));
            priceEntity.setStartDateTime(Timestamp.valueOf(startDate1));
            history.setEndDateTime(Timestamp.valueOf(endDate1));
            history.setStartDateTime(Timestamp.valueOf(startDate1));
        } else if (end == null && start != null) {
            String startDate1 = new DateJsonValueProcessor().dateString(start);
            priceEntity.setEndDateTime(null);
            priceEntity.setStartDateTime(Timestamp.valueOf(startDate1));
            history.setStartDateTime(Timestamp.valueOf(startDate1));
            history.setEndDateTime(null);
        } else if (end != null && start == null) {
            String endDate1 = new DateJsonValueProcessor().dateString(end);
            priceEntity.setStartDateTime(null);
            history.setStartDateTime(null);
            priceEntity.setEndDateTime(Timestamp.valueOf(endDate1));
            history.setEndDateTime(Timestamp.valueOf(endDate1));
        }
    }

    /**
     * 批量删除或者保存价格数据
     *
     * @param priceEntityList
     * @return 页面需要回显新的价格数据
     */
    @Override
    @Transactional
    public List<PriceEntity> batchSaveOrUpdatePrice(List<PriceEntity> priceEntityList) {
        for (PriceEntity priceEntity : priceEntityList) {
            priceEntity.setUseTime(priceEntity.getUseTime() * 60);
        }
        List<PriceEntity> priceEntities = this.priceRepository.saveAll(priceEntityList);
        return priceEntities;
    }

    @Transactional
    @Override
    public List<PriceEntity> batchDeletePrice(List<PriceEntity> priceEntityList) {
        for (PriceEntity priceEntity : priceEntityList) {
            priceEntity.setStatus(0);
        }
        List<PriceEntity> priceEntities = this.priceRepository.saveAll(priceEntityList);
        return priceEntities;
    }

    /**
     * 根据设备id的价格进行查询
     *
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
        List<PriceEntity> priceList1 = priceRepository.findDevicePrice(deviceId);
        priceSet.addAll(priceList1);
        Timestamp date = new Timestamp(System.currentTimeMillis());
        for (PriceEntity p : priceSet
                ) {
            if ((p.getEndDateTime() != null && p.getEndDateTime().getTime() < date.getTime()) || p.getStatus() == 0) {
                priceSet.remove(p);
            }
        }
        return priceSet;
    }

    //查询该设备当前未绑定的价格
    @Override
    @Transactional
    public List<PriceEntity> findUnDevicePrice(int deviceId) {
        List<PriceEntity> priceAll = this.priceRepository.findPriceEntitiesByMcType(deviceId, new Timestamp(System.currentTimeMillis()));
        List<PriceEntity> priceEntityList = this.priceRepository.findDevicePrice(deviceId);
        List<PriceEntity> priceList = new ArrayList<>();
        for (PriceEntity price : priceAll
                ) {
            if (!priceEntityList.contains(price)) {
                priceList.add(price);
            }
        }

        return priceList;
    }

    @Transactional
    @Override
    public List<PriceEntity> deviceSavePrice(Map<String, Object> listMap) {
        Object deviceId = listMap.get("deviceId");
        Object priceId = listMap.get("price");
        DeviceEntity device = this.deviceRepository.findDeviceById((int) deviceId);

        PriceEntity price = findPriceById((int) priceId);
        List<PriceEntity> devicePrice = device.getPriceEntities();
        if(price.getEndDateTime() == null && price.getStartDateTime() == null){
            for (int i = 0; i < devicePrice.size(); i++) {
                if (devicePrice.get(i).getUseTime() == price.getUseTime() && (devicePrice.get(i).getStartDateTime() == null && devicePrice.get(i).getEndDateTime() == null)) {
                    device.getPriceEntities().remove(devicePrice.get(i));
                }
            }

        }else {

            for (int i = 0; i < devicePrice.size(); i++) {
            if (devicePrice.get(i).getUseTime() == price.getUseTime() && (devicePrice.get(i).getStartDateTime() != null || devicePrice.get(i).getEndDateTime() != null)) {
                device.getPriceEntities().remove(devicePrice.get(i));
            }
        }}


        device.getPriceEntities().add(price);
        this.deviceRepository.save(device);

        return device.getPriceEntities();
    }

    @Transactional
    @Override
    public List<PriceEntity> deviceDeletePrice(Map<String, Object> listMap) {
        Object deviceId = listMap.get("deviceId");
        Object price = listMap.get("price");
        DeviceEntity device = this.deviceRepository.findDeviceById((int) deviceId);
        for (int priceId : (ArrayList<Integer>) price
                ) {
            device.getPriceEntities().remove(this.priceRepository.findPriceEntitiesById(priceId));
        }
        return device.getPriceEntities();
    }

    //为场地上某种类型的机器添加价格
    @Override
    @Transactional
    public List<PriceEntity> placeAddPrice(Map<String, Object> listMap) {

        Object placeId = listMap.get("placeId");
        Object priceId = listMap.get("price");
        PriceEntity priceEntity = this.priceRepository.findPriceEntitiesById((int) priceId);
        int modelId = priceEntity.getDeviceModelEntity().getId();
        List<DeviceEntity> deviceList = this.deviceRepository.findDeviceByPlace((int)placeId,modelId);

        if (priceEntity.getEndDateTime() == null && priceEntity.getStartDateTime() == null){

            for (DeviceEntity device : deviceList
                    ) {
                List<PriceEntity> priceEntityList = device.getPriceEntities();
                if (!priceEntityList.contains(priceEntity)) {
                    for (int i = 0; i < priceEntityList.size(); i++) {
                        if (priceEntityList.get(i).getUseTime() == priceEntity.getUseTime() && (priceEntityList.get(i).getStartDateTime() == null && priceEntityList.get(i).getEndDateTime() == null)) {
                            device.getPriceEntities().remove(priceEntityList.get(i));
                        }
                    }
                    device.getPriceEntities().add(priceEntity);
                }

            }
        }else {
            for (DeviceEntity device : deviceList
                    ) {
                List<PriceEntity> priceEntityList = device.getPriceEntities();
                if (!priceEntityList.contains(priceEntity)) {
                    for (int i = 0; i < priceEntityList.size(); i++) {
                        if (priceEntityList.get(i).getUseTime() == priceEntity.getUseTime() &&(priceEntityList.get(i).getEndDateTime() != null || priceEntityList.get(i).getStartDateTime() != null)) {
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
     *
     * @param deviceCode
     * @return
     */
    @Override
    public List<Map<String, Object>> queryPriceAndTime(String deviceCode) {
        List<Map<String, Object>> listmap = new ArrayList<>();
        int deviceId = this.deviceRepository.queryDeviceIdByDeviceCode(deviceCode);
        List<PriceEntity> priceEntityList = this.priceRepository.queryPriceAndTime(deviceId);
        for (int i = 0; i < priceEntityList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            PriceEntity priceEntity = priceEntityList.get(i);
            String priceName = priceEntity.getPriceName();
            BigDecimal price = priceEntity.getPrice();
            int useTime = priceEntity.getUseTime();

            map.put("priceName", priceName);
            map.put("price", price);
            map.put("useTime", useTime / 60);
            listmap.add(map);
        }
        return listmap;
    }

    @Override
    public String findPriceEntitiesByEnd() {
        List<PriceEntity> priceList = this.priceRepository.findPriceEntitiesByEnd();

        int total = this.priceRepository.findPriceTotal();
        String userName = "";
        String deviceModel = "";
        String deviceType = "";
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
//        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setExcludes(new String[]{"deviceModelEntity", "user", "deviceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray


//        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray jsonArray = JSONArray.fromObject(priceList, config);//转化为jsonArray
        JSONArray jsonArray1 = new JSONArray();//新建json数组
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            userName = priceList.get(i).getUser().getName();
            int useTime = (int) jsonObject2.get("useTime");
            int newUseTime = useTime / 60;
            jsonObject2.replace("useTime", newUseTime);
            deviceModel = priceList.get(i).getDeviceModelEntity().getName();
            deviceType = priceList.get(i).getDeviceModelEntity().getModel();
            jsonObject2.put("userName", userName);
            jsonObject2.put("deviceModel", deviceModel);
            jsonObject2.put("deviceType", deviceType);
            jsonArray1.add(jsonObject2);
        }
        jsonObject.put("data", jsonArray1);
        jsonObject.put("total", total);
        return jsonObject.toString();
    }

    /**
     * 查询所有导出数据
     *
     * @return
     */
    @Override
    public List<PriceEntity> findAllPagePrice() {

        return this.priceRepository.findAllPrice();
    }


    /**
     * 导入价格绑定数据
     *
     * @param file
     * @throws IOException
     */
    @Override
    public Set getExcel(MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();
        Set<ExcelSetPriceResult> set = new HashSet<>();
        String excelName = name.substring(name.indexOf("."));
        //System.out.println(excelName);获取文件名
        if (excelName.toLowerCase().equals(".xls")) {//判断文件版本
            //System.out.println(name.toLowerCase());
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            if (hssfSheet == null) {
                return null;
            }
            //遍历行row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                //获取每一行
                HSSFRow row = hssfSheet.getRow(rowNum);
                ExcelSetPriceResult result = new ExcelSetPriceResult();
                if (row == null) {
                    continue;
                }
                //遍历列cell
                for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
                    //获取每一列
                    HSSFCell cell = row.getCell(cellNum);

                    if (cell == null) {

                        continue;
                    }
                    Object pricename=getValue(row.getCell(1));
                    Object sn = getValue(row.getCell(0));
                    Object price = getValue(row.getCell(2));
                    Object useTime = getValue(row.getCell(3));

                    if (pricename!=null&& sn != null && price != null && useTime != null) {
                        Double ut = Double.parseDouble(useTime.toString());
                        int userTime1 = Double.valueOf(ut).intValue();
                        int userTime = userTime1 * 60;//时间
                        BigDecimal bigDecimal = new BigDecimal(price.toString());//价格
                        bigDecimal = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);

                        //测试数据

                        //判断sn 是否存在
                        DeviceEntity deviceEntity = this.deviceRepository.getDeviceBySN(sn.toString());

                        //判断 price 是否存在
                        // DeviceModelEntity d=deviceEntity.getDeviceModelEntity();
                        PriceEntity priceEntity = this.priceRepository.findAllFlag(userTime, bigDecimal, sn.toString(),pricename.toString());
                        if (deviceEntity != null && priceEntity != null) {
                            if (priceEntity.getEndDateTime() == null || priceEntity.getEndDateTime().getTime() > new Date().getTime()) {
                                if (!deviceEntity.getPriceEntities().contains(priceEntity)) {
                                    if (deviceEntity.getDeviceModelEntity() == priceEntity.getDeviceModelEntity()) {
                                        // 1 先判断保存的价格起始截至时间是否为空
                                        List<PriceEntity> priceEntities = deviceEntity.getPriceEntities();
                                        if (priceEntity.getStartDateTime() == null && priceEntity.getEndDateTime() == null) {
                                            //为空

                                            for (int i = 0; i < priceEntities.size(); i++) {
                                                if (priceEntities.get(i).getUseTime() == userTime &&
                                                        (priceEntities.get(i).getStartDateTime() == null && priceEntities.get(i).getEndDateTime() == null)) {
                                                    priceEntities.remove(priceEntities.get(i));
                                                }
                                            }
                                        } else {

                                            for (int i = 0; i < priceEntities.size(); i++) {
                                                if (priceEntities.get(i).getUseTime() == userTime &&
                                                        (priceEntities.get(i).getStartDateTime() != null || priceEntities.get(i).getEndDateTime() != null)) {
                                                    priceEntities.remove(priceEntities.get(i));
                                                }
                                            }
                                        }
                                        priceEntities.add(priceEntity);
                                        System.out.println(deviceEntity);
                                        this.deviceRepository.save(deviceEntity);

                                    }else{
                                        result.setId(sn.toString());
                                        result.setPrice(bigDecimal);
                                        result.setTime(userTime1);
                                        result.setPriceName(pricename.toString());
                                        result.setMsg("绑定失败");
                                        set.add(result);
                                    }
                                }else{
                                    result.setId(sn.toString());
                                    result.setPrice(bigDecimal);
                                    result.setTime(userTime1);
                                    result.setPriceName(pricename.toString());
                                    result.setMsg("价格已绑定");
                                    set.add(result);
                                }
                            }else{
                                result.setId(sn.toString());
                                result.setPrice(bigDecimal);
                                result.setTime(userTime1);
                                result.setPriceName(pricename.toString());
                                result.setMsg("绑定失败");
                                set.add(result);
                            }

                        }else{
                            result.setId(sn.toString());
                            result.setPrice(bigDecimal);
                            result.setTime(userTime1);
                            result.setPriceName(pricename.toString());
                            result.setMsg("绑定失败");
                            set.add(result);
                        }
                    }
                }
            }

        }
        if (excelName.toLowerCase().equals(".xlsx")) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            if (xssfSheet == null) {
                return null;
            }
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                //获取每一行
                XSSFRow row = xssfSheet.getRow(rowNum);
                ExcelSetPriceResult result = new ExcelSetPriceResult();
                if (row == null) {
                    continue;
                }
                //遍历列cell
                for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
                    //获取每一列
                    XSSFCell cell = row.getCell(cellNum);

                    if (cell == null) {

                        continue;
                    }
                    Object pricename=getValue(row.getCell(1));
                    Object sn = getValue(row.getCell(0));
                    Object price = getValue(row.getCell(2));
                    Object useTime = getValue(row.getCell(3));

                    if (pricename!=null&& sn != null && price != null && useTime != null) {
                        Double ut = Double.parseDouble(useTime.toString());
                        int userTime1 = Double.valueOf(ut).intValue();
                        int userTime = userTime1 * 60;//时间
                        BigDecimal bigDecimal = new BigDecimal(price.toString());//价格
                        bigDecimal = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);

                        //测试数据

                        //判断sn 是否存在
                        DeviceEntity deviceEntity = this.deviceRepository.getDeviceBySN(sn.toString());

                        //判断 price 是否存在
                        // DeviceModelEntity d=deviceEntity.getDeviceModelEntity();
                        PriceEntity priceEntity = this.priceRepository.findAllFlag(userTime, bigDecimal, sn.toString(),pricename.toString());
                        if (deviceEntity != null && priceEntity != null) {
                            if (priceEntity.getEndDateTime() == null || priceEntity.getEndDateTime().getTime() > new Date().getTime()) {
                                if (!deviceEntity.getPriceEntities().contains(priceEntity)) {
                                    if (deviceEntity.getDeviceModelEntity() == priceEntity.getDeviceModelEntity()) {
                                        // 1 先判断保存的价格起始截至时间是否为空
                                        List<PriceEntity> priceEntities = deviceEntity.getPriceEntities();
                                        if (priceEntity.getStartDateTime() == null && priceEntity.getEndDateTime() == null) {
                                            //为空

                                            for (int i = 0; i < priceEntities.size(); i++) {
                                                if (priceEntities.get(i).getUseTime() == userTime &&
                                                        (priceEntities.get(i).getStartDateTime() == null && priceEntities.get(i).getEndDateTime() == null)) {
                                                    priceEntities.remove(priceEntities.get(i));
                                                }
                                            }
                                        } else {

                                            for (int i = 0; i < priceEntities.size(); i++) {
                                                if (priceEntities.get(i).getUseTime() == userTime &&
                                                        (priceEntities.get(i).getStartDateTime() != null || priceEntities.get(i).getEndDateTime() != null)) {
                                                    priceEntities.remove(priceEntities.get(i));
                                                }
                                            }
                                        }
                                        priceEntities.add(priceEntity);
                                        System.out.println(deviceEntity);
                                        this.deviceRepository.save(deviceEntity);

                                    }else{
                                        result.setId(sn.toString());
                                        result.setPrice(bigDecimal);
                                        result.setTime(userTime1);
                                        result.setPriceName(pricename.toString());
                                        result.setMsg("绑定失败");
                                        set.add(result);
                                    }
                                }else{
                                    result.setId(sn.toString());
                                    result.setPrice(bigDecimal);
                                    result.setTime(userTime1);
                                    result.setPriceName(pricename.toString());
                                    result.setMsg("价格已绑定");
                                    set.add(result);
                                }
                            }else{
                                result.setId(sn.toString());
                                result.setPrice(bigDecimal);
                                result.setTime(userTime1);
                                result.setPriceName(pricename.toString());
                                result.setMsg("绑定失败");
                                set.add(result);
                            }

                        }else{
                            result.setId(sn.toString());
                            result.setPrice(bigDecimal);
                            result.setTime(userTime1);
                            result.setPriceName(pricename.toString());
                            result.setMsg("绑定失败");
                            set.add(result);
                        }
                    }
                }
            }
        }
        return set;


    }


}
