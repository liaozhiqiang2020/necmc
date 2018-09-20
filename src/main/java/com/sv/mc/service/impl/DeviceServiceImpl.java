package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.*;
import com.sv.mc.pojo.vo.ExcelSetDeviceResult;
import com.sv.mc.repository.*;
import com.sv.mc.service.DeviceModelService;
import com.sv.mc.service.DeviceService;
import com.sv.mc.service.SupplierService;
import com.sv.mc.util.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import static javax.xml.bind.JAXBIntrospector.getValue;

@Service
public class DeviceServiceImpl implements DeviceService {
        @Autowired
        private DeviceRepository deviceRepository;
        @Autowired
        private PlaceRepository placeRepository;
        @Autowired
        private DeviceModelService deviceModelService;
        @Autowired
        private SupplierService supplierService;
        @Autowired
        private SupplierRepository supplierRepository;
        @Autowired
        private DeviceModelRepository deviceModelRepository;
        @Autowired
        private GatewayRepository gatewayRepository;

        /**
         * 保存数据
         * @param device 设备数据
         * @return
         */
        @Override
        @Transactional
        public DeviceEntity save(DeviceEntity device) {
                return deviceRepository.save(device);
        }

        /**
         * 根据id查找
         * @param id  设备id
         * @return
         */
        @Override
        @Transactional
        public DeviceEntity findDeviceById(int id) {
                return deviceRepository.findDeviceById(id);
        }

        /**
         * 根据id修改
         * @param id  设备id
         * @param device
         * @return
         */
        @Override
        @Transactional
        public DeviceEntity updateDeviceById(int id, DeviceEntity device) {
                return deviceRepository.save(device);
        }

        /**
         * 插入一条新设备数据
         * @param device
         * @return
         */
        @Override
        @Transactional
        public DeviceEntity insertDevice(DeviceEntity device) {
                WxUtil wxUtil = new WxUtil();
                Timestamp nowTime = wxUtil.getNowDate();
                device.setLastCorrespondTime(nowTime);
//                device.setOfflineTime(0);
                device.setDiscardStatus(1);
                return deviceRepository.save(device);
        }

        /**
         * 查询所有设备数据
         * @return
         */
        @Override
        @Transactional
        public List<DeviceEntity> findAllEntities() {
                return deviceRepository.findAll();
        }



        @Override
        @Transactional
        public DeviceEntity updateDevice(DeviceEntity device) {
                return this.deviceRepository.save(device);
        }

        @Override
        @Transactional
        public String findAllDeviceByPage(int page, int pageSize) {
//                Gson gson = new Gson();
                DataSourceResult<DeviceEntity> deviceEntityDataSourceResult = new DataSourceResult<>();
                int offset = ((page-1)*pageSize);
                List<DeviceEntity> deviceEntityList = this.deviceRepository.findAllDeviceByPage(offset,pageSize);
                int total = this.deviceRepository.findDeviceTotal();
                deviceEntityDataSourceResult.setData(deviceEntityList);
                deviceEntityDataSourceResult.setTotal(total);
                JsonConfig config = new JsonConfig();
                config.setExcludes(new String[] { "priceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray
                JSONArray jsonArray = JSONArray.fromObject(deviceEntityDataSourceResult,config);

                return jsonArray.toString();
        }

        @Override
        @Transactional
        public void deleteDevice(int deviceId) {
                DeviceEntity deviceEntity = findDeviceById(deviceId);
                deviceEntity.setDiscardStatus(0);
                this.deviceRepository.save(deviceEntity);
        }


        @Override
        @Transactional
        public String findAllDevice(HttpSession session) {
                UserEntity userEntity = (UserEntity) session.getAttribute("user");
                int superId = userEntity.getGradeId();//1.2.3.4
                int flag = userEntity.getpId();//上级id
                List<DeviceEntity> deviceEntityList;
                int total;

                if(superId==1){
                     deviceEntityList=this.deviceRepository.findAllDevice2();
                     total =this.deviceRepository.findDeviceTotal();
                }else if(superId==2){
                     deviceEntityList= this.deviceRepository.findAllDevice3(flag);
                     total = this.deviceRepository.findAllDeviceTotal3(flag);
                }else if(superId==3){
                     deviceEntityList= this.deviceRepository.findAllDevice4(flag);
                     total = this.deviceRepository.findAllDeviceTotal4(flag);
                }else{
                     deviceEntityList= this.deviceRepository.findAllDevice5(flag);
                     total = this.deviceRepository.findAllDeviceTotal5(flag);
                }

                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
                config.setExcludes(new String[]{"priceEntities"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray

                JSONArray jsonArray = JSONArray.fromObject(deviceEntityList, config);
                JSONArray jsonArray1 = new JSONArray();
                JSONObject jsonObject1 = new JSONObject();
                for (int i = 0; i < jsonArray.size(); i++) {
                        String placeName = "";
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int placeId = Integer.parseInt(jsonObject.get("placeId").toString());

                        placeName = this.placeRepository.findPlaceById(placeId).getName();

                        jsonObject.put("placeName", placeName);
                        jsonArray1.add(jsonObject);
                }
                jsonObject1.put("data",jsonArray1.toString());
                jsonObject1.put("total",total);

                return jsonObject1.toString();
        }


        /**
         * 根据椅子sn修改椅子运行状态
         */
        @Override
        public void findChairRuningStatus(String deviceCode,int mcStatus) {
                int deviceId = this.deviceRepository.queryDeviceIdByDeviceCode(deviceCode);
                DeviceEntity deviceEntity = this.findDeviceById(deviceId);
                deviceEntity.setMcStatus(mcStatus);
                this.deviceRepository.save(deviceEntity);
        }

        /**
         * 根据椅子sn修改椅子按摩强度
         */
        @Override
        public void findChairStrength(String deviceCode, int strength) {
                int deviceId = this.deviceRepository.queryDeviceIdByDeviceCode(deviceCode);
                DeviceEntity deviceEntity = this.findDeviceById(deviceId);
                deviceEntity.setStrength(strength);
                this.deviceRepository.save(deviceEntity);
        }

        /**
         * 根据场地查询所有的设备编码
         * @param id  场地id
         */
        @Override
        public List<String> getFill_SN(int id) {
                List<String> deviceEntities = this.deviceRepository.findAllByPlaceId(id);
                return deviceEntities;
        }

        /**
         * 根据权限查询设备
         * @param id
         * @return
         */
        @Override
        public List<DeviceEntity> geyDeviceByPid(int id) {
                return this.deviceRepository.findAllDevice3(id);
        }

        /**
         * 根据场地查询设备
         * @param id
         * @return
         */
        @Override
        public List<DeviceEntity> getDeviceByplace_id(int id) {
                return this.deviceRepository.findDevicesByPlaceId(id);
        }


        /**
         * 无条件查询所有设备
         * @return
         */

        @Override
        public List<DeviceEntity> findDevice2() {
                return  this.deviceRepository.getAllDevice();
        }


        //Excel导出

        @Override
        public void getAllExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) {


                UserEntity user= (UserEntity) request.getSession().getAttribute("user");
                List<DeviceEntity> list = null;
                int id=user.getGradeId();
//                System.out.println(id);
                if (user.getGradeId()==1){
                        list =   this.deviceRepository.getAllDevice();
                }else if(user.getGradeId()==4) {
                        list = this.deviceRepository.findDevicesByPlaceId(user.getpId());

                }else{
                        list =    this.deviceRepository.findAllDevice3(user.getpId());
                }

                //标题
                String[] title = {"按摩椅编号", "模块编号","所属场地","所属型号","供应商","备注信息"};
                //文件名
                Date d = new Date();
                String time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
                String fileName = "设备信息表" + time + ".xls";
                //sheet 名
                String sheetName = "设备信息表";
                String[][] content = new String[list.size()][0];
                for (int i = 0; i < list.size(); i++) {
//                        System.out.println("进入了循环");
                        content[i] = new String[title.length];
                        DeviceEntity deviceEntity=list.get(i);
                        String obj = deviceEntity.getMcSn();
//                        System.out.println(obj);
                        String obj1 = deviceEntity.getLoraId();
//                        String obj2 =deviceEntity.getPlaceEntity().getName();
                        String obj2 = this.placeRepository.findPlaceById(deviceEntity.getPlaceId()).getName();
                        String obj3 = deviceEntity.getDeviceModelEntity().getName();
                        String obj4 = deviceEntity.getSupplierEntity().getSupplierName();
                        String obj5 = deviceEntity.getNote();

                        content[i][0] = obj;
                        content[i][1] = obj1;
                        content[i][2] = obj2;
                        content[i][3] = obj3;
                        content[i][4] = obj4;
                        content[i][5] = obj5;
                }
                //创建HSSFWorkbook
                HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
                try {
                        this.setResponseHeader(response, fileName);
                        OutputStream os = response.getOutputStream();
                        wb.write(os);
                        os.flush();
                        os.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        private void setResponseHeader(HttpServletResponse response, String fileName) {
                try {
                        try {
                                fileName = new String(fileName.getBytes(),"iso-8859-1");
                        } catch (UnsupportedEncodingException e) {

                                e.printStackTrace();
                        }
                        response.setContentType("application/octet-stream;charset=UTF-8");
                        response.setHeader("Content-Disposition", "attachment;filename=\""+ fileName+"\"");
                        response.addHeader("Pargam", "no-cache");
                        response.addHeader("Cache-Control", "no-cache");
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }


        /**
         * 导入Excel
         * @param file 用户上传的Excel 文件
         * @return
         */
        /**
         * 导入Excel
         * @param file 用户上传的Excel 文件
         * @return
         */
        @Override
        public List<ExcelSetDeviceResult> setAllExcel(MultipartFile file) throws IOException {
                WxUtil wxUtil = new WxUtil();
                boolean flag = false;
                String name = file.getOriginalFilename();
                List<ExcelSetDeviceResult> result = new ArrayList<>();

                String excelName = name.substring(name.indexOf("."));

                //System.out.println(excelName);获取文件名
                if (excelName.toLowerCase().equals(".xls")) {//判断文件版本
                        //System.out.println(name.toLowerCase());
                        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
                        // HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
                        // HSSFDataFormat format = hssfWorkbook.createDataFormat();
                        //  cellStyle.setDataFormat(format.getFormat("@"));


                        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
                        if (hssfSheet == null) {
                                return null;
                        }
                        //遍历行row
                        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                             //   System.out.println(rowNum);
                                //获取每一行
                                ExcelSetDeviceResult edr = new ExcelSetDeviceResult();
                                HSSFRow row = hssfSheet.getRow(rowNum);

                                if (row == null) {
                                        continue;
                                }
                                //遍历列cell
                                for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
                                        //获取每一列

                                        HSSFCell cell = row.getCell(cellNum);
                                        DeviceEntity deviceEntity = new DeviceEntity();
                                        if (cell == null) {

                                                continue;
                                        }
                                        Object placeName = getValue(row.getCell(0));// 1 扫描到的场地名称
                                       // System.out.println(placeName);
                                        // 2 维修时间不需要插入,直接生成null
                                        String jingdu = null;
                                        if (getValue(row.getCell(1)) != null) {
                                                jingdu = row.getCell(1).toString();
                                        }
                                        String weidu = null;
                                        if (getValue(row.getCell(2)) != null) {
                                                weidu = row.getCell(2).toString();//经度纬度如果不输入为null
                                        }


                                        Object deviceType = getValue(row.getCell(3));//设备型号名称
                                        //如果设备型号名称查不到  设备型号表创建一条新设备型号数据
                                        Object type = getValue(row.getCell(4));//型号 大中小
                                        //设备状态刚导入的一律为空闲


                                        Object sn1 = getValue(row.getCell(5));//设备编号
                                        String sn=null;
                                        DecimalFormat df = new DecimalFormat("0");
                                        if (sn1!=null){
                                                //System.out.println(sn1);
                                          if (sn1.toString().trim().length()!=0){
                                        sn=  df.format(Double.parseDouble(sn1.toString()));
                                          }
                                        }

                                        String remark = "";     //备注
                                        if (getValue(row.getCell(6)) != null) {
                                                remark = row.getCell(6).toString();
                                        }

                                        Object supplierName = getValue(row.getCell(7));//供应商

                                        Object gatsn=getValue(row.getCell(8));//网关
                                        String wg=null;
                                        if (gatsn!=null){
                                        if (gatsn.toString().trim().length()!=0) {
                                                wg = df.format(Double.parseDouble(gatsn.toString()));
                                        }
                                        }
                                       if(cellNum==8){
                                              // System.out.println(cellNum);
                                        if (placeName!=null&&deviceType!=null&&type!=null&&sn!=null&&supplierName!=null){
                                                 //System.out.println("内容不为空");
                                                if (this.placeRepository.getPlaceName(placeName.toString()) != null) {
                                                          //  System.out.println("场地存在");
                                                        if (this.deviceModelRepository.getDeviceByName(deviceType.toString(), type.toString()) != null) {
                                                                   //System.out.println("设备类型存在");
                                                                if (this.supplierRepository.getSupplierBySName(supplierName.toString()) != null) {
                                                                        //失败供应商返回集合
                                                                           // System.out.println("供应商存在");
                                                                       // if (/*设备编号重复*/this.deviceRepository.getDeviceBySn(sn.toString())==null) {
                                                                                      // System.out.println("SN不重复");
                                                                              if (this.gatewayRepository.findGatewayBySn(wg)!=null){//网关存在
                                                                                      if (weidu!=null){

                                                                                              if(!weidu.equals("")&&Integer.parseInt( df.format(Double.parseDouble(weidu)))<=999){
                                                                                                      BigDecimal bweidu = new BigDecimal(weidu);

                                                                                                      deviceEntity.setLatitude(bweidu);
                                                                                              }}
                                                                                      if (jingdu!=null){
                                                                                              if (!jingdu.equals("")&&Integer.parseInt(df.format(Double.parseDouble(jingdu)))<=999){
                                                                                                      BigDecimal bjingdu = new BigDecimal(jingdu);
                                                                                                      deviceEntity.setLongitude(bjingdu);
                                                                                              }}
                                                                                      deviceEntity.setPlaceId(this.placeRepository.getPlaceName(placeName.toString()).getId());
                                                                                      deviceEntity.setMaintainDateTime(null);

                                                                                      deviceEntity.setMcStatus(1);
                                                                                      deviceEntity.setDeviceModelEntity(this.deviceModelService.getDeviceByType(deviceType.toString(), type.toString()));
                                                                                      deviceEntity.setMcSn(sn);
                                                                                      deviceEntity.setNote(remark);
                                                                                      deviceEntity.setDiscardStatus(1);
                                                                                      deviceEntity.setMcIsNotOnline(0);
                                                                                      deviceEntity.setLastCorrespondTime(wxUtil.getNowDate());
                                                                                      deviceEntity.setSupplierEntity(this.supplierService.getSupplierBySName(supplierName.toString()));
                                                                                      deviceEntity.setLoraId(sn.toString());
                                                                                      deviceEntity.setGatewayEntity(this.gatewayRepository.findGatewayBySn(wg));
                                                                                    if (this.deviceRepository.getDeviceBySn(sn)!=null){
                                                                                           deviceEntity.setId(this.deviceRepository.getDeviceBySn(sn).getId());
                                                                                    }
                                                                                      this.deviceRepository.save(deviceEntity);

                                                                                      edr.setName(placeName.toString());
                                                                                      edr.setWeidu(weidu);
                                                                                      edr.setJingdu(jingdu);
                                                                                      edr.setDeviceType(deviceType.toString());
                                                                                      edr.setType(type.toString());
                                                                                      edr.setSn(sn);
                                                                                      edr.setBeizhu(remark);
                                                                                      edr.setSupplier(supplierName.toString());
                                                                                      edr.setGatSn(wg);
                                                                                      edr.setMsg("绑定成功");
                                                                                      result.add(edr);
                                                                              }else {
                                                                                      edr.setName(placeName.toString());
                                                                                      edr.setWeidu(weidu);
                                                                                      edr.setJingdu(jingdu);
                                                                                      edr.setDeviceType(deviceType.toString());
                                                                                      edr.setType(type.toString());
                                                                                      edr.setSn(sn.toString());
                                                                                      edr.setBeizhu(remark);
                                                                                      edr.setSupplier(supplierName.toString());
                                                                                      edr.setGatSn(wg);
                                                                                      edr.setMsg("绑定失败1");
                                                                                      result.add(edr);
                                                                              }




                                                                }else {
                                                                        edr.setName(placeName.toString());
                                                                        edr.setWeidu(weidu);
                                                                        edr.setJingdu(jingdu);
                                                                        edr.setDeviceType(deviceType.toString());
                                                                        edr.setType(type.toString());
                                                                        edr.setSn(sn.toString());
                                                                        edr.setBeizhu(remark);
                                                                        edr.setSupplier(supplierName.toString());
                                                                        edr.setMsg("绑定失败3");
                                                                        edr.setGatSn(wg);
                                                                        result.add(edr);

                                                                }

                                                        }else {
                                                                edr.setName(placeName.toString());
                                                                edr.setWeidu(weidu);
                                                                edr.setJingdu(jingdu);
                                                                edr.setDeviceType(deviceType.toString());
                                                                edr.setType(type.toString());
                                                                edr.setSn(sn.toString());
                                                                edr.setBeizhu(remark);
                                                                edr.setSupplier(supplierName.toString());
                                                                edr.setMsg("绑定失败4");
                                                                edr.setGatSn(wg);
                                                                result.add(edr);

                                                        }

                                                }else {
                                                        edr.setName(placeName.toString());
                                                        edr.setWeidu(weidu);
                                                        edr.setJingdu(jingdu);
                                                        edr.setDeviceType(deviceType.toString());
                                                        edr.setType(type.toString());
                                                        edr.setSn(sn.toString());
                                                        edr.setBeizhu(remark);
                                                        edr.setSupplier(supplierName.toString());
                                                        edr.setMsg("绑定失败5");
                                                        edr.setGatSn(wg);
                                                        result.add(edr);

                                                }
//
                                        }else {

                                                edr.setMsg("数据不完整");

                                                result.add(edr);

                                        }}else {
                                               edr.setMsg("没有数据");

                                               result.add(edr);
                                       }

                                }

                        }

                }



                if(excelName.toLowerCase().equals(".xlsx")) {
                        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                        if (xssfSheet == null) {
                                return null;
                        }
                        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                                //获取每一行
                                ExcelSetDeviceResult edr = new ExcelSetDeviceResult();
                                XSSFRow row = xssfSheet.getRow(rowNum);

                                if (row == null) {
                                        continue;
                                }
                                //遍历列cell
                                for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
                                        //获取每一列

                                        XSSFCell cell = row.getCell(cellNum);
                                        DeviceEntity deviceEntity = new DeviceEntity();
                                        if (cell == null) {

                                                continue;
                                        }
                                        Object placeName = getValue(row.getCell(0));// 1 扫描到的场地名称
                                        //System.out.println(placeName);
                                        // 2 维修时间不需要插入,直接生成null
                                        String jingdu = null;
                                        if (getValue(row.getCell(1)) != null) {
                                                jingdu = row.getCell(1).toString();
                                        }
                                        String weidu = null;
                                        if (getValue(row.getCell(2)) != null) {
                                                weidu = row.getCell(2).toString();//经度纬度如果不输入为null
                                        }


                                        Object deviceType = getValue(row.getCell(3));//设备型号名称
                                        //如果设备型号名称查不到  设备型号表创建一条新设备型号数据
                                        Object type = getValue(row.getCell(4));//型号 大中小
                                        //设备状态刚导入的一律为空闲
                                        Object sn1 = getValue(row.getCell(5));//设备编号
                                        String sn=null;
                                        DecimalFormat df = new DecimalFormat("0");
                                        if (sn1!=null){
                                                //System.out.println(sn1);
                                                if (sn1.toString().trim().length()!=0){
                                                        sn=  df.format(Double.parseDouble(sn1.toString()));
                                                }
                                        }


                                        String remark = "";     //备注
                                        if (getValue(row.getCell(6)) != null) {
                                                remark = row.getCell(6).toString();
                                        }

                                        Object supplierName = getValue(row.getCell(7));//供应商

                                        Object gatsn=getValue(row.getCell(8));//网关
                                        String wg=null;
                                        if (gatsn!=null){
                                        if (gatsn.toString().trim().length()!=0) {
                                                wg = df.format(Double.parseDouble(gatsn.toString()));
                                        }
                                        }
                                        if(cellNum==8){
                                                System.out.println(cellNum);
                                                if (placeName!=null&&deviceType!=null&&type!=null&&sn!=null&&supplierName!=null){
                                                        //System.out.println("内容不为空");
                                                        if (this.placeRepository.getPlaceName(placeName.toString()) != null) {
                                                                //  System.out.println("场地存在");
                                                                if (this.deviceModelRepository.getDeviceByName(deviceType.toString(), type.toString()) != null) {
                                                                        //System.out.println("设备类型存在");
                                                                        if (this.supplierRepository.getSupplierBySName(supplierName.toString()) != null) {
                                                                                //失败供应商返回集合
                                                                                // System.out.println("供应商存在");
                                                                                //if (/*设备编号重复*/this.deviceRepository.getDeviceBySn(sn.toString())==null) {
                                                                                        // System.out.println("SN不重复");
                                                                                        if (this.gatewayRepository.findGatewayBySn(wg)!=null){//网关存在
                                                                                                if (weidu!=null){

                                                                                                        if(!weidu.equals("")&&Integer.parseInt( df.format(Double.parseDouble(weidu)))<=999){
                                                                                                                BigDecimal bweidu = new BigDecimal(weidu);

                                                                                                                deviceEntity.setLatitude(bweidu);
                                                                                                        }}
                                                                                                if (jingdu!=null){
                                                                                                        if (!jingdu.equals("")&&Integer.parseInt(df.format(Double.parseDouble(jingdu)))<=999){
                                                                                                                BigDecimal bjingdu = new BigDecimal(jingdu);
                                                                                                                deviceEntity.setLongitude(bjingdu);
                                                                                                        }}
                                                                                                deviceEntity.setPlaceId(this.placeRepository.getPlaceName(placeName.toString()).getId());
                                                                                                deviceEntity.setMaintainDateTime(null);

                                                                                                deviceEntity.setMcStatus(1);
                                                                                                deviceEntity.setDeviceModelEntity(this.deviceModelService.getDeviceByType(deviceType.toString(), type.toString()));
                                                                                                deviceEntity.setMcSn(sn.toString());
                                                                                                deviceEntity.setNote(remark);
                                                                                                deviceEntity.setDiscardStatus(1);
                                                                                                deviceEntity.setSupplierEntity(this.supplierService.getSupplierBySName(supplierName.toString()));
                                                                                                deviceEntity.setLoraId(sn.toString());
                                                                                                deviceEntity.setGatewayEntity(this.gatewayRepository.findGatewayBySn(wg));
                                                                                                if (this.deviceRepository.getDeviceBySn(sn)!=null){
                                                                                                        deviceEntity.setId(this.deviceRepository.getDeviceBySn(sn).getId());
                                                                                                }
                                                                                                this.deviceRepository.save(deviceEntity);

                                                                                                edr.setName(placeName.toString());
                                                                                                edr.setWeidu(weidu);
                                                                                                edr.setJingdu(jingdu);
                                                                                                edr.setDeviceType(deviceType.toString());
                                                                                                edr.setType(type.toString());
                                                                                                edr.setSn(sn);
                                                                                                edr.setBeizhu(remark);
                                                                                                edr.setSupplier(supplierName.toString());
                                                                                                edr.setGatSn(wg);
                                                                                                edr.setMsg("绑定成功");
                                                                                                result.add(edr);
                                                                                        }else {
                                                                                                edr.setName(placeName.toString());
                                                                                                edr.setWeidu(weidu);
                                                                                                edr.setJingdu(jingdu);
                                                                                                edr.setDeviceType(deviceType.toString());
                                                                                                edr.setType(type.toString());
                                                                                                edr.setSn(sn.toString());
                                                                                                edr.setBeizhu(remark);
                                                                                                edr.setSupplier(supplierName.toString());
                                                                                                edr.setGatSn(wg);
                                                                                                edr.setMsg("绑定失败");
                                                                                                result.add(edr);
                                                                                        }




                                                                        }else {
                                                                                edr.setName(placeName.toString());
                                                                                edr.setWeidu(weidu);
                                                                                edr.setJingdu(jingdu);
                                                                                edr.setDeviceType(deviceType.toString());
                                                                                edr.setType(type.toString());
                                                                                edr.setSn(sn.toString());
                                                                                edr.setBeizhu(remark);
                                                                                edr.setSupplier(supplierName.toString());
                                                                                edr.setMsg("绑定失败");
                                                                                edr.setGatSn(wg);
                                                                                result.add(edr);

                                                                        }

                                                                }else {
                                                                        edr.setName(placeName.toString());
                                                                        edr.setWeidu(weidu);
                                                                        edr.setJingdu(jingdu);
                                                                        edr.setDeviceType(deviceType.toString());
                                                                        edr.setType(type.toString());
                                                                        edr.setSn(sn.toString());
                                                                        edr.setBeizhu(remark);
                                                                        edr.setSupplier(supplierName.toString());
                                                                        edr.setMsg("绑定失败");
                                                                        edr.setGatSn(wg);
                                                                        result.add(edr);

                                                                }

                                                        }else {
                                                                edr.setName(placeName.toString());
                                                                edr.setWeidu(weidu);
                                                                edr.setJingdu(jingdu);
                                                                edr.setDeviceType(deviceType.toString());
                                                                edr.setType(type.toString());
                                                                edr.setSn(sn.toString());
                                                                edr.setBeizhu(remark);
                                                                edr.setSupplier(supplierName.toString());
                                                                edr.setMsg("绑定失败");
                                                                edr.setGatSn(wg);
                                                                result.add(edr);

                                                        }
//
                                                }else {

                                                        edr.setMsg("数据不完整");

                                                        result.add(edr);

                                                }} else {edr.setMsg("没有数据");

                                        result.add(edr);}
                                }

                        }


                }
                for  ( int  i  =   0 ; i  <  result.size()  -   1 ; i ++ )  {
                        for  ( int  j  =  result.size()  -   1 ; j  >  i; j -- )  {
                                if  (result.get(j).equals(result.get(i)))  {
                                        result.remove(j);
                                }
                        }
                }
                return result;


        }
        /**
         * 导出设备模板
         * @param response
         */
        @Override
        public void getExcelModel(HttpServletResponse response) {
                //标题
                String[] title = {"场地名称*", "坐标纬度","坐标经度","设备型号名称*","型号大小*","设备编号*","备注","供应商*","网关*"};
                //文件名
                Date d = new Date();
                String time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
                String fileName = "设备信息表" + time + ".xls";
                //sheet 名
                String sheetName = "设备信息表";
                String[][] content = new String[1][8];
               /* content[0][0] = "*必填";
                content[0][1] = "";
                content[0][2] = "";
                content[0][3] = "*必填";
                content[0][4] = "*必填";
                content[0][5] = "*必填(请勿重复)";
                content[0][6] = "";
                content[0][7] = "*必填";*/

              /*  content[1][0] = "请输入地址";
                content[1][1] = "纬度(可以不填)";
                content[1][2] = "经度(可以不填)";
                content[1][3] = "设备型号名称";
                content[1][4] = "型号(大中小)";
                content[1][5] = "设备编号(请勿重复)";
                content[1][6] = "备注(可以不填)";
                content[1][7] = "供应商";*/

                //创建HSSFWorkbook
                HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
                try {
                        this.setResponseHeader(response, fileName);
                        OutputStream os = response.getOutputStream();
                        wb.write(os);
                        os.flush();
                        os.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }



        }

        @Override
        public DeviceEntity selectDeviceBYSN(String sn) {
               return this.deviceRepository.getDeviceBySN(sn);
        }




        //导出失败结果Excel
        @Override
        public void getExcelError(Set excelError1, HttpServletResponse response) {
              Set<ExcelSetDeviceResult>list= excelError1;
                String[] title = {"场地名称", "坐标纬度","坐标经度","设备型号名称","型号大小","设备编号","备注","供应商","导入结果"};
                //文件名
                Date d = new Date();
                String time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
                String fileName = "绑定结果" + time + ".xls";
                String sheetName = "设备绑定结果";

                String[][] content = new String[excelError1.size()][0];
                for (int i = 0; i < excelError1.size(); i++) {
//                        System.out.println("进入了循环");
                        content[i] = new String[title.length];
                        for(ExcelSetDeviceResult e :list){
                                String obj = e.getName();
                                String obj1 = e.getWeidu();
                                String obj2 =e.getJingdu();
                                String obj3 = e.getDeviceType();
                                String obj4 = e.getType();
                                String obj5 = e.getSn();
                                String obj6=e.getBeizhu();
                                String obj7=e.getSupplier();
                                String obj8=e.getMsg();
                                content[i][0] = obj;
                                content[i][1] = obj1;
                                content[i][2] = obj2;
                                content[i][3] = obj3;
                                content[i][4] = obj4;
                                content[i][5] = obj5;
                                content[i][6] = obj6;
                                content[i][7] = obj7;
                                content[i][8] = obj8;
                        }
                        //创建HSSFWorkbook
                        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
                        try {
                                this.setResponseHeader(response, fileName);
                                OutputStream os = response.getOutputStream();
                                wb.write(os);
                                os.flush();
                                os.close();
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }

        }

        @Override
        public void updateMcStatusToZero() {
               List<DeviceEntity> deviceList = this.deviceRepository.findDeviceEntities();//所有按摩椅状态改为待查询
                for (int i = 0; i <deviceList.size() ; i++) {
                        DeviceEntity deviceEntity = deviceList.get(i);
                        deviceEntity.setMcStatus(0);
                        this.deviceRepository.save(deviceEntity);
                }
        }
}



