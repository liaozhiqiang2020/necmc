package com.sv.mc.controller;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.UserEntity;
import com.sv.mc.service.DeviceService;
import com.sv.mc.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.parser.Entity;
import javax.websocket.server.PathParam;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class DeviceController {
    //注入
    @Autowired
    private DeviceService deviceService;

    /**
     * 全部查询
     * @return 返回所有设备内容
     */
    @GetMapping(value = "/deviceMgr/allDevice")
    public @ResponseBody
    List<DeviceEntity> getAll() {
        return this.deviceService.findAllEntities();
    }
    /**
     * 根据设备id查询单个设备内容
     * @param id
     * @return 单个设备内容
     */
    @RequestMapping(value = "/deviceMgr/device",method=RequestMethod.GET)
    public @ResponseBody
    DeviceEntity getDeviceById(@PathParam("id") int id ) {
            return this.deviceService.findDeviceById(id);
    }

//    /**
//     * 插入一条设备数据
//     * @param deivce
//     * @return
//     */
//    @RequestMapping(value = "/device/insert",method = RequestMethod.POST)
//    public @ResponseBody
//    DeviceEntity insertDevice(@RequestBody DeviceEntity deivce){
//        return  deviceService.insertDevice(deivce);
//    }
//
//    /**
//     * 根据设备id更改数据
//     * @param id
//     * @param deivce
//     * @return
//     */
//    @RequestMapping(value = "/device/update",method = RequestMethod.POST)
//    public @ResponseBody
//    DeviceEntity updateBranch(@PathParam("id") int id,@RequestBody DeviceEntity deivce){
//        return deviceService.updateDeviceById(id,deivce);
//
//    }


    /**
     * 跳转到设备库存管理页面
     * @return
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @GetMapping(value="/turnToInventoryMgr")
    public ModelAndView turnToInventoryMgr(){
        return new ModelAndView("./baseInfo/inventoryMgr");
    }

    /**
     * 跳转到设备控制页面
     * @return
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @GetMapping(value="/turnToDeviceCtrl")
    public ModelAndView turnToDeviceCtrl(){
        return new ModelAndView("./deviceManager/deviceCtrlMgr");
    }


    /**
     * 跳转到设备场地绑定页面
     * @return
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @GetMapping(value="/turnToDeviceMgr")
    public ModelAndView turnToDeviceMgr(){
        return new ModelAndView("./baseInfo/deviceMgr");
    }



//    /**
//     * 全部查询
//     * @return 返回所有设备内容
//     */
//    @GetMapping(value = "/deviceMgr/getAllDevice")
//    public @ResponseBody
//    String getAllDevice(@Param("page") String page, @Param("pageSize") String pageSize) {
//        return this.deviceService.findAllDeviceByPage(Integer.parseInt(page),Integer.parseInt(pageSize));
//    }

    /**
     * 全部查询
     * @return 返回所有设备内容
     */
    @GetMapping(value = "/deviceMgr/getAllDevice")
    public @ResponseBody
    List<DeviceEntity> getAllDevice(HttpSession session) {
        return this.deviceService.findAllDevice(session);
    }



    /**
     * 插入一条设备数据
     * @param deviceEntity
     * @return
     */
    @RequestMapping(value = "/deviceMgr/insertDevice",method = RequestMethod.POST)
    public @ResponseBody
    DeviceEntity insertDevice(@RequestBody DeviceEntity deviceEntity){
        return  this.deviceService.insertDevice(deviceEntity);
    }

    /**
     * 更改设备数据
     * @param deviceEntity
     * @return
     */
    @RequestMapping(value = "/deviceMgr/updateDevice",method = RequestMethod.POST)
    public @ResponseBody
    DeviceEntity updateDevice(@RequestBody DeviceEntity deviceEntity){
        return this.deviceService.updateDevice(deviceEntity);

    }

    /**
     * 逻辑删除设备数据
     */
    @RequestMapping(value = "/deviceMgr/deleteDevice",method = RequestMethod.POST)
    public @ResponseBody
    void deleteDevice(@RequestBody Map<String,Object> map){
        this.deviceService.deleteDevice(Integer.parseInt(map.get("id").toString()));
    }



//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
//    }

































    /**                                                                         失效代码
     *
     * 更改分公司名称
     * @param id 分公司id  , newName 新名字
     * @return BranchEntity
    @RequestMapping(value = "/branch/updateBranchName",method=RequestMethod.POST)
    public @ResponseBody BranchEntity updateBranchNameById(@PathParam("id")int id ,@RequestBody String newName) {
        BranchEntity result = new BranchEntity();
            result = branchService.updateBranchNameById(id,newName);
        return result;
    }
    /**
     * 更改分公司地址
     * @param id 分公司id , newAddress 新办公地址
     * @return BranchEntity
    @RequestMapping(value = "/branch/updateBranchAddress",method=RequestMethod.POST)
    public @ResponseBody
    BranchEntity updateBranchAddressById(@PathParam("id")int id ,@RequestBody String newAddress) {
        BranchEntity result = new BranchEntity();
        result = branchService.updateBranchAddressById(id,newAddress);
        return result;
    }
    /**
     * 更改分公司负责人
     * @param id 分公司id , newPrincipal 新分公司负责人
     * @return BranchEntity
    @RequestMapping(value = "/branch/updateBranchPrincipal",method=RequestMethod.POST)
    public @ResponseBody
    BranchEntity updateBranchPrincipalById(@PathParam("id")int id ,@RequestBody String newPrincipal) {
        BranchEntity result = new BranchEntity();
        result = branchService.updateBranchPrincipalById(id,newPrincipal);
        return result;
    }
    /**
     * 更改分公司联系电话
     * @param id 分公司id , newTelephone新分公司联系电话
     * @return BranchEntity
    @RequestMapping(value = "/branch/updateBranchTelephone",method=RequestMethod.POST)
    public @ResponseBody
    BranchEntity updateBranchTelephoneById(@PathParam("id")int id ,@RequestBody String newTelephone) {
        BranchEntity result = new BranchEntity();
        result = branchService.updateBranchTelephoneById(id,newTelephone);
        return result;
    }
    /**
     * 更改分公司联系邮箱
     * @param id 分公司id , newEmail新分公司联系邮箱
     * @return BranchEntity
    @RequestMapping(value = "/branch/updateBranchEmail",method=RequestMethod.POST)
    public @ResponseBody
    BranchEntity updateBranchEmailById(@PathParam("id")int id ,@RequestBody String newEmail) {
        BranchEntity result = new BranchEntity();
        result = branchService.updateBranchEmailById(id,newEmail);
        return result;
    }
    /**
     * 更该分公司事物状态
     * @param id 分公司id
     * @param newDeleteID delete_id
     * @return RequestBody
    @RequestMapping(value = "/branch/updateBranchAffair",method =RequestMethod.POST)
    public @ResponseBody
    BranchEntity updateBranchAffairById(@PathParam("id") int id,@RequestBody int newDeleteID){
        BranchEntity result = new BranchEntity();
        result = branchService.updateBranchAffairById(id,newDeleteID);
        return result;
    }
    */
    /**
     * 导出全部查询
     * @return 返回所有设备内容
     */
    @GetMapping(value = "/deviceMgr/getallDevice")
    public
    void getAllExcel(HttpServletRequest request,HttpServletResponse response) {
        UserEntity user= (UserEntity) request.getSession().getAttribute("user");
        List<Object> list = null;
        if (user.getGradeId()==1){
             list =    this.deviceService.findAllDevice();
        }if(user.getGradeId()==4) {
             list = this.deviceService.getDeviceByplace_id(user.getpId());
        }else{
             list =    this.deviceService.geyDeviceByPid(user.getGradeId());
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
            content[i] = new String[title.length];
            DeviceEntity deviceEntity=(DeviceEntity)list.get(i);

            String obj = deviceEntity.getMcSn();
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
                fileName = new String(fileName.getBytes(),"UTF-8");
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
