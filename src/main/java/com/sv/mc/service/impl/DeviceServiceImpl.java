package com.sv.mc.service.impl;

import com.google.gson.Gson;
import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.UserEntity;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.service.DeviceService;
import com.sv.mc.util.BaseUtil;
import com.sv.mc.util.DataSourceResult;
import com.sv.mc.util.ExcelUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
        @Autowired
        private DeviceRepository deviceRepository;

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




        }

