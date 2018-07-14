package com.sv.mc.controller;

import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.pojo.VendorEntity;
import com.sv.mc.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/vendorMgr")
public class VendorController {
    //注入
    @Autowired
    private VendorService vendorService;

//    /**
//     * 全部查询
//     * @return 返回所有代理商内容
//     */
//    @GetMapping(value = "/allVendor")
//    public @ResponseBody
//    List<VendorEntity> getAllVendor() {
//        return this.vendorService.findAllEntities();
//    }
    /**
     * 根据分公司id查询单个代理商内容
     * @param id
     * @return 单个代理商内容
     */
    @RequestMapping(value = "/vendor",method=RequestMethod.GET)
    public @ResponseBody
    VendorEntity getVendorById(@PathParam("id") int id ) {
            return this.vendorService.findVendorById(id);
    }

//    /**
//     * 插入一条代理商数据
//     * @param vendor
//     * @return
//     */
//    @RequestMapping(value = "/vendor/insertVendor",method = RequestMethod.POST)
//    public @ResponseBody
//    VendorEntity insertVendor(@RequestBody VendorEntity vendor){
//        return  vendorService.insertVendor(vendor);
//    }
    /**
     * 更改代理商id更改数据
     * @param id
     * @param vendor
     * @return
     */
    @RequestMapping(value = "/vendor/updateVendor",method = RequestMethod.POST)
    public @ResponseBody
    VendorEntity updateVendor(@PathParam("id") int id,@RequestBody VendorEntity vendor){
        return vendorService.updateVendorDataById(id,vendor);

    }



    /**
     * 跳转到代理商管理页面
     * @return
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @GetMapping(value="/turnToVendorMgr")
    public ModelAndView turnToVendorMgr(){
        return new ModelAndView("./baseInfo/vendorMgr");
    }

    /**
     * 全部查询
     * @return 返回所有代理商内容
     */
    @GetMapping(value = "/allVendor")
    public @ResponseBody
    String getAllVendor(@Param("page") String page, @Param("pageSize") String pageSize) {
        return this.vendorService.findAllVendorByPage(Integer.parseInt(page),Integer.parseInt(pageSize));
    }

    /**
     * 插入一条代理商数据
     * @param vendorEntity
     * @return
     */
    @RequestMapping(value = "/insertVendor",method = RequestMethod.POST)
    public @ResponseBody
    VendorEntity insertVendor(@RequestBody VendorEntity vendorEntity){
        return this.vendorService.insertVendor(vendorEntity);
    }

    /**
     * 更改代理商数据
     * @param vendorEntity
     * @return
     */
    @RequestMapping(value = "/updateVendor",method = RequestMethod.POST)
    public @ResponseBody
    VendorEntity updateVendor(@RequestBody VendorEntity vendorEntity){
       return this.vendorService.updateVendorDataById(vendorEntity);
    }

    /**
     * 逻辑删除代理商数据
     */
    @RequestMapping(value = "/deleteVendor",method = RequestMethod.POST)
    public @ResponseBody
    void deleteVendor(@RequestBody VendorEntity vendorEntity){
        this.vendorService.deleteVendor(vendorEntity.getId());
    }

































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
