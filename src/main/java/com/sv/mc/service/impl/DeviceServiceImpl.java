package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.*;
import com.sv.mc.pojo.vo.ExcelSetDeviceResult;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.repository.PlaceRepository;
import com.sv.mc.service.DeviceModelService;
import com.sv.mc.service.DeviceService;
import com.sv.mc.service.SupplierService;
import com.sv.mc.util.BaseUtil;
import com.sv.mc.util.DataSourceResult;
import com.sv.mc.util.ExcelUtil;
import net.sf.json.JSONArray;
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
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        public List<DeviceEntity> findAllDevice(HttpSession session) {
                UserEntity userEntity = (UserEntity) session.getAttribute("user");
                int superId = userEntity.getGradeId();//1.2.3.4
                int flag = userEntity.getpId();//上级id
                List<DeviceEntity> deviceEntityList;

                if(superId==1){
                     deviceEntityList=this.deviceRepository.findAllDevice2();
                }else if(superId==2){
                     deviceEntityList= this.deviceRepository.findAllDevice3(flag);
                }else if(superId==3){
                     deviceEntityList= this.deviceRepository.findAllDevice4(flag);
                }else{
                     deviceEntityList= this.deviceRepository.findAllDevice5(flag);
                }

                return deviceEntityList;
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
                System.out.println(id);
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
                        System.out.println("进入了循环");
                        content[i] = new String[title.length];
                        DeviceEntity deviceEntity=list.get(i);
                        String obj = deviceEntity.getMcSn();
                        System.out.println(obj);
                        String obj1 = deviceEntity.getLoraId();
                        String obj2 =deviceEntity.getPlaceEntity().getName();
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
        @Override
        public Set<ExcelSetDeviceResult> setAllExcel(MultipartFile file) throws IOException {
                boolean flag = false;
                String name = file.getOriginalFilename();
                Set<ExcelSetDeviceResult> result = new HashSet<>();
                ExcelSetDeviceResult edr = new ExcelSetDeviceResult();
                String excelName = name.substring(name.indexOf("."));
                DeviceEntity deviceEntity = new DeviceEntity();
                //System.out.println(excelName);获取文件名
                if (excelName.toLowerCase().equals(".xls")) {//判断文件版本
                        //System.out.println(name.toLowerCase());
                        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
                        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
                        if (hssfSheet == null) {
                                edr.setMsg("上传文件没有内容");
                        }
                        //遍历行row
                        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                                //获取每一行
                                HSSFRow row = hssfSheet.getRow(rowNum);

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
                                        Object placeName = getValue(row.getCell(0));// 1 扫描到的场地名称
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
                                        Object sn = getValue(row.getCell(5));//设备编号
                                        String remark = "";     //备注
                                        if (getValue(row.getCell(6)) != null) {
                                                remark = row.getCell(6).toString();
                                        }

                                        Object supplierName = getValue(row.getCell(7));//供应商

                                        if (this.placeRepository.getPlaceName(placeName.toString()) == null) {
                                                //失败地区返回集合
                                                edr.setName(placeName.toString());
                                                edr.setWeidu(weidu);
                                                edr.setJingdu(jingdu);
                                                edr.setDeviceType(deviceType.toString());
                                                edr.setType(type.toString());
                                                edr.setSn(sn.toString());
                                                edr.setBeizhu(remark);
                                                edr.setSupplier(supplierName.toString());
                                                edr.setMsg("绑定失败");
                                                result.add(edr);
                                                if (this.deviceModelService.getDeviceByType(deviceType.toString(), type.toString()) == null) {
                                                        //失败类型返回集合
                                                        edr.setName(placeName.toString());
                                                        edr.setWeidu(weidu);
                                                        edr.setJingdu(jingdu);
                                                        edr.setDeviceType(deviceType.toString());
                                                        edr.setType(type.toString());
                                                        edr.setSn(sn.toString());
                                                        edr.setBeizhu(remark);
                                                        edr.setSupplier(supplierName.toString());
                                                        edr.setMsg("绑定失败");
                                                        result.add(edr);
                                                        if (this.supplierService.getSupplierBySName(supplierName.toString()) == null) {
                                                                //失败供应商返回集合
                                                                edr.setName(placeName.toString());
                                                                edr.setWeidu(weidu);
                                                                edr.setJingdu(jingdu);
                                                                edr.setDeviceType(deviceType.toString());
                                                                edr.setType(type.toString());
                                                                edr.setSn(sn.toString());
                                                                edr.setBeizhu(remark);
                                                                edr.setSupplier(supplierName.toString());
                                                                edr.setMsg("绑定失败");
                                                                result.add(edr);
                                                                if (/*设备编号重复*/sn.toString().equals(this.deviceRepository.getDeviceBySn(sn.toString()).getMcSn())) {
                                                                        edr.setName(placeName.toString());
                                                                        edr.setWeidu(weidu);
                                                                        edr.setJingdu(jingdu);
                                                                        edr.setDeviceType(deviceType.toString());
                                                                        edr.setType(type.toString());
                                                                        edr.setSn(sn.toString());
                                                                        edr.setBeizhu(remark);
                                                                        edr.setSupplier(supplierName.toString());
                                                                        edr.setMsg("绑定失败");
                                                                        result.add(edr);

                                                                } else {
                                                                        BigDecimal bweidu = new BigDecimal(weidu);
                                                                        BigDecimal bjingdu = new BigDecimal(jingdu);
                                                                        deviceEntity.setPlaceEntity(this.placeRepository.getPlaceName(placeName.toString()));
                                                                        deviceEntity.setMaintainDateTime(null);
                                                                        deviceEntity.setLatitude(bweidu);
                                                                        deviceEntity.setLongitude(bjingdu);
                                                                        deviceEntity.setMcStatus(1);
                                                                        deviceEntity.setDeviceModelEntity(this.deviceModelService.getDeviceByType(deviceType.toString(), type.toString()));
                                                                        deviceEntity.setMcSn(sn.toString());
                                                                        deviceEntity.setNote(remark);
                                                                        deviceEntity.setDiscardStatus(1);
                                                                        deviceEntity.setSupplierEntity(this.supplierService.getSupplierBySName(supplierName.toString()));
                                                                        this.deviceRepository.save(deviceEntity);
                                                                        edr.setName(placeName.toString());
                                                                        edr.setWeidu(weidu);
                                                                        edr.setJingdu(jingdu);
                                                                        edr.setDeviceType(deviceType.toString());
                                                                        edr.setType(type.toString());
                                                                        edr.setSn(sn.toString());
                                                                        edr.setBeizhu(remark);
                                                                        edr.setSupplier(supplierName.toString());
                                                                        edr.setMsg("绑定成功");
                                                                        result.add(edr);
                                                                }

                                                        }

                                                }

                                        }

                                }

                        }
                }


                if(excelName.toLowerCase().equals(".xlsx")) {
                        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                        if (xssfSheet == null) {
                                edr.setMsg("上传文件没有内容");
                        }
                        //遍历行row
                        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                                //获取每一行
                                XSSFRow row = xssfSheet.getRow(rowNum);
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
                                        Object placeName = getValue(row.getCell(0));// 1 扫描到的场地名称
                                        // 2 维修时间不需要插入,直接生成null
                                        String jingdu =null;
                                        if(getValue(row.getCell(1))!=null){
                                                jingdu= row.getCell(1).toString();
                                        }
                                        String weidu = null;
                                        if (getValue(row.getCell(2))!=null){
                                                weidu=row.getCell(2).toString();//经度纬度如果不输入为null
                                        }


                                        Object deviceType = getValue(row.getCell(3));//设备型号名称
                                        //如果设备型号名称查不到  设备型号表创建一条新设备型号数据
                                        Object type=getValue(row.getCell(4));//型号 大中小
                                        //设备状态刚导入的一律为空闲
                                        Object sn=getValue(row.getCell(5));//设备编号
                                        String remark="";     //备注
                                        if (getValue(row.getCell(6))!=null){
                                                remark=row.getCell(6).toString();
                                        }

                                        Object supplierName=getValue(row.getCell(7));//供应商

                                        if (this.placeRepository.getPlaceName(placeName.toString())==null){
                                                //失败地区返回集合
                                                edr.setName(placeName.toString());
                                                edr.setWeidu(weidu);
                                                edr.setJingdu(jingdu);
                                                edr.setDeviceType(deviceType.toString());
                                                edr.setType(type.toString());
                                                edr.setSn(sn.toString());
                                                edr.setBeizhu(remark);
                                                edr.setSupplier(supplierName.toString());
                                                edr.setMsg("绑定失败");
                                                result.add(edr);
                                                if(this.deviceModelService.getDeviceByType(deviceType.toString(),type.toString())==null){
                                                        //失败类型返回集合
                                                        edr.setName(placeName.toString());
                                                        edr.setWeidu(weidu);
                                                        edr.setJingdu(jingdu);
                                                        edr.setDeviceType(deviceType.toString());
                                                        edr.setType(type.toString());
                                                        edr.setSn(sn.toString());
                                                        edr.setBeizhu(remark);
                                                        edr.setSupplier(supplierName.toString());
                                                        edr.setMsg("绑定失败");
                                                        result.add(edr);
                                                        if (this.supplierService.getSupplierBySName(supplierName.toString())==null){
                                                                //失败供应商返回集合
                                                                edr.setName(placeName.toString());
                                                                edr.setWeidu(weidu);
                                                                edr.setJingdu(jingdu);
                                                                edr.setDeviceType(deviceType.toString());
                                                                edr.setType(type.toString());
                                                                edr.setSn(sn.toString());
                                                                edr.setBeizhu(remark);
                                                                edr.setSupplier(supplierName.toString());
                                                                edr.setMsg("绑定失败");
                                                                result.add(edr);
                                                                if (/*设备编号重复*/sn.toString().equals(this.deviceRepository.getDeviceBySn(sn.toString()).getMcSn())) {
                                                                        edr.setName(placeName.toString());
                                                                        edr.setWeidu(weidu);
                                                                        edr.setJingdu(jingdu);
                                                                        edr.setDeviceType(deviceType.toString());
                                                                        edr.setType(type.toString());
                                                                        edr.setSn(sn.toString());
                                                                        edr.setBeizhu(remark);
                                                                        edr.setSupplier(supplierName.toString());
                                                                        edr.setMsg("绑定失败");
                                                                        result.add(edr);

                                                                } else {
                                                                        BigDecimal bweidu = new BigDecimal(weidu);
                                                                        BigDecimal bjingdu = new BigDecimal(jingdu);
                                                                        deviceEntity.setPlaceEntity(this.placeRepository.getPlaceName(placeName.toString()));
                                                                        deviceEntity.setMaintainDateTime(null);
                                                                        deviceEntity.setLatitude(bweidu);
                                                                        deviceEntity.setLongitude(bjingdu);
                                                                        deviceEntity.setMcStatus(1);
                                                                        deviceEntity.setDeviceModelEntity(this.deviceModelService.getDeviceByType(deviceType.toString(), type.toString()));
                                                                        deviceEntity.setMcSn(sn.toString());
                                                                        deviceEntity.setNote(remark);
                                                                        deviceEntity.setDiscardStatus(1);
                                                                        deviceEntity.setSupplierEntity(this.supplierService.getSupplierBySName(supplierName.toString()));
                                                                        this.deviceRepository.save(deviceEntity);
                                                                        edr.setName(placeName.toString());
                                                                        edr.setWeidu(weidu);
                                                                        edr.setJingdu(jingdu);
                                                                        edr.setDeviceType(deviceType.toString());
                                                                        edr.setType(type.toString());
                                                                        edr.setSn(sn.toString());
                                                                        edr.setBeizhu(remark);
                                                                        edr.setSupplier(supplierName.toString());
                                                                        edr.setMsg("绑定成功");
                                                                        result.add(edr);
                                                                }




                                                        }

                                                }

                                        }
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
                String[] title = {"场地名称", "坐标纬度","坐标经度","设备型号名称","型号大小","设备编号","备注","供应商"};
                //文件名
                Date d = new Date();
                String time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
                String fileName = "设备信息表" + time + ".xls";
                //sheet 名
                String sheetName = "设备信息表";
                String[][] content = new String[1][8];
                content[0][0] = "请输入地址";
                content[0][1] = "纬度(可以不填)";
                content[0][2] = "经度(可以不填)";
                content[0][3] = "设备型号名称";
                content[0][4] = "型号(大中小)";
                content[0][5] = "设备编号(请勿重复)";
                content[0][6] = "备注(可以不填)";
                content[0][7] = "供应商";

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


}



