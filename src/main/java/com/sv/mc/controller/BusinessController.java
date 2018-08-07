package com.sv.mc.controller;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.BusinessEntity;
import com.sv.mc.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    /**
     * 全部查询
     * @return 返回所有行业内容
     */
    @GetMapping(value = "/bussinessMgr/allBusiness")
    public @ResponseBody
    List<BusinessEntity> getAll() {
        return this.businessService.findAllEntities();
    }

    /**
     * 全部查询(未删除的)
     * @return 返回所有行业内容
     */
    @GetMapping(value = "/bussinessMgr/allUseBusiness")
    public @ResponseBody
    List<BusinessEntity> allUseBusiness() {
        return this.businessService.allUseBusiness();
    }

    /**
     * 根据id查询行业
     * @return 返回所有行业内容p
     */
    @RequestMapping(value = "/bussinessMgr/business",method=RequestMethod.GET)
    public @ResponseBody
    BusinessEntity getBusinessById(@PathParam("id") int id ) {
        return this.businessService.fianBusinessById(id);
    }

    /**
     * 根据id修改数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/update/business",method=RequestMethod.GET)
    public @ResponseBody
    BusinessEntity updateBusinessById(@PathParam("id") int id,BusinessEntity business ) {
        return this.businessService.save(business);
    }

    /**
     * 跳转到行业分类管理页面
     * @return
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @GetMapping(value="/turnToBussinessMgr")
    public ModelAndView turnToBussinessMgr(){
        return new ModelAndView("./baseInfo/bussinessMgr");
    }


    /**
     * 插入一条行业分类数据
     * @param businessEntity
     * @return
     */
    @RequestMapping(value = "/bussinessMgr/insertBussiness",method = RequestMethod.POST)
    public @ResponseBody
    BusinessEntity insertBussiness(BusinessEntity businessEntity){
        return  this.businessService.insertBusiness(businessEntity);
}

    /**
     * 更改行业分类数据
     * @param businessEntity
     * @return
     */
    @RequestMapping(value = "/bussinessMgr/updateBussiness",method = RequestMethod.POST)
    public @ResponseBody
    BusinessEntity updateBussiness(BusinessEntity businessEntity){
        return this.businessService.updateBussiness(businessEntity);

    }

    /**
     * 逻辑删除行业分类数据
     */
    @RequestMapping(value = "/bussinessMgr/deleteBussiness",method = RequestMethod.POST)
    public @ResponseBody
    void deleteBussiness(int id){
        this.businessService.deleteBussiness(id);
    }

    /**
     * 全部查询
     * @return 返回所有行业分类内容
     */
    @GetMapping(value = "/bussinessMgr/getAllBusiness")
    public @ResponseBody
    String getAllBusiness(@Param("page") String page, @Param("pageSize") String pageSize) {

        return this.businessService.findAllBusinessByPage(Integer.parseInt(page),Integer.parseInt(pageSize));
    }

    /**
     * 查询一级分类
     * @return 返回一级分类内容
     */
    @GetMapping(value = "/bussinessMgr/getBusiness")
    public @ResponseBody
    List<BusinessEntity> findAllBusinessByLevel(Integer id){
        return this.businessService.selectBussinessByLevel(1);

    }

    /**
     * 根据上级id查询二级分类
     * @return 返回二级分类内容
     */

    @GetMapping(value = "/bussinessMgr/getBusiness2")
    public @ResponseBody
    List<BusinessEntity> findAllBusinessByLevel(@Param("id")int id){
        return this.businessService.selectBussinessByLevel2(id);

    }


    /**
     * 增加一级分类
     * @return 返回一级分类内容
     */

    @PostMapping(value = "/bussinessMgr/createBusiness1")
    public @ResponseBody
    BusinessEntity insertBusinessOne(@Param("business")BusinessEntity business){
        return this.businessService.insertBussiness1(business);
        /**
         * 增加二级分类
         * @return 返回一级分类内容
         */
    }
    @PostMapping(value = "/bussinessMgr/createBusiness2")
    public @ResponseBody
    BusinessEntity insertBusinessTwo(@RequestBody BusinessEntity business){
        System.out.println(business.toString());
        return this.businessService.insertBussiness2(business);

    }



    /**
     * 删除一级分类
     *
     */

    @PostMapping(value = "/bussinessMgr/removeBusiness1")
    public @ResponseBody
    void removeBusiness1(@Param("business")BusinessEntity business){
        this.businessService.deleteBussiness1(business);

    }



    /**
     * 删除二级分类
     *
     */

    @PostMapping(value = "/bussinessMgr/removeBusiness2")
    public @ResponseBody
    void removeBusiness2(@RequestBody BusinessEntity business){
        System.out.println(business.toString());
        this.businessService.deleteBussiness2(business);

    }


    /**
     * 修改一级分类
     * @return 返回一级分类内容
     */
    @PostMapping(value = "/bussinessMgr/uppdateBusiness1")
    public @ResponseBody
    BusinessEntity uppdateBusiness1(@Param("business")BusinessEntity business){
        this.businessService.upadateBussiness1(business);
    return business;
    }
    /**
     * 修改二级分类
     * @return 返回一级分类内容
     */
    @PostMapping(value = "/bussinessMgr/uppdateBusiness2")
    public @ResponseBody
    BusinessEntity uppdateBusiness2(@RequestBody BusinessEntity business){

        return this.businessService.upadateBussiness2(business);

    }




}
