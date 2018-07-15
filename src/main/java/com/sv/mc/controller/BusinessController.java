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

@Controller
@RequestMapping("/bussinessMgr")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    /**
     * 全部查询
     * @return 返回所有行业内容
     */
    @GetMapping(value = "/allBusiness")
    public @ResponseBody
    List<BusinessEntity> getAll() {
        return this.businessService.findAllEntities();
    }
    /**
     * 根据id查询行业
     * @return 返回所有行业内容p
     */
    @RequestMapping(value = "/business",method=RequestMethod.GET)
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
    @RequestMapping(value = "/insertBussiness",method = RequestMethod.POST)
    public @ResponseBody
    BusinessEntity insertBussiness(BusinessEntity businessEntity){
        return  this.businessService.insertBusiness(businessEntity);
}

    /**
     * 更改行业分类数据
     * @param businessEntity
     * @return
     */
    @RequestMapping(value = "/updateBussiness",method = RequestMethod.POST)
    public @ResponseBody
    BusinessEntity updateBussiness(BusinessEntity businessEntity){
        return this.businessService.updateBussiness(businessEntity);

    }

    /**
     * 逻辑删除行业分类数据
     */
    @RequestMapping(value = "/deleteBussiness",method = RequestMethod.POST)
    public @ResponseBody
    void deleteBussiness(int id){
        this.businessService.deleteBussiness(id);
    }

    /**
     * 全部查询
     * @return 返回所有行业分类内容
     */
    @GetMapping(value = "/getAllBusiness")
    public @ResponseBody
    String getAllBusiness(@Param("page") String page, @Param("pageSize") String pageSize) {
        return this.businessService.findAllBusinessByPage(Integer.parseInt(page),Integer.parseInt(pageSize));
    }

}
