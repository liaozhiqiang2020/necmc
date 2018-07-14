package com.sv.mc.controller;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.UserEntity;
import com.sv.mc.service.BranchService;
import com.sv.mc.util.DataSourceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;
@Controller
@RequestMapping("/branchMgr")
public class BranchController {
    //注入
    @Autowired
    private BranchService branchService;

    /**
     * 跳转到分公司管理页面
     * @return
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @GetMapping(value="/turnToBranchMgr")
    public ModelAndView turnToBranchMgr(){
        return new ModelAndView("./baseInfo/branchMgr");
    }

    /**
     * 全部查询
     * @return 返回所有分公司内容
     */
    @GetMapping(value = "/allBranchByPage")
    public @ResponseBody
    String getAllBranchsByPage(@Param("page") String page, @Param("pageSize") String pageSize) {
        return this.branchService.findAllBranchByPage(Integer.parseInt(page),Integer.parseInt(pageSize));
    }

    /**
     * 全部查询
     * @return 返回所有分公司内容(不分页)
     */
    @GetMapping(value = "/allBranch")
    public @ResponseBody
    List<BranchEntity> getAllBranchs() {
        return this.branchService.findAllEntities();
    }

    /**
     * 根据分公司id查询单个分公司内容
     * @param id
     * @return 单个分公司内容
     */
    @RequestMapping(value = "/branch",method=RequestMethod.GET)
    public @ResponseBody
        BranchEntity getbranchById(@PathParam("id") int id ) {
            return this.branchService.findBranchById(id);
    }



    /**
     * 插入一条分公司数据
     * @param branch
     * @return
     */
    @RequestMapping(value = "/insertBranch",method = RequestMethod.POST)
    public @ResponseBody
    BranchEntity insertBranch(@RequestBody BranchEntity branch){
        return  branchService.insertBranch(branch);
    }

    /**
     * 更改分公司数据
     * @param branch
     * @return
     */
    @RequestMapping(value = "/updateBranch",method = RequestMethod.POST)
    public @ResponseBody
    BranchEntity updateBranch(@RequestBody BranchEntity branch){
        return branchService.updateBranchDataById(branch);

    }

    /**
     * 逻辑删除分公司数据
     */
    @RequestMapping(value = "/deleteBranch",method = RequestMethod.POST)
    public @ResponseBody
    void deleteBranch(@RequestBody BranchEntity branch){
        branchService.deleteBranch(branch.getId());
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