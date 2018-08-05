package com.sv.mc.controller;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.ContractEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.UserEntity;
import com.sv.mc.service.BranchService;
import com.sv.mc.service.HeadQuartersService;
import com.sv.mc.service.PlaceService;
import com.sv.mc.service.UserService;
import com.sv.mc.util.DataSourceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;
@RestController
public class BranchController {
    //注入
    @Autowired
    private BranchService branchService;
    @Autowired
    private UserService userService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private HeadQuartersService headQuartersService;

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
    @GetMapping(value = "/branchMgr/allBranchByPage")
    public @ResponseBody
    String getAllBranchsByPage(@Param("page") String page, @Param("pageSize") String pageSize, HttpSession session) {
        return this.branchService.findAllBranchByPage(Integer.parseInt(page),Integer.parseInt(pageSize),session);
    }

    /**
     * 全部查询
     * @return 返回所有分公司内容(不分页)
     */
    @GetMapping(value = "/branchMgr/allBranch")
    public @ResponseBody
    List<BranchEntity> getAllBranchs() {
        return this.branchService.findAllEntities();
    }

    /**
     * 根据分公司id查询单个分公司内容
     * @param id
     * @return 单个分公司内容
     */
    @RequestMapping(value = "/branchMgr/branch",method=RequestMethod.GET)
    public @ResponseBody
    BranchEntity getbranchById(@PathParam("id") int id ) {
            return this.branchService.findBranchById(id);
    }

    /**
     * 插入一条分公司数据
     * @param branch
     * @return
     */
    @RequestMapping(value = "/branchMgr/insertBranch",method = RequestMethod.POST)
    public @ResponseBody
    BranchEntity insertBranch(@RequestBody BranchEntity branch){
        return  branchService.insertBranch(branch);
    }

    /**
     * 更改分公司数据
     * @param branch
     * @return
     */
    @RequestMapping(value = "/branchMgr/updateBranch",method = RequestMethod.POST)
    public @ResponseBody
    BranchEntity updateBranch(@RequestBody BranchEntity branch){
        return branchService.updateBranchDataById(branch);
    }

    /**
     * 逻辑删除分公司数据
     */
    @RequestMapping(value = "/branchMgr/deleteBranch",method = RequestMethod.POST)
    public @ResponseBody
    void deleteBranch(@RequestBody BranchEntity branch){
        branchService.deleteBranch(branch.getId());
    }



    /**
     * 全部查询
     * @return 返回所有分公司和总公司内容
     */
    @GetMapping(value = "/branchMgr/allBranchAndHead")
    public @ResponseBody
    String allBranchAndHead() {
        return this.branchService.allBranchAndHead();
    }

    /**
     * 全部查询
     * @return 返回所有分公司和总公司和代理商内容
     */
    @GetMapping(value = "/branchMgr/allBranchAndHeadAndVendor")
    public @ResponseBody
    String allBranchAndHeadAndVendor() {
        return this.branchService.allBranchAndHeadAndVendor();
    }


    /**
     * 查询所有状态为1的用户
     */
    @GetMapping(value="/branchMgr/findAllByStatus")
    public @ResponseBody
    List<UserEntity> findAllByStatus(){
        return this.branchService.findAllByStatus();
    }


    /**
     * 根据分公司id查询下面的场地
     */
    @GetMapping(value = "/branchMgr/findAllPlaceByBranchId")
    public @ResponseBody
    List<PlaceEntity> findAllPlaceByBranchId(@Param("branchId")int branchId) {
        return this.branchService.findAllPlaceByBranchId(branchId);
    }

    /**
     * 根据分公司id查询下面的合同
     */
    @GetMapping(value = "/branchMgr/findContractByBranchId")
    public @ResponseBody
    String findContractsByBranchId(@Param("branchId")int branchId) {
        return this.branchService.findContractsByBranchId(branchId);
    }

    /**
     * 根据分公司id查询历史合同
     */
    @GetMapping(value = "/branchMgr/findHistoryContractByBranchId")
    public @ResponseBody
    String findHistoryContractByBranchId(@Param("branchId")int branchId) {
        return this.branchService.findHistoryContractByBranchId(branchId);
    }



    /**
     * 分公司绑定场地
     */
    @PostMapping(value="/branchMgr/branchBoundPlace")
    public @ResponseBody
    void branchBoundPlace(@Param("branchId")int branchId,@Param("placeId")int placeId){
//        String result = "绑定成功！";
//        String agreement = this.placeService.findPlaceById(placeId).getFile();
//        if(agreement==null){
//            result="请上传协议后再进行绑定操作！";
//            return result;
//        }
        this.branchService.branchBoundPlace(branchId,placeId);
//        return result;
    }
}
