package com.sv.mc.controller;

import com.sv.mc.pojo.PriceEntity;
import com.sv.mc.pojo.PriceHistoryEntity;
import com.sv.mc.service.impl.PriceHistoryServiceImpl;
import com.sv.mc.service.impl.PriceServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class PriceController {

    @Resource
    private PriceServiceImpl priceService;

    @Resource
    private PriceHistoryServiceImpl priceHistoryService;

    /**
     * 分页查询价格列表
     * @param page 当前开始页码（代码中从0开始）
     * @param pageSize 页号
     * @return
     */
    @GetMapping("/price/pageall")
    public Page<PriceEntity> findAllPagePrcie(@RequestParam String page, @RequestParam String pageSize){
        PageRequest pageRequest =new PageRequest(Integer.parseInt(page)-1,Integer.parseInt(pageSize));
        Page<PriceEntity> priceEntityPage = this.priceService.findAllPagePrice(pageRequest);
        return priceEntityPage;
    }

    /**
     * 不分页查询价格列表
     * @return 价格集合
     */
    @GetMapping("/price/all")
    public List<PriceEntity> findAllPrice(){
        List<PriceEntity> priceList = priceService.findAllPrice();
        for (PriceEntity priceEntity:priceList) {
            int useTime = priceEntity.getUseTime()/60;
            priceEntity.setUseTime(useTime);
        }
        return priceList;
    }

    /**
     * 不分页查询历史价格列表
     * @return 价格集合
     */
    @GetMapping("/price/allHistory")
    public List<PriceHistoryEntity> findAllHistoryPrice(){
        List<PriceHistoryEntity> priceList = priceHistoryService.findAllPrice();
        for (PriceHistoryEntity priceEntity:priceList) {
            int useTime = priceEntity.getUseTime()/60;
            priceEntity.setUseTime(useTime);
        }
        return priceList;
    }

    /**
     * 新建价格
     * @param priceEntity 价格对象
     */
    @PostMapping("/price/save")
    public String addPrice(@RequestBody PriceEntity priceEntity){
        this.priceService.addPrice(priceEntity);
        PriceHistoryEntity priceHistoryEntity = new PriceHistoryEntity();
        priceHistoryEntity.setUseTime(priceEntity.getUseTime());
        priceHistoryEntity.setCreateDateTime(priceEntity.getCreateDateTime());
        priceHistoryEntity.setEndDateTime(priceEntity.getEndDateTime());
        priceHistoryEntity.setLatestDateTime(new Timestamp(System.currentTimeMillis()));
        priceHistoryEntity.setPrice(priceEntity.getPrice());
        priceHistoryEntity.setStartDateTime(priceEntity.getStartDateTime());
        priceHistoryEntity.setStatus(priceEntity.getStatus());
        priceHistoryEntity.setUser(priceEntity.getUser());
        priceHistoryEntity.setDeviceEntities(priceEntity.getDeviceEntities());

        this.priceHistoryService.addPrice(priceHistoryEntity);
        return "保存成功";
    }

    /**
     * 更新价格
     * @param models 价格对象集合
     */
    @PostMapping("/price/update")
    public @ResponseBody List<PriceEntity> updatePrice(@RequestBody List<PriceEntity> models) {
        System.out.println(models);
        for (PriceEntity priceEntity : models
                ) {
            PriceHistoryEntity priceHistoryEntity = new PriceHistoryEntity();
            priceHistoryEntity.setUseTime(priceEntity.getUseTime());
            priceHistoryEntity.setCreateDateTime(priceEntity.getCreateDateTime());
            priceHistoryEntity.setEndDateTime(priceEntity.getEndDateTime());
            priceHistoryEntity.setLatestDateTime(new Timestamp(System.currentTimeMillis()));
            priceHistoryEntity.setPrice(priceEntity.getPrice());
            priceHistoryEntity.setStartDateTime(priceEntity.getStartDateTime());
            priceHistoryEntity.setStatus(priceEntity.getStatus());
            priceHistoryEntity.setUser(priceEntity.getUser());
            priceHistoryEntity.setDeviceEntities(priceEntity.getDeviceEntities());
            this.priceHistoryService.addPrice(priceHistoryEntity);
            this.priceService.updatePrice(priceEntity);
        }
        return models;
    }

    /**
     * 删除价格
     * @param priceId 价格Id
     */
    @PostMapping("/price/delete")
    public String deletePrice(@RequestBody int priceId){
        this.priceService.deletePrice(priceId);
        PriceEntity priceEntity = this.priceService.findPriceById(priceId);
        PriceHistoryEntity priceHistoryEntity = new PriceHistoryEntity();
        priceHistoryEntity.setUseTime(priceEntity.getUseTime());
        priceHistoryEntity.setCreateDateTime(priceEntity.getCreateDateTime());
        priceHistoryEntity.setEndDateTime(priceEntity.getEndDateTime());
        priceHistoryEntity.setLatestDateTime(new Timestamp(System.currentTimeMillis()));
        priceHistoryEntity.setPrice(priceEntity.getPrice());
        priceHistoryEntity.setStartDateTime(priceEntity.getStartDateTime());
        priceHistoryEntity.setStatus(priceEntity.getStatus());
        priceHistoryEntity.setUser(priceEntity.getUser());
        priceHistoryEntity.setDeviceEntities(priceEntity.getDeviceEntities());
        this.priceHistoryService.addPrice(priceHistoryEntity);
        return "删除成功";
    }

    /**
     * 根据设备id查询价格
     * @param deviceId
     * @return 当前设备的价格集合
     */
    @PostMapping("/price/devicePrice")
    public List<Object[]> findPriceByDeviceId(@RequestParam int deviceId){

     return this.priceService.findPriceByDeviceId(deviceId);

    }


//    @GetMapping("/price/status")
//    public List<PriceEntity> priceStatus(@RequestParam int status){
//        return priceService.statusPrice(status);
//    }

    /**
     * 跳转到priceTest页面
     *
     * @return
     */
    @GetMapping(value = "/priceTest")
    public ModelAndView turnToRoleMgrTest() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("./priceDemo");
        return mv;
    }

    /**
     * 跳转到priceTest页面
     *
     * @return
     */
    @GetMapping(value = "/priceTest1")
    public ModelAndView turnTopriceMgrTest() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("./priceForplace");
        return mv;
    }



}
