package com.sv.mc.controller;

import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.VendorEntity;
import com.sv.mc.service.PlaceService;
import com.sv.mc.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
public class VendorController {
    //注入
    @Autowired
    private VendorService vendorService;
    @Autowired
    private PlaceService placeService;

    /**
     * 全部查询
     * @return 返回所有代理商内容
     */
    @GetMapping(value = "/vendorMgr/getAllVendor")
    public @ResponseBody
    List<VendorEntity> getAllVendor() {
        return this.vendorService.findAllEntities();
    }
    /**
     * 根据分公司id查询单个代理商内容
     * @param id
     * @return 单个代理商内容
     */
    @RequestMapping(value = "/vendorMgr/vendor",method=RequestMethod.GET)
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
    @GetMapping(value = "/vendorMgr/allVendor")
    public @ResponseBody
    String getAllVendorByPage(@Param("page") String page, @Param("pageSize") String pageSize, HttpSession session) {
        return this.vendorService.findAllVendorByPage(Integer.parseInt(page),Integer.parseInt(pageSize),session);
    }

    /**
     * 插入一条代理商数据
     * @param
     * @return
     */
    @RequestMapping(value = "/vendorMgr/insertVendor",method = RequestMethod.POST)
    public @ResponseBody
    VendorEntity insertVendor(@RequestBody Map<String,Object> map){
        return this.vendorService.insertVendor(map);
    }

    /**
     * 更改代理商数据
     * @param
     * @return
     */
    @RequestMapping(value = "/vendorMgr/updateVendor",method = RequestMethod.POST)
    public @ResponseBody
    VendorEntity updateVendor(@RequestBody Map<String,Object> map){
       return this.vendorService.updateVendorDataById(map);
    }

    /**
     * 逻辑删除代理商数据
     */
    @RequestMapping(value = "/vendorMgr/deleteVendor",method = RequestMethod.POST)
    public @ResponseBody
    void deleteVendor(@RequestBody Map<String,Object> map){
        this.vendorService.deleteVendor(Integer.parseInt(map.get("id").toString()));
    }



    /**
     * 根据代理商id查询下面的场地
     */
    @GetMapping(value = "/vendorMgr/findAllPlaceByVendorId")
    public @ResponseBody
    List<PlaceEntity> findAllPlaceByVendorId(@Param("vendorId")int vendorId) {
        return this.vendorService.findAllPlaceByVendorId(vendorId);
    }

    /**
     * 代理商绑定场地
     */
    @PostMapping(value="/vendorMgr/vendorBoundPlace")
    public @ResponseBody
    void vendorBoundPlace(@Param("vendorId")int vendorId,@Param("placeId")int placeId){
//        String result = "绑定成功！";
//        String agreement = this.placeService.findPlaceById(placeId).getFile();
////        if(agreement==null){
////            result="请上传协议后再进行绑定操作！";
////            return result;
////        }
        this.vendorService.vendorBoundPlace(vendorId,placeId);
//        return result;
    }


    /**
     * 根据代理商id查询下面的合同
     */
    @GetMapping(value = "/vendorMgr/findContractByVendorId")
    public @ResponseBody
    String findContractByVendorId(@Param("vendorId")int vendorId) {
        return this.vendorService.findContractByVendorId(vendorId);
    }

    /**
     * 根据代理商id查询历史合同
     */
    @GetMapping(value = "/vendorMgr/findHistoryContractByVendorId")
    public @ResponseBody
    String findHistoryContractByVendorId(@Param("vendorId")int vendorId) {
        return this.vendorService.findHistoryContractByVendorId(vendorId);
    }

}
