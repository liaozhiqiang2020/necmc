package com.sv.mc.controller;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.parser.Entity;
import javax.websocket.server.PathParam;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/deviceMgr")
public class DeviceController {
    //注入
    @Autowired
    private DeviceService deviceService;

    /**
     * 全部查询
     * @return 返回所有设备内容
     */
    @GetMapping(value = "/allDevice")
    public @ResponseBody
    List<DeviceEntity> getAll() {
        return this.deviceService.findAllEntities();
    }
    /**
     * 根据设备id查询单个设备内容
     * @param id
     * @return 单个设备内容
     */
    @RequestMapping(value = "/device",method=RequestMethod.GET)
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
     * 跳转到设备管理页面
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
//    @GetMapping(value = "/getAllDevice")
//    public @ResponseBody
//    String getAllDevice(@Param("page") String page, @Param("pageSize") String pageSize) {
//        return this.deviceService.findAllDeviceByPage(Integer.parseInt(page),Integer.parseInt(pageSize));
//    }

    /**
     * 全部查询
     * @return 返回所有设备内容
     */
    @GetMapping(value = "/getAllDevice")
    public @ResponseBody
    List<DeviceEntity> getAllDevice() {
        return this.deviceService.findAllDevice();
    }

    /**
     * 插入一条设备数据
     * @param deviceEntity
     * @return
     */
    @RequestMapping(value = "/insertDevice",method = RequestMethod.POST)
    public @ResponseBody
    DeviceEntity insertDevice(DeviceEntity deviceEntity,BindingResult bindingResult,String maintainDateTime){
        return  this.deviceService.insertDevice(deviceEntity,maintainDateTime);
    }

    /**
     * 更改设备数据
     * @param deviceEntity
     * @return
     */
    @RequestMapping(value = "/updateDevice",method = RequestMethod.POST)
    public @ResponseBody
    DeviceEntity updateDevice(DeviceEntity deviceEntity, BindingResult bindingResult,String maintainDateTime){
        return this.deviceService.updateDevice(deviceEntity,maintainDateTime);

    }

    /**
     * 逻辑删除设备数据
     */
    @RequestMapping(value = "/deleteDevice",method = RequestMethod.POST)
    public @ResponseBody
    void deleteDevice(int id){
        this.deviceService.deleteDevice(id);
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

}